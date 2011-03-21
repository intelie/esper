package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.core.ExpressionResultCacheEntry;
import com.espertech.esper.core.ExpressionResultCacheStackEntry;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.StreamTypeServiceImpl;
import com.espertech.esper.epl.enummethod.eval.EnumEval;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.util.CollectionUtil;
import com.espertech.esper.util.JavaClassHelper;

import java.io.StringWriter;
import java.util.*;

public abstract class ExprDotEvalEnumMethodBase implements ExprDotEvalEnumMethod, ExpressionResultCacheStackEntry {

    private EnumMethodEnum enumMethodEnum;
    private String enumMethodUsedName;
    private int streamCountIncoming;
    private EnumEval enumEval;
    private ExprDotEvalTypeInfo typeInfo;

    private boolean cache;
    private long contextNumber = 0;

    protected ExprDotEvalEnumMethodBase() {
    }

    public abstract EventType[] getAddStreamTypes(String enumMethodUsedName, List<String> goesToNames, EventType inputEventType, Class collectionComponentType, List<ExprDotEvalParam> bodiesAndParameters);
    public abstract EnumEval getEnumEval(StreamTypeService streamTypeService, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, Class collectionComponentType, int numStreamsIncoming) throws ExprValidationException;

    public EnumMethodEnum getEnumMethodEnum() {
        return enumMethodEnum;
    }

    public void init(EnumMethodEnum enumMethodEnum, String enumMethodUsedName, ExprDotEvalTypeInfo typeInfo, List<ExprNode> parameters, ValidationContext validationContext, StreamTypeService streamTypeService) throws ExprValidationException {

        EventType eventType = typeInfo.getEventTypeColl();
        Class collectionComponentType = typeInfo.getComponent();

        this.enumMethodEnum = enumMethodEnum;
        this.enumMethodUsedName = enumMethodUsedName;
        this.streamCountIncoming = streamTypeService.getEventTypes().length;

        if (eventType == null && collectionComponentType == null) {
            throw new ExprValidationException("Invalid input for built-in enumeration method '" + enumMethodUsedName + "', expecting collection of event-type or scalar values as input, received " + typeInfo.toTypeName());
        }

        // compile parameter abstract for validation against available footprints
        EnumMethodFootprintProvided footprintProvided = getProvidedFootprint(parameters);

        // validate parameters
        EnumMethodFootprint footprint = validateParameters(enumMethodEnum, enumMethodUsedName, footprintProvided);

        // validate input criteria met for this footprint
        if (footprint.getInput() != EnumMethodInputEnum.ANY) {
            String message = "Invalid input for built-in enumeration method '" + enumMethodUsedName + "' and " + footprint.getParams().length + "-parameter footprint, expecting collection of ";
            String received = " as input, received " + typeInfo.toTypeName();
            if (footprint.getInput() == EnumMethodInputEnum.EVENTCOLL && typeInfo.getEventTypeColl() == null) {
                throw new ExprValidationException(message + "events" + received);
            }
            if (footprint.getInput().isScalar() && typeInfo.getComponent() == null) {
                throw new ExprValidationException(message + "values (typically scalar values)" + received);
            }
            if (footprint.getInput() == EnumMethodInputEnum.SCALAR_NUMERIC && !JavaClassHelper.isNumeric(collectionComponentType)) {
                throw new ExprValidationException(message + "numeric values" + received);
            }
        }

        // manage context of this lambda-expression in regards to outer lambda-expression that may call this one.
        validationContext.getExprEvaluatorContext().getExpressionResultCacheService().pushStack(this);

        List<ExprDotEvalParam> bodiesAndParameters = new ArrayList<ExprDotEvalParam>();
        int count = 0;
        for (ExprNode node : parameters) {
            ExprDotEvalParam bodyAndParameter = getBodyAndParameter(enumMethodUsedName, count++, node, eventType, collectionComponentType, validationContext, streamTypeService, bodiesAndParameters, footprint);
            bodiesAndParameters.add(bodyAndParameter);
        }

        this.enumEval = getEnumEval(streamTypeService, enumMethodUsedName, bodiesAndParameters, eventType, collectionComponentType, streamCountIncoming);

        // determine the stream ids of event properties asked for in the evaluator(s)
        HashSet<Integer> streamsRequired = new HashSet<Integer>();
        ExprNodeIdentifierCollectVisitor visitor = new ExprNodeIdentifierCollectVisitor();
        for (ExprDotEvalParam desc : bodiesAndParameters) {
            desc.getBody().accept(visitor);
            for (ExprIdentNode ident : visitor.getExprProperties()) {
                streamsRequired.add(ident.getStreamId());
            }
        }

        // We turn on caching if the stack is not empty (we are an inner lambda) and the dependency does not include the stream.
        boolean isInner = !validationContext.getExprEvaluatorContext().getExpressionResultCacheService().popLambda();
        if (isInner) {
            // If none of the properties that the current lambda uses comes from the ultimate parent(s) or subsequent streams, then cache.
            Deque<ExpressionResultCacheStackEntry> parents = validationContext.getExprEvaluatorContext().getExpressionResultCacheService().getStack();
            boolean found = false;
            for (int req : streamsRequired) {
                ExprDotEvalEnumMethodBase first = (ExprDotEvalEnumMethodBase) parents.getFirst();
                int parentIncoming = first.streamCountIncoming - 1;
                int selfAdded = streamCountIncoming;    // the one we use ourselfs
                if (req > parentIncoming && req < selfAdded) {
                    found = true;
                }
            }
            cache = !found;
        }
    }

