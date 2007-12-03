package net.esper.eql.spec;

import net.esper.client.EPException;
import net.esper.client.soda.*;
import net.esper.eql.agg.AggregationSupport;
import net.esper.eql.core.EngineImportException;
import net.esper.eql.core.EngineImportService;
import net.esper.eql.core.EngineImportUndefinedException;
import net.esper.eql.expression.*;
import net.esper.eql.variable.VariableService;
import net.esper.pattern.*;
import net.esper.type.MathArithTypeEnum;
import net.esper.type.MinMaxTypeEnum;
import net.esper.type.RelationalOpEnum;
import net.esper.collection.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper for mapping internal representations of a statement to the SODA object model for statements. 
 */
public class StatementSpecMapper
{
    /**
     * Maps the SODA object model to a statement specification.
     * @param sodaStatement is the object model to map
     * @param engineImportService for resolving imports such as plug-in aggregations
     * @return statement specification, and internal representation of a statement
     */
    public static StatementSpecRaw map(EPStatementObjectModel sodaStatement, EngineImportService engineImportService, VariableService variableService)
    {
        StatementSpecMapContext mapContext = new StatementSpecMapContext(engineImportService, variableService);
        
        StatementSpecRaw raw = map(sodaStatement, mapContext);
        if (mapContext.isHasVariables())
        {
            raw.setHasVariables(true);
        }
        return raw;
    }

    private static StatementSpecRaw map(EPStatementObjectModel sodaStatement, StatementSpecMapContext mapContext)
    {
        StatementSpecRaw raw = new StatementSpecRaw();
        mapCreateWindow(sodaStatement.getCreateWindow(), raw);
        mapOnTrigger(sodaStatement.getOnExpr(), raw, mapContext);
        mapInsertInto(sodaStatement.getInsertInto(), raw);
        mapSelect(sodaStatement.getSelectClause(), raw, mapContext);
        mapFrom(sodaStatement.getFromClause(), raw, mapContext);
        mapWhere(sodaStatement.getWhereClause(), raw, mapContext);
        mapGroupBy(sodaStatement.getGroupByClause(), raw, mapContext);
        mapHaving(sodaStatement.getHavingClause(), raw, mapContext);
        mapOutputLimit(sodaStatement.getOutputLimitClause(), raw);
        mapOrderBy(sodaStatement.getOrderByClause(), raw, mapContext);
        return raw;
    }

    /**
     * Maps the internal representation of a statement to the SODA object model.
     * @param statementSpec is the internal representation
     * @return object model of statement
     */
    public static StatementSpecUnMapResult unmap(StatementSpecRaw statementSpec)
    {
        StatementSpecUnMapContext unmapContext = new StatementSpecUnMapContext();

        EPStatementObjectModel model = new EPStatementObjectModel();
        unmapCreateWindow(statementSpec.getCreateWindowDesc(), model);
        unmapOnClause(statementSpec.getOnTriggerDesc(), model, unmapContext);
        unmapInsertInto(statementSpec.getInsertIntoDesc(), model);
        unmapSelect(statementSpec.getSelectClauseSpec(), statementSpec.getSelectStreamSelectorEnum(), model, unmapContext);
        unmapFrom(statementSpec.getStreamSpecs(), statementSpec.getOuterJoinDescList(), model, unmapContext);
        unmapWhere(statementSpec.getFilterRootNode(), model, unmapContext);
        unmapGroupBy(statementSpec.getGroupByExpressions(), model, unmapContext);
        unmapHaving(statementSpec.getHavingExprRootNode(), model, unmapContext);
        unmapOutputLimit(statementSpec.getOutputLimitSpec(), model);
        unmapOrderBy(statementSpec.getOrderByList(), model, unmapContext);
        
        return new StatementSpecUnMapResult(model, unmapContext.getIndexedParams());
    }

    private static void unmapOnClause(OnTriggerDesc onTriggerDesc, EPStatementObjectModel model, StatementSpecUnMapContext unmapContext)
    {
        if (onTriggerDesc == null)
        {
            return;
        }
        if (onTriggerDesc.getOnTriggerType() == OnTriggerType.ON_DELETE)
        {
            OnTriggerWindowDesc window = (OnTriggerWindowDesc) onTriggerDesc;
            model.setOnExpr(new OnDeleteClause(window.getWindowName(), window.getOptionalAsName()));
        }
        if (onTriggerDesc.getOnTriggerType() == OnTriggerType.ON_SELECT)
        {
            OnTriggerWindowDesc window = (OnTriggerWindowDesc) onTriggerDesc;
            model.setOnExpr(new OnSelectClause(window.getWindowName(), window.getOptionalAsName()));
        }
        if (onTriggerDesc.getOnTriggerType() == OnTriggerType.ON_SET)
        {
            OnTriggerSetDesc trigger = (OnTriggerSetDesc) onTriggerDesc;
            OnSetClause clause = new OnSetClause();
            for (OnTriggerSetAssignment assignment : trigger.getAssignments())
            {
                Expression expr = unmapExpressionDeep(assignment.getExpression(), unmapContext);
                clause.addAssignment(assignment.getVariableName(), expr);
            }
            model.setOnExpr(clause);
        }
    }

    private static void unmapCreateWindow(CreateWindowDesc createWindowDesc, EPStatementObjectModel model)
    {
        if (createWindowDesc == null)
        {
            return;
        }
        model.setCreateWindow(new CreateWindowClause(createWindowDesc.getWindowName(), unmapViews(createWindowDesc.getViewSpecs())));
    }

