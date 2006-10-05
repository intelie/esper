package net.esper.eql.parse;

import junit.framework.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import antlr.collections.AST;
import net.esper.support.eql.parse.SupportParserHelper;
import net.esper.support.bean.*;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.eql.expression.*;
import net.esper.view.ViewSpec;
import net.esper.filter.FilterSpec;
import net.esper.eql.spec.*;
import net.esper.type.OuterJoinType;
import net.esper.event.EventAdapterService;
import net.esper.pattern.*;

import java.util.List;

public class TestEQLTreeWalker extends TestCase
{
    private static String CLASSNAME = SupportBean.class.getName();
    private static String EXPRESSION = "select * from " +
                    CLASSNAME + "(string='a').win:length(10).std:lastevent() as win1," +
                    CLASSNAME + "(string='b').win:length(10).std:lastevent() as win2 ";

    public void testWalkEQLSimpleWhere() throws Exception
    {
        String expression = EXPRESSION + "where win1.f1=win2.f2";

        EQLTreeWalker walker = parseAndWalkEQL(expression);

        assertEquals(2, walker.getStatementSpec().getStreamSpecs().size());

        FilterStreamSpec streamSpec = (FilterStreamSpec) walker.getStatementSpec().getStreamSpecs().get(0);
        assertEquals(2, streamSpec.getViewSpecs().size());
        assertEquals(SupportBean.class, streamSpec.getFilterSpec().getEventType().getUnderlyingType());
        assertEquals("length", streamSpec.getViewSpecs().get(0).getObjectName());
        assertEquals("lastevent", streamSpec.getViewSpecs().get(1).getObjectName());
        assertEquals("win1", streamSpec.getOptionalStreamName());

        streamSpec = (FilterStreamSpec) walker.getStatementSpec().getStreamSpecs().get(1);
        assertEquals("win2", streamSpec.getOptionalStreamName());

        // Join expression tree validation
        assertTrue(walker.getStatementSpec().getFilterRootNode() instanceof ExprEqualsNode);
        ExprNode equalsNode = (walker.getStatementSpec().getFilterRootNode());
        assertEquals(2, equalsNode.getChildNodes().size());

        ExprIdentNode identNode = (ExprIdentNode) equalsNode.getChildNodes().get(0);
        assertEquals("win1", identNode.getStreamOrPropertyName());
        assertEquals("f1", identNode.getUnresolvedPropertyName());
        identNode = (ExprIdentNode) equalsNode.getChildNodes().get(1);
        assertEquals("win2", identNode.getStreamOrPropertyName());
        assertEquals("f2", identNode.getUnresolvedPropertyName());
    }

    public void testWalkEQLWhereWithAnd() throws Exception
    {
        String expression = "select * from " +
                        CLASSNAME + "(string='a').win:length(10).std:lastevent() as win1," +
                        CLASSNAME + "(string='b').win:length(9).std:lastevent() as win2, " +
                        CLASSNAME + "(string='c').win:length(3).std:lastevent() as win3 " +
                        "where win1.f1=win2.f2 and win3.f3=f4";

        EQLTreeWalker walker = parseAndWalkEQL(expression);

        // Stream spec validation
        assertEquals(3, walker.getStatementSpec().getStreamSpecs().size());
        assertEquals("win1", walker.getStatementSpec().getStreamSpecs().get(0).getOptionalStreamName());
        assertEquals("win2", walker.getStatementSpec().getStreamSpecs().get(1).getOptionalStreamName());
        assertEquals("win3", walker.getStatementSpec().getStreamSpecs().get(2).getOptionalStreamName());

        FilterStreamSpec streamSpec = (FilterStreamSpec) walker.getStatementSpec().getStreamSpecs().get(2);
        assertEquals(2, streamSpec.getViewSpecs().size());
        assertEquals(SupportBean.class, streamSpec.getFilterSpec().getEventType().getUnderlyingType());
        assertEquals("length", streamSpec.getViewSpecs().get(0).getObjectName());
        assertEquals("lastevent", streamSpec.getViewSpecs().get(1).getObjectName());

        // Join expression tree validation
        assertTrue(walker.getStatementSpec().getFilterRootNode() instanceof ExprAndNode);
        assertEquals(2, walker.getStatementSpec().getFilterRootNode().getChildNodes().size());
        ExprNode equalsNode = (walker.getStatementSpec().getFilterRootNode().getChildNodes().get(0));
        assertEquals(2, equalsNode.getChildNodes().size());

        ExprIdentNode identNode = (ExprIdentNode) equalsNode.getChildNodes().get(0);
        assertEquals("win1", identNode.getStreamOrPropertyName());
        assertEquals("f1", identNode.getUnresolvedPropertyName());
        identNode = (ExprIdentNode) equalsNode.getChildNodes().get(1);
        assertEquals("win2", identNode.getStreamOrPropertyName());
        assertEquals("f2", identNode.getUnresolvedPropertyName());

        equalsNode = (walker.getStatementSpec().getFilterRootNode().getChildNodes().get(1));
        identNode = (ExprIdentNode) equalsNode.getChildNodes().get(0);
        assertEquals("win3", identNode.getStreamOrPropertyName());
        assertEquals("f3", identNode.getUnresolvedPropertyName());
        identNode = (ExprIdentNode) equalsNode.getChildNodes().get(1);
        assertNull(identNode.getStreamOrPropertyName());
        assertEquals("f4", identNode.getUnresolvedPropertyName());
    }