    public void setTypeInfo(ExprDotEvalTypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    public ExprDotEvalTypeInfo getTypeInfo() {
        return typeInfo;
    }

    private EnumMethodFootprint validateParameters(EnumMethodEnum enumMethod, String enumMethodUsedName, EnumMethodFootprintProvided providedFootprint)
        throws ExprValidationException
    {
        EnumMethodFootprint[] footprints = enumMethod.getFootprints();

        EnumMethodFootprint found = null;
        EnumMethodFootprint bestMatch = null;
        for (EnumMethodFootprint footprint : footprints) {

            EnumMethodParam[] requiredParams = footprint.getParams();
            if (requiredParams.length != providedFootprint.getParams().length) {
                continue;
            }

            if (bestMatch == null) {    // take first if number of parameters matches
                bestMatch = footprint;
            }

            boolean paramMatch = true;
            int count = 0;
            for (EnumMethodParam requiredParam : requiredParams) {

                EnumMethodParamProvided providedParam = providedFootprint.getParams()[count++];
                if (requiredParam.getLambdaParamNum() != providedParam.getLambdaParamNum()) {
                    paramMatch = false;
                }
            }

            if (paramMatch) {
                found = footprint;
                break;
            }
        }

        if (found != null) {
            return found;
        }

        String message = "Parameters mismatch for enumeration method '" + enumMethodUsedName + "', the method ";
        if (bestMatch != null) {
            StringWriter buf = new StringWriter();
            buf.append(bestMatch.toStringFootprint());
            buf.append(", but receives ");
            buf.append(EnumMethodFootprint.toStringProvided(providedFootprint));
            throw new ExprValidationException(message + "requires " + buf.toString());
        }

        if (footprints.length == 1) {
            throw new ExprValidationException(message + "requires " + footprints[0].toStringFootprint());
        }
        else {
            StringWriter buf = new StringWriter();
            String delimiter = "";
            for (EnumMethodFootprint footprint : footprints) {
                buf.append(delimiter);
                buf.append(footprint.toStringFootprint());
                delimiter = ", or ";
            }
            throw new ExprValidationException(message + "has multiple footprints accepting " + buf +
                    ", but receives " + EnumMethodFootprint.toStringProvided(providedFootprint));
        }
    }

    public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        if (cache) {
            ExpressionResultCacheEntry<Long[], Object> cacheValue = exprEvaluatorContext.getExpressionResultCacheService().getEnumerationMethodLastValue(this);
            if (cacheValue != null) {
                return cacheValue.getResult();
            }
            Collection coll = (Collection) target;
            if (coll == null) {
                return null;
            }
            EventBean[] eventsLambda = enumEval.getEventsPrototype();
            System.arraycopy(eventsPerStream, 0, eventsLambda, 0, eventsPerStream.length);
            Object result = enumEval.evaluateEnumMethod(coll, isNewData, exprEvaluatorContext);
            exprEvaluatorContext.getExpressionResultCacheService().saveEnumerationMethodLastValue(this, result);
            return result;
        }

        contextNumber++;
        try {
            exprEvaluatorContext.getExpressionResultCacheService().pushContext(contextNumber);
            Collection coll = (Collection) target;
            if (coll == null) {
                return null;
            }
            EventBean[] eventsLambda = enumEval.getEventsPrototype();
            System.arraycopy(eventsPerStream, 0, eventsLambda, 0, eventsPerStream.length);
            return enumEval.evaluateEnumMethod(coll, isNewData, exprEvaluatorContext);
        }
        finally {
            exprEvaluatorContext.getExpressionResultCacheService().popContext();
        }
    }