    private static void unmapOrderBy(List<OrderByItem> orderByList, EPStatementObjectModel model, StatementSpecUnMapContext unmapContext)
    {
        if ((orderByList == null) || (orderByList.size() == 0))
        {
            return;
        }

        OrderByClause clause = new OrderByClause();
        for (OrderByItem item : orderByList)
        {
            Expression expr = unmapExpressionDeep(item.getExprNode(), unmapContext);
            clause.add(expr, item.isDescending());
        }
        model.setOrderByClause(clause);
    }

    private static void unmapOutputLimit(OutputLimitSpec outputLimitSpec, EPStatementObjectModel model)
    {
        if (outputLimitSpec == null)
        {
            return;
        }

        OutputLimitSelector selector = OutputLimitSelector.ALL;
        if (outputLimitSpec.isDisplayFirstOnly())
        {
            selector = OutputLimitSelector.FIRST;
        }
        if (outputLimitSpec.isDisplayLastOnly())
        {
            selector = OutputLimitSelector.LAST;
        }

        OutputLimitClause clause;
        if (outputLimitSpec.isEventLimit())
        {
            clause = new OutputLimitClause(selector, outputLimitSpec.getEventRate(), OutputLimitUnit.EVENTS);
        }
        else
        {
            clause = new OutputLimitClause(selector, outputLimitSpec.getTimeRate(), OutputLimitUnit.SECONDS);
        }
        model.setOutputLimitClause(clause);
    }

    private static void mapOrderBy(OrderByClause orderByClause, StatementSpecRaw raw, StatementSpecMapContext mapContext)
    {
        if (orderByClause == null)
        {
            return;
        }
        for (OrderByElement element : orderByClause.getOrderByExpressions())
        {
            ExprNode orderExpr = mapExpressionDeep(element.getExpression(), mapContext);
            OrderByItem item = new OrderByItem(orderExpr, element.isDescending());
            raw.getOrderByList().add(item);
        }
    }

    private static void mapOutputLimit(OutputLimitClause outputLimitClause, StatementSpecRaw raw)
    {
        if (outputLimitClause == null)
        {
            return;
        }
        OutputLimitSpec spec;
        OutputLimitSpec.DisplayLimit displayLimit = OutputLimitSpec.DisplayLimit.valueOf(outputLimitClause.getSelector().toString().toUpperCase());
        if (outputLimitClause.getUnit() == OutputLimitUnit.EVENTS)
        {
            spec = new OutputLimitSpec((int) outputLimitClause.getFrequency(), displayLimit);
        }
        else
        {
            spec = new OutputLimitSpec(outputLimitClause.getFrequency(), displayLimit);
        }
        raw.setOutputLimitSpec(spec);
    }

    private static void mapOnTrigger(OnClause onExpr, StatementSpecRaw raw, StatementSpecMapContext mapContext)
    {
        if (onExpr == null)
        {
            return;
        }

        if (onExpr instanceof OnDeleteClause)
        {
            OnDeleteClause onDeleteClause = (OnDeleteClause) onExpr;
            raw.setOnTriggerDesc(new OnTriggerWindowDesc(onDeleteClause.getWindowName(), onDeleteClause.getOptionalAsName(), true));
        }
        else if (onExpr instanceof OnSelectClause)
        {
            OnSelectClause onSelectClause = (OnSelectClause) onExpr;
            raw.setOnTriggerDesc(new OnTriggerWindowDesc(onSelectClause.getWindowName(), onSelectClause.getOptionalAsName(), true));            
        }
        else if (onExpr instanceof OnSetClause)
        {
            OnSetClause setClause = (OnSetClause) onExpr;
            OnTriggerSetDesc desc = new OnTriggerSetDesc();
            mapContext.setHasVariables(true);
            for (Pair<String, Expression> pair : setClause.getAssignments())
            {
                ExprNode expr = mapExpressionDeep(pair.getSecond(), mapContext);
                desc.addAssignment(new OnTriggerSetAssignment(pair.getFirst(), expr));
            }
            raw.setOnTriggerDesc(desc);
        }
        else
        {
            throw new IllegalArgumentException("Cannot map on-clause expression type : " + onExpr);
        }
    }

    private static void mapHaving(Expression havingClause, StatementSpecRaw raw, StatementSpecMapContext mapContext)
    {
        if (havingClause == null)
        {
            return;
        }
        ExprNode node = mapExpressionDeep(havingClause, mapContext);
        raw.setHavingExprRootNode(node);
    }

    private static void unmapHaving(ExprNode havingExprRootNode, EPStatementObjectModel model, StatementSpecUnMapContext unmapContext)
    {
        if (havingExprRootNode == null)
        {
            return;
        }
        Expression expr = unmapExpressionDeep(havingExprRootNode, unmapContext);
        model.setHavingClause(expr);
    }

    private static void mapGroupBy(GroupByClause groupByClause, StatementSpecRaw raw, StatementSpecMapContext mapContext)
    {
        if (groupByClause == null)
        {
            return;
        }
        for (Expression expr : groupByClause.getGroupByExpressions())
        {
            ExprNode node = mapExpressionDeep(expr, mapContext);
            raw.getGroupByExpressions().add(node);
        }
    }

    private static void unmapGroupBy(List<ExprNode> groupByExpressions, EPStatementObjectModel model, StatementSpecUnMapContext unmapContext)
    {
        if (groupByExpressions.size() == 0)
        {
            return;
        }
        GroupByClause clause = new GroupByClause();
        for (ExprNode node : groupByExpressions)
        {
            Expression expr = unmapExpressionDeep(node, unmapContext);
            clause.getGroupByExpressions().add(expr);
        }
        model.setGroupByClause(clause);
    }

    private static void mapWhere(Expression whereClause, StatementSpecRaw raw, StatementSpecMapContext mapContext)
    {
        if (whereClause == null)
        {
            return;
        }
        ExprNode node = mapExpressionDeep(whereClause, mapContext);
        raw.setFilterRootNode(node);
    }

