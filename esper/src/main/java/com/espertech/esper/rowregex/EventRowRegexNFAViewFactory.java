package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventType;
import com.espertech.esper.client.annotation.Hint;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.agg.AggregationServiceFactory;
import com.espertech.esper.epl.agg.AggregationServiceMatchRecognize;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.StreamTypeServiceImpl;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.spec.MatchRecognizeDefineItem;
import com.espertech.esper.epl.spec.MatchRecognizeMeasureItem;
import com.espertech.esper.epl.spec.MatchRecognizeSpec;
import com.espertech.esper.view.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.lang.annotation.Annotation;

public class EventRowRegexNFAViewFactory extends ViewFactorySupport
{
    private static final Log log = LogFactory.getLog(EventRowRegexNFAViewFactory.class);

    private final MatchRecognizeSpec matchRecognizeSpec;
    private final LinkedHashMap<String, Pair<Integer, Boolean>> variableStreams;
    private final Map<Integer, String> streamVariables;
    private final Set<String> variablesSingle;
    private final Set<String> variablesMultiple;
    private final EventType compositeEventType;
    private final EventType rowEventType;
    private final AggregationServiceMatchRecognize aggregationService;
    private final TreeMap<Integer, List<ExprPreviousMatchRecognizeNode>> callbacksPerIndex = new TreeMap<Integer, List<ExprPreviousMatchRecognizeNode>>();
    private final boolean isUnbound;
    private final boolean isIterateOnly;