    public void testWalkEQLPerRowFunctions() throws Exception
    {
        assertEquals(9, tryExpression("max(6, 9)"));
        assertEquals(6.11, tryExpression("min(6.11, 6.12)"));
        assertEquals(6.10, tryExpression("min(6.11, 6.12, 6.1)"));
        assertEquals("ab", tryExpression("'a'||'b'"));
        assertEquals(null, tryExpression("coalesce(null, null)"));
        assertEquals(1, tryExpression("coalesce(null, 1)"));
        assertEquals(1l, tryExpression("coalesce(null, 1l)"));
        assertEquals("a", tryExpression("coalesce(null, 'a', 'b')"));
        assertEquals(13.5d, tryExpression("coalesce(null, null, 3*4.5)"));
        assertEquals(true, tryExpression("coalesce(null, true)"));
        assertEquals(5, tryExpression("coalesce(5, null, 6)"));
        assertEquals(2, tryExpression("(case 1 when 1 then 2 end)"));
    }

    public void testWalkEQLMath() throws Exception
    {
        assertEquals(32, tryExpression("5*6-3+15/3"));
        assertEquals(-5, tryExpression("1-1-1-2-1-1"));
        assertEquals(2.8d, tryExpression("1.4 + 1.4"));
        assertEquals(1d, tryExpression("55.5/5/11.1"));
        assertEquals(0, tryExpression("2/3"));
        assertEquals(2/3d, tryExpression("2.0/3"));
        assertEquals(10, tryExpression("(1+4)*2"));
        assertEquals(12, tryExpression("(3*(6-4))*2"));
        assertEquals(8, tryExpression("(1+(4*3)+2)/2+1"));
        assertEquals(1, tryExpression("10%3"));
        assertEquals(10.1 % 3, tryExpression("10.1%3"));
    }

    public void testWalkEQLRelationalOp() throws Exception
    {
        assertEquals(true, tryRelationalOp("3>2"));
        assertEquals(false, tryRelationalOp("3*5/2 >= 7.5"));
        assertEquals(true, tryRelationalOp("3*5/2.0 >= 7.5"));
        assertEquals(false, tryRelationalOp("1.1 + 2.2 < 3.2"));
        assertEquals(false, tryRelationalOp("3<=2"));
        assertEquals(true, tryRelationalOp("4*(3+1)>=16"));

        assertEquals(false, tryRelationalOp("(4>2) and (2>3)"));
        assertEquals(true, tryRelationalOp("(4>2) or (2>3)"));

        assertEquals(false, tryRelationalOp("not 3>2"));
        assertEquals(true, tryRelationalOp("not (not 3>2)"));
    }

    public void testWalkEQLInsertInto() throws Exception
    {
        String expression = "insert into MyAlias select * from " +
                        CLASSNAME + "().win:length(10).std:lastevent() as win1," +
                        CLASSNAME + "(string='b').win:length(9).std:lastevent() as win2";

        EQLTreeWalker walker = parseAndWalkEQL(expression);

        InsertIntoDesc desc = walker.getStatementSpec().getInsertIntoDesc();
        assertTrue(desc.isIStream());
        assertEquals("MyAlias", desc.getEventTypeAlias());
        assertEquals(0, desc.getColumnNames().size());

        expression = "insert rstream into MyAlias(a, b, c) select * from " +
                        CLASSNAME + "().win:length(10).std:lastevent() as win1," +
                        CLASSNAME + "(string='b').win:length(9).std:lastevent() as win2";

        walker = parseAndWalkEQL(expression);

        desc = walker.getStatementSpec().getInsertIntoDesc();
        assertFalse(desc.isIStream());
        assertEquals("MyAlias", desc.getEventTypeAlias());
        assertEquals(3, desc.getColumnNames().size());
        assertEquals("a", desc.getColumnNames().get(0));
        assertEquals("b", desc.getColumnNames().get(1));
        assertEquals("c", desc.getColumnNames().get(2));

        expression = "insert istream into Test2 select * from " + CLASSNAME + "().win:length(10)";
        walker = parseAndWalkEQL(expression);
        desc = walker.getStatementSpec().getInsertIntoDesc();
        assertTrue(desc.isIStream());
        assertEquals("Test2", desc.getEventTypeAlias());
        assertEquals(0, desc.getColumnNames().size());
    }