    private static void unmapWhere(ExprNode filterRootNode, EPStatementObjectModel model, StatementSpecUnMapContext unmapContext)
    {
        if (filterRootNode == null)
        {
            return;
        }
        Expression expr = unmapExpressionDeep(filterRootNode, unmapContext);
        model.setWhereClause(expr);
    }

    private static void unmapFrom(List<StreamSpecRaw> streamSpecs, List<OuterJoinDesc> outerJoinDescList, EPStatementObjectModel model, StatementSpecUnMapContext unmapContext)
    {
        FromClause from = new FromClause();
        model.setFromClause(from);
        
        for (StreamSpecRaw stream : streamSpecs)
        {
            Stream targetStream;
            if (stream instanceof FilterStreamSpecRaw)
            {
                FilterStreamSpecRaw filterStreamSpec = (FilterStreamSpecRaw) stream;
                Filter filter = unmapFilter(filterStreamSpec.getRawFilterSpec(), unmapContext);
                targetStream = new FilterStream(filter, filterStreamSpec.getOptionalStreamName());
            }
            else if (stream instanceof DBStatementStreamSpec)
            {
                DBStatementStreamSpec db = (DBStatementStreamSpec) stream;
                targetStream = new SQLStream(db.getDatabaseName(), db.getSqlWithSubsParams(), db.getOptionalStreamName(), db.getMetadataSQL());
            }
            else if (stream instanceof PatternStreamSpecRaw)
            {
                PatternStreamSpecRaw pattern = (PatternStreamSpecRaw) stream;
                PatternExpr patternExpr = unmapPatternEvalDeep(pattern.getEvalNode(), unmapContext);
                targetStream = new PatternStream(patternExpr, pattern.getOptionalStreamName());
            }
            else
            {
                throw new IllegalArgumentException("Stream modelled by " + stream.getClass() + " cannot be unmapped");
            }

            if (targetStream instanceof ProjectedStream)
            {
                ProjectedStream projStream = (ProjectedStream) targetStream;
                for (ViewSpec viewSpec : stream.getViewSpecs())
                {
                    projStream.addView(View.create(viewSpec.getObjectNamespace(), viewSpec.getObjectName(), viewSpec.getObjectParameters()));
                }
            }
            from.add(targetStream);
        }

        for (OuterJoinDesc desc : outerJoinDescList)
        {
            PropertyValueExpression left = (PropertyValueExpression) unmapExpressionFlat(desc.getLeftNode(), unmapContext);
            PropertyValueExpression right = (PropertyValueExpression) unmapExpressionFlat(desc.getRightNode(), unmapContext);

            ArrayList<Pair<PropertyValueExpression, PropertyValueExpression>> additionalProperties = new ArrayList<Pair<PropertyValueExpression, PropertyValueExpression>>();
            if (desc.getAdditionalLeftNodes() != null)
            {
                for (int i = 0; i < desc.getAdditionalLeftNodes().length; i++)
                {
                    ExprIdentNode leftNode = desc.getAdditionalLeftNodes()[i];
                    ExprIdentNode rightNode = desc.getAdditionalRightNodes()[i];
                    PropertyValueExpression propLeft = (PropertyValueExpression) unmapExpressionFlat(leftNode, unmapContext);
                    PropertyValueExpression propRight = (PropertyValueExpression) unmapExpressionFlat(rightNode, unmapContext);
                    additionalProperties.add(new Pair<PropertyValueExpression, PropertyValueExpression>(propLeft, propRight));
                }
            }
            from.add(new OuterJoinQualifier(desc.getOuterJoinType(), left, right, additionalProperties));
        }

    }

    private static void unmapSelect(SelectClauseSpec selectClauseSpec, SelectClauseStreamSelectorEnum selectStreamSelectorEnum, EPStatementObjectModel model, StatementSpecUnMapContext unmapContext)
    {
        SelectClause clause = SelectClause.create();
        clause.setWildcard(selectClauseSpec.isUsingWildcard());
        clause.setStreamSelector(SelectClauseStreamSelectorEnum.mapFromSODA(selectStreamSelectorEnum));
        for (SelectExprElementRawSpec raw : selectClauseSpec.getSelectExprList())
        {
            Expression expression = unmapExpressionDeep(raw.getSelectExpression(), unmapContext);
            clause.add(expression, raw.getOptionalAsName());
        }
        for (SelectExprElementStreamRawSpec raw : selectClauseSpec.getSelectStreamsList())
        {
            clause.addStreamWildcard(raw.getStreamAliasName(), raw.getOptionalAsName());
        }
        model.setSelectClause(clause);
    }

    private static void unmapInsertInto(InsertIntoDesc insertIntoDesc, EPStatementObjectModel model)
    {
        StreamSelector s = StreamSelector.ISTREAM_ONLY;
        if (insertIntoDesc == null)
        {
            return;
        }
        if (!insertIntoDesc.isIStream())
        {
            s = StreamSelector.RSTREAM_ONLY;
        }
        model.setInsertInto(
                InsertIntoClause.create(insertIntoDesc.getEventTypeAlias(),
                        insertIntoDesc.getColumnNames().toArray(new String[0]), s));
    }

    private static void mapCreateWindow(CreateWindowClause createWindow, StatementSpecRaw raw)
    {
        if (createWindow == null)
        {
            return;
        }

        raw.setCreateWindowDesc(new CreateWindowDesc(createWindow.getWindowName(), mapViews(createWindow.getViews())));
    }

    private static void mapInsertInto(InsertIntoClause insertInto, StatementSpecRaw raw)
    {
        if (insertInto == null)
        {
            return;
        }

        boolean isIStream = insertInto.isIStream();
        String eventTypeAlias = insertInto.getStreamName();
        InsertIntoDesc desc = new InsertIntoDesc(isIStream, eventTypeAlias);

        for (String name : insertInto.getColumnNames())
        {
            desc.add(name);
        }

        raw.setInsertIntoDesc(desc);
    }

