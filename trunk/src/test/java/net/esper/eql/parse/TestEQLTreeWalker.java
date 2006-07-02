package net.esper.eql.parse;

import junit.framework.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import antlr.collections.AST;
import net.esper.support.eql.parse.SupportParserHelper;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportBean_N;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.eql.expression.*;
import net.esper.view.ViewSpec;
import net.esper.filter.FilterSpec;
import net.esper.collection.Pair;
import net.esper.eql.expression.OuterJoinDesc;
import net.esper.type.OuterJoinType;
import net.esper.event.EventAdapterService;

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

        EQLTreeWalker walker = parseAndWalk(expression);

        assertEquals(2, walker.getStreamSpecs().size());

        StreamSpec streamSpec = walker.getStreamSpecs().get(0);
        assertEquals(2, streamSpec.getViewSpecs().size());
        assertEquals(SupportBean.class, streamSpec.getFilterSpec().getEventType().getUnderlyingType());
        assertEquals("length", streamSpec.getViewSpecs().get(0).getObjectName());
        assertEquals("lastevent", streamSpec.getViewSpecs().get(1).getObjectName());
        assertEquals("win1", streamSpec.getOptionalStreamName());

        streamSpec = walker.getStreamSpecs().get(1);
        assertEquals("win2", streamSpec.getOptionalStreamName());

        // Join expression tree validation
        assertTrue(walker.getFilterRootNode() instanceof ExprEqualsNode);
        ExprNode equalsNode = (walker.getFilterRootNode());
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

        EQLTreeWalker walker = parseAndWalk(expression);

        // Stream spec validation
        assertEquals(3, walker.getStreamSpecs().size());
        assertEquals("win1", walker.getStreamSpecs().get(0).getOptionalStreamName());
        assertEquals("win2", walker.getStreamSpecs().get(1).getOptionalStreamName());
        assertEquals("win3", walker.getStreamSpecs().get(2).getOptionalStreamName());

        StreamSpec streamSpec = walker.getStreamSpecs().get(2);
        assertEquals(2, streamSpec.getViewSpecs().size());
        assertEquals(SupportBean.class, streamSpec.getFilterSpec().getEventType().getUnderlyingType());
        assertEquals("length", streamSpec.getViewSpecs().get(0).getObjectName());
        assertEquals("lastevent", streamSpec.getViewSpecs().get(1).getObjectName());

        // Join expression tree validation
        assertTrue(walker.getFilterRootNode() instanceof ExprAndNode);
        assertEquals(2, walker.getFilterRootNode().getChildNodes().size());
        ExprNode equalsNode = (walker.getFilterRootNode().getChildNodes().get(0));
        assertEquals(2, equalsNode.getChildNodes().size());

        ExprIdentNode identNode = (ExprIdentNode) equalsNode.getChildNodes().get(0);
        assertEquals("win1", identNode.getStreamOrPropertyName());
        assertEquals("f1", identNode.getUnresolvedPropertyName());
        identNode = (ExprIdentNode) equalsNode.getChildNodes().get(1);
        assertEquals("win2", identNode.getStreamOrPropertyName());
        assertEquals("f2", identNode.getUnresolvedPropertyName());

        equalsNode = (walker.getFilterRootNode().getChildNodes().get(1));
        identNode = (ExprIdentNode) equalsNode.getChildNodes().get(0);
        assertEquals("win3", identNode.getStreamOrPropertyName());
        assertEquals("f3", identNode.getUnresolvedPropertyName());
        identNode = (ExprIdentNode) equalsNode.getChildNodes().get(1);
        assertNull(identNode.getStreamOrPropertyName());
        assertEquals("f4", identNode.getUnresolvedPropertyName());
    }
    
    public void testWalkEQLBuiltin() throws Exception
    {
        assertEquals(9, tryMath("max(6, 9)"));
        assertEquals(6.11, tryMath("min(6.11, 6.12)"));
        assertEquals(6.10, tryMath("min(6.11, 6.12, 6.1)"));
    }

    public void testWalkEQLMath() throws Exception
    {
        assertEquals(32, tryMath("5*6-3+15/3"));
        assertEquals(-5, tryMath("1-1-1-2-1-1"));
        assertEquals(2.8d, tryMath("1.4 + 1.4"));
        assertEquals(1d, tryMath("55.5/5/11.1"));
        assertEquals(0, tryMath("2/3"));
        assertEquals(2/3d, tryMath("2.0/3"));
        assertEquals(10, tryMath("(1+4)*2"));
        assertEquals(12, tryMath("(3*(6-4))*2"));
        assertEquals(8, tryMath("(1+(4*3)+2)/2+1"));
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
    }

    public void testWalkEQLInsertInto() throws Exception
    {
        String expression = "insert into MyAlias select * from " +
                        CLASSNAME + "().win:length(10).std:lastevent() as win1," +
                        CLASSNAME + "(string='b').win:length(9).std:lastevent() as win2";

        EQLTreeWalker walker = parseAndWalk(expression);

        InsertIntoDesc desc = walker.getInsertIntoDesc();
        assertTrue(desc.isIStream());
        assertEquals("MyAlias", desc.getEventTypeAlias());
        assertEquals(0, desc.getColumnNames().size());

        expression = "insert rstream into MyAlias(a, b, c) select * from " +
                        CLASSNAME + "().win:length(10).std:lastevent() as win1," +
                        CLASSNAME + "(string='b').win:length(9).std:lastevent() as win2";

        walker = parseAndWalk(expression);

        desc = walker.getInsertIntoDesc();
        assertFalse(desc.isIStream());
        assertEquals("MyAlias", desc.getEventTypeAlias());
        assertEquals(3, desc.getColumnNames().size());
        assertEquals("a", desc.getColumnNames().get(0));
        assertEquals("b", desc.getColumnNames().get(1));
        assertEquals("c", desc.getColumnNames().get(2));

        expression = "insert istream into Test2 select * from " + CLASSNAME + "().win:length(10)";
        walker = parseAndWalk(expression);
        desc = walker.getInsertIntoDesc();
        assertTrue(desc.isIStream());
        assertEquals("Test2", desc.getEventTypeAlias());
        assertEquals(0, desc.getColumnNames().size());
    }

    public void testWalkView() throws Exception
    {
        String text = "select * from " + SupportBean.class.getName() + "(string=\"IBM\").win:lenght(10, 1.1, \"a\").stat:uni('price', false)";

        EQLTreeWalker walker = parseAndWalk(text);
        FilterSpec filterSpec = walker.getStreamSpecs().get(0).getFilterSpec();

        // Check filter spec properties
        assertEquals(SupportBean.class, filterSpec.getEventType().getUnderlyingType());
        assertEquals(1, filterSpec.getParameters().size());

        // Check views
        List<ViewSpec> viewSpecs = walker.getStreamSpecs().get(0).getViewSpecs();
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
        EQLTreeWalker walker = parseAndWalk(text);
        List<Pair<ExprNode, String>> selectExpressions = walker.getSelectListExpressions();
        assertEquals(4, selectExpressions.size());
        assertTrue(selectExpressions.get(0).getFirst() instanceof ExprIdentNode);
        assertTrue(selectExpressions.get(1).getFirst() instanceof ExprMathNode);
        assertTrue(selectExpressions.get(2).getFirst() instanceof ExprConstantNode);
        assertEquals("myConst", selectExpressions.get(2).getSecond());
        assertTrue(selectExpressions.get(3).getFirst() instanceof ExprIdentNode);
        assertEquals("theString", selectExpressions.get(3).getSecond());
        assertNull(walker.getInsertIntoDesc());

        text = "select * from " + SupportBean.class.getName() + "().win:lenght(10)";
        walker = parseAndWalk(text);
        assertEquals(0, walker.getSelectListExpressions().size());
    }

    public void testArrayViewParams() throws Exception
    {
        // Check a list of integer as a view parameter
        String text = "select * from " + SupportBean.class.getName() + "().win:lenght({10, 11, 12})";
        EQLTreeWalker walker = parseAndWalk(text);

        List<ViewSpec> viewSpecs = walker.getStreamSpecs().get(0).getViewSpecs();
        int[] intParams = (int[]) viewSpecs.get(0).getObjectParameters().get(0);
        assertEquals(10, intParams[0]);
        assertEquals(11, intParams[1]);
        assertEquals(12, intParams[2]);

        // Check a list of objects
        text = "select * from " + SupportBean.class.getName() + "().win:lenght({false, 11.2, 's'})";
        walker = parseAndWalk(text);
        viewSpecs = walker.getStreamSpecs().get(0).getViewSpecs();
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
        parseAndWalk(text);
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
        parseAndWalk(text);

        // try min-max aggregate versus row functions
        text = "select max(intPrimitive), min(intPrimitive)," +
                      "max(intPrimitive,intBoxed), min(intPrimitive,intBoxed)," +
                      "max(distinct intPrimitive), min(distinct intPrimitive)" +
                      fromClause;
        EQLTreeWalker walker = parseAndWalk(text);
        List<Pair<ExprNode, String>> select = walker.getSelectListExpressions();
        assertTrue(select.get(0).getFirst() instanceof ExprMinMaxAggrNode);
        assertTrue(select.get(1).getFirst() instanceof ExprMinMaxAggrNode);
        assertTrue(select.get(2).getFirst() instanceof ExprMinMaxRowNode);
        assertTrue(select.get(3).getFirst() instanceof ExprMinMaxRowNode);
        assertTrue(select.get(4).getFirst() instanceof ExprMinMaxAggrNode);
        assertTrue(select.get(5).getFirst() instanceof ExprMinMaxAggrNode);

        try
        {
            parseAndWalk("select max(distinct intPrimitive, intboxed)");
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
        EQLTreeWalker walker = parseAndWalk(text);

        List<ExprNode> groupByList = walker.getGroupByExpressions();
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
        EQLTreeWalker walker = parseAndWalk(text);

        ExprNode havingNode = walker.getHavingExprRootNode();

        assertTrue(havingNode instanceof ExprRelationalOpNode);
        assertTrue(havingNode.getChildNodes().get(0) instanceof ExprSumNode);
        assertTrue(havingNode.getChildNodes().get(1) instanceof ExprConstantNode);

        text = "select sum(intPrimitive) from SupportBean_N().win:lenght(10) as win1 where intBoxed > 5 " +
            "having intPrimitive < avg(intPrimitive)";
        walker = parseAndWalk(text);

        havingNode = walker.getHavingExprRootNode();
        assertTrue(havingNode instanceof ExprRelationalOpNode);
    }

    public void testDistinct() throws Exception
    {
        String text = "select sum(distinct intPrimitive) from SupportBean_N().win:lenght(10) as win1";
        EQLTreeWalker walker = parseAndWalk(text);

        ExprAggregateNode aggrNode = (ExprAggregateNode) walker.getSelectListExpressions().get(0).getFirst();
        assertTrue(aggrNode.isDistinct());
    }

    public void testComplexProperty() throws Exception
    {
        String text = "select array [ 1 ],s0.map('a'),nested.nested2, a[1].b as x " +
                " from SupportBean_N().win:lenght(10) as win1 " +
                " where a[1].b('a').nested.c[0] = 4";
        EQLTreeWalker walker = parseAndWalk(text);

        ExprIdentNode identNode = (ExprIdentNode) walker.getSelectListExpressions().get(0).getFirst();
        assertEquals("array[1]", identNode.getUnresolvedPropertyName());
        assertNull(identNode.getStreamOrPropertyName());

        identNode = (ExprIdentNode) walker.getSelectListExpressions().get(1).getFirst();
        assertEquals("map('a')", identNode.getUnresolvedPropertyName());
        assertEquals("s0", identNode.getStreamOrPropertyName());

        identNode = (ExprIdentNode) walker.getSelectListExpressions().get(2).getFirst();
        assertEquals("nested2", identNode.getUnresolvedPropertyName());
        assertEquals("nested", identNode.getStreamOrPropertyName());

        identNode = (ExprIdentNode) walker.getSelectListExpressions().get(3).getFirst();
        assertEquals("a[1].b", identNode.getUnresolvedPropertyName());
        assertEquals(null, identNode.getStreamOrPropertyName());

        identNode = (ExprIdentNode) walker.getFilterRootNode().getChildNodes().get(0);
        assertEquals("a[1].b('a').nested.c[0]", identNode.getUnresolvedPropertyName());
        assertEquals(null, identNode.getStreamOrPropertyName());
    }

    public void testBitWise() throws Exception
    {
        String text = "select intPrimitive & intBoxed from " + SupportBean.class.getName() + "().win:lenght(10) as stream0";
        EQLTreeWalker walker = parseAndWalk(text);
        List<Pair<ExprNode, String>> selectExpressions = walker.getSelectListExpressions();
        assertEquals(1, selectExpressions.size());
        assertTrue(selectExpressions.get(0).getFirst() instanceof ExprBitWiseNode);

        assertEquals(0, tryBitWise("1&2"));
        assertEquals(3, tryBitWise("1|2"));
        assertEquals(8, tryBitWise("10^2"));
    }

    private Object tryBitWise(String equation) throws Exception
    {
        String expression = EXPRESSION + "where (" + equation + ")=win2.f2";

        EQLTreeWalker walker = parseAndWalk(expression);
        ExprNode exprNode = walker.getFilterRootNode().getChildNodes().get(0);
        ExprBitWiseNode bitWiseNode = (ExprBitWiseNode) (exprNode);
        bitWiseNode.validateDescendents(null);
        return bitWiseNode.evaluate(null);
    }

    private void tryOuterJoin(String outerType, OuterJoinType typeExpected) throws Exception
    {
        String text = "select intPrimitive from " +
                        SupportBean_A.class.getName() + "().win:lenght(10) as win1 " +
                        outerType + " outer join " +
                        SupportBean_A.class.getName() + "().win:lenght(10) as win2 " +
                        "on win1.f1 = win2.f2[1]";
        EQLTreeWalker walker = parseAndWalk(text);

        List<OuterJoinDesc> descList = walker.getOuterJoinDescList();
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
        walker = parseAndWalk(text);

        descList = walker.getOuterJoinDescList();
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

    private Object tryMath(String equation) throws Exception
    {
        String expression = EXPRESSION + "where " + equation + "=win2.f2";

        EQLTreeWalker walker = parseAndWalk(expression);
        ExprNode mathNode = (walker.getFilterRootNode().getChildNodes().get(0));
        mathNode.validateDescendents(null);
        return mathNode.evaluate(null);
    }

    private Object tryRelationalOp(String subExpr) throws Exception
    {
        String expression = EXPRESSION + "where " + subExpr;

        EQLTreeWalker walker = parseAndWalk(expression);
        ExprNode filterExprNode = walker.getFilterRootNode();
        filterExprNode.validateDescendents(null);
        return filterExprNode.evaluate(null);
    }

    private static EQLTreeWalker parseAndWalk(String expression) throws Exception
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

    private static final Log log = LogFactory.getLog(TestEQLTreeWalker.class);
}