    public void testWalkView() throws Exception
    {
        String text = "select * from " + SupportBean.class.getName() + "(string=\"IBM\").win:lenght(10, 1.1, \"a\").stat:uni('price', false)";

        EQLTreeWalker walker = parseAndWalkEQL(text);
        FilterSpec filterSpec = ((FilterStreamSpec) walker.getStatementSpec().getStreamSpecs().get(0)).getFilterSpec();

        // Check filter spec properties
        assertEquals(SupportBean.class, filterSpec.getEventType().getUnderlyingType());
        assertEquals(1, filterSpec.getParameters().size());

        // Check views
        List<ViewSpec> viewSpecs = ((FilterStreamSpec) walker.getStatementSpec().getStreamSpecs().get(0)).getViewSpecs();
        assertEquals(2, viewSpecs.size());

        ViewSpec specOne = viewSpecs.get(0);
        assertEquals("win", specOne.getObjectNamespace());
        assertEquals("lenght", specOne.getObjectName());
        assertEquals(3, specOne.getObjectParameters().size());
        assertEquals(10, specOne.getObjectParameters().get(0));
        assertEquals(1.1d, specOne.getObjectParameters().get(1));
        assertEquals("a", specOne.getObjectParameters().get(2));

        ViewSpec specTwo = viewSpecs.get(1);
        assertEquals("stat", specTwo.getObjectNamespace());
        assertEquals("uni", specTwo.getObjectName());
        assertEquals(2, specTwo.getObjectParameters().size());
        assertEquals("price", specTwo.getObjectParameters().get(0));
        assertEquals(false, specTwo.getObjectParameters().get(1));
    }

    public void testSelectList() throws Exception
    {
        String text = "select intPrimitive, 2 * intBoxed, 5 as myConst, stream0.string as theString from " + SupportBean.class.getName() + "().win:lenght(10) as stream0";
        EQLTreeWalker walker = parseAndWalkEQL(text);
        List<SelectExprElementUnnamedSpec> selectExpressions = walker.getStatementSpec().getSelectListExpressions();
        assertEquals(4, selectExpressions.size());
        assertTrue(selectExpressions.get(0).getSelectExpression() instanceof ExprIdentNode);
        assertTrue(selectExpressions.get(1).getSelectExpression() instanceof ExprMathNode);
        assertTrue(selectExpressions.get(2).getSelectExpression() instanceof ExprConstantNode);
        assertEquals("myConst", selectExpressions.get(2).getOptionalAsName());
        assertTrue(selectExpressions.get(3).getSelectExpression() instanceof ExprIdentNode);
        assertEquals("theString", selectExpressions.get(3).getOptionalAsName());
        assertNull(walker.getStatementSpec().getInsertIntoDesc());

        text = "select * from " + SupportBean.class.getName() + "().win:lenght(10)";
        walker = parseAndWalkEQL(text);
        assertEquals(0, walker.getStatementSpec().getSelectListExpressions().size());
    }

    public void testArrayViewParams() throws Exception
    {
        // Check a list of integer as a view parameter
        String text = "select * from " + SupportBean.class.getName() + "().win:lenght({10, 11, 12})";
        EQLTreeWalker walker = parseAndWalkEQL(text);

        List<ViewSpec> viewSpecs = ((FilterStreamSpec) walker.getStatementSpec().getStreamSpecs().get(0)).getViewSpecs();
        int[] intParams = (int[]) viewSpecs.get(0).getObjectParameters().get(0);
        assertEquals(10, intParams[0]);
        assertEquals(11, intParams[1]);
        assertEquals(12, intParams[2]);

        // Check a list of objects
        text = "select * from " + SupportBean.class.getName() + "().win:lenght({false, 11.2, 's'})";
        walker = parseAndWalkEQL(text);
        viewSpecs = ((FilterStreamSpec)walker.getStatementSpec().getStreamSpecs().get(0)).getViewSpecs();
        Object[] objParams = (Object[]) viewSpecs.get(0).getObjectParameters().get(0);
        assertEquals(false, objParams[0]);
        assertEquals(11.2, objParams[1]);
        assertEquals("s", objParams[2]);
    }