    private static void mapSelect(SelectClause selectClause, StatementSpecRaw raw, StatementSpecMapContext mapContext)
    {
        if (selectClause == null)
        {
            return;
        }
        SelectClauseSpec spec = new SelectClauseSpec();
        spec.setIsUsingWildcard(selectClause.isWildcard());
        raw.setSelectStreamDirEnum(SelectClauseStreamSelectorEnum.mapFromSODA(selectClause.getStreamSelector()));
        raw.setSelectClauseSpec(spec);

        for (SelectClauseElement element : selectClause.getSelectList())
        {
            Expression expr = element.getExpression();
            ExprNode exprNode = mapExpressionDeep(expr, mapContext);

            SelectExprElementRawSpec rawElement = new SelectExprElementRawSpec(exprNode, element.getAsName());
            spec.add(rawElement);
        }
        for (SelectClauseStreamWildcard element : selectClause.getStreamWildcardSelectList())
        {
            SelectExprElementStreamRawSpec rawElement = new SelectExprElementStreamRawSpec(element.getStreamAliasName(), element.getOptionalColumnAlias());
            spec.add(rawElement);
        }
    }

    private static Expression unmapExpressionDeep(ExprNode exprNode, StatementSpecUnMapContext unmapContext)
    {
        Expression parent = unmapExpressionFlat(exprNode, unmapContext);
        unmapExpressionRecursive(parent, exprNode, unmapContext);
        return parent;
    }

    private static ExprNode mapExpressionDeep(Expression expr, StatementSpecMapContext mapContext)
    {
        ExprNode parent = mapExpressionFlat(expr, mapContext);
        mapExpressionRecursive(parent, expr, mapContext);
        return parent;
    }

