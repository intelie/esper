package net.esper.eql.parse;

import net.esper.filter.*;
import net.esper.event.EventType;
import net.esper.event.EventAdapterException;
import net.esper.type.PrimitiveValue;
import net.esper.type.PrimitiveValueFactory;
import net.esper.type.PrimitiveValueType;
import net.esper.eql.generated.EqlTokenTypes;
import net.esper.event.EventAdapterService;
import net.esper.event.EventAdapterException;
import net.esper.event.EventAdapterService;
import net.esper.core.EPAdministratorImpl;
import net.esper.util.JavaClassHelper;
import antlr.collections.AST;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Builds a filter specification from filter AST nodes.
 */
public class ASTFilterSpecHelper implements EqlTokenTypes
{
    /**
     * Returns the name tag for the event in the filter spec, if any, else null.
     * @param filterAST
     * @return event name tag or null if none specified
     */
    public static String getEventNameTag(AST filterAST)
    {
        AST tagNode = filterAST.getFirstChild();
        if ( (tagNode != null) && (tagNode.getType() == EVENT_FILTER_NAME_TAG) )
        {
            return tagNode.getText();
        }
        return null;
    }

    /**
     * Creates a filter specification for the AST representing the filter expression.
     * @param filterAST - root filter AST node
     * @param optionalTaggedEventTypes - event type for each named event if named events are allowed in filter
     * @param eventAdapterService - service for resolving event names to known event types
     * @return filter spec
     * @throws ASTFilterSpecValidationException if the filter spec cannot be validate
     */
    public static FilterSpec buildSpec(AST filterAST, 
                                       Map<String, EventType> optionalTaggedEventTypes,
                                       EventAdapterService eventAdapterService)
        throws ASTFilterSpecValidationException
    {
        // Ignore the event name tag if the
        AST startNode = filterAST.getFirstChild();
        if (startNode.getType() == EVENT_FILTER_NAME_TAG)
        {
            startNode = startNode.getNextSibling();
        }

        String eventName = startNode.getText();
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
                throw new ASTFilterSpecValidationException("Failed to resolve event type: " + ex.getMessage(), ex);
            }
        }

        // Create parameter list
        AST paramNodeAST = startNode.getNextSibling();
        List<FilterSpecParam> parameters = new LinkedList<FilterSpecParam>();
        while(paramNodeAST != null)
        {
            if (paramNodeAST.getType() != EVENT_FILTER_PARAM)
            {
                throw new IllegalStateException("Expected filter parameter node but received node type " + paramNodeAST.getType());
            }
            FilterSpecParam filterParam = makeParameter(paramNodeAST, eventType);
            parameters.add(filterParam);
            paramNodeAST = paramNodeAST.getNextSibling();
        }

        FilterSpec filterSpec = new FilterSpec(eventType, parameters);
        FilterSpecValidator.validate(filterSpec, optionalTaggedEventTypes);

        return filterSpec;
    }

    private static FilterSpecParam makeParameter(AST filterParamNode, EventType eventType)
        throws ASTFilterSpecValidationException
    {
        AST propertyIdentNode = filterParamNode.getFirstChild();
        String propertyName;
        // The parameter name can be a simple identifier or event property expression (mapped/indexed/nested/combined)
        if (propertyIdentNode.getType() == IDENT)
        {
            propertyName = propertyIdentNode.getText();
        }
        else if (propertyIdentNode.getType() == EVENT_PROP_EXPR)
        {
            propertyName = getPropertyName(propertyIdentNode.getFirstChild());
        }
        else
        {
            throw new IllegalStateException("AST property name node type unknown, type=" + propertyIdentNode.getType());
        }

        // get property type, check it exists
        Class propertyType = eventType.getPropertyType(propertyName);
        if (propertyType == null)
        {
            throw new ASTFilterSpecValidationException("Property named '" + propertyName
                    + "' not found in class " + eventType.getUnderlyingType().getName());
        }

        // Get type representation
        PrimitiveValue primitiveValue = PrimitiveValueFactory.create(propertyType);
        if (primitiveValue == null)
        {
            throw new ASTFilterSpecValidationException("Property named '" + propertyName
                    + "' of type '" +
                    propertyType.getName() +
                    "' is not supported type");
        }

        AST filterCompareNode = filterParamNode.getFirstChild().getNextSibling();
        int nodeType = filterCompareNode.getType();
        if (nodeType == IN)
        {
            if (!JavaClassHelper.isNumeric(propertyType))
            {
                throw new ASTFilterSpecValidationException("Property named '" + propertyName
                        + "' of type '" +
                        propertyType.getName() +
                        "' not numeric as required for ranges");
            }
            return createRangeParam(propertyName, primitiveValue, filterCompareNode);
        }
        else if ((nodeType == EQUALS) || (nodeType == LT) || (nodeType == LE) ||
                 (nodeType == GT) || (nodeType == GE))
        {
            return createNonRangeParam(propertyName, primitiveValue, filterCompareNode);
        }
        else
        {
            throw new IllegalStateException("Invalid node type for filter parameter, type=" + nodeType);
        }
    }

    /**
     * Return the generated property name that is defined by the AST child node and it's siblings.
     * @param propertyNameExprChildNode is the child node from which to start putting the property name together
     * @return property name, ie. indexed[1] or mapped('key') or nested.nested or a combination or just 'simple'.
     */
    protected static String getPropertyName(AST propertyNameExprChildNode)
    {
        StringBuffer buffer = new StringBuffer();
        String delimiter = "";
        AST child = propertyNameExprChildNode;

        do
        {
            buffer.append(delimiter);

            switch (child.getType()) {
                case EVENT_PROP_SIMPLE:
                    buffer.append(child.getFirstChild().getText());
                    break;
                case EVENT_PROP_MAPPED:
                    buffer.append(child.getFirstChild().getText());
                    buffer.append("(");
                    buffer.append(child.getFirstChild().getNextSibling().getText());
                    buffer.append(")");
                    break;
                case EVENT_PROP_INDEXED:
                    buffer.append(child.getFirstChild().getText());
                    buffer.append("[");
                    buffer.append(child.getFirstChild().getNextSibling().getText());
                    buffer.append("]");
                    break;
                default:
                    throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
            }

            delimiter = ".";
            child = child.getNextSibling();
        }
        while (child != null);

        return buffer.toString();
    }

    private static FilterSpecParam createNonRangeParam(String propertyName, PrimitiveValue primitiveValue, AST filterParamNode)
        throws ASTFilterSpecValidationException
    {
        FilterOperator operator = FilterOperator.parseComparisonOperator(filterParamNode.getText());

        // Deal with use-result filter parameters
        if (filterParamNode.getFirstChild().getType() == EVENT_FILTER_IDENT)
        {
            AST identRoot = filterParamNode.getFirstChild();
            String eventAsName = identRoot.getFirstChild().getText();
            String eventProperty = identRoot.getFirstChild().getNextSibling().getText();
            return new FilterSpecParamEventProp(propertyName, operator, eventAsName, eventProperty);
        }

        // Deal with constants
        AST constantNode = filterParamNode.getFirstChild();
        int constantType = constantNode.getType();
        if (!ASTConstantHelper.canConvert(constantType, primitiveValue.getType()))
        {
            String message = getConversionErrorMsg(constantType, primitiveValue.getType(), propertyName);
            throw new ASTFilterSpecValidationException(message);
        }

        String stringValue = constantNode.getText();
        try
        {
            primitiveValue.parse(stringValue);
        }
        catch (RuntimeException ex)
        {
            String message = "Conversion from datatype '" +
                    ASTConstantHelper.getConstantTypeName(constantType) +
                    "' to '" +
                    primitiveValue.getType().toString() +
                    "' for property '" +
                    propertyName +
                    "' failed with error:" + ex.getMessage();
            log.debug(".createNonRangeParam " + message, ex);
            throw new ASTFilterSpecValidationException(ex.getMessage());
        }
        Object value = primitiveValue.getValueObject();

        return new FilterSpecParamConstant(propertyName, operator, value);
    }

    private static FilterSpecParam createRangeParam(String propertyName, PrimitiveValue primitiveValue, AST filterParamNode)
        throws ASTFilterSpecValidationException
    {
        // Deal with Ranges
        AST ast = filterParamNode.getFirstChild();
        boolean lowInclusive = ast.getType() == LBRACK;

        ast = ast.getNextSibling();
        if  ((ast.getType() != EVENT_FILTER_IDENT) &&
             (!ASTConstantHelper.canConvert(ast.getType(), primitiveValue.getType())) )
        {
            String message = getConversionErrorMsg(ast.getType(), primitiveValue.getType(), propertyName);
            throw new ASTFilterSpecValidationException(message);
        }
        FilterSpecParamRangeValue valueMin = getRangePoint(ast, primitiveValue);

        ast = ast.getNextSibling();
        if ((ast.getType() != EVENT_FILTER_IDENT) &&
            (!ASTConstantHelper.canConvert(ast.getType(), primitiveValue.getType())) )
        {
            String message = getConversionErrorMsg(ast.getType(), primitiveValue.getType(), propertyName);
            throw new ASTFilterSpecValidationException(message);
        }
        FilterSpecParamRangeValue valueMax = getRangePoint(ast, primitiveValue);

        ast = ast.getNextSibling();
        boolean highInclusive = ast.getType() == RBRACK;

        FilterOperator operator = FilterOperator.parseRangeOperator(lowInclusive, highInclusive);
        
        return new FilterSpecParamRange(propertyName, operator, valueMin, valueMax);
    }

    private static FilterSpecParamRangeValue getRangePoint(AST ast, PrimitiveValue primitiveValue)
    {
        if (ast.getType() == EVENT_FILTER_IDENT)
        {
            String eventAsName = ast.getFirstChild().getText();
            String eventProperty = ast.getFirstChild().getNextSibling().getText();
            return new RangeValueEventProp(eventAsName, eventProperty);
        }
        else
        {
            // Parse text node
            String value = ast.getText();
            primitiveValue.parse(value);
            double dbl = ((Number) primitiveValue.getValueObject()).doubleValue();
            return new RangeValueDouble(dbl);
        }
    }

    private static String getConversionErrorMsg(int astConstantType, PrimitiveValueType primitiveValueType, String propertyName)
    {
        return "Implicit conversion from datatype '" +
                ASTConstantHelper.getConstantTypeName(astConstantType) +
                "' to '" +
                primitiveValueType.toString() +
                "' for property '" +
                propertyName +
                "' is not allowed";

    }

    private static Log log = LogFactory.getLog(ASTFilterSpecHelper.class);
}
