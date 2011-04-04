package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.core.ExpressionResultCacheEntry;
import com.espertech.esper.core.ExpressionResultCacheStackEntry;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.StreamTypeServiceImpl;
import com.espertech.esper.epl.enummethod.eval.EnumEval;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.methodbase.*;
import com.espertech.esper.util.CollectionUtil;
import com.espertech.esper.util.JavaClassHelper;

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

    public void init(EnumMethodEnum enumMethodEnum, String enumMethodUsedName, ExprDotEvalTypeInfo typeInfo, List<ExprNode> parameters, ExprValidationContext validationContext) throws ExprValidationException {

        EventType eventType = typeInfo.getEventTypeColl();
        Class collectionComponentType = typeInfo.getComponent();

        this.enumMethodEnum = enumMethodEnum;
        this.enumMethodUsedName = enumMethodUsedName;
        this.streamCountIncoming = validationContext.getStreamTypeService().getEventTypes().length;

        if (eventType == null && collectionComponentType == null) {
            throw new ExprValidationException("Invalid input for built-in enumeration method '" + enumMethodUsedName + "', expecting collection of event-type or scalar values as input, received " + typeInfo.toTypeName());
        }

        // compile parameter abstract for validation against available footprints
        DotMethodFPProvided footprintProvided = DotMethodUtil.getProvidedFootprint(parameters);

        // validate parameters
        DotMethodFP footprint = DotMethodUtil.validateParameters(enumMethodEnum.getFootprints(), DotMethodTypeEnum.ENUM, enumMethodUsedName, footprintProvided);

        // validate input criteria met for this footprint
        if (footprint.getInput() != DotMethodFPInputEnum.ANY) {
            String message = "Invalid input for built-in enumeration method '" + enumMethodUsedName + "' and " + footprint.getParams().length + "-parameter footprint, expecting collection of ";
            String received = " as input, received " + typeInfo.toTypeName();
            if (footprint.getInput() == DotMethodFPInputEnum.EVENTCOLL && typeInfo.getEventTypeColl() == null) {
                throw new ExprValidationException(message + "events" + received);
            }
            if (footprint.getInput().isScalar() && typeInfo.getComponent() == null) {
                throw new ExprValidationException(message + "values (typically scalar values)" + received);
            }
            if (footprint.getInput() == DotMethodFPInputEnum.SCALAR_NUMERIC && !JavaClassHelper.isNumeric(collectionComponentType)) {
                throw new ExprValidationException(message + "numeric values" + received);
            }
        }

        // manage context of this lambda-expression in regards to outer lambda-expression that may call this one.
        validationContext.getExprEvaluatorContext().getExpressionResultCacheService().pushStack(this);

        List<ExprDotEvalParam> bodiesAndParameters = new ArrayList<ExprDotEvalParam>();
        int count = 0;
        for (ExprNode node : parameters) {
            ExprDotEvalParam bodyAndParameter = getBodyAndParameter(enumMethodUsedName, count++, node, eventType, collectionComponentType, validationContext, bodiesAndParameters, footprint);
            bodiesAndParameters.add(bodyAndParameter);
        }

        this.enumEval = getEnumEval(validationContext.getStreamTypeService(), enumMethodUsedName, bodiesAndParameters, eventType, collectionComponentType, streamCountIncoming);

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

    private ExprDotEvalParam getBodyAndParameter(String enumMethodUsedName,
                                                 int parameterNum,
                                                 ExprNode parameterNode,
                                                 EventType inputEventType,
                                                 Class collectionComponentType,
                                                 ExprValidationContext validationContext,
                                                 List<ExprDotEvalParam> priorParameters,
                                                 DotMethodFP footprint) throws ExprValidationException {

        // handle an expression that is a constant or other (not =>)
        if (!(parameterNode instanceof ExprLambdaGoesNode)) {

            // no node subtree validation is required here, the chain parameter validation has taken place in ExprDotNode.validate
            // validation of parameter types has taken place in footprint matching
            return new ExprDotEvalParamExpr(parameterNum, parameterNode, parameterNode.getExprEvaluator());
        }

        ExprLambdaGoesNode goesNode = (ExprLambdaGoesNode) parameterNode;

        // Get secondary
        EventType[] additionalTypes = getAddStreamTypes(enumMethodUsedName, goesNode.getGoesToNames(), inputEventType, collectionComponentType, priorParameters);
        String[] additionalStreamNames = goesNode.getGoesToNames().toArray(new String[goesNode.getGoesToNames().size()]);

        validateDuplicateStreamNames(validationContext.getStreamTypeService().getStreamNames(), additionalStreamNames);

        // add name and type to list of known types
        EventType[] addTypes = (EventType[]) CollectionUtil.expandAddElement(validationContext.getStreamTypeService().getEventTypes(), additionalTypes);
        String[] addNames = (String[]) CollectionUtil.expandAddElement(validationContext.getStreamTypeService().getStreamNames(), additionalStreamNames);

        StreamTypeServiceImpl types = new StreamTypeServiceImpl(addTypes, addNames, new boolean[addTypes.length], null, false);

        // validate expression body
        ExprNode filter = goesNode.getChildNodes().get(0);
        try {
            ExprValidationContext filterValidationContext = new ExprValidationContext(types, validationContext);
            filter = ExprNodeUtil.getValidatedSubtree(filter, filterValidationContext);
        }
        catch (ExprValidationException ex) {
            throw new ExprValidationException("Error validating enumeration method '" + enumMethodUsedName + "' parameter " + parameterNum + ": " + ex.getMessage(), ex);
        }

        ExprEvaluator filterEvaluator = filter.getExprEvaluator();
        DotMethodFPParamTypeEnum expectedType = footprint.getParams()[parameterNum].getType();
        // Lambda-methods don't use a specific expected return-type, so passing null for type is fine.
        DotMethodUtil.validateSpecificType(enumMethodUsedName, DotMethodTypeEnum.ENUM, expectedType, null, filterEvaluator.getType(), parameterNum);

        int numStreamsIncoming = validationContext.getStreamTypeService().getEventTypes().length;
        return new ExprDotEvalParamLambda(parameterNum, filter, filterEvaluator,
                numStreamsIncoming, goesNode.getGoesToNames(), additionalTypes);
    }

    private void validateDuplicateStreamNames(String[] streamNames, String[] additionalStreamNames) throws ExprValidationException {
        for (int added = 0; added < additionalStreamNames.length; added++) {
            for (int exist = 0; exist < streamNames.length; exist++) {
                if (streamNames[exist] != null && streamNames[exist].equalsIgnoreCase(additionalStreamNames[added])) {
                    String message = "Error validating enumeration method '" + enumMethodUsedName + "', the lambda-parameter name '" + additionalStreamNames[added] + "' has already been declared in this context";
                    throw new ExprValidationException(message);
                }
            }
        }
    }

    public String toString() {
        return this.getClass().getSimpleName() +
                " lambda=" + enumMethodEnum;
    }
}