    public void testOuterJoin() throws Exception
    {
        tryOuterJoin("left", OuterJoinType.LEFT);
        tryOuterJoin("right", OuterJoinType.RIGHT);
        tryOuterJoin("full", OuterJoinType.FULL);
    }

    public void testNoPackageName() throws Exception
    {
        String text = "select intPrimitive from SupportBean_N().win:lenght(10) as win1";
        parseAndWalkEQL(text);
    }

    public void testAggregateFunction() throws Exception
    {
        String fromClause = "from " + SupportBean_N.class.getName() + "().win:lenght(10) as win1";
        String text = "select sum(intPrimitive)," +
                "sum(distinct doubleBoxed)," +
                "avg(doubleBoxed)," +
                "avg(distinct doubleBoxed)," +
                "count(*)," +
                "count(intPrimitive)," +
                "count(distinct intPrimitive)," +
                "max(distinct intPrimitive)," +
                "min(distinct intPrimitive)," +
                "max(intPrimitive)," +
                "min(intPrimitive), " +
                "median(intPrimitive), " +
                "median(distinct intPrimitive)," +
                "stddev(intPrimitive), " +
                "stddev(distinct intPrimitive)," +
                "avedev(intPrimitive)," +
                "avedev(distinct intPrimitive) " +
                fromClause;
        parseAndWalkEQL(text);

        // try min-max aggregate versus row functions
        text = "select max(intPrimitive), min(intPrimitive)," +
                      "max(intPrimitive,intBoxed), min(intPrimitive,intBoxed)," +
                      "max(distinct intPrimitive), min(distinct intPrimitive)" +
                      fromClause;
        EQLTreeWalker walker = parseAndWalkEQL(text);
        List<SelectExprElementUnnamedSpec> select = walker.getStatementSpec().getSelectListExpressions();
        assertTrue(select.get(0).getSelectExpression() instanceof ExprMinMaxAggrNode);
        assertTrue(select.get(1).getSelectExpression() instanceof ExprMinMaxAggrNode);
        assertTrue(select.get(2).getSelectExpression() instanceof ExprMinMaxRowNode);
        assertTrue(select.get(3).getSelectExpression() instanceof ExprMinMaxRowNode);
        assertTrue(select.get(4).getSelectExpression() instanceof ExprMinMaxAggrNode);
        assertTrue(select.get(5).getSelectExpression() instanceof ExprMinMaxAggrNode);

        try
        {
            parseAndWalkEQL("select max(distinct intPrimitive, intboxed)");
            fail();
        }
        catch (Exception ex)
        {
            // expected
        }
    }

    public void testGroupBy() throws Exception
    {
        String text = "select sum(intPrimitive) from SupportBean_N().win:lenght(10) as win1 where intBoxed > 5 " +
            "group by intBoxed, 3 * doubleBoxed, max(2, doublePrimitive)";
        EQLTreeWalker walker = parseAndWalkEQL(text);

        List<ExprNode> groupByList = walker.getStatementSpec().getGroupByExpressions();
        assertEquals(3, groupByList.size());

        ExprNode node = groupByList.get(0);
        assertTrue(node instanceof ExprIdentNode);

        node = groupByList.get(1);
        assertTrue(node instanceof ExprMathNode);
        assertTrue(node.getChildNodes().get(0) instanceof ExprConstantNode);
        assertTrue(node.getChildNodes().get(1) instanceof ExprIdentNode);

        node = groupByList.get(2);
        assertTrue(node instanceof ExprMinMaxRowNode);
    }

    public void testHaving() throws Exception
    {
        String text = "select sum(intPrimitive) from SupportBean_N().win:lenght(10) as win1 where intBoxed > 5 " +
            "group by intBoxed having sum(intPrimitive) > 5";
        EQLTreeWalker walker = parseAndWalkEQL(text);

        ExprNode havingNode = walker.getStatementSpec().getHavingExprRootNode();

        assertTrue(havingNode instanceof ExprRelationalOpNode);
        assertTrue(havingNode.getChildNodes().get(0) instanceof ExprSumNode);
        assertTrue(havingNode.getChildNodes().get(1) instanceof ExprConstantNode);

        text = "select sum(intPrimitive) from SupportBean_N().win:lenght(10) as win1 where intBoxed > 5 " +
            "having intPrimitive < avg(intPrimitive)";
        walker = parseAndWalkEQL(text);

        havingNode = walker.getStatementSpec().getHavingExprRootNode();
        assertTrue(havingNode instanceof ExprRelationalOpNode);
    }