    private static ExprNode mapExpressionFlat(Expression expr, StatementSpecMapContext mapContext)
    {
        if (expr == null)
        {
            throw new IllegalArgumentException("Null expression parameter");
        }
        if (expr instanceof ArithmaticExpression)
        {
            ArithmaticExpression arith = (ArithmaticExpression) expr;
            return new ExprMathNode(MathArithTypeEnum.parseOperator(arith.getOperator()));
        }
        else if (expr instanceof PropertyValueExpression)
        {
            PropertyValueExpression prop = (PropertyValueExpression) expr;
            int indexDot = prop.getPropertyName().indexOf('.');
            if (indexDot != -1)
            {
                String stream = prop.getPropertyName().substring(0, indexDot);
                String property = prop.getPropertyName().substring(indexDot + 1, prop.getPropertyName().length());
                return new ExprIdentNode(property, stream);
            }

            if (mapContext.getVariableService().getReader(prop.getPropertyName()) != null)
            {
                mapContext.setHasVariables(true);
                return new ExprVariableNode(prop.getPropertyName());
            }
            return new ExprIdentNode(prop.getPropertyName());
        }
        else if (expr instanceof Conjunction)
        {
            return new ExprAndNode();
        }
        else if (expr instanceof Disjunction)
        {
            return new ExprOrNode();
        }
        else if (expr instanceof RelationalOpExpression)
        {
            RelationalOpExpression op = (RelationalOpExpression) expr;
            if (op.getOperator().equals("="))
            {
                return new ExprEqualsNode(false);
            }
            if (op.getOperator().equals("!="))
            {
                return new ExprEqualsNode(true);
            }
            else
            {
                return new ExprRelationalOpNode(RelationalOpEnum.parse(op.getOperator()));
            }
        }
        else if (expr instanceof ConstantExpression)
        {
            ConstantExpression op = (ConstantExpression) expr;
            return new ExprConstantNode(op.getConstant());
        }
        else if (expr instanceof ConcatExpression)
        {
            return new ExprConcatNode();
        }
        else if (expr instanceof SubqueryExpression)
        {
            SubqueryExpression sub = (SubqueryExpression) expr;
            StatementSpecRaw rawSubselect = map(sub.getModel(), mapContext);
            return new ExprSubselectRowNode(rawSubselect);
        }
        else if (expr instanceof SubqueryInExpression)
        {
            SubqueryInExpression sub = (SubqueryInExpression) expr;
            StatementSpecRaw rawSubselect = map(sub.getModel(), mapContext);
            ExprSubselectInNode inSub = new ExprSubselectInNode(rawSubselect);
            inSub.setNotIn(sub.isNotIn());
            return inSub;
        }
        else if (expr instanceof SubqueryExistsExpression)
        {
            SubqueryExistsExpression sub = (SubqueryExistsExpression) expr;
            StatementSpecRaw rawSubselect = map(sub.getModel(), mapContext);
            return new ExprSubselectExistsNode(rawSubselect);
        }
        else if (expr instanceof CountStarProjectionExpression)
        {
            return new ExprCountNode(false);
        }
        else if (expr instanceof CountProjectionExpression)
        {
            CountProjectionExpression count = (CountProjectionExpression) expr;
            return new ExprCountNode(count.isDistinct());
        }
        else if (expr instanceof AvgProjectionExpression)
        {
            AvgProjectionExpression avg = (AvgProjectionExpression) expr;
            return new ExprAvgNode(avg.isDistinct());
        }
        else if (expr instanceof SumProjectionExpression)
        {
            SumProjectionExpression avg = (SumProjectionExpression) expr;
            return new ExprSumNode(avg.isDistinct());
        }
        else if (expr instanceof BetweenExpression)
        {
            BetweenExpression between = (BetweenExpression) expr;
            return new ExprBetweenNode(between.isLowEndpointIncluded(), between.isHighEndpointIncluded(), between.isNotBetween());
        }
        else if (expr instanceof PriorExpression)
        {
            return new ExprPriorNode();
        }
        else if (expr instanceof PreviousExpression)
        {
            return new ExprPreviousNode();
        }
        else if (expr instanceof StaticMethodExpression)
        {
            StaticMethodExpression method = (StaticMethodExpression) expr;
            return new ExprStaticMethodNode(method.getClassName(), method.getMethod());
        }
        else if (expr instanceof MinProjectionExpression)
        {
            MinProjectionExpression method = (MinProjectionExpression) expr;
            return new ExprMinMaxAggrNode(method.isDistinct(), MinMaxTypeEnum.MIN);
        }
        else if (expr instanceof MaxProjectionExpression)
        {
            MaxProjectionExpression method = (MaxProjectionExpression) expr;
            return new ExprMinMaxAggrNode(method.isDistinct(), MinMaxTypeEnum.MAX);
        }
        else if (expr instanceof NotExpression)
        {
            return new ExprNotNode();
        }
        else if (expr instanceof InExpression)
        {
            InExpression in = (InExpression) expr;
            return new ExprInNode(in.isNotIn());
        }
        else if (expr instanceof CoalesceExpression)
        {
            return new ExprCoalesceNode();
        }
        else if (expr instanceof CaseWhenThenExpression)
        {
            return new ExprCaseNode(false);
        }
        else if (expr instanceof CaseSwitchExpression)
        {
            return new ExprCaseNode(true);
        }
        else if (expr instanceof MaxRowExpression)
        {
            return new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
        }
        else if (expr instanceof MinRowExpression)
        {
            return new ExprMinMaxRowNode(MinMaxTypeEnum.MIN);
        }
        else if (expr instanceof BitwiseOpExpression)
        {
            BitwiseOpExpression bit = (BitwiseOpExpression) expr;
            return new ExprBitWiseNode(bit.getBinaryOp());
        }
        else if (expr instanceof ArrayExpression)
        {
            return new ExprArrayNode();
        }
        else if (expr instanceof LikeExpression)
        {
            return new ExprLikeNode(false);
        }
        else if (expr instanceof RegExpExpression)
        {
            return new ExprRegexpNode(false);
        }
        else if (expr instanceof MedianProjectionExpression)
        {
            MedianProjectionExpression median = (MedianProjectionExpression) expr;
            return new ExprMedianNode(median.isDistinct());
        }
        else if (expr instanceof AvedevProjectionExpression)
        {
            AvedevProjectionExpression node = (AvedevProjectionExpression) expr;
            return new ExprAvedevNode(node.isDistinct());
        }
        else if (expr instanceof StddevProjectionExpression)
        {
            StddevProjectionExpression node = (StddevProjectionExpression) expr;
            return new ExprStddevNode(node.isDistinct());
        }
        else if (expr instanceof InstanceOfExpression)
        {
            InstanceOfExpression node = (InstanceOfExpression) expr;
            return new ExprInstanceofNode(node.getTypeNames());
        }
        else if (expr instanceof CastExpression)
        {
            CastExpression node = (CastExpression) expr;
            return new ExprCastNode(node.getTypeName());
        }
        else if (expr instanceof PropertyExistsExpression)
        {
            return new ExprPropertyExistsNode();
        }
        else if (expr instanceof CurrentTimestampExpression)
        {
            return new ExprTimestampNode();
        }
        else if (expr instanceof SubstitutionParameterExpression)
        {
            SubstitutionParameterExpression node = (SubstitutionParameterExpression) expr;
            if (!(node.isSatisfied()))
            {
                throw new EPException("Substitution parameter value for index " + node.getIndex() + " not set, please provide a value for this parameter");
            }
            return new ExprConstantNode(node.getConstant());
        }
        else if (expr instanceof PlugInProjectionExpression)
        {
            PlugInProjectionExpression node = (PlugInProjectionExpression) expr;
            try
            {
                AggregationSupport aggregation = mapContext.getEngineImportService().resolveAggregation(node.getFunctionName());
                return new ExprPlugInAggFunctionNode(node.isDistinct(), aggregation, node.getFunctionName());
            }
            catch (EngineImportUndefinedException e)
            {
                throw new EPException("Error resolving aggregation: " + e.getMessage(), e);
            }
            catch (EngineImportException e)
            {
                throw new EPException("Error resolving aggregation: " + e.getMessage(), e);            
            }
        }
        throw new IllegalArgumentException("Could not map expression node of type " + expr.getClass().getSimpleName());
    }

