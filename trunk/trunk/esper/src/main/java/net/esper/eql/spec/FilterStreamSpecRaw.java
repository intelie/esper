package net.esper.eql.spec;

import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.StreamTypeServiceImpl;
import net.esper.eql.expression.*;
import net.esper.event.EventAdapterException;
import net.esper.event.EventAdapterService;
import net.esper.event.EventType;
import net.esper.filter.*;
import net.esper.type.RelationalOpEnum;
import net.esper.view.ViewSpec;
import net.esper.util.JavaClassHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class FilterStreamSpecRaw extends StreamSpecBase implements StreamSpecRaw
{
    protected final static String PROPERTY_NAME_BOOLEAN_EXPRESSION = "boolean_expression";

    private FilterSpecRaw rawFilterSpec;

    public FilterStreamSpecRaw(FilterSpecRaw rawFilterSpec, List<ViewSpec> viewSpecs, String optionalStreamName)
    {
        super(optionalStreamName, viewSpecs);
        this.rawFilterSpec = rawFilterSpec;
    }

    public FilterSpecRaw getRawFilterSpec()
    {
        return rawFilterSpec;
    }

    public StreamSpecCompiled compile(EventAdapterService eventAdapterService,
                                      AutoImportService autoImportService)
            throws ExprValidationException
    {
        // Determine the event type
        String eventName = rawFilterSpec.getEventTypeAlias();
        EventType eventType = resolveType(eventName, eventAdapterService);

        // Validate all nodes, make sure each returns a boolean and types are good;
        // Also decompose all AND super nodes into individual expressions
        StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {eventType}, new String[] {"s0"});
        List<ExprNode> constituents = validateAndDecompose(rawFilterSpec.getFilterExpressions(), streamTypeService, autoImportService);

        FilterSpec spec = makeFilterSpec(eventType, constituents);
        
        return new FilterStreamSpecCompiled(spec, this.getViewSpecs(), this.getOptionalStreamName());
    }

    protected static FilterSpec makeFilterSpec(EventType eventType, List<ExprNode> constituents)
            throws ExprValidationException
    {
        // Make filter parameter for each expression node
        List<FilterSpecParam> filterParams = new ArrayList<FilterSpecParam>();
        List<ExprNode> unoptimizedNodes = new ArrayList<ExprNode>();
        for (ExprNode constituent : constituents)
        {
            FilterSpecParam param = makeFilterParam(constituent);
            if (param == null)
            {
                unoptimizedNodes.add(constituent);
            }
            else
            {
                filterParams.add(param);
            }
        }

        // any unoptimized expression nodes are put under one AND
        ExprNode exprNode = null;
        if (!unoptimizedNodes.isEmpty())
        {
            if (unoptimizedNodes.size() == 1)
            {
                exprNode = unoptimizedNodes.get(0);
            }
            else
            {
                ExprAndNode andNode = new ExprAndNode();
                for (ExprNode unoptimized : unoptimizedNodes)
                {
                    andNode.addChildNode(unoptimized);
                }
                exprNode = andNode;
            }
        }

        // if there are boolean expressions, add
        if (exprNode != null)
        {
            FilterSpecParamExprNode param = new FilterSpecParamExprNode(PROPERTY_NAME_BOOLEAN_EXPRESSION, FilterOperator.BOOLEAN_EXPRESSION, exprNode);
            filterParams.add(param);
        }

        return new FilterSpec(eventType, filterParams);        
    }

    protected static EventType resolveType(String eventName, EventAdapterService eventAdapterService)
            throws ExprValidationException
    {
        EventType eventType = eventAdapterService.getEventType(eventName);

        // The type is not known yet, attempt to add as a JavaBean type with the same alias
        if (eventType == null)
        {
            try
            {
                eventType = eventAdapterService.addBeanType(eventName, eventName);
            }
            catch (EventAdapterException ex)
            {
                throw new ExprValidationException("Failed to resolve event type: " + ex.getMessage());
            }
        }

        return eventType;
    }

    protected static FilterSpecParam makeFilterParam(ExprNode constituent)
            throws ExprValidationException
    {
        // Is this expresson node a simple compare, i.e. a=5 or b<4; these can be indexed
        if ((constituent instanceof ExprEqualsNode) ||
            (constituent instanceof ExprRelationalOpNode))
        {
            FilterSpecParam param = handleEqualsAndRelOp(constituent);
            if (param != null)
            {
                return param;
            }
        }

        // Is this expresson node a simple compare, i.e. a=5 or b<4; these can be indexed
        if (constituent instanceof ExprInNode)
        {
            FilterSpecParam param = handleInSetNode((ExprInNode)constituent);
            if (param != null)
            {
                return param;
            }
        }

        if (constituent instanceof ExprBetweenNode)
        {
            FilterSpecParam param = handleRangeNode((ExprBetweenNode)constituent);
            if (param != null)
            {
                return param;
            }
        }

        return null;
    }

    private static FilterSpecParam handleRangeNode(ExprBetweenNode betweenNode)
    {                   
        ExprNode left = betweenNode.getChildNodes().get(0);
        if (left instanceof ExprIdentNode)
        {
            ExprIdentNode identNode = (ExprIdentNode) left;
            String propertyName = identNode.getResolvedPropertyName();
            FilterOperator op = FilterOperator.parseRangeOperator(betweenNode.isLowEndpointIncluded(), betweenNode.isHighEndpointIncluded(),
                    betweenNode.isNotBetween());

            FilterSpecParamRangeValue low = handleRangeNodeEndpoint(betweenNode.getChildNodes().get(1));
            FilterSpecParamRangeValue high = handleRangeNodeEndpoint(betweenNode.getChildNodes().get(2));

            if ((low != null) && (high != null))
            {
                return new FilterSpecParamRange(propertyName, op, low, high);
            }
        }
        return null;
    }

    private static FilterSpecParamRangeValue handleRangeNodeEndpoint(ExprNode endpoint)
    {
        // constant
        if (endpoint instanceof ExprConstantNode)
        {
            ExprConstantNode node = (ExprConstantNode) endpoint;
            Number result = (Number) node.evaluate(null, true);
            return new RangeValueDouble(result.doubleValue());
        }

        // or property
        if (endpoint instanceof ExprIdentNode)
        {
            ExprIdentNode identNodeInner = (ExprIdentNode) endpoint;
            return new RangeValueEventProp(identNodeInner.getResolvedStreamName(), identNodeInner.getResolvedPropertyName());
        }
        
        return null;
    }

    private static FilterSpecParam handleInSetNode(ExprInNode constituent)
            throws ExprValidationException
    {
        ExprNode left = constituent.getChildNodes().get(0);
        if (left instanceof ExprIdentNode)
        {
            ExprIdentNode identNodeInSet = (ExprIdentNode) left;
            String propertyName = identNodeInSet.getResolvedPropertyName();
            FilterOperator op = FilterOperator.IN_LIST_OF_VALUES;
            if (constituent.isNotIn())
            {
                op = FilterOperator.NOT_IN_LIST_OF_VALUES;
            }

            List<FilterSpecParamInValue> listofValues = new ArrayList<FilterSpecParamInValue>();
            Iterator<ExprNode> it = constituent.getChildNodes().iterator();
            it.next();  // ignore the first node as it's the identifier
            boolean isAllConstants = true;
            while (it.hasNext())
            {
                ExprNode subNode = it.next();
                if (subNode instanceof ExprConstantNode)
                {
                    ExprConstantNode constantNode = (ExprConstantNode) subNode;
                    Object constant = constantNode.evaluate(null, true);
                    constant = handleConstantsCoercion(identNodeInSet, constant);                    
                    listofValues.add(new InSetOfValuesConstant(constant));
                }
                if (subNode instanceof ExprIdentNode)
                {
                    isAllConstants = false;
                    ExprIdentNode identNodeInner = (ExprIdentNode) subNode;

                    boolean isMustCoerce = false;
                    Class numericCoercionType = identNodeInSet.getType();
                    if (identNodeInner.getType() != identNodeInSet.getType())
                    {
                        if (JavaClassHelper.isNumeric(identNodeInSet.getType()))
                        {
                            if (!JavaClassHelper.canCoerce(identNodeInner.getType(), identNodeInSet.getType()))
                            {
                                throwConversionError(identNodeInner.getType(), identNodeInSet.getType(), identNodeInSet.getResolvedPropertyName());
                            }
                            isMustCoerce = true;
                        }
                    }
                    listofValues.add(new InSetOfValuesEventProp(identNodeInner.getResolvedStreamName(), identNodeInner.getResolvedPropertyName(), isMustCoerce, numericCoercionType));
                }
            }

            // Fallback if not all values in the in-node can be resolved to properties or constants
            if (listofValues.size() == constituent.getChildNodes().size() - 1)
            {
                return new FilterSpecParamIn(propertyName, op, listofValues, isAllConstants, constituent.getCoercionType());
            }
        }
        return null;
    }

    private static FilterSpecParam handleEqualsAndRelOp(ExprNode constituent)
            throws ExprValidationException
    {
        FilterOperator op;
        if (constituent instanceof ExprEqualsNode)
        {
            op = FilterOperator.EQUAL;
            if (((ExprEqualsNode) constituent).isNotEquals())
            {
                op = FilterOperator.NOT_EQUAL;
            }
        }
        else
        {
            ExprRelationalOpNode relNode = (ExprRelationalOpNode) constituent;
            if (relNode.getRelationalOpEnum() == RelationalOpEnum.GT)
            {
                op = FilterOperator.GREATER;
            }
            else if (relNode.getRelationalOpEnum() == RelationalOpEnum.LT)
            {
                op = FilterOperator.LESS;
            }
            else if (relNode.getRelationalOpEnum() == RelationalOpEnum.LE)
            {
                op = FilterOperator.LESS_OR_EQUAL;
            }
            else if (relNode.getRelationalOpEnum() == RelationalOpEnum.GE)
            {
                op = FilterOperator.GREATER_OR_EQUAL;
            }
            else
            {
                throw new IllegalStateException("Opertor '" + relNode.getRelationalOpEnum() + "' not mapped");
            }
        }

        ExprNode left = constituent.getChildNodes().get(0);
        ExprNode right = constituent.getChildNodes().get(1);

        // check identifier and constant combination
        if ((right instanceof ExprConstantNode) && (left instanceof ExprIdentNode))
        {
            ExprIdentNode identNode = (ExprIdentNode) left;
            ExprConstantNode constantNode = (ExprConstantNode) right;
            String propertyName = identNode.getResolvedPropertyName();
            Object constant = constantNode.evaluate(null, true);
            constant = handleConstantsCoercion(identNode, constant);

            return new FilterSpecParamConstant(propertyName, op, constant);
        }
        if ((left instanceof ExprConstantNode) && (right instanceof ExprIdentNode))
        {
            ExprIdentNode identNode = (ExprIdentNode) right;
            ExprConstantNode constantNode = (ExprConstantNode) left;
            String propertyName = identNode.getResolvedPropertyName();
            Object constant = constantNode.evaluate(null, true);
            constant = handleConstantsCoercion(identNode, constant);
            return new FilterSpecParamConstant(propertyName, op, constant);
        }
        // check identifier and expression containing other streams
        if (left instanceof ExprIdentNode)
        {
            ExprIdentNode identNodeLeft = (ExprIdentNode) left;
            ExprIdentNode identNodeRight = (ExprIdentNode) right;

            if ((identNodeLeft.getStreamId() == 0) && (identNodeRight.getStreamId() != 0))
            {
                return handleProperty(op, identNodeLeft, identNodeRight);
            }
            if ((identNodeRight.getStreamId() == 0) && (identNodeLeft.getStreamId() != 0))
            {
                return handleProperty(op, identNodeRight, identNodeLeft);
            }
        }
        return null;
    }

    private static FilterSpecParamEventProp handleProperty(FilterOperator op, ExprIdentNode identNodeLeft, ExprIdentNode identNodeRight)
            throws ExprValidationException
    {
        String propertyName = identNodeLeft.getResolvedPropertyName();

        boolean isMustCoerce = false;
        Class numericCoercionType = identNodeLeft.getType();
        if (identNodeRight.getType() != identNodeLeft.getType())
        {
            if (JavaClassHelper.isNumeric(identNodeRight.getType()))
            {
                if (!JavaClassHelper.canCoerce(identNodeRight.getType(), identNodeLeft.getType()))
                {
                    throwConversionError(identNodeRight.getType(), identNodeLeft.getType(), identNodeLeft.getResolvedPropertyName());
                }
                isMustCoerce = true;
            }
        }

        return new FilterSpecParamEventProp(propertyName, op, identNodeRight.getResolvedStreamName(), identNodeRight.getResolvedPropertyName(),
                isMustCoerce, numericCoercionType);
    }

    private static void throwConversionError(Class fromType, Class toType, String propertyName)
            throws ExprValidationException
    {
        String text = "Implicit conversion from datatype '" +
                fromType.getSimpleName() +
                "' to '" +
                toType.getSimpleName() +
                "' for property '" +
                propertyName +
                "' is not allowed (strict filter type coercion)";
        throw new ExprValidationException(text);
    }

    // expressions automatically coerce to the most upwards type
    // filters require the same type
    private static Object handleConstantsCoercion(ExprIdentNode identNode, Object constant)
            throws ExprValidationException
    {
        Class identNodeType = identNode.getType();
        if (!JavaClassHelper.isNumeric(identNodeType))
        {
            return constant;    // no coercion required, other type checking performed by expression this comes from
        }

        if (!JavaClassHelper.canCoerce(constant.getClass(), identNodeType))
        {
            throwConversionError(constant.getClass(), identNodeType, identNode.getResolvedPropertyName());
        }
        
        Class identNodeTypeBoxed = JavaClassHelper.getBoxedType(identNodeType);
        return JavaClassHelper.coerceBoxed((Number) constant, identNodeTypeBoxed);
    }

    private static void recursiveAndConstituents(List<ExprNode> constituents, ExprNode exprNode)
    {
        for (ExprNode inner : exprNode.getChildNodes())
        {
            if (inner instanceof ExprAndNode)
            {
                recursiveAndConstituents(constituents, inner);
            }
            else
            {
                constituents.add(inner);
            }
        }
    }

    protected static List<ExprNode> validateAndDecompose(List<ExprNode> exprNodes, StreamTypeService streamTypeService, AutoImportService autoImportService)
            throws ExprValidationException
    {
        List<ExprNode> validatedNodes = new ArrayList<ExprNode>();
        for (ExprNode node : exprNodes)
        {
            ExprNode validated = node.getValidatedSubtree(streamTypeService, autoImportService, null);
            validatedNodes.add(validated);

            if (validated.getType() != Boolean.class)
            {
                throw new ExprValidationException("Filter expression not returning a boolean value: '" + validated.toExpressionString() + "'");
            }
        }

        // Break a top-level AND into constituent expression nodes
        List<ExprNode> constituents = new ArrayList<ExprNode>();
        for (ExprNode validated : validatedNodes)
        {
            if (validated instanceof ExprAndNode)
            {
                recursiveAndConstituents(constituents, validated);
            }
            else
            {
                constituents.add(validated);
            }
        }

        return constituents;
    }
}
