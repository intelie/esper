package net.esper.eql.spec;

import net.esper.client.soda.*;
import net.esper.eql.expression.*;
import net.esper.pattern.*;
import net.esper.type.MathArithTypeEnum;
import net.esper.type.RelationalOpEnum;

import java.util.ArrayList;
import java.util.List;

public class StatementSpecTranslator
{
    public static StatementSpecRaw map(EPStatementObjectModel sodaStatement)
    {
        StatementSpecRaw raw = new StatementSpecRaw();
        mapInsertInto(sodaStatement.getInsertInto(), raw);
        mapSelect(sodaStatement.getSelectClause(), raw);
        mapFrom(sodaStatement.getFromClause(), raw);
        mapWhere(sodaStatement.getWhereClause(), raw);
        mapGroupBy(sodaStatement.getGroupByClause(), raw);
        mapHaving(sodaStatement.getHavingClause(), raw);
        mapOutputLimit(sodaStatement.getOutputLimitClause(), raw);
        mapOrderBy(sodaStatement.getOrderByClause(), raw);
        return raw;
    }

    public static EPStatementObjectModel unmap(StatementSpecRaw statementSpec)
    {
        EPStatementObjectModel model = new EPStatementObjectModel();
        unmapInsertInto(statementSpec.getInsertIntoDesc(), model);
        unmapSelect(statementSpec.getSelectClauseSpec(), model);
        unmapFrom(statementSpec.getStreamSpecs(), statementSpec.getOuterJoinDescList(), model);
        unmapWhere(statementSpec.getFilterRootNode(), model);
        unmapGroupBy(statementSpec.getGroupByExpressions(), model);
        unmapHaving(statementSpec.getHavingExprRootNode(), model);
        unmapOutputLimit(statementSpec.getOutputLimitSpec(), model);
        unmapOrderBy(statementSpec.getOrderByList(), model);
        return model;
    }