    private static Expression unmapExpressionFlat(ExprNode expr, StatementSpecUnMapContext unmapContext)
    {
        if (expr instanceof ExprMathNode)
        {
            ExprMathNode math = (ExprMathNode) expr;
            return new ArithmaticExpression(math.getMathArithTypeEnum().getExpressionText());
        }
        else if (expr instanceof ExprIdentNode)
        {
            ExprIdentNode prop = (ExprIdentNode) expr;
            String propertyName = prop.getUnresolvedPropertyName();
            if (prop.getStreamOrPropertyName() != null)
            {
                propertyName = prop.getStreamOrPropertyName() + "." + prop.getUnresolvedPropertyName();
            }
            return new PropertyValueExpression(propertyName);
        }
        else if (expr instanceof ExprVariableNode)
        {
            ExprVariableNode prop = (ExprVariableNode) expr;
            String propertyName = prop.getVariableName();
            return new PropertyValueExpression(propertyName);
        }
        else if (expr instanceof ExprEqualsNode)
        {
            ExprEqualsNode equals = (ExprEqualsNode) expr;
            String operator = "=";
            if (equals.isNotEquals())
            {
                operator = "!=";
            }
            return new RelationalOpExpression(operator);
        }
        else if (expr instanceof ExprRelationalOpNode)
        {
            ExprRelationalOpNode rel = (ExprRelationalOpNode) expr;
            return new RelationalOpExpression(rel.getRelationalOpEnum().getExpressionText());
        }
        else if (expr instanceof ExprAndNode)
        {
            return new Conjunction();
        }
        else if (expr instanceof ExprOrNode)
        {
            return new Disjunction();
        }
        else if (expr instanceof ExprConstantNode)
        {
            ExprConstantNode constNode = (ExprConstantNode) expr;
            return new ConstantExpression(constNode.getValue());
        }
        else if (expr instanceof ExprConcatNode)
        {
            return new ConcatExpression();
        }
        else if (expr instanceof ExprSubselectRowNode)
        {
            ExprSubselectRowNode sub = (ExprSubselectRowNode) expr;
            StatementSpecUnMapResult unmapped = unmap(sub.getStatementSpecRaw());
            unmapContext.addAll(unmapped.getIndexedParams());
            return new SubqueryExpression(unmapped.getObjectModel());
        }
        else if (expr instanceof ExprSubselectInNode)
        {
            ExprSubselectInNode sub = (ExprSubselectInNode) expr;
            StatementSpecUnMapResult unmapped = unmap(sub.getStatementSpecRaw());
            unmapContext.addAll(unmapped.getIndexedParams());
            return new SubqueryInExpression(unmapped.getObjectModel(), sub.isNotIn());
        }
        else if (expr instanceof ExprSubselectExistsNode)
        {
            ExprSubselectExistsNode sub = (ExprSubselectExistsNode) expr;
            StatementSpecUnMapResult unmapped = unmap(sub.getStatementSpecRaw());
            unmapContext.addAll(unmapped.getIndexedParams());
            return new SubqueryExistsExpression(unmapped.getObjectModel());
        }
        else if (expr instanceof ExprCountNode)
        {
            ExprCountNode sub = (ExprCountNode) expr;
            if (sub.getChildNodes().size() == 0)
            {
                return new CountStarProjectionExpression();
            }
            else
            {
                return new CountProjectionExpression(sub.isDistinct());
            }
        }
        else if (expr instanceof ExprAvgNode)
        {
            ExprAvgNode sub = (ExprAvgNode) expr;            
            return new AvgProjectionExpression(sub.isDistinct());
        }
        else if (expr instanceof ExprSumNode)
        {
            ExprSumNode sub = (ExprSumNode) expr;
            return new SumProjectionExpression(sub.isDistinct());
        }
        else if (expr instanceof ExprBetweenNode)
        {
            ExprBetweenNode between = (ExprBetweenNode) expr;
            return new BetweenExpression(between.isLowEndpointIncluded(), between.isHighEndpointIncluded(), between.isNotBetween());
        }
        else if (expr instanceof ExprPriorNode)
        {
            return new PriorExpression();
        }
        else if (expr instanceof ExprPreviousNode)
        {
            return new PreviousExpression();
        }
        else if (expr instanceof ExprStaticMethodNode)
        {
            ExprStaticMethodNode node = (ExprStaticMethodNode) expr;
            return new StaticMethodExpression(node.getClassName(), node.getMethodName());
        }
        else if (expr instanceof ExprMinMaxAggrNode)
        {
            ExprMinMaxAggrNode node = (ExprMinMaxAggrNode) expr;
            if (node.getMinMaxTypeEnum() == MinMaxTypeEnum.MIN)
            {
                return new MinProjectionExpression(node.isDistinct());
            }
            else
            {
                return new MaxProjectionExpression(node.isDistinct());
            }
        }
        else if (expr instanceof ExprNotNode)
        {
            return new NotExpression();
        }
        else if (expr instanceof ExprInNode)
        {
            ExprInNode in = (ExprInNode) expr;
            return new InExpression(in.isNotIn());
        }
        else if (expr instanceof ExprCoalesceNode)
        {
            return new CoalesceExpression();
        }
        else if (expr instanceof ExprCaseNode)
        {
            ExprCaseNode mycase = (ExprCaseNode) expr;
            if (mycase.isCase2())
            {
                return new CaseSwitchExpression();
            }
            else
            {
                return new CaseWhenThenExpression();
            }
        }
        else if (expr instanceof ExprMinMaxRowNode)
        {
            ExprMinMaxRowNode node = (ExprMinMaxRowNode) expr;
            if (node.getMinMaxTypeEnum() == MinMaxTypeEnum.MAX)
            {
                return new MaxRowExpression();
            }
            return new MinRowExpression();
        }
        else if (expr instanceof ExprBitWiseNode)
        {
            ExprBitWiseNode node = (ExprBitWiseNode) expr;
            return new BitwiseOpExpression(node.getBitWiseOpEnum());
        }
        else if (expr instanceof ExprArrayNode)
        {
            return new ArrayExpression();
        }
        else if (expr instanceof ExprLikeNode)
        {
            return new LikeExpression();
        }
        else if (expr instanceof ExprRegexpNode)
        {
            return new RegExpExpression();
        }
        else if (expr instanceof ExprMedianNode)
        {
            ExprMedianNode median = (ExprMedianNode) expr;
            return new MedianProjectionExpression(median.isDistinct());
        }
        else if (expr instanceof ExprAvedevNode)
        {
            ExprAvedevNode node = (ExprAvedevNode) expr;
            return new AvedevProjectionExpression(node.isDistinct());
        }
        else if (expr instanceof ExprStddevNode)
        {
            ExprStddevNode node = (ExprStddevNode) expr;
            return new StddevProjectionExpression(node.isDistinct());
        }
        else if (expr instanceof ExprPlugInAggFunctionNode)
        {
            ExprPlugInAggFunctionNode node = (ExprPlugInAggFunctionNode) expr;
            return new PlugInProjectionExpression(node.getAggregationFunctionName(), node.isDistinct());
        }
        else if (expr instanceof ExprInstanceofNode)
        {
            ExprInstanceofNode node = (ExprInstanceofNode) expr;
            return new InstanceOfExpression(node.getClassIdentifiers());
        }
        else if (expr instanceof ExprCastNode)
        {
            ExprCastNode node = (ExprCastNode) expr;
            return new CastExpression(node.getClassIdentifier());
        }
        else if (expr instanceof ExprPropertyExistsNode)
        {
            return new PropertyExistsExpression();
        }
        else if (expr instanceof ExprTimestampNode)
        {
            return new CurrentTimestampExpression();
        }
        else if (expr instanceof ExprSubstitutionNode)
        {
            ExprSubstitutionNode node = (ExprSubstitutionNode) expr;
            SubstitutionParameterExpression subParam = new SubstitutionParameterExpression(node.getIndex());
            unmapContext.add(node.getIndex(), subParam);
            return subParam;
        }
        throw new IllegalArgumentException("Could not map expression node of type " + expr.getClass().getSimpleName());
    }