    public void testDistinct() throws Exception
    {
        String text = "select sum(distinct intPrimitive) from SupportBean_N().win:lenght(10) as win1";
        EQLTreeWalker walker = parseAndWalkEQL(text);

        ExprAggregateNode aggrNode = (ExprAggregateNode) walker.getStatementSpec().getSelectListExpressions().get(0).getSelectExpression();
        assertTrue(aggrNode.isDistinct());
    }

    public void testComplexProperty() throws Exception
    {
        String text = "select array [ 1 ],s0.map('a'),nested.nested2, a[1].b as x " +
                " from SupportBean_N().win:lenght(10) as win1 " +
                " where a[1].b('a').nested.c[0] = 4";
        EQLTreeWalker walker = parseAndWalkEQL(text);

        ExprIdentNode identNode = (ExprIdentNode) walker.getStatementSpec().getSelectListExpressions().get(0).getSelectExpression();
        assertEquals("array[1]", identNode.getUnresolvedPropertyName());
        assertNull(identNode.getStreamOrPropertyName());

        identNode = (ExprIdentNode) walker.getStatementSpec().getSelectListExpressions().get(1).getSelectExpression();
        assertEquals("map('a')", identNode.getUnresolvedPropertyName());
        assertEquals("s0", identNode.getStreamOrPropertyName());

        identNode = (ExprIdentNode) walker.getStatementSpec().getSelectListExpressions().get(2).getSelectExpression();
        assertEquals("nested2", identNode.getUnresolvedPropertyName());
        assertEquals("nested", identNode.getStreamOrPropertyName());

        identNode = (ExprIdentNode) walker.getStatementSpec().getSelectListExpressions().get(3).getSelectExpression();
        assertEquals("a[1].b", identNode.getUnresolvedPropertyName());
        assertEquals(null, identNode.getStreamOrPropertyName());

        identNode = (ExprIdentNode) walker.getStatementSpec().getFilterRootNode().getChildNodes().get(0);
        assertEquals("a[1].b('a').nested.c[0]", identNode.getUnresolvedPropertyName());
        assertEquals(null, identNode.getStreamOrPropertyName());
    }

    public void testBitWise() throws Exception
    {
        String text = "select intPrimitive & intBoxed from " + SupportBean.class.getName() + "().win:lenght(10) as stream0";
        EQLTreeWalker walker = parseAndWalkEQL(text);
        List<SelectExprElementUnnamedSpec> selectExpressions = walker.getStatementSpec().getSelectListExpressions();
        assertEquals(1, selectExpressions.size());
        assertTrue(selectExpressions.get(0).getSelectExpression() instanceof ExprBitWiseNode);

        assertEquals(0, tryBitWise("1&2"));
        assertEquals(3, tryBitWise("1|2"));
        assertEquals(8, tryBitWise("10^2"));
    }