    private static void unmapOrderBy(List<OrderByItem> orderByList, EPStatementObjectModel model)
    {
        if ((orderByList != null) && (orderByList.size() == 0))
        {
            return;
        }

        OrderByClause clause = new OrderByClause();
        for (OrderByItem item : orderByList)
        {
            Expression expr = unmapExpressionDeep(item.getExprNode());
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

    private static void mapOrderBy(OrderByClause orderByClause, StatementSpecRaw raw)
    {
        if (orderByClause == null)
        {
            return;
        }
        for (OrderByElement element : orderByClause.getOrderByExpressions())
        {
            ExprNode orderExpr = mapExpressionDeep(element.getExpression());
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
            spec = new OutputLimitSpec((double) outputLimitClause.getFrequency(), displayLimit);
        }
        raw.setOutputLimitSpec(spec);
    }

    private static void mapHaving(Expression havingClause, StatementSpecRaw raw)
    {
        if (havingClause == null)
        {
            return;
        }
        ExprNode node = mapExpressionDeep(havingClause);
        raw.setHavingExprRootNode(node);
    }

    private static void unmapHaving(ExprNode havingExprRootNode, EPStatementObjectModel model)
    {
        if (havingExprRootNode == null)
        {
            return;
        }
        Expression expr = unmapExpressionDeep(havingExprRootNode);
        model.setHavingClause(expr);
    }

    private static void mapGroupBy(GroupByClause groupByClause, StatementSpecRaw raw)
    {
        if (groupByClause == null)
        {
            return;
        }
        for (Expression expr : groupByClause.getGroupByExpressions())
        {
            ExprNode node = mapExpressionDeep(expr);
            raw.getGroupByExpressions().add(node);
        }
    }

    private static void unmapGroupBy(List<ExprNode> groupByExpressions, EPStatementObjectModel model)
    {
        if (groupByExpressions.size() == 0)
        {
            return;
        }
        GroupByClause clause = new GroupByClause();
        for (ExprNode node : groupByExpressions)
        {
            Expression expr = unmapExpressionDeep(node);
            clause.getGroupByExpressions().add(expr);
        }
        model.setGroupByClause(clause);
    }

    private static void mapWhere(Expression whereClause, StatementSpecRaw raw)
    {
        if (whereClause == null)
        {
            return;
        }
        ExprNode node = mapExpressionDeep(whereClause);
        raw.setFilterExprRootNode(node);
    }

    private static void unmapWhere(ExprNode filterRootNode, EPStatementObjectModel model)
    {
        if (filterRootNode == null)
        {
            return;
        }
        Expression expr = unmapExpressionDeep(filterRootNode);
        model.setWhereClause(expr);
    }

    private static void unmapFrom(List<StreamSpecRaw> streamSpecs, List<OuterJoinDesc> outerJoinDescList, EPStatementObjectModel model)
    {
        FromClause from = new FromClause();
        model.setFromClause(from);
        
        for (StreamSpecRaw stream : streamSpecs)
        {
            if (stream instanceof FilterStreamSpecRaw)
            {
                FilterStreamSpecRaw filterStreamSpec = (FilterStreamSpecRaw) stream;
                Filter filter = unmapFilter(filterStreamSpec.getRawFilterSpec());
                FilterStream filterStream = new FilterStream(filter, filterStreamSpec.getOptionalStreamName());

                for (ViewSpec viewSpec : filterStreamSpec.getViewSpecs())
                {
                    filterStream.addView(View.create(viewSpec.getObjectNamespace(), viewSpec.getObjectName(), viewSpec.getObjectParameters()));
                }
                from.add(filterStream);
            }
            else if (stream instanceof DBStatementStreamSpec)
            {
                DBStatementStreamSpec db = (DBStatementStreamSpec) stream;
                SQLStream sqlStream = new SQLStream(db.getDatabaseName(), db.getSqlWithSubsParams(), db.getOptionalStreamName());
                from.add(sqlStream);
            }
            else if (stream instanceof PatternStreamSpecRaw)
            {
                PatternStreamSpecRaw pattern = (PatternStreamSpecRaw) stream;
                PatternExpr patternExpr = unmapPatternEvalDeep(pattern.getEvalNode());
                PatternStream pStream = new PatternStream(patternExpr, pattern.getOptionalStreamName());
                from.add(pStream);
            }
            else
            {
                throw new IllegalArgumentException("Stream modelled by " + stream.getClass() + " cannot be unmapped");
            }
        }

        for (OuterJoinDesc desc : outerJoinDescList)
        {
            PropertyValueExpression left = (PropertyValueExpression) unmapExpressionFlat(desc.getLeftNode());
            PropertyValueExpression right = (PropertyValueExpression) unmapExpressionFlat(desc.getRightNode());
            from.add(new OuterJoinQualifier(desc.getOuterJoinType(), left, right));
        }

    }

    private static void unmapSelect(SelectClauseSpec selectClauseSpec, EPStatementObjectModel model)
    {
        SelectClause clause = SelectClause.create();
        clause.setWildcard(selectClauseSpec.isUsingWildcard());
        for (SelectExprElementRawSpec raw : selectClauseSpec.getSelectList())
        {
            Expression expression = unmapExpressionDeep(raw.getSelectExpression());
            clause.add(expression, raw.getOptionalAsName());
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
                InsertInto.create(insertIntoDesc.getEventTypeAlias(),
                        insertIntoDesc.getColumnNames().toArray(new String[0]), s));
    }

    private static void mapInsertInto(InsertInto insertInto, StatementSpecRaw raw)
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

    private static void mapSelect(SelectClause selectClause, StatementSpecRaw raw)
    {
        SelectClauseSpec spec = new SelectClauseSpec();
        spec.setIsUsingWildcard(selectClause.isWildcard());
        raw.setSelectClauseSpec(spec);

        for (SelectClauseElement element : selectClause.getSelectList())
        {
            Expression expr = element.getExpression();
            ExprNode exprNode = mapExpressionDeep(expr);

            SelectExprElementRawSpec rawElement = new SelectExprElementRawSpec(exprNode, element.getOptionalAsName());
            spec.add(rawElement);
        }
    }

    private static Expression unmapExpressionDeep(ExprNode exprNode)
    {
        Expression parent = unmapExpressionFlat(exprNode);
        unmapExpressionRecursive(parent, exprNode);
        return parent;
    }

    private static ExprNode mapExpressionDeep(Expression expr)
    {
        ExprNode parent = mapExpressionFlat(expr);
        mapExpressionRecursive(parent, expr);
        return parent;
    }

    private static ExprNode mapExpressionFlat(Expression expr)
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
            StatementSpecRaw rawSubselect = map(sub.getModel());
            return new ExprSubselectRowNode(rawSubselect);
        }
        else if (expr instanceof SubqueryInExpression)
        {
            SubqueryInExpression sub = (SubqueryInExpression) expr;
            StatementSpecRaw rawSubselect = map(sub.getModel());
            return new ExprSubselectInNode(rawSubselect);
        }
        else if (expr instanceof SubqueryExistsExpression)
        {
            SubqueryExistsExpression sub = (SubqueryExistsExpression) expr;
            StatementSpecRaw rawSubselect = map(sub.getModel());
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
        throw new IllegalArgumentException("Could not map expression node of type " + expr.getClass().getSimpleName());
    }

    private static Expression unmapExpressionFlat(ExprNode expr)
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
        else if (expr instanceof ExprEqualsNode)
        {
            return new RelationalOpExpression("=");
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
            EPStatementObjectModel model = unmap(sub.getStatementSpecRaw());
            return new SubqueryExpression(model);
        }
        else if (expr instanceof ExprSubselectInNode)
        {
            ExprSubselectInNode sub = (ExprSubselectInNode) expr;
            EPStatementObjectModel model = unmap(sub.getStatementSpecRaw());
            return new SubqueryInExpression(model);
        }
        else if (expr instanceof ExprSubselectExistsNode)
        {
            ExprSubselectExistsNode sub = (ExprSubselectExistsNode) expr;
            EPStatementObjectModel model = unmap(sub.getStatementSpecRaw());
            return new SubqueryExistsExpression(model);
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
        throw new IllegalArgumentException("Could not map expression node of type " + expr.getClass().getSimpleName());
    }

    private static void unmapExpressionRecursive(Expression parent, ExprNode expr)
    {
        for (ExprNode child : expr.getChildNodes())
        {
            Expression result = unmapExpressionFlat(child);
            parent.getChildren().add(result);
            unmapExpressionRecursive(result, child);
        }
    }

    private static void mapExpressionRecursive(ExprNode parent, Expression expr)
    {
        for (Expression child : expr.getChildren())
        {
            ExprNode result = mapExpressionFlat(child);
            parent.addChildNode(result);
            mapExpressionRecursive(result, child);
        }
    }

    private static void mapFrom(FromClause fromClause, StatementSpecRaw raw)
    {
        for (ProjectedStream stream : fromClause.getStreams())
        {
            StreamSpecRaw spec;
            
            if (stream instanceof FilterStream)
            {
                FilterStream filterStream = (FilterStream) stream;
                FilterSpecRaw filterSpecRaw = mapFilter(filterStream.getFilter());
                spec = new FilterStreamSpecRaw(filterSpecRaw, new ArrayList<ViewSpec>(), filterStream.getOptStreamName());
            }
            else if (stream instanceof SQLStream)
            {
                SQLStream sqlStream = (SQLStream) stream;
                spec = new DBStatementStreamSpec(sqlStream.getOptStreamName(), new ArrayList<ViewSpec>(),
                        sqlStream.getDatabaseName(), sqlStream.getSqlWithSubsParams());
            }
            else if (stream instanceof PatternStream)
            {
                PatternStream patternStream = (PatternStream) stream;
                EvalNode child = mapPatternEvalDeep(patternStream.getExpression());
                spec = new PatternStreamSpecRaw(child, new ArrayList<ViewSpec>(), patternStream.getOptStreamName());
            }
            else
            {
                throw new IllegalArgumentException("Could not map from stream " + stream + " to an internal representation");
            }

            raw.getStreamSpecs().add(spec);
            addViews(stream, spec);
        }

        for (OuterJoinQualifier qualifier : fromClause.getOuterJoinQualifiers())
        {
            ExprIdentNode left = (ExprIdentNode) mapExpressionFlat(qualifier.getLeft());
            ExprIdentNode right = (ExprIdentNode) mapExpressionFlat(qualifier.getRight());
            raw.getOuterJoinDescList().add(new OuterJoinDesc(qualifier.getType(), left, right));
        }
    }

    private static void addViews(ProjectedStream stream, StreamSpecRaw spec)
    {
        for (View view : stream.getViews())
        {
            spec.getViewSpecs().add(new ViewSpec(view.getNamespace(), view.getName(), view.getParameters()));
        }
    }

    private static EvalNode mapPatternEvalFlat(PatternExpr eval)
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
            FilterSpecRaw filterSpec = mapFilter(filterExpr.getFilter());
            return new EvalFilterNode(filterSpec, filterExpr.getTagName());
        }
        throw new IllegalArgumentException("Could not map pattern expression node of type " + eval.getClass().getSimpleName());
    }

    private static PatternExpr unmapPatternEvalFlat(EvalNode eval)
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
        else if (eval instanceof EvalFilterNode)
        {
            EvalFilterNode filterNode = (EvalFilterNode) eval;
            Filter filter = unmapFilter(filterNode.getRawFilterSpec());
            return new PatternFilterExpr(filter, filterNode.getEventAsName());
        }
        throw new IllegalArgumentException("Could not map pattern expression node of type " + eval.getClass().getSimpleName());
    }

    private static void unmapPatternEvalRecursive(PatternExpr parent, EvalNode eval)
    {
        for (EvalNode child : eval.getChildNodes())
        {
            PatternExpr result = unmapPatternEvalFlat(child);
            parent.getChildren().add(result);
            unmapPatternEvalRecursive(result, child);
        }
    }

    private static void mapPatternEvalRecursive(EvalNode parent, PatternExpr expr)
    {
        for (PatternExpr child : expr.getChildren())
        {
            EvalNode result = mapPatternEvalFlat(child);
            parent.addChildNode(result);
            mapPatternEvalRecursive(result, child);
        }
    }

    private static PatternExpr unmapPatternEvalDeep(EvalNode exprNode)
    {
        PatternExpr parent = unmapPatternEvalFlat(exprNode);
        unmapPatternEvalRecursive(parent, exprNode);
        return parent;
    }

    private static EvalNode mapPatternEvalDeep(PatternExpr expr)
    {
        EvalNode parent = mapPatternEvalFlat(expr);
        mapPatternEvalRecursive(parent, expr);
        return parent;
    }

    private static FilterSpecRaw mapFilter(Filter filter)
    {
        List<ExprNode> expr = new ArrayList<ExprNode>();
        if (filter.getFilter() != null)
        {
            ExprNode exprNode = mapExpressionDeep(filter.getFilter());
            expr.add(exprNode);
        }

        FilterSpecRaw raw = new FilterSpecRaw(filter.getEventTypeAlias(), expr);
        return raw;
    }

    private static Filter unmapFilter(FilterSpecRaw filter)
    {
        Expression expr = null;
        if (filter.getFilterExpressions().size() > 1)
        {
            expr = new Conjunction();
            for (ExprNode exprNode : filter.getFilterExpressions())
            {
                Expression expression = unmapExpressionDeep(exprNode);
                expr.getChildren().add(expression);
            }
        }
        else if (filter.getFilterExpressions().size() == 1)
        {
            expr = unmapExpressionDeep(filter.getFilterExpressions().get(0));
        }

        return new Filter(filter.getEventTypeAlias(), expr);
    }
}