    private static void unmapExpressionRecursive(Expression parent, ExprNode expr, StatementSpecUnMapContext unmapContext)
    {
        for (ExprNode child : expr.getChildNodes())
        {
            Expression result = unmapExpressionFlat(child, unmapContext);
            parent.getChildren().add(result);
            unmapExpressionRecursive(result, child, unmapContext);
        }
    }

    private static void mapExpressionRecursive(ExprNode parent, Expression expr, StatementSpecMapContext mapContext)
    {
        for (Expression child : expr.getChildren())
        {
            ExprNode result = mapExpressionFlat(child, mapContext);
            parent.addChildNode(result);
            mapExpressionRecursive(result, child, mapContext);
        }
    }

    private static void mapFrom(FromClause fromClause, StatementSpecRaw raw, StatementSpecMapContext mapContext)
    {
        for (Stream stream : fromClause.getStreams())
        {
            StreamSpecRaw spec;
            
            if (stream instanceof FilterStream)
            {
                FilterStream filterStream = (FilterStream) stream;
                FilterSpecRaw filterSpecRaw = mapFilter(filterStream.getFilter(), mapContext);
                spec = new FilterStreamSpecRaw(filterSpecRaw, new ArrayList<ViewSpec>(), filterStream.getStreamName());
            }
            else if (stream instanceof SQLStream)
            {
                SQLStream sqlStream = (SQLStream) stream;
                spec = new DBStatementStreamSpec(sqlStream.getStreamName(), new ArrayList<ViewSpec>(),
                        sqlStream.getDatabaseName(), sqlStream.getSqlWithSubsParams(), sqlStream.getOptionalMetadataSQL());
            }
            else if (stream instanceof PatternStream)
            {
                PatternStream patternStream = (PatternStream) stream;
                EvalNode child = mapPatternEvalDeep(patternStream.getExpression(), mapContext);
                spec = new PatternStreamSpecRaw(child, new ArrayList<ViewSpec>(), patternStream.getStreamName());
            }
            else
            {
                throw new IllegalArgumentException("Could not map from stream " + stream + " to an internal representation");
            }

            raw.getStreamSpecs().add(spec);
            
            if (stream instanceof ProjectedStream)
            {
                ProjectedStream projectedStream = (ProjectedStream) stream;
                spec.getViewSpecs().addAll(mapViews(projectedStream.getViews()));
            }
        }

        for (OuterJoinQualifier qualifier : fromClause.getOuterJoinQualifiers())
        {
            ExprIdentNode left = (ExprIdentNode) mapExpressionFlat(qualifier.getLeft(), mapContext);
            ExprIdentNode right = (ExprIdentNode) mapExpressionFlat(qualifier.getRight(), mapContext);

            ExprIdentNode[] additionalLeft = null;
            ExprIdentNode[] additionalRight = null;
            if (qualifier.getAdditionalProperties().size() != 0)
            {
                additionalLeft = new ExprIdentNode[qualifier.getAdditionalProperties().size()];
                additionalRight = new ExprIdentNode[qualifier.getAdditionalProperties().size()];
                int count = 0;
                for (Pair<PropertyValueExpression, PropertyValueExpression> pair : qualifier.getAdditionalProperties())
                {
                    additionalLeft[count] = (ExprIdentNode) mapExpressionFlat(pair.getFirst(), mapContext);
                    additionalRight[count] = (ExprIdentNode) mapExpressionFlat(pair.getSecond(), mapContext);
                    count++;
                }
            }
            raw.getOuterJoinDescList().add(new OuterJoinDesc(qualifier.getType(), left, right, additionalLeft, additionalRight));
        }
    }

    private static List<ViewSpec> mapViews(List<View> views)
    {
        List<ViewSpec> viewSpecs = new ArrayList<ViewSpec>();
        for (View view : views)
        {
            viewSpecs.add(new ViewSpec(view.getNamespace(), view.getName(), view.getParameters()));
        }
        return viewSpecs;
    }

    private static List<View> unmapViews(List<ViewSpec> viewSpecs)
    {
        List<View> views = new ArrayList<View>();
        for (ViewSpec viewSpec : viewSpecs)
        {
            views.add(View.create(viewSpec.getObjectNamespace(), viewSpec.getObjectName(), viewSpec.getObjectParameters()));
        }
        return views;
    }