    public EventRowRegexNFAViewFactory(ViewFactoryChain viewChain, MatchRecognizeSpec matchRecognizeSpec, StatementContext statementContext, boolean isUnbound, Annotation[] annotations)
            throws ExprValidationException
    {
        EventType parentViewType = viewChain.getEventType();
        this.matchRecognizeSpec = matchRecognizeSpec;
        this.isUnbound = isUnbound;
        this.isIterateOnly = isIterateOnlyHint(annotations);

        // Determine single-row and multiple-row variables
        variablesSingle = new LinkedHashSet<String>();
        variablesMultiple = new LinkedHashSet<String>();
        EventRowRegexHelper.recursiveInspectVariables(matchRecognizeSpec.getPattern(), false, variablesSingle, variablesMultiple);

        // each variable gets associated with a stream number (multiple-row variables as well to hold the current event for the expression).
        int streamNum = 0;
        variableStreams = new LinkedHashMap<String, Pair<Integer, Boolean>>();
        for (String variableSingle : variablesSingle)
        {
            variableStreams.put(variableSingle, new Pair<Integer, Boolean>(streamNum, false));
            streamNum++;
        }
        for (String variableMultiple : variablesMultiple)
        {
            variableStreams.put(variableMultiple, new Pair<Integer, Boolean>(streamNum, true));
            streamNum++;
        }

        // mapping of stream to variable
        streamVariables = new TreeMap<Integer, String>();
        for (Map.Entry<String, Pair<Integer, Boolean>> entry : variableStreams.entrySet())
        {
            streamVariables.put(entry.getValue().getFirst(), entry.getKey());
        }

        // assemble all single-row variables for expression validation
        String[] singleVarStreamNames = new String[variableStreams.size()];
        String[] allStreamNames = new String[variableStreams.size()];
        EventType[] singleVarTypes = new EventType[variableStreams.size()];
        EventType[] allTypes = new EventType[variableStreams.size()];
        
        streamNum = 0;
        for (String variableSingle : variablesSingle)
        {
            singleVarStreamNames[streamNum] = variableSingle;
            singleVarTypes[streamNum] = parentViewType;
            allStreamNames[streamNum] = variableSingle;
            allTypes[streamNum] = parentViewType;
            streamNum++;
        }
        for (String variableMultiple : variablesMultiple)
        {
            allStreamNames[streamNum] = variableMultiple;
            allTypes[streamNum] = parentViewType;
            streamNum++;
        }

        // determine type service for use with DEFINE
        // validate each DEFINE clause expression
        for (MatchRecognizeDefineItem defineItem : matchRecognizeSpec.getDefines())
        {
            String[] streamNamesDefine = new String[singleVarStreamNames.length];
            System.arraycopy(singleVarStreamNames, 0, streamNamesDefine, 0, singleVarStreamNames.length);
            EventType[] typesDefine = new EventType[singleVarTypes.length];
            System.arraycopy(singleVarTypes, 0, typesDefine, 0, singleVarTypes.length);

            // the own stream is available for querying
            if (!variableStreams.containsKey(defineItem.getIdentifier()))
            {
                throw new ExprValidationException("Variable '" + defineItem.getIdentifier() + "' does not occur in pattern");
            }
            int streamNumDefine = variableStreams.get(defineItem.getIdentifier()).getFirst();
            streamNamesDefine[streamNumDefine] = defineItem.getIdentifier();
            typesDefine[streamNumDefine] = parentViewType;

            StreamTypeService typeServiceDefines = new StreamTypeServiceImpl(typesDefine, streamNamesDefine, statementContext.getEngineURI());
            ExprNode exprNodeResult = handlePreviousFunctions(defineItem.getExpression());
            ExprNode validated = exprNodeResult.getValidatedSubtree(typeServiceDefines, statementContext.getMethodResolutionService(), null, statementContext.getSchedulingService(), statementContext.getVariableService());
            defineItem.setExpression(validated);
        }

        // determine type service for use with MEASURE
        Map<String, Object> measureTypeDef = new LinkedHashMap<String, Object>();
        for (String variableSingle : variablesSingle)
        {
            measureTypeDef.put(variableSingle, parentViewType);
        }
        for (String variableMultiple : variablesMultiple)
        {
            measureTypeDef.put(variableMultiple, new EventType[] {parentViewType});
        }
        compositeEventType = statementContext.getEventAdapterService().createAnonymousMapType(measureTypeDef);
        StreamTypeService typeServiceMeasure = new StreamTypeServiceImpl(compositeEventType, "MATCH_RECOGNIZE", statementContext.getEngineURI());

        // find MEASURE clause aggregations
        List<ExprAggregateNode> measureAggregateExprNodes = new ArrayList<ExprAggregateNode>();
        for (MatchRecognizeMeasureItem measureItem : matchRecognizeSpec.getMeasures())
        {
            ExprAggregateNode.getAggregatesBottomUp(measureItem.getExpr(), measureAggregateExprNodes);
        }
        if (!measureAggregateExprNodes.isEmpty())
        {
            StreamTypeServiceImpl typeServiceAggregateMeasure = new StreamTypeServiceImpl(allTypes, allStreamNames, statementContext.getEngineURI());
            Map<Integer, List<ExprAggregateNode>> measureExprAggNodesPerStream = new HashMap<Integer, List<ExprAggregateNode>>();

            for (ExprAggregateNode aggregateNode : measureAggregateExprNodes)
            {
                int count = 0;
                ExprNodeIdentifierVisitor visitor = new ExprNodeIdentifierVisitor(true);

                for (ExprNode child : aggregateNode.getChildNodes())
                {
                    ExprNode validated = child.getValidatedSubtree(typeServiceAggregateMeasure, statementContext.getMethodResolutionService(), null, statementContext.getSchedulingService(), statementContext.getVariableService());
                    validated.accept(visitor);
                    aggregateNode.getChildNodes().set(count++, new ExprNodeValidated(validated));
                }
                aggregateNode.validate(typeServiceMeasure, statementContext.getMethodResolutionService(), null, statementContext.getSchedulingService(), statementContext.getVariableService());

                // verify properties used within the aggregation
                Set<Integer> aggregatedStreams = new HashSet<Integer>();
                for (Pair<Integer, String> pair : visitor.getExprProperties())
                {
                    aggregatedStreams.add(pair.getFirst());
                }

                Integer multipleVarStream = null;
                for (int streamNumAggregated : aggregatedStreams)
                {
                    String variable = streamVariables.get(streamNumAggregated);
                    if (variablesMultiple.contains(variable))
                    {
                        if (multipleVarStream == null)
                        {
                            multipleVarStream = streamNumAggregated;
                            continue;
                        }
                        else if (streamNumAggregated != streamNumAggregated)
                        {
                            throw new ExprValidationException("Aggregation functions in the measure-clause must only refer to properties of a single DEFINE item returning multiple events");
                        }
                    }
                }

                if (multipleVarStream == null)
                {
                    throw new ExprValidationException("Aggregation functions in the measure-clause must refer to one or more properties of a single DEFINE item returning multiple events");
                }

                List<ExprAggregateNode> aggNodesForStream = measureExprAggNodesPerStream.get(multipleVarStream);
                if (aggNodesForStream == null)
                {
                    aggNodesForStream = new ArrayList<ExprAggregateNode>();
                    measureExprAggNodesPerStream.put(multipleVarStream, aggNodesForStream);
                }
                aggNodesForStream.add(aggregateNode);
            }

            aggregationService = AggregationServiceFactory.getServiceMatchRecognize(streamVariables.size(), measureExprAggNodesPerStream, statementContext.getMethodResolutionService());
        }
        else
        {
            aggregationService = null;
        }

        // validate each MEASURE clause expression
        Map<String, Object> rowTypeDef = new LinkedHashMap<String, Object>();
        for (MatchRecognizeMeasureItem measureItem : matchRecognizeSpec.getMeasures())
        {
            ExprNode validated = measureItem.getExpr().getValidatedSubtree(typeServiceMeasure, statementContext.getMethodResolutionService(), null, statementContext.getSchedulingService(), statementContext.getVariableService());
            measureItem.setExpr(validated);
            rowTypeDef.put(measureItem.getName(), validated.getType());
        }

        // create rowevent type
        rowEventType = statementContext.getEventAdapterService().createAnonymousMapType(rowTypeDef);

        // validate partition-by expressions, if any
        if (!matchRecognizeSpec.getPartitionByExpressions().isEmpty())
        {
            StreamTypeService typeServicePartition = new StreamTypeServiceImpl(parentViewType, "MATCH_RECOGNIZE_PARTITION", statementContext.getEngineURI());
            List<ExprNode> validated = new ArrayList<ExprNode>();
            for (ExprNode partitionExpr : matchRecognizeSpec.getPartitionByExpressions())
            {
                validated.add(partitionExpr.getValidatedSubtree(typeServicePartition, statementContext.getMethodResolutionService(), null, statementContext.getSchedulingService(), statementContext.getVariableService()));
            }
            matchRecognizeSpec.setPartitionByExpressions(validated);
        }
    }