    public void testPatternsOnly() throws Exception
    {
        String patternOne = "a=" + SupportBean.class.getName() + " -> b=" + SupportBean.class.getName();
        String patternTwo = "c=" + SupportBean.class.getName() + " or " + SupportBean.class.getName();

        // Test simple case, one pattern and no "as streamName"
        EQLTreeWalker walker = parseAndWalkEQL("select * from pattern [" + patternOne + "]");
        assertEquals(1, walker.getStatementSpec().getStreamSpecs().size());
        PatternStreamSpec patternStreamSpec = (PatternStreamSpec) walker.getStatementSpec().getStreamSpecs().get(0);

        assertEquals(EvalFollowedByNode.class, patternStreamSpec.getEvalNode().getClass());
        assertEquals(2, patternStreamSpec.getTaggedEventTypes().size());
        assertEquals(SupportBean.class, patternStreamSpec.getTaggedEventTypes().get("a").getUnderlyingType());
        assertNull(patternStreamSpec.getOptionalStreamName());

        // Test case with "as s0"
        walker = parseAndWalkEQL("select * from pattern [" + patternOne + "] as s0");
        patternStreamSpec = (PatternStreamSpec) walker.getStatementSpec().getStreamSpecs().get(0);
        assertEquals("s0", patternStreamSpec.getOptionalStreamName());

        // Test case with multiple patterns
        walker = parseAndWalkEQL("select * from pattern [" + patternOne + "] as s0, pattern [" + patternTwo + "] as s1");
        assertEquals(2, walker.getStatementSpec().getStreamSpecs().size());
        patternStreamSpec = (PatternStreamSpec) walker.getStatementSpec().getStreamSpecs().get(0);
        assertEquals("s0", patternStreamSpec.getOptionalStreamName());
        assertEquals(EvalFollowedByNode.class, patternStreamSpec.getEvalNode().getClass());
        assertEquals(2, patternStreamSpec.getTaggedEventTypes().size());
        assertEquals(SupportBean.class, patternStreamSpec.getTaggedEventTypes().get("b").getUnderlyingType());

        patternStreamSpec = (PatternStreamSpec) walker.getStatementSpec().getStreamSpecs().get(1);
        assertEquals("s1", patternStreamSpec.getOptionalStreamName());
        assertEquals(EvalOrNode.class, patternStreamSpec.getEvalNode().getClass());
        assertEquals(1, patternStreamSpec.getTaggedEventTypes().size());
        assertEquals(SupportBean.class, patternStreamSpec.getTaggedEventTypes().get("c").getUnderlyingType());

        // Test 3 patterns
        walker = parseAndWalkEQL("select * from pattern [" + patternOne + "], pattern [" + patternTwo + "] as s1," +
                "pattern[x=" + SupportBean_S2.class.getName() + "] as s2");
        assertEquals(3, walker.getStatementSpec().getStreamSpecs().size());
        patternStreamSpec = (PatternStreamSpec) walker.getStatementSpec().getStreamSpecs().get(2);
        assertEquals("s2", patternStreamSpec.getOptionalStreamName());
        assertEquals(SupportBean_S2.class, patternStreamSpec.getTaggedEventTypes().get("x").getUnderlyingType());

        // Test patterns with views
        walker = parseAndWalkEQL("select * from pattern [" + patternOne + "].win:time(1), pattern [" + patternTwo + "].win:length(1).std:lastevent() as s1");
        assertEquals(2, walker.getStatementSpec().getStreamSpecs().size());
        patternStreamSpec = (PatternStreamSpec) walker.getStatementSpec().getStreamSpecs().get(0);
        assertEquals(1, patternStreamSpec.getViewSpecs().size());
        assertEquals("time", patternStreamSpec.getViewSpecs().get(0).getObjectName());
        patternStreamSpec = (PatternStreamSpec) walker.getStatementSpec().getStreamSpecs().get(1);
        assertEquals(2, patternStreamSpec.getViewSpecs().size());
        assertEquals("length", patternStreamSpec.getViewSpecs().get(0).getObjectName());
        assertEquals("lastevent", patternStreamSpec.getViewSpecs().get(1).getObjectName());
    }

    public void testIfThenElseCase() throws Exception
    {
        String text;
        text = "select case when intPrimitive > shortPrimitive then count(intPrimitive) end from " +    SupportBean.class.getName() + "().win:lenght(10) as win";
        parseAndWalkEQL(text);
        text = "select case when intPrimitive > shortPrimitive then count(intPrimitive) end as p1 from " +    SupportBean.class.getName() + "().win:lenght(10) as win";
        parseAndWalkEQL(text);
        text = "select case when intPrimitive > shortPrimitive then count(intPrimitive) else shortPrimitive end from " +    SupportBean.class.getName() + "().win:lenght(10) as win";
        parseAndWalkEQL(text);
        text = "select case when intPrimitive > shortPrimitive then count(intPrimitive) when longPrimitive > intPrimitive then count(longPrimitive) else shortPrimitive end from " +    SupportBean.class.getName() + "().win:lenght(10) as win";
        parseAndWalkEQL(text);
        text = "select case intPrimitive  when 1 then count(intPrimitive) end from " +    SupportBean.class.getName() + "().win:lenght(10) as win";
        parseAndWalkEQL(text);
        text = "select case intPrimitive when longPrimitive then (intPrimitive + longPrimitive) end" +
        " from " + SupportBean.class.getName() + ".win:length(3)";
        parseAndWalkEQL(text);
    }