    private static EvalNode mapPatternEvalFlat(PatternExpr eval, StatementSpecMapContext mapContext)
    {
        if (eval == null)
        {
            throw new IllegalArgumentException("Null expression parameter");
        }
        if (eval instanceof PatternAndExpr)
        {
            return new EvalAndNode();
        }
        else if (eval instanceof PatternOrExpr)
        {
            return new EvalOrNode();
        }
        else if (eval instanceof PatternFollowedByExpr)
        {
            return new EvalFollowedByNode();
        }
        else if (eval instanceof PatternEveryExpr)
        {
            return new EvalEveryNode();
        }
        else if (eval instanceof PatternFilterExpr)
        {
            PatternFilterExpr filterExpr = (PatternFilterExpr) eval;
            FilterSpecRaw filterSpec = mapFilter(filterExpr.getFilter(), mapContext);
            return new EvalFilterNode(filterSpec, filterExpr.getTagName());
        }
        else if (eval instanceof PatternObserverExpr)
        {
            PatternObserverExpr observer = (PatternObserverExpr) eval;
            return new EvalObserverNode(new PatternObserverSpec(observer.getNamespace(), observer.getName(), observer.getParameters()));
        }
        else if (eval instanceof PatternGuardExpr)
        {
            PatternGuardExpr guard = (PatternGuardExpr) eval;
            return new EvalGuardNode(new PatternGuardSpec(guard.getNamespace(), guard.getName(), guard.getParameters()));
        }
        else if (eval instanceof PatternNotExpr)
        {
            return new EvalNotNode();
        }
        throw new IllegalArgumentException("Could not map pattern expression node of type " + eval.getClass().getSimpleName());
    }

    private static PatternExpr unmapPatternEvalFlat(EvalNode eval, StatementSpecUnMapContext unmapContext)
    {
        if (eval instanceof EvalAndNode)
        {
            return new PatternAndExpr();
        }
        else if (eval instanceof EvalOrNode)
        {
            return new PatternOrExpr();
        }
        else if (eval instanceof EvalFollowedByNode)
        {
            return new PatternFollowedByExpr();
        }
        else if (eval instanceof EvalEveryNode)
        {
            return new PatternEveryExpr();
        }
        else if (eval instanceof EvalNotNode)
        {
            return new PatternNotExpr();
        }
        else if (eval instanceof EvalFilterNode)
        {
            EvalFilterNode filterNode = (EvalFilterNode) eval;
            Filter filter = unmapFilter(filterNode.getRawFilterSpec(), unmapContext);
            return new PatternFilterExpr(filter, filterNode.getEventAsName());
        }
        else if (eval instanceof EvalObserverNode)
        {
            EvalObserverNode observerNode = (EvalObserverNode) eval;
            return new PatternObserverExpr(observerNode.getPatternObserverSpec().getObjectNamespace(),
                    observerNode.getPatternObserverSpec().getObjectName(), observerNode.getPatternObserverSpec().getObjectParameters());
        }
        else if (eval instanceof EvalGuardNode)
        {
            EvalGuardNode guardNode = (EvalGuardNode) eval;
            return new PatternGuardExpr(guardNode.getPatternGuardSpec().getObjectNamespace(),
                    guardNode.getPatternGuardSpec().getObjectName(), guardNode.getPatternGuardSpec().getObjectParameters());
        }
        throw new IllegalArgumentException("Could not map pattern expression node of type " + eval.getClass().getSimpleName());
    }

    private static void unmapPatternEvalRecursive(PatternExpr parent, EvalNode eval, StatementSpecUnMapContext unmapContext)
    {
        for (EvalNode child : eval.getChildNodes())
        {
            PatternExpr result = unmapPatternEvalFlat(child, unmapContext);
            parent.getChildren().add(result);
            unmapPatternEvalRecursive(result, child, unmapContext);
        }
    }

    private static void mapPatternEvalRecursive(EvalNode parent, PatternExpr expr, StatementSpecMapContext mapContext)
    {
        for (PatternExpr child : expr.getChildren())
        {
            EvalNode result = mapPatternEvalFlat(child, mapContext);
            parent.addChildNode(result);
            mapPatternEvalRecursive(result, child, mapContext);
        }
    }

    private static PatternExpr unmapPatternEvalDeep(EvalNode exprNode, StatementSpecUnMapContext unmapContext)
    {
        PatternExpr parent = unmapPatternEvalFlat(exprNode, unmapContext);
        unmapPatternEvalRecursive(parent, exprNode, unmapContext);
        return parent;
    }

    private static EvalNode mapPatternEvalDeep(PatternExpr expr, StatementSpecMapContext mapContext)
    {
        EvalNode parent = mapPatternEvalFlat(expr, mapContext);
        mapPatternEvalRecursive(parent, expr, mapContext);
        return parent;
    }

    private static FilterSpecRaw mapFilter(Filter filter, StatementSpecMapContext mapContext)
    {
        List<ExprNode> expr = new ArrayList<ExprNode>();
        if (filter.getFilter() != null)
        {
            ExprNode exprNode = mapExpressionDeep(filter.getFilter(), mapContext);
            expr.add(exprNode);
        }

        return new FilterSpecRaw(filter.getEventTypeAlias(), expr);
    }

    private static Filter unmapFilter(FilterSpecRaw filter, StatementSpecUnMapContext unmapContext)
    {
        Expression expr = null;
        if (filter.getFilterExpressions().size() > 1)
        {
            expr = new Conjunction();
            for (ExprNode exprNode : filter.getFilterExpressions())
            {
                Expression expression = unmapExpressionDeep(exprNode, unmapContext);
                expr.getChildren().add(expression);
            }
        }
        else if (filter.getFilterExpressions().size() == 1)
        {
            expr = unmapExpressionDeep(filter.getFilterExpressions().get(0), unmapContext);
        }

        return new Filter(filter.getEventTypeAlias(), expr);
    }
}