    private ExprNode handlePreviousFunctions(ExprNode defineItemExpression) throws ExprValidationException
    {
        ExprNodePreviousVisitorWParent previousVisitor = new ExprNodePreviousVisitorWParent();
        defineItemExpression.accept(previousVisitor);

        if (previousVisitor.getPrevious() == null)
        {
            return defineItemExpression;
        }

        for (Pair<ExprNode, ExprPreviousNode> pairs : previousVisitor.getPrevious())
        {
            ExprPreviousNode previousNode = pairs.getSecond();
            ExprPreviousMatchRecognizeNode matchRecogPrevNode = new ExprPreviousMatchRecognizeNode();

            if (pairs.getSecond().getChildNodes().size() == 1)
            {
                matchRecogPrevNode.addChildNode(previousNode.getChildNodes().get(0));
                matchRecogPrevNode.addChildNode(new ExprConstantNode(1));
            }
            else if (pairs.getSecond().getChildNodes().size() == 2)
            {
                ExprNode first = previousNode.getChildNodes().get(0);
                ExprNode second = previousNode.getChildNodes().get(1);
                if ((first.isConstantResult()) && (!second.isConstantResult()))
                {
                    matchRecogPrevNode.addChildNode(second);
                    matchRecogPrevNode.addChildNode(first);
                }
                else if ((!first.isConstantResult()) && (second.isConstantResult()))
                {
                    matchRecogPrevNode.addChildNode(first);
                    matchRecogPrevNode.addChildNode(second);
                }
                else
                {
                    throw new ExprValidationException("PREV operator requires a constant index");
                }
            }

            if (pairs.getFirst() == null)
            {
                defineItemExpression = matchRecogPrevNode;
            }
            else
            {
                int index = pairs.getFirst().getChildNodes().indexOf(pairs.getSecond());
                pairs.getFirst().getChildNodes().set(index, matchRecogPrevNode);
            }

            // store in a list per index such that we can consolidate this into a single buffer
            int index = matchRecogPrevNode.getConstantIndexNumber();
            List<ExprPreviousMatchRecognizeNode> callbackList = callbacksPerIndex.get(index);
            if (callbackList == null)
            {
                callbackList = new ArrayList<ExprPreviousMatchRecognizeNode>();
                callbacksPerIndex.put(index, callbackList);
            }
            callbackList.add(matchRecogPrevNode);
        }

        return defineItemExpression;
    }

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> viewParameters) throws ViewParameterException {
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return false;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException {
    }

    public View makeView(StatementContext statementContext) {

        return new EventRowRegexNFAView(compositeEventType,
                rowEventType,
                matchRecognizeSpec,
                variableStreams,
                streamVariables,
                variablesSingle,
                statementContext,
                callbacksPerIndex,
                aggregationService,
                isUnbound,
                isIterateOnly
             );
    }

    public EventType getEventType() {
        return rowEventType;
    }

    private boolean isIterateOnlyHint(Annotation[] annotations)
    {
        String hint = null;
        for (Annotation annotation : annotations)
        {
            if (!(annotation instanceof Hint))
            {
                continue;
            }
            hint = ((Hint) annotation).value();
        }
        if (hint == null)
        {
            return false;
        }
        String[] hints = hint.split(",");
        boolean isIterateOnly = false;
        for (int i = 0; i < hints.length; i++)
        {
            if (hints[i].trim().toUpperCase().equals("ITERATE_ONLY"))
            {
                isIterateOnly = true;
            }
        }
        return isIterateOnly;
    }
}