    private void tryOuterJoin(String outerType, OuterJoinType typeExpected) throws Exception
    {
        String text = "select intPrimitive from " +
                        SupportBean_A.class.getName() + "().win:lenght(10) as win1 " +
                        outerType + " outer join " +
                        SupportBean_A.class.getName() + "().win:lenght(10) as win2 " +
                        "on win1.f1 = win2.f2[1]";
        EQLTreeWalker walker = parseAndWalkEQL(text);

        List<OuterJoinDesc> descList = walker.getStatementSpec().getOuterJoinDescList();
        assertEquals(1, descList.size());
        OuterJoinDesc desc = descList.get(0);
        assertEquals(typeExpected, desc.getOuterJoinType());
        assertEquals("f1", desc.getLeftNode().getUnresolvedPropertyName());
        assertEquals("win1", desc.getLeftNode().getStreamOrPropertyName());
        assertEquals("f2[1]", desc.getRightNode().getUnresolvedPropertyName());
        assertEquals("win2", desc.getRightNode().getStreamOrPropertyName());

        text = "select intPrimitive from " +
                        SupportBean_A.class.getName() + "().win:lenght(10) as win1 " +
                        outerType + " outer join " +
                        SupportBean_A.class.getName() + "().win:lenght(10) as win2 " +
                        "on win1.f1 = win2.f2 " +
                        outerType + " outer join " +
                        SupportBean_A.class.getName() + "().win:lenght(10) as win3 " +
                        "on win1.f1 = win3.f3";
        walker = parseAndWalkEQL(text);

        descList = walker.getStatementSpec().getOuterJoinDescList();
        assertEquals(2, descList.size());

        desc = descList.get(0);
        assertEquals(typeExpected, desc.getOuterJoinType());
        assertEquals("f1", desc.getLeftNode().getUnresolvedPropertyName());
        assertEquals("win1", desc.getLeftNode().getStreamOrPropertyName());
        assertEquals("f2", desc.getRightNode().getUnresolvedPropertyName());
        assertEquals("win2", desc.getRightNode().getStreamOrPropertyName());

        desc = descList.get(1);
        assertEquals(typeExpected, desc.getOuterJoinType());
        assertEquals("f1", desc.getLeftNode().getUnresolvedPropertyName());
        assertEquals("win1", desc.getLeftNode().getStreamOrPropertyName());
        assertEquals("f3", desc.getRightNode().getUnresolvedPropertyName());
        assertEquals("win3", desc.getRightNode().getStreamOrPropertyName());
    }

    public void testWalkPattern() throws Exception
    {
        String text = "every g=" + SupportBean.class.getName() + "(string=\"IBM\") where timer:within(20)";

        EQLTreeWalker walker = parseAndWalkPattern(text);

        assertEquals(1, walker.getStatementSpec().getStreamSpecs().size());
        PatternStreamSpec patternStreamSpec = (PatternStreamSpec) walker.getStatementSpec().getStreamSpecs().get(0);

        EvalNode rootNode = patternStreamSpec.getEvalNode();
        rootNode.dumpDebug(".testWalk ");

        EvalEveryNode everyNode = (EvalEveryNode) rootNode;

        assertEquals(1, everyNode.getChildNodes().size());
        assertTrue(everyNode.getChildNodes().get(0) instanceof EvalGuardNode);
        EvalGuardNode guardNode = (EvalGuardNode) everyNode.getChildNodes().get(0);

        assertEquals(1, guardNode.getChildNodes().size());
        assertTrue(guardNode.getChildNodes().get(0) instanceof EvalFilterNode);
        EvalFilterNode filterNode = (EvalFilterNode) guardNode.getChildNodes().get(0);

        assertEquals("g", filterNode.getEventAsName());
        assertEquals(0, filterNode.getChildNodes().size());
        assertEquals(1, filterNode.getFilterSpec().getParameters().size());

        assertEquals(1, patternStreamSpec.getTaggedEventTypes().size());
        assertEquals(SupportBean.class, patternStreamSpec.getTaggedEventTypes().get("g").getUnderlyingType());
    }

    public void testWalkPropertyPatternCombination() throws Exception
    {
        final String EVENT = SupportBeanComplexProps.class.getName();
        String property = tryWalkGetPropertyPattern(EVENT + "(mapped ( 'key' )  = 'value')");
        assertEquals("mapped('key')", property);

        property = tryWalkGetPropertyPattern(EVENT + "(indexed [ 1 ]  = 1)");
        assertEquals("indexed[1]", property);
        property = tryWalkGetPropertyPattern(EVENT + "(nested . nestedValue  = 'value')");
        assertEquals("nested.nestedValue", property);
    }