    private static EnumMethodFootprintProvided getProvidedFootprint(List<ExprNode> parameters) {
        List<EnumMethodParamProvided> params = new ArrayList<EnumMethodParamProvided>();
        for (ExprNode node : parameters) {
            if (!(node instanceof ExprLambdaGoesNode)) {
                params.add(new EnumMethodParamProvided(0));
                continue;
            }
            ExprLambdaGoesNode goesNode = (ExprLambdaGoesNode) node;
            params.add(new EnumMethodParamProvided(goesNode.getGoesToNames().size()));
        }
        return new EnumMethodFootprintProvided(params.toArray(new EnumMethodParamProvided[params.size()]));
    }

    private ExprDotEvalParam getBodyAndParameter(String enumMethodUsedName,
                                                 int parameterNum,
                                                 ExprNode parameterNode,
                                                 EventType inputEventType,
                                                 Class collectionComponentType,
                                                 ValidationContext validationContext,
                                                 StreamTypeService streamTypeService,
                                                 List<ExprDotEvalParam> priorParameters,
                                                 EnumMethodFootprint footprint) throws ExprValidationException {

        // handle an expression that is a constant or other (not =>)
        if (!(parameterNode instanceof ExprLambdaGoesNode)) {

            // no node subtree validation is required here, the chain parameter validation has taken place in ExprDotNode.validate
            // we validate the parameter type so that a uniform error message can be presented
            EnumMethodEnumParamType expectedType = footprint.getParams()[parameterNum].getType();
            validateExpr(expectedType, parameterNode.getExprEvaluator().getType(), parameterNum);
            return new ExprDotEvalParamExpr(parameterNum, parameterNode, parameterNode.getExprEvaluator());
        }

        ExprLambdaGoesNode goesNode = (ExprLambdaGoesNode) parameterNode;

        // Get secondary
        EventType[] additionalTypes = getAddStreamTypes(enumMethodUsedName, goesNode.getGoesToNames(), inputEventType, collectionComponentType, priorParameters);
        String[] additionalStreamNames = goesNode.getGoesToNames().toArray(new String[goesNode.getGoesToNames().size()]);

        // add name and type to list of known types
        EventType[] addTypes = (EventType[]) CollectionUtil.expandAddElement(streamTypeService.getEventTypes(), additionalTypes);
        String[] addNames = (String[]) CollectionUtil.expandAddElement(streamTypeService.getStreamNames(), additionalStreamNames);

        StreamTypeServiceImpl types = new StreamTypeServiceImpl(addTypes, addNames, new boolean[addTypes.length], null, false);

        // validate expression body
        ExprNode filter = goesNode.getChildNodes().get(0);
        try {
            filter = filter.getValidatedSubtree(types, validationContext.getMethodResolutionService(), validationContext.getViewResourceDelegate(), validationContext.getTimeProvider(), validationContext.getVariableService(), validationContext.getExprEvaluatorContext(), validationContext.getEventAdapterService());
        }
        catch (ExprValidationException ex) {
            throw new ExprValidationException("Error validating enumeration method '" + enumMethodUsedName + "' parameter " + parameterNum + ": " + ex.getMessage(), ex);
        }

        ExprEvaluator filterEvaluator = filter.getExprEvaluator();
        EnumMethodEnumParamType expectedType = footprint.getParams()[parameterNum].getType();
        validateExpr(expectedType, filterEvaluator.getType(), parameterNum);

        int numStreamsIncoming = streamTypeService.getEventTypes().length;
        return new ExprDotEvalParamLambda(parameterNum, filter, filterEvaluator,
                numStreamsIncoming, goesNode.getGoesToNames(), additionalTypes);
    }

    private void validateExpr(EnumMethodEnumParamType expectedType, Class returnType, int parameterNum)
        throws ExprValidationException{
        if (expectedType == EnumMethodEnumParamType.BOOLEAN && (!JavaClassHelper.isBoolean(returnType))) {
            throw new ExprValidationException("Error validating enumeration method '" + enumMethodUsedName + "', expected a boolean-type result for expression parameter " + parameterNum + " but received " + JavaClassHelper.getClassNameFullyQualPretty(returnType));
        }
        if (expectedType == EnumMethodEnumParamType.NUMERIC && (!JavaClassHelper.isNumeric(returnType))) {
            throw new ExprValidationException("Error validating enumeration method '" + enumMethodUsedName + "', expected a number-type result for expression parameter " + parameterNum + " but received " + JavaClassHelper.getClassNameFullyQualPretty(returnType));
        }
    }

    public String toString() {
        return this.getClass().getSimpleName() +
                " lambda=" + enumMethodEnum;
    }
}