    public void testWalkPatternUseResult() throws Exception
    {
        final String EVENT = SupportBean_N.class.getName();
        String text = "na=" + EVENT + "() -> every nb=" + EVENT + "(doublePrimitive in [0:na.doublePrimitive])";
        parseAndWalkPattern(text);
    }

    public void testWalkPatternNoPackage() throws Exception
    {
        SupportEventAdapterService.getService().addBeanType("SupportBean_N", SupportBean_N.class);
        String text = "na=SupportBean_N()";
        parseAndWalkPattern(text);
    }

    public void testWalkPatternTypesValid() throws Exception
    {
        String text = SupportBean.class.getName();
        EQLTreeWalker walker = parseAndWalkPattern(text);
        assertEquals(1, walker.getStatementSpec().getStreamSpecs().size());
        PatternStreamSpec spec = (PatternStreamSpec) walker.getStatementSpec().getStreamSpecs().get(0);
        assertEquals(0, spec.getTaggedEventTypes().size());
    }

    public void testPatternWalkTypesInvalid() throws Exception
    {
        String text = "a=" + SupportBean.class.getName() + " or a=" + SupportBean_A.class.getName();

        try
        {
            parseAndWalkPattern(text);
            TestCase.fail();
        }
        catch (Exception ex)
        {
            log.debug(".testWalkTypesInvalid Expected exception, msg=" + ex.getMessage());
        }
    }

    private String tryWalkGetPropertyPattern(String stmt) throws Exception
    {
        EQLTreeWalker walker = parseAndWalkPattern(stmt);

        assertEquals(1, walker.getStatementSpec().getStreamSpecs().size());
        PatternStreamSpec patternStreamSpec = (PatternStreamSpec) walker.getStatementSpec().getStreamSpecs().get(0);

        EvalFilterNode filterNode = (EvalFilterNode) patternStreamSpec.getEvalNode();
        assertEquals(1, filterNode.getFilterSpec().getParameters().size());
        return filterNode.getFilterSpec().getParameters().get(0).getPropertyName();
    }

    private static EQLTreeWalker parseAndWalkPattern(String expression) throws Exception
    {
        log.debug(".parseAndWalk Trying text=" + expression);
        AST ast = SupportParserHelper.parsePattern(expression);
        log.debug(".parseAndWalk success, tree walking...");
        SupportParserHelper.displayAST(ast);

        EQLTreeWalker walker = new EQLTreeWalker(SupportEventAdapterService.getService());
        walker.startPatternExpressionRule(ast);
        return walker;
    }

    private static EQLTreeWalker parseAndWalkEQL(String expression) throws Exception
    {
        log.debug(".parseAndWalk Trying text=" + expression);
        AST ast = SupportParserHelper.parseEQL(expression);
        log.debug(".parseAndWalk success, tree walking...");
        SupportParserHelper.displayAST(ast);

        EventAdapterService eventAdapterService = SupportEventAdapterService.getService();
        eventAdapterService.addBeanType("SupportBean_N", SupportBean_N.class);

        EQLTreeWalker walker = new EQLTreeWalker(eventAdapterService);
        walker.startEQLExpressionRule(ast);
        return walker;
    }

    private Object tryBitWise(String equation) throws Exception
    {
        String expression = EXPRESSION + "where (" + equation + ")=win2.f2";

        EQLTreeWalker walker = parseAndWalkEQL(expression);
        ExprNode exprNode = walker.getStatementSpec().getFilterRootNode().getChildNodes().get(0);
        ExprBitWiseNode bitWiseNode = (ExprBitWiseNode) (exprNode);
        bitWiseNode.getValidatedSubtree(null, null);
        return bitWiseNode.evaluate(null);
    }

    private Object tryExpression(String equation) throws Exception
    {
        String expression = EXPRESSION + "where " + equation + "=win2.f2";

        EQLTreeWalker walker = parseAndWalkEQL(expression);
        ExprNode exprNode = (walker.getStatementSpec().getFilterRootNode().getChildNodes().get(0));
        exprNode = exprNode.getValidatedSubtree(null, null);
        return exprNode.evaluate(null);
    }

    private Object tryRelationalOp(String subExpr) throws Exception
    {
        String expression = EXPRESSION + "where " + subExpr;

        EQLTreeWalker walker = parseAndWalkEQL(expression);
        ExprNode filterExprNode = walker.getStatementSpec().getFilterRootNode();
        filterExprNode.getValidatedSubtree(null, null);
        return filterExprNode.evaluate(null);
    }

    private static final Log log = LogFactory.getLog(TestEQLTreeWalker.class);
}