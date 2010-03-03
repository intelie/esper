package com.espertech.esper.epl.parse;

import com.espertech.esper.epl.core.EngineImportService;
import com.espertech.esper.epl.core.EngineImportServiceImpl;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.spec.*;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.epl.variable.VariableServiceImpl;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.pattern.*;
import com.espertech.esper.schedule.SchedulingServiceImpl;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.epl.SupportPluginAggregationMethodOne;
import com.espertech.esper.support.epl.parse.SupportEPLTreeWalkerFactory;
import com.espertech.esper.support.epl.parse.SupportParserHelper;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.timer.TimeSourceServiceImpl;
import com.espertech.esper.type.OuterJoinType;
import junit.framework.TestCase;
import org.antlr.runtime.tree.Tree;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class TestEPLTreeWalker extends TestCase
{
    private static String CLASSNAME = SupportBean.class.getName();
    private static String EXPRESSION = "select * from " +
                    CLASSNAME + "(string='a').win:length(10).std:lastevent() as win1," +
                    CLASSNAME + "(string='b').win:length(10).std:lastevent() as win2 ";

    public void testWalkCreateIndex() throws Exception
    {
        String expression = "create index A_INDEX on B_NAMEDWIN (c, d)";

        EPLTreeWalker walker = parseAndWalkEPL(expression);
        CreateIndexDesc createIndex = walker.getStatementSpec().getCreateIndexDesc();
        assertEquals("A_INDEX", createIndex.getIndexName());
        assertEquals("B_NAMEDWIN", createIndex.getWindowName());
        assertEquals(2, createIndex.getColumns().size());
        assertEquals("c", createIndex.getColumns().get(0));
        assertEquals("d", createIndex.getColumns().get(1));
    }

    public void testWalkViewExpressions() throws Exception
    {
        String className = SupportBean.class.getName();
        String expression = "select * from " + className + ".win:x(intPrimitive, a.nested)";

        EPLTreeWalker walker = parseAndWalkEPL(expression);
        List<ViewSpec> viewSpecs = walker.getStatementSpec().getStreamSpecs().get(0).getViewSpecs();
        List<ExprNode> parameters = viewSpecs.get(0).getObjectParameters();
        assertEquals("intPrimitive", ((ExprIdentNode) parameters.get(0)).getFullUnresolvedName());
        assertEquals("a.nested", ((ExprIdentNode) parameters.get(1)).getFullUnresolvedName());
    }

    public void testWalkJoinMethodStatement() throws Exception
    {
        String className = SupportBean.class.getName();
        String expression = "select distinct * from " + className + " unidirectional, method:com.MyClass.myMethod(string, 2*intPrimitive) as s0";

        EPLTreeWalker walker = parseAndWalkEPL(expression);
        StatementSpecRaw statementSpec = walker.getStatementSpec();
        assertTrue(statementSpec.getSelectClauseSpec().isDistinct());
        assertEquals(2, statementSpec.getStreamSpecs().size());
        assertTrue(statementSpec.getStreamSpecs().get(0).getOptions().isUnidirectional());
        assertFalse(statementSpec.getStreamSpecs().get(0).getOptions().isRetainUnion());
        assertFalse(statementSpec.getStreamSpecs().get(0).getOptions().isRetainIntersection());

        MethodStreamSpec methodSpec = (MethodStreamSpec) statementSpec.getStreamSpecs().get(1);
        assertEquals("method", methodSpec.getIdent());
        assertEquals("com.MyClass", methodSpec.getClassName());
        assertEquals("myMethod", methodSpec.getMethodName());
        assertEquals(2, methodSpec.getExpressions().size());
        assertTrue(methodSpec.getExpressions().get(0) instanceof ExprIdentNode);
        assertTrue(methodSpec.getExpressions().get(1) instanceof ExprMathNode);
    }

    public void testWalkRetainKeywords() throws Exception
    {
        String className = SupportBean.class.getName();
        String expression = "select * from " + className + " retain-union";

        EPLTreeWalker walker = parseAndWalkEPL(expression);
        StatementSpecRaw statementSpec = walker.getStatementSpec();
        assertEquals(1, statementSpec.getStreamSpecs().size());
        assertTrue(statementSpec.getStreamSpecs().get(0).getOptions().isRetainUnion());
        assertFalse(statementSpec.getStreamSpecs().get(0).getOptions().isRetainIntersection());

        expression = "select * from " + className + " retain-intersection";

        walker = parseAndWalkEPL(expression);
        statementSpec = walker.getStatementSpec();
        assertEquals(1, statementSpec.getStreamSpecs().size());
        assertFalse(statementSpec.getStreamSpecs().get(0).getOptions().isRetainUnion());
        assertTrue(statementSpec.getStreamSpecs().get(0).getOptions().isRetainIntersection());
    }

    public void testWalkCreateVariable() throws Exception
    {
        String expression = "create variable sometype var1 = 1";
        EPLTreeWalker walker = parseAndWalkEPL(expression);
        StatementSpecRaw raw = walker.getStatementSpec();

        CreateVariableDesc createVarDesc = raw.getCreateVariableDesc();
        assertEquals("sometype", createVarDesc.getVariableType());
        assertEquals("var1", createVarDesc.getVariableName());
        assertTrue(createVarDesc.getAssignment() instanceof ExprConstantNode);
    }

    public void testWalkOnSet() throws Exception
    {
        VariableService variableService = new VariableServiceImpl(0, new SchedulingServiceImpl(new TimeSourceServiceImpl()), SupportEventAdapterService.getService(), null);
        variableService.createNewVariable("var1", Long.class.getName(), 100L, null);

        String expression = "on com.MyClass as myevent set var1 = 'a', var2 = 2*3, var3 = var1";
        EPLTreeWalker walker = parseAndWalkEPL(expression, null, variableService);
        StatementSpecRaw raw = walker.getStatementSpec();

        FilterStreamSpecRaw streamSpec = (FilterStreamSpecRaw) raw.getStreamSpecs().get(0);
        assertEquals("com.MyClass", streamSpec.getRawFilterSpec().getEventTypeName());
        assertEquals(0, streamSpec.getRawFilterSpec().getFilterExpressions().size());
        assertEquals("myevent", streamSpec.getOptionalStreamName());

        OnTriggerSetDesc setDesc = (OnTriggerSetDesc) raw.getOnTriggerDesc();
        assertTrue(setDesc.getOnTriggerType() == OnTriggerType.ON_SET);
        assertEquals(3, setDesc.getAssignments().size());

        OnTriggerSetAssignment assign = setDesc.getAssignments().get(0);
        assertEquals("var1", assign.getVariableName());
        assertTrue(assign.getExpression() instanceof ExprConstantNode);

        assign = setDesc.getAssignments().get(1);
        assertEquals("var2", assign.getVariableName());
        assertTrue(assign.getExpression() instanceof ExprMathNode);

        assign = setDesc.getAssignments().get(2);
        assertEquals("var3", assign.getVariableName());
        ExprVariableNode varNode = (ExprVariableNode) assign.getExpression();
        assertEquals("var1", varNode.getVariableName());

        assertTrue(raw.isHasVariables());

        // try a subquery
        expression = "select (select var1 from MyEvent) from MyEvent2";
        walker = parseAndWalkEPL(expression, null, variableService);
        raw = walker.getStatementSpec();
        assertTrue(raw.isHasVariables());
    }

    public void testWalkOnUpdate() throws Exception
    {
        String expression = "on com.MyClass as myevent update MyWindow as mw set prop1 = 'a', prop2=a.b*c where a=b";
        EPLTreeWalker walker = parseAndWalkEPL(expression);
        StatementSpecRaw raw = walker.getStatementSpec();

        FilterStreamSpecRaw streamSpec = (FilterStreamSpecRaw) raw.getStreamSpecs().get(0);
        assertEquals("com.MyClass", streamSpec.getRawFilterSpec().getEventTypeName());
        assertEquals(0, streamSpec.getRawFilterSpec().getFilterExpressions().size());
        assertEquals("myevent", streamSpec.getOptionalStreamName());

        OnTriggerWindowUpdateDesc setDesc = (OnTriggerWindowUpdateDesc) raw.getOnTriggerDesc();
        assertTrue(setDesc.getOnTriggerType() == OnTriggerType.ON_UPDATE);
        assertEquals(2, setDesc.getAssignments().size());

        OnTriggerSetAssignment assign = setDesc.getAssignments().get(0);
        assertEquals("prop1", assign.getVariableName());
        assertTrue(assign.getExpression() instanceof ExprConstantNode);
        
        assertEquals("a = b", raw.getFilterExprRootNode().toExpressionString());
    }

    public void testWalkOnSelectNoInsert() throws Exception
    {
        String expression = "on com.MyClass(myval != 0) as myevent select *, mywin.* as abc, myevent.* from MyNamedWindow as mywin where a=b";
        EPLTreeWalker walker = parseAndWalkEPL(expression);
        StatementSpecRaw raw = walker.getStatementSpec();

        FilterStreamSpecRaw streamSpec = (FilterStreamSpecRaw) raw.getStreamSpecs().get(0);
        assertEquals("com.MyClass", streamSpec.getRawFilterSpec().getEventTypeName());
        assertEquals(1, streamSpec.getRawFilterSpec().getFilterExpressions().size());
        assertEquals("myevent", streamSpec.getOptionalStreamName());

        OnTriggerWindowDesc windowDesc = (OnTriggerWindowDesc) raw.getOnTriggerDesc();
        assertEquals("MyNamedWindow", windowDesc.getWindowName());
        assertEquals("mywin", windowDesc.getOptionalAsName());
        assertEquals(OnTriggerType.ON_SELECT, windowDesc.getOnTriggerType());

        assertNull(raw.getInsertIntoDesc());
        assertTrue(raw.getSelectClauseSpec().isUsingWildcard());
        assertEquals(3, raw.getSelectClauseSpec().getSelectExprList().size());
        assertTrue(raw.getSelectClauseSpec().getSelectExprList().get(0) instanceof SelectClauseElementWildcard);
        assertEquals("mywin", ((SelectClauseStreamRawSpec) raw.getSelectClauseSpec().getSelectExprList().get(1)).getStreamName());
        assertEquals("mywin", ((SelectClauseStreamRawSpec) raw.getSelectClauseSpec().getSelectExprList().get(1)).getStreamName());
        assertEquals("abc", ((SelectClauseStreamRawSpec) raw.getSelectClauseSpec().getSelectExprList().get(1)).getOptionalAsName());
        assertEquals("myevent", (((SelectClauseStreamRawSpec) raw.getSelectClauseSpec().getSelectExprList().get(2)).getStreamName()));
        assertNull(((SelectClauseStreamRawSpec) raw.getSelectClauseSpec().getSelectExprList().get(2)).getOptionalAsName());
        assertTrue(raw.getFilterRootNode() instanceof ExprEqualsNode);
    }

    public void testWalkOnSelectInsert() throws Exception
    {
        String expression = "on pattern [com.MyClass] as pat insert into MyStream(a, b) select c, d from MyNamedWindow as mywin " +
                " where a=b group by symbol having c=d order by e";
        EPLTreeWalker walker = parseAndWalkEPL(expression);
        StatementSpecRaw raw = walker.getStatementSpec();

        PatternStreamSpecRaw streamSpec = (PatternStreamSpecRaw) raw.getStreamSpecs().get(0);
        assertTrue(streamSpec.getEvalNode() instanceof EvalFilterNode);
        assertEquals("pat", streamSpec.getOptionalStreamName());

        OnTriggerWindowDesc windowDesc = (OnTriggerWindowDesc) raw.getOnTriggerDesc();
        assertEquals("MyNamedWindow", windowDesc.getWindowName());
        assertEquals("mywin", windowDesc.getOptionalAsName());
        assertEquals(OnTriggerType.ON_SELECT, windowDesc.getOnTriggerType());
        assertTrue(raw.getFilterRootNode() instanceof ExprEqualsNode);

        assertEquals("MyStream", raw.getInsertIntoDesc().getEventTypeName());
        assertEquals(2, raw.getInsertIntoDesc().getColumnNames().size());
        assertEquals("a", raw.getInsertIntoDesc().getColumnNames().get(0));
        assertEquals("b", raw.getInsertIntoDesc().getColumnNames().get(1));

        assertFalse(raw.getSelectClauseSpec().isUsingWildcard());
        assertEquals(2, raw.getSelectClauseSpec().getSelectExprList().size());

        assertEquals(1, raw.getGroupByExpressions().size());
        assertTrue(raw.getHavingExprRootNode() instanceof ExprEqualsNode);
        assertEquals(1, raw.getOrderByList().size());
    }

    public void testWalkOnSelectMultiInsert() throws Exception
    {
        String expression = "on Bean as pat " +
                " insert into MyStream select * where 1>2" +
                " insert into BStream(a, b) select * where 1=2" +
                " insert into CStream select a,b";
        EPLTreeWalker walker = parseAndWalkEPL(expression);
        StatementSpecRaw raw = walker.getStatementSpec();

        FilterStreamSpecRaw streamSpec = (FilterStreamSpecRaw) raw.getStreamSpecs().get(0);
        assertEquals("pat", streamSpec.getOptionalStreamName());

        OnTriggerSplitStreamDesc triggerDesc = (OnTriggerSplitStreamDesc) raw.getOnTriggerDesc();
        assertEquals(OnTriggerType.ON_SPLITSTREAM, triggerDesc.getOnTriggerType());
        assertEquals(2, triggerDesc.getSplitStreams().size());

        assertEquals("MyStream", raw.getInsertIntoDesc().getEventTypeName());
        assertTrue(raw.getSelectClauseSpec().isUsingWildcard());
        assertEquals(1, raw.getSelectClauseSpec().getSelectExprList().size());
        assertNotNull((ExprRelationalOpNode) raw.getFilterRootNode());

        OnTriggerSplitStream splitStream = triggerDesc.getSplitStreams().get(0);
        assertEquals("BStream", splitStream.getInsertInto().getEventTypeName());
        assertEquals(2, splitStream.getInsertInto().getColumnNames().size());
        assertEquals("a", splitStream.getInsertInto().getColumnNames().get(0));
        assertEquals("b", splitStream.getInsertInto().getColumnNames().get(1));
        assertTrue(splitStream.getSelectClause().isUsingWildcard());
        assertEquals(1, splitStream.getSelectClause().getSelectExprList().size());
        assertNotNull((ExprEqualsNode) splitStream.getWhereClause());

        splitStream = triggerDesc.getSplitStreams().get(1);
        assertEquals("CStream", splitStream.getInsertInto().getEventTypeName());
        assertEquals(0, splitStream.getInsertInto().getColumnNames().size());
        assertFalse(splitStream.getSelectClause().isUsingWildcard());
        assertEquals(2, splitStream.getSelectClause().getSelectExprList().size());
        assertNull(splitStream.getWhereClause());
    }

    public void testWalkOnDelete() throws Exception
    {
        // try a filter
        String expression = "on com.MyClass(myval != 0) as myevent delete from MyNamedWindow as mywin where mywin.key = myevent.otherKey";
        EPLTreeWalker walker = parseAndWalkEPL(expression);
        StatementSpecRaw raw = walker.getStatementSpec();

        FilterStreamSpecRaw streamSpec = (FilterStreamSpecRaw) raw.getStreamSpecs().get(0);
        assertEquals("com.MyClass", streamSpec.getRawFilterSpec().getEventTypeName());
        assertEquals(1, streamSpec.getRawFilterSpec().getFilterExpressions().size());
        assertEquals("myevent", streamSpec.getOptionalStreamName());

        OnTriggerWindowDesc windowDesc = (OnTriggerWindowDesc) raw.getOnTriggerDesc();
        assertEquals("MyNamedWindow", windowDesc.getWindowName());
        assertEquals("mywin", windowDesc.getOptionalAsName());
        assertEquals(OnTriggerType.ON_DELETE, windowDesc.getOnTriggerType());

        assertTrue(raw.getFilterRootNode() instanceof ExprEqualsNode);

        // try a pattern
        expression = "on pattern [every MyClass] as myevent delete from MyNamedWindow";
        walker = parseAndWalkEPL(expression);
        raw = walker.getStatementSpec();

        PatternStreamSpecRaw patternSpec = (PatternStreamSpecRaw) raw.getStreamSpecs().get(0);
        assertTrue(patternSpec.getEvalNode() instanceof EvalEveryNode);
    }

    public void testWalkCreateWindow() throws Exception
    {
        String expression = "create window MyWindow.std:groupby(symbol).win:length(20) as select *, aprop, bprop as someval from com.MyClass insert where a=b";
        EPLTreeWalker walker = parseAndWalkEPL(expression);
        StatementSpecRaw raw = walker.getStatementSpec();

        // window name
        assertEquals("MyWindow", raw.getCreateWindowDesc().getWindowName());
        assertTrue(raw.getCreateWindowDesc().isInsert());
        assertTrue(raw.getCreateWindowDesc().getInsertFilter() instanceof ExprEqualsNode);

        // select clause
        assertTrue(raw.getSelectClauseSpec().isUsingWildcard());
        List<SelectClauseElementRaw> selectSpec = raw.getSelectClauseSpec().getSelectExprList();
        assertEquals(3, selectSpec.size());
        assertTrue(raw.getSelectClauseSpec().getSelectExprList().get(0) instanceof SelectClauseElementWildcard);
        SelectClauseExprRawSpec rawSpec = (SelectClauseExprRawSpec) selectSpec.get(1);
        assertEquals("aprop", ((ExprIdentNode)rawSpec.getSelectExpression()).getUnresolvedPropertyName());
        rawSpec = (SelectClauseExprRawSpec) selectSpec.get(2);
        assertEquals("bprop", ((ExprIdentNode)rawSpec.getSelectExpression()).getUnresolvedPropertyName());
        assertEquals("someval", rawSpec.getOptionalAsName());

        // filter is the event type
        FilterStreamSpecRaw streamSpec = (FilterStreamSpecRaw) raw.getStreamSpecs().get(0);
        assertEquals("com.MyClass", streamSpec.getRawFilterSpec().getEventTypeName());

        // 2 views
        assertEquals(2, raw.getCreateWindowDesc().getViewSpecs().size());
        assertEquals("groupby", raw.getCreateWindowDesc().getViewSpecs().get(0).getObjectName());
        assertEquals("std", raw.getCreateWindowDesc().getViewSpecs().get(0).getObjectNamespace());
        assertEquals("length", raw.getCreateWindowDesc().getViewSpecs().get(1).getObjectName());
    }

    public void testWalkMatchRecognize() throws Exception
    {
        String[] patternTests = new String[] {
                "A", "A B", "A? B*", "(A|B)+", "A C|B C", "(G1|H1) (I1|J1)", "(G1*|H1)? (I1+|J1?)", "A B (G) (H H|(I P)?) K?"};

        for (int i = 0; i < patternTests.length; i++)
        {
            String expression = "select * from MyEvent.win:keepall() match_recognize (" +
                    "  partition by string measures A.string as a_string pattern ( " + patternTests[i] + ") define A as (A.value = 1) )";

            EPLTreeWalker walker = parseAndWalkEPL(expression);
            StatementSpecRaw raw = walker.getStatementSpec();

            assertEquals(1, raw.getMatchRecognizeSpec().getMeasures().size());
            assertEquals(1, raw.getMatchRecognizeSpec().getDefines().size());
            assertEquals(1, raw.getMatchRecognizeSpec().getPartitionByExpressions().size());

            String received = raw.getMatchRecognizeSpec().getPattern().toExpressionString();
            assertEquals(patternTests[i], received);
        }
    }

    public void testWalkSubstitutionParams() throws Exception
    {
        // try EPL
        String expression = "select * from " + CLASSNAME + "(string=?, value=?)";
        EPLTreeWalker walker = parseAndWalkEPL(expression);
        StatementSpecRaw raw = walker.getStatementSpec();
        assertTrue(raw.isExistsSubstitutionParameters());

        FilterStreamSpecRaw streamSpec = (FilterStreamSpecRaw) raw.getStreamSpecs().get(0);
        ExprEqualsNode equalsFilter = (ExprEqualsNode) streamSpec.getRawFilterSpec().getFilterExpressions().get(0);
        assertEquals(1, ((ExprSubstitutionNode) equalsFilter.getChildNodes().get(1)).getIndex());
        equalsFilter = (ExprEqualsNode) streamSpec.getRawFilterSpec().getFilterExpressions().get(1);
        assertEquals(2, ((ExprSubstitutionNode) equalsFilter.getChildNodes().get(1)).getIndex());

        // try pattern
        expression = CLASSNAME + "(string=?, value=?)";
        walker = parseAndWalkPattern(expression);
        raw = walker.getStatementSpec();
        assertTrue(raw.isExistsSubstitutionParameters());
    }

    public void testWalkPatternMatchUntil() throws Exception
    {
        EPLTreeWalker walker = parseAndWalkPattern("A until (B or C)");
        StatementSpecRaw raw = walker.getStatementSpec();
        PatternStreamSpecRaw a = (PatternStreamSpecRaw) raw.getStreamSpecs().get(0);
        EvalMatchUntilNode matchNode = (EvalMatchUntilNode) a.getEvalNode();
        assertEquals(2, matchNode.getChildNodes().size());
        assertTrue(matchNode.getChildNodes().get(0) instanceof EvalFilterNode);
        assertTrue(matchNode.getChildNodes().get(1) instanceof EvalOrNode);

        EvalMatchUntilSpec spec = getMatchUntilSpec("A until (B or C)");
        assertNull(spec.getLowerBounds());
        assertNull(spec.getUpperBounds());

        spec = getMatchUntilSpec("[1..10] A until (B or C)");
        assertEquals(1, (int) spec.getLowerBounds());
        assertEquals(10, (int) spec.getUpperBounds());

        spec = getMatchUntilSpec("[1 .. 10] A until (B or C)");
        assertEquals(1, (int) spec.getLowerBounds());
        assertEquals(10, (int) spec.getUpperBounds());

        spec = getMatchUntilSpec("[1:10] A until (B or C)");
        assertEquals(1, (int) spec.getLowerBounds());
        assertEquals(10, (int) spec.getUpperBounds());

        spec = getMatchUntilSpec("[1..] A until (B or C)");
        assertEquals(1, (int) spec.getLowerBounds());
        assertEquals(null, spec.getUpperBounds());

        spec = getMatchUntilSpec("[1 ..] A until (B or C)");
        assertEquals(1, (int) spec.getLowerBounds());
        assertEquals(null, spec.getUpperBounds());

        spec = getMatchUntilSpec("[..2] A until (B or C)");
        assertEquals(null, spec.getLowerBounds());
        assertEquals(2, (int) spec.getUpperBounds());

        spec = getMatchUntilSpec("[.. 2] A until (B or C)");
        assertEquals(null, spec.getLowerBounds());
        assertEquals(2, (int) spec.getUpperBounds());

        spec = getMatchUntilSpec("[2] A until (B or C)");
        assertEquals(2, (int) spec.getLowerBounds());
        assertEquals(2, (int) spec.getUpperBounds());
    }

    private EvalMatchUntilSpec getMatchUntilSpec(String text) throws Exception
    {
        EPLTreeWalker walker = parseAndWalkPattern(text);
        StatementSpecRaw raw = walker.getStatementSpec();
        PatternStreamSpecRaw a = (PatternStreamSpecRaw) raw.getStreamSpecs().get(0);
        EvalMatchUntilNode matchNode = (EvalMatchUntilNode) a.getEvalNode();
        return matchNode.getSpec();
    }

    public void testWalkSimpleWhere() throws Exception
    {
        String expression = EXPRESSION + "where win1.f1=win2.f2";

        EPLTreeWalker walker = parseAndWalkEPL(expression);

        assertEquals(2, walker.getStatementSpec().getStreamSpecs().size());

        FilterStreamSpecRaw streamSpec = (FilterStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0);
        assertEquals(2, streamSpec.getViewSpecs().size());
        assertEquals(SupportBean.class.getName(), streamSpec.getRawFilterSpec().getEventTypeName());
        assertEquals("length", streamSpec.getViewSpecs().get(0).getObjectName());
        assertEquals("lastevent", streamSpec.getViewSpecs().get(1).getObjectName());
        assertEquals("win1", streamSpec.getOptionalStreamName());

        streamSpec = (FilterStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(1);
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

    public void testWalkWhereWithAnd() throws Exception
    {
        String expression = "select * from " +
                        CLASSNAME + "(string='a').win:length(10).std:lastevent() as win1," +
                        CLASSNAME + "(string='b').win:length(9).std:lastevent() as win2, " +
                        CLASSNAME + "(string='c').win:length(3).std:lastevent() as win3 " +
                        "where win1.f1=win2.f2 and win3.f3=f4 limit 5 offset 10";

        EPLTreeWalker walker = parseAndWalkEPL(expression);

        // ProjectedStream spec validation
        assertEquals(3, walker.getStatementSpec().getStreamSpecs().size());
        assertEquals("win1", walker.getStatementSpec().getStreamSpecs().get(0).getOptionalStreamName());
        assertEquals("win2", walker.getStatementSpec().getStreamSpecs().get(1).getOptionalStreamName());
        assertEquals("win3", walker.getStatementSpec().getStreamSpecs().get(2).getOptionalStreamName());

        FilterStreamSpecRaw streamSpec = (FilterStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(2);
        assertEquals(2, streamSpec.getViewSpecs().size());
        assertEquals(SupportBean.class.getName(), streamSpec.getRawFilterSpec().getEventTypeName());
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
        
        assertEquals(5, (int) walker.getStatementSpec().getRowLimitSpec().getNumRows());
        assertEquals(10, (int) walker.getStatementSpec().getRowLimitSpec().getOptionalOffset());
    }

    public void testWalkPerRowFunctions() throws Exception
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

    public void testWalkMath() throws Exception
    {
        assertEquals(32.0, tryExpression("5*6-3+15/3"));
        assertEquals(-5, tryExpression("1-1-1-2-1-1"));
        assertEquals(2.8d, tryExpression("1.4 + 1.4"));
        assertEquals(1d, tryExpression("55.5/5/11.1"));
        assertEquals(2/3d, tryExpression("2/3"));
        assertEquals(2/3d, tryExpression("2.0/3"));
        assertEquals(10, tryExpression("(1+4)*2"));
        assertEquals(12, tryExpression("(3*(6-4))*2"));
        assertEquals(8.5, tryExpression("(1+(4*3)+2)/2+1"));
        assertEquals(1, tryExpression("10%3"));
        assertEquals(10.1 % 3, tryExpression("10.1%3"));
    }

    public void testWalkRelationalOp() throws Exception
    {
        assertEquals(true, tryRelationalOp("3>2"));
        assertEquals(true, tryRelationalOp("3*5/2 >= 7.5"));
        assertEquals(true, tryRelationalOp("3*5/2.0 >= 7.5"));
        assertEquals(false, tryRelationalOp("1.1 + 2.2 < 3.2"));
        assertEquals(false, tryRelationalOp("3<=2"));
        assertEquals(true, tryRelationalOp("4*(3+1)>=16"));

        assertEquals(false, tryRelationalOp("(4>2) and (2>3)"));
        assertEquals(true, tryRelationalOp("(4>2) or (2>3)"));

        assertEquals(false, tryRelationalOp("not 3>2"));
        assertEquals(true, tryRelationalOp("not (not 3>2)"));
    }

    public void testWalkInsertInto() throws Exception
    {
        String expression = "insert into MyAlias select * from " +
                        CLASSNAME + "().win:length(10).std:lastevent() as win1," +
                        CLASSNAME + "(string='b').win:length(9).std:lastevent() as win2";

        EPLTreeWalker walker = parseAndWalkEPL(expression);

        InsertIntoDesc desc = walker.getStatementSpec().getInsertIntoDesc();
        assertTrue(desc.isIStream());
        assertEquals("MyAlias", desc.getEventTypeName());
        assertEquals(0, desc.getColumnNames().size());

        expression = "insert rstream into MyAlias(a, b, c) select * from " +
                        CLASSNAME + "().win:length(10).std:lastevent() as win1," +
                        CLASSNAME + "(string='b').win:length(9).std:lastevent() as win2";

        walker = parseAndWalkEPL(expression);

        desc = walker.getStatementSpec().getInsertIntoDesc();
        assertFalse(desc.isIStream());
        assertEquals("MyAlias", desc.getEventTypeName());
        assertEquals(3, desc.getColumnNames().size());
        assertEquals("a", desc.getColumnNames().get(0));
        assertEquals("b", desc.getColumnNames().get(1));
        assertEquals("c", desc.getColumnNames().get(2));

        expression = "insert istream into Test2 select * from " + CLASSNAME + "().win:length(10)";
        walker = parseAndWalkEPL(expression);
        desc = walker.getStatementSpec().getInsertIntoDesc();
        assertTrue(desc.isIStream());
        assertEquals("Test2", desc.getEventTypeName());
        assertEquals(0, desc.getColumnNames().size());
    }

    public void testWalkView() throws Exception
    {
        String text = "select * from " + SupportBean.class.getName() + "(string=\"IBM\").win:lenght(10, 1.1, \"a\").stat:uni(price, false)";

        EPLTreeWalker walker = parseAndWalkEPL(text);
        FilterSpecRaw filterSpec = ((FilterStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0)).getRawFilterSpec();

        // Check filter spec properties
        assertEquals(SupportBean.class.getName(), filterSpec.getEventTypeName());
        assertEquals(1, filterSpec.getFilterExpressions().size());

        // Check views
        List<ViewSpec> viewSpecs = walker.getStatementSpec().getStreamSpecs().get(0).getViewSpecs();
        assertEquals(2, viewSpecs.size());

        ViewSpec specOne = viewSpecs.get(0);
        assertEquals("win", specOne.getObjectNamespace());
        assertEquals("lenght", specOne.getObjectName());
        assertEquals(3, specOne.getObjectParameters().size());
        assertEquals(10, ((ExprConstantNode) specOne.getObjectParameters().get(0)).getValue());
        assertEquals(1.1d, ((ExprConstantNode) specOne.getObjectParameters().get(1)).getValue());
        assertEquals("a", ((ExprConstantNode) specOne.getObjectParameters().get(2)).getValue());

        ViewSpec specTwo = viewSpecs.get(1);
        assertEquals("stat", specTwo.getObjectNamespace());
        assertEquals("uni", specTwo.getObjectName());
        assertEquals(2, specTwo.getObjectParameters().size());
        assertEquals("price", ((ExprIdentNode) specTwo.getObjectParameters().get(0)).getFullUnresolvedName());
        assertEquals(false, ((ExprConstantNode) specTwo.getObjectParameters().get(1)).getValue());
    }

    public void testWalkPropertyExpr() throws Exception
    {
        String text = "select * from " + SupportBean.class.getName() + "[a.b][select c,d.*,* from e as f where g]";

        EPLTreeWalker walker = parseAndWalkEPL(text);
        FilterSpecRaw filterSpec = ((FilterStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0)).getRawFilterSpec();
        assertEquals(2, filterSpec.getOptionalPropertyEvalSpec().getAtoms().size());
        assertEquals("a.b", filterSpec.getOptionalPropertyEvalSpec().getAtoms().get(0).getPropertyName());
        assertEquals(0, filterSpec.getOptionalPropertyEvalSpec().getAtoms().get(0).getOptionalSelectClause().getSelectExprList().size());

        PropertyEvalAtom atomTwo = filterSpec.getOptionalPropertyEvalSpec().getAtoms().get(1);
        assertEquals("e", atomTwo.getPropertyName());
        assertEquals("f", atomTwo.getOptionalAsName());
        assertNotNull(atomTwo.getOptionalWhereClause());
        List<SelectClauseElementRaw> list = atomTwo.getOptionalSelectClause().getSelectExprList();
        assertEquals(3, list.size());
        assertTrue(list.get(0) instanceof SelectClauseExprRawSpec);
        assertTrue(list.get(1) instanceof SelectClauseStreamRawSpec);
        assertTrue(list.get(2) instanceof SelectClauseElementWildcard);
    }

    public void testSelectList() throws Exception
    {
        String text = "select intPrimitive, 2 * intBoxed, 5 as myConst, stream0.string as theString from " + SupportBean.class.getName() + "().win:lenght(10) as stream0";
        EPLTreeWalker walker = parseAndWalkEPL(text);
        List<SelectClauseElementRaw> selectExpressions = walker.getStatementSpec().getSelectClauseSpec().getSelectExprList();
        assertEquals(4, selectExpressions.size());

        SelectClauseExprRawSpec rawSpec = (SelectClauseExprRawSpec) selectExpressions.get(0);
        assertTrue(rawSpec.getSelectExpression() instanceof ExprIdentNode);

        rawSpec = (SelectClauseExprRawSpec) selectExpressions.get(1);
        assertTrue(rawSpec.getSelectExpression() instanceof ExprMathNode);

        rawSpec = (SelectClauseExprRawSpec) selectExpressions.get(2);
        assertTrue(rawSpec.getSelectExpression() instanceof ExprConstantNode);
        assertEquals("myConst", rawSpec.getOptionalAsName());

        rawSpec = (SelectClauseExprRawSpec) selectExpressions.get(3);
        assertTrue(rawSpec.getSelectExpression() instanceof ExprIdentNode);
        assertEquals("theString", rawSpec.getOptionalAsName());
        assertNull(walker.getStatementSpec().getInsertIntoDesc());

        text = "select * from " + SupportBean.class.getName() + "().win:lenght(10)";
        walker = parseAndWalkEPL(text);
        assertEquals(1, walker.getStatementSpec().getSelectClauseSpec().getSelectExprList().size());
    }

    public void testArrayViewParams() throws Exception
    {
        // Check a list of integer as a view parameter
        String text = "select * from " + SupportBean.class.getName() + "().win:lenght({10, 11, 12})";
        EPLTreeWalker walker = parseAndWalkEPL(text);

        List<ViewSpec> viewSpecs = walker.getStatementSpec().getStreamSpecs().get(0).getViewSpecs();
        ExprNode node = viewSpecs.get(0).getObjectParameters().get(0);
        node.validate(null, null, null, null, null, null);
        Object[] intParams = (Object[]) ((ExprArrayNode) node).evaluate(null, true, null);
        assertEquals(10, intParams[0]);
        assertEquals(11, intParams[1]);
        assertEquals(12, intParams[2]);

        // Check a list of objects
        text = "select * from " + SupportBean.class.getName() + "().win:lenght({false, 11.2, 's'})";
        walker = parseAndWalkEPL(text);
        viewSpecs = walker.getStatementSpec().getStreamSpecs().get(0).getViewSpecs();
        ExprNode param = viewSpecs.get(0).getObjectParameters().get(0);
        param.validate(null, null, null, null, null, null);
        Object[] objParams = (Object[]) ((ExprArrayNode) param).evaluate(null, true, null);
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
        parseAndWalkEPL(text);
    }

    public void testAggregateFunction() throws Exception
    {
        String fromClause = "from " + SupportBean_N.class.getName() + "().win:lenght(10) as win1";
        String text = "select max(distinct intPrimitive) " + fromClause;
        parseAndWalkEPL(text);

        text = "select sum(intPrimitive)," +
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
        parseAndWalkEPL(text);

        // try min-max aggregate versus row functions
        text = "select max(intPrimitive), min(intPrimitive)," +
                      "max(intPrimitive,intBoxed), min(intPrimitive,intBoxed)," +
                      "max(distinct intPrimitive), min(distinct intPrimitive)" +
                      fromClause;
        parseAndWalkEPL(text);

        try
        {
            parseAndWalkEPL("select max(distinct intPrimitive, intboxed)");
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
        EPLTreeWalker walker = parseAndWalkEPL(text);

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
        EPLTreeWalker walker = parseAndWalkEPL(text);

        ExprNode havingNode = walker.getStatementSpec().getHavingExprRootNode();

        assertTrue(havingNode instanceof ExprRelationalOpNode);
        assertTrue(havingNode.getChildNodes().get(0) instanceof ExprSumNode);
        assertTrue(havingNode.getChildNodes().get(1) instanceof ExprConstantNode);

        text = "select sum(intPrimitive) from SupportBean_N().win:lenght(10) as win1 where intBoxed > 5 " +
            "having intPrimitive < avg(intPrimitive)";
        walker = parseAndWalkEPL(text);

        havingNode = walker.getStatementSpec().getHavingExprRootNode();
        assertTrue(havingNode instanceof ExprRelationalOpNode);
    }

    public void testDistinct() throws Exception
    {
        String text = "select sum(distinct intPrimitive) from SupportBean_N().win:lenght(10) as win1";
        EPLTreeWalker walker = parseAndWalkEPL(text);

        SelectClauseElementRaw rawElement = walker.getStatementSpec().getSelectClauseSpec().getSelectExprList().get(0);
        SelectClauseExprRawSpec exprSpec = (SelectClauseExprRawSpec) rawElement;
        ExprAggregateNode aggrNode = (ExprAggregateNode) exprSpec.getSelectExpression();
        assertTrue(aggrNode.isDistinct());
    }

    public void testComplexProperty() throws Exception
    {
        String text = "select array [ 1 ],s0.map('a'),nested.nested2, a[1].b as x, nested.abcdef? " +
                " from SupportBean_N().win:lenght(10) as win1 " +
                " where a[1].b('a').nested.c[0] = 4";
        EPLTreeWalker walker = parseAndWalkEPL(text);

        ExprIdentNode identNode = (ExprIdentNode) getSelectExprSpec(walker.getStatementSpec(), 0).getSelectExpression();
        assertEquals("array[1]", identNode.getUnresolvedPropertyName());
        assertNull(identNode.getStreamOrPropertyName());

        identNode = (ExprIdentNode) getSelectExprSpec(walker.getStatementSpec(), 1).getSelectExpression();
        assertEquals("map('a')", identNode.getUnresolvedPropertyName());
        assertEquals("s0", identNode.getStreamOrPropertyName());

        identNode = (ExprIdentNode) getSelectExprSpec(walker.getStatementSpec(), 2).getSelectExpression();
        assertEquals("nested2", identNode.getUnresolvedPropertyName());
        assertEquals("nested", identNode.getStreamOrPropertyName());

        identNode = (ExprIdentNode) getSelectExprSpec(walker.getStatementSpec(), 3).getSelectExpression();
        assertEquals("a[1].b", identNode.getUnresolvedPropertyName());
        assertEquals(null, identNode.getStreamOrPropertyName());

        identNode = (ExprIdentNode) getSelectExprSpec(walker.getStatementSpec(), 4).getSelectExpression();
        assertEquals("abcdef?", identNode.getUnresolvedPropertyName());
        assertEquals("nested", identNode.getStreamOrPropertyName());

        identNode = (ExprIdentNode) walker.getStatementSpec().getFilterRootNode().getChildNodes().get(0);
        assertEquals("a[1].b('a').nested.c[0]", identNode.getUnresolvedPropertyName());
        assertEquals(null, identNode.getStreamOrPropertyName());
    }

    public void testBitWise() throws Exception
    {
        String text = "select intPrimitive & intBoxed from " + SupportBean.class.getName() + "().win:lenght(10) as stream0";
        EPLTreeWalker walker = parseAndWalkEPL(text);
        List<SelectClauseElementRaw> selectExpressions = walker.getStatementSpec().getSelectClauseSpec().getSelectExprList();
        assertEquals(1, selectExpressions.size());
        assertTrue(getSelectExprSpec(walker.getStatementSpec(), 0).getSelectExpression() instanceof ExprBitWiseNode);

        assertEquals(0, tryBitWise("1&2"));
        assertEquals(3, tryBitWise("1|2"));
        assertEquals(8, tryBitWise("10^2"));
    }

    public void testPatternsOnly() throws Exception
    {
        String patternOne = "a=" + SupportBean.class.getName() + " -> b=" + SupportBean.class.getName();
        String patternTwo = "c=" + SupportBean.class.getName() + " or " + SupportBean.class.getName();

        // Test simple case, one pattern and no "as streamName"
        EPLTreeWalker walker = parseAndWalkEPL("select * from pattern [" + patternOne + "]");
        assertEquals(1, walker.getStatementSpec().getStreamSpecs().size());
        PatternStreamSpecRaw patternStreamSpec = (PatternStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0);

        assertEquals(EvalFollowedByNode.class, patternStreamSpec.getEvalNode().getClass());
        assertNull(patternStreamSpec.getOptionalStreamName());

        // Test case with "as s0"
        walker = parseAndWalkEPL("select * from pattern [" + patternOne + "] as s0");
        patternStreamSpec = (PatternStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0);
        assertEquals("s0", patternStreamSpec.getOptionalStreamName());

        // Test case with multiple patterns
        walker = parseAndWalkEPL("select * from pattern [" + patternOne + "] as s0, pattern [" + patternTwo + "] as s1");
        assertEquals(2, walker.getStatementSpec().getStreamSpecs().size());
        patternStreamSpec = (PatternStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0);
        assertEquals("s0", patternStreamSpec.getOptionalStreamName());
        assertEquals(EvalFollowedByNode.class, patternStreamSpec.getEvalNode().getClass());

        patternStreamSpec = (PatternStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(1);
        assertEquals("s1", patternStreamSpec.getOptionalStreamName());
        assertEquals(EvalOrNode.class, patternStreamSpec.getEvalNode().getClass());

        // Test 3 patterns
        walker = parseAndWalkEPL("select * from pattern [" + patternOne + "], pattern [" + patternTwo + "] as s1," +
                "pattern[x=" + SupportBean_S2.class.getName() + "] as s2");
        assertEquals(3, walker.getStatementSpec().getStreamSpecs().size());
        patternStreamSpec = (PatternStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(2);
        assertEquals("s2", patternStreamSpec.getOptionalStreamName());

        // Test patterns with views
        walker = parseAndWalkEPL("select * from pattern [" + patternOne + "].win:time(1), pattern [" + patternTwo + "].win:length(1).std:lastevent() as s1");
        assertEquals(2, walker.getStatementSpec().getStreamSpecs().size());
        patternStreamSpec = (PatternStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0);
        assertEquals(1, patternStreamSpec.getViewSpecs().size());
        assertEquals("time", patternStreamSpec.getViewSpecs().get(0).getObjectName());
        patternStreamSpec = (PatternStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(1);
        assertEquals(2, patternStreamSpec.getViewSpecs().size());
        assertEquals("length", patternStreamSpec.getViewSpecs().get(0).getObjectName());
        assertEquals("lastevent", patternStreamSpec.getViewSpecs().get(1).getObjectName());
    }

    public void testIfThenElseCase() throws Exception
    {
        String text;
        text = "select case when intPrimitive > shortPrimitive then count(intPrimitive) end from " +    SupportBean.class.getName() + "().win:lenght(10) as win";
        parseAndWalkEPL(text);
        text = "select case when intPrimitive > shortPrimitive then count(intPrimitive) end as p1 from " +    SupportBean.class.getName() + "().win:lenght(10) as win";
        parseAndWalkEPL(text);
        text = "select case when intPrimitive > shortPrimitive then count(intPrimitive) else shortPrimitive end from " +    SupportBean.class.getName() + "().win:lenght(10) as win";
        parseAndWalkEPL(text);
        text = "select case when intPrimitive > shortPrimitive then count(intPrimitive) when longPrimitive > intPrimitive then count(longPrimitive) else shortPrimitive end from " +    SupportBean.class.getName() + "().win:lenght(10) as win";
        parseAndWalkEPL(text);
        text = "select case intPrimitive  when 1 then count(intPrimitive) end from " +    SupportBean.class.getName() + "().win:lenght(10) as win";
        parseAndWalkEPL(text);
        text = "select case intPrimitive when longPrimitive then (intPrimitive + longPrimitive) end" +
        " from " + SupportBean.class.getName() + ".win:length(3)";
        parseAndWalkEPL(text);
    }

    private void tryOuterJoin(String outerType, OuterJoinType typeExpected) throws Exception
    {
        String text = "select intPrimitive from " +
                        SupportBean_A.class.getName() + "().win:lenght(10) as win1 " +
                        outerType + " outer join " +
                        SupportBean_A.class.getName() + "().win:lenght(10) as win2 " +
                        "on win1.f1 = win2.f2[1]";
        EPLTreeWalker walker = parseAndWalkEPL(text);

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
                        "on win1.f1 = win3.f3 and win1.f11 = win3.f31";
        walker = parseAndWalkEPL(text);

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

        assertEquals(1, desc.getAdditionalLeftNodes().length);
        assertEquals("f11", desc.getAdditionalLeftNodes()[0].getUnresolvedPropertyName());
        assertEquals("win1", desc.getAdditionalLeftNodes()[0].getStreamOrPropertyName());
        assertEquals(1, desc.getAdditionalRightNodes().length);
        assertEquals("f31", desc.getAdditionalRightNodes()[0].getUnresolvedPropertyName());
        assertEquals("win3", desc.getAdditionalRightNodes()[0].getStreamOrPropertyName());
    }

    public void testWalkPattern() throws Exception
    {
        String text = "every g=" + SupportBean.class.getName() + "(string=\"IBM\", intPrimitive != 1) where timer:within(20)";

        EPLTreeWalker walker = parseAndWalkPattern(text);

        assertEquals(1, walker.getStatementSpec().getStreamSpecs().size());
        PatternStreamSpecRaw patternStreamSpec = (PatternStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0);

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
        assertEquals(2, filterNode.getRawFilterSpec().getFilterExpressions().size());
        ExprEqualsNode equalsNode = (ExprEqualsNode) filterNode.getRawFilterSpec().getFilterExpressions().get(1);
        assertEquals(2, equalsNode.getChildNodes().size());
    }

    public void testWalkPropertyPatternCombination() throws Exception
    {
        final String EVENT = SupportBeanComplexProps.class.getName();
        String property = tryWalkGetPropertyPattern(EVENT + "(mapped ( 'key' )  = 'value')");
        assertEquals("mapped('key')", property);

        property = tryWalkGetPropertyPattern(EVENT + "(indexed [ 1 ]  = 1)");
        assertEquals("indexed[1]", property);
        property = tryWalkGetPropertyPattern(EVENT + "(nested . nestedValue  = 'value')");
        assertEquals("nestedValue", property);
    }

    public void testWalkPatternUseResult() throws Exception
    {
        final String EVENT = SupportBean_N.class.getName();
        String text = "na=" + EVENT + "() -> every nb=" + EVENT + "(doublePrimitive in [0:na.doublePrimitive])";
        parseAndWalkPattern(text);
    }

    public void testWalkIStreamRStreamSelect() throws Exception
    {
        String text = "select rstream 'a' from " + SupportBean_N.class.getName();
        EPLTreeWalker walker = parseAndWalkEPL(text);
        assertEquals(SelectClauseStreamSelectorEnum.RSTREAM_ONLY, walker.getStatementSpec().getSelectStreamSelectorEnum());

        text = "select istream 'a' from " + SupportBean_N.class.getName();
        walker = parseAndWalkEPL(text);
        assertEquals(SelectClauseStreamSelectorEnum.ISTREAM_ONLY, walker.getStatementSpec().getSelectStreamSelectorEnum());

        text = "select 'a' from " + SupportBean_N.class.getName();
        walker = parseAndWalkEPL(text);
        assertEquals(SelectClauseStreamSelectorEnum.ISTREAM_ONLY, walker.getStatementSpec().getSelectStreamSelectorEnum());

        text = "select irstream 'a' from " + SupportBean_N.class.getName();
        walker = parseAndWalkEPL(text);
        assertEquals(SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH, walker.getStatementSpec().getSelectStreamSelectorEnum());
    }

    public void testWalkPatternNoPackage() throws Exception
    {
        SupportEventAdapterService.getService().addBeanType("SupportBean_N", SupportBean_N.class, true);
        String text = "na=SupportBean_N()";
        parseAndWalkPattern(text);
    }

    public void testWalkPluginAggregationFunction() throws Exception
    {
        EngineImportService engineImportService = new EngineImportServiceImpl(true);
        engineImportService.addAggregation("concat", SupportPluginAggregationMethodOne.class.getName());

        String text = "select * from " + SupportBean.class.getName() + " group by concat(1)";
        EPLTreeWalker walker = parseAndWalkEPL(text, engineImportService, null);
        ExprPlugInAggFunctionNode node = (ExprPlugInAggFunctionNode) walker.getStatementSpec().getGroupByExpressions().get(0);
        assertEquals("concat", node.getAggregationFunctionName());
        assertFalse(node.isDistinct());

        text = "select * from " + SupportBean.class.getName() + " group by concat(distinct 1)";
        walker = parseAndWalkEPL(text, engineImportService, null);
        node = (ExprPlugInAggFunctionNode) walker.getStatementSpec().getGroupByExpressions().get(0);
        assertEquals("concat", node.getAggregationFunctionName());
        assertTrue(node.isDistinct());
    }

    public void testWalkPatternTypesValid() throws Exception
    {
        String text = SupportBean.class.getName();
        EPLTreeWalker walker = parseAndWalkPattern(text);
        assertEquals(1, walker.getStatementSpec().getStreamSpecs().size());
    }

    public void testWalkPatternIntervals() throws Exception
    {
        Object[][] intervals = {
                {"1E2 milliseconds", 0.1d},
                {"11 millisecond", 11/1000d},
                {"1.1 msec", 1.1/1000d},
                {"5 seconds", 5d},
                {"0.1 second", 0.1d},
                {"135L sec", 135d},
                {"1.4 minutes", 1.4 * 60d},
                {"11 minute", 11 * 60d},
                {"123.2 min", 123.2 * 60d},
                {".2 hour", .2 * 60 * 60d},
                {"11.2 hours", 11.2 * 60 * 60d},
                {"2 day", 2 * 24 * 60 * 60d},
                {"11.2 days", 11.2 * 24 * 60 * 60d},
                {"1 days 6 hours 2 minutes 4 seconds 3 milliseconds",
                            1*24*60*60 + 6*60*60 + 2*60 + 4 + 3/1000d},
                {"0.2 day 3.3 hour 1E3 minute 0.33 second 10000 millisecond",
                            0.2d*24*60*60 + 3.3d*60*60 + 1E3*60 + 0.33 + 10000/1000},
                {"0.2 day 3.3 hour 1E3 min 0.33 sec 10000 msec",
                            0.2d*24*60*60 + 3.3d*60*60 + 1E3*60 + 0.33 + 10000/1000},
                {"1.01 hour 2 sec", 1.01d*60*60 + 2},
                {"0.02 day 5 msec", 0.02d*24*60*60 + 5/1000d},
                {"66 min 4 sec", 66*60 + 4d},
        };

        for (int i = 0; i < intervals.length; i++)
        {
            String interval = (String) intervals[i][0];
            double result = tryInterval(interval);
            double expected = (Double) intervals[i][1];
            double delta = result - expected;
            assertTrue("Interval '" + interval + "' expected=" + expected + " actual=" + result, Math.abs(delta) < 0.0000001);
        }
    }

    public void testWalkInAndBetween() throws Exception
    {
        assertTrue((Boolean) tryRelationalOp("1 between 0 and 2"));
        assertFalse((Boolean) tryRelationalOp("-1 between 0 and 2"));
        assertFalse((Boolean) tryRelationalOp("1 not between 0 and 2"));
        assertTrue((Boolean) tryRelationalOp("-1 not between 0 and 2"));

        assertFalse((Boolean) tryRelationalOp("1 in (2,3)"));
        assertTrue((Boolean) tryRelationalOp("1 in (2,3,1)"));
        assertTrue((Boolean) tryRelationalOp("1 not in (2,3)"));
    }

    public void testWalkLikeRegex() throws Exception
    {
        assertTrue((Boolean) tryRelationalOp("'abc' like 'a__'"));
        assertFalse((Boolean) tryRelationalOp("'abcd' like 'a__'"));

        assertFalse((Boolean) tryRelationalOp("'abcde' not like 'a%'"));
        assertTrue((Boolean) tryRelationalOp("'bcde' not like 'a%'"));

        assertTrue((Boolean) tryRelationalOp("'a_' like 'a!_' escape '!'"));
        assertFalse((Boolean) tryRelationalOp("'ab' like 'a!_' escape '!'"));

        assertFalse((Boolean) tryRelationalOp("'a' not like 'a'"));
        assertTrue((Boolean) tryRelationalOp("'a' not like 'ab'"));
    }

    public void testWalkStaticFunc() throws Exception
    {
        String text = "select MyClass.someFunc(1) from SupportBean_N";
        EPLTreeWalker walker = parseAndWalkEPL(text);

        SelectClauseExprRawSpec spec = getSelectExprSpec(walker.getStatementSpec(), 0);
        ExprStaticMethodNode staticMethod = (ExprStaticMethodNode) spec.getSelectExpression();
        assertEquals("MyClass", staticMethod.getClassName());
        assertEquals("someFunc", staticMethod.getMethodName());
    }

    public void testWalkDBJoinStatement() throws Exception
    {
        String className = SupportBean.class.getName();
        String sql = "select a from b where $x.id=c.d";
        String expression = "select * from " + className + ", sql:mydb ['" + sql + "']";

        EPLTreeWalker walker = parseAndWalkEPL(expression);
        StatementSpecRaw statementSpec = walker.getStatementSpec();
        assertEquals(2, statementSpec.getStreamSpecs().size());
        DBStatementStreamSpec dbSpec = (DBStatementStreamSpec) statementSpec.getStreamSpecs().get(1);
        assertEquals("mydb", dbSpec.getDatabaseName());
        assertEquals(sql, dbSpec.getSqlWithSubsParams());

        expression = "select * from " + className + ", sql:mydb ['" + sql + "' metadatasql 'select * from B']";

        walker = parseAndWalkEPL(expression);
        statementSpec = walker.getStatementSpec();
        assertEquals(2, statementSpec.getStreamSpecs().size());
        dbSpec = (DBStatementStreamSpec) statementSpec.getStreamSpecs().get(1);
        assertEquals("mydb", dbSpec.getDatabaseName());
        assertEquals(sql, dbSpec.getSqlWithSubsParams());
        assertEquals("select * from B", dbSpec.getMetadataSQL());
    }

    public void testRangeBetweenAndIn() throws Exception
    {
        String className = SupportBean.class.getName();
        String expression = "select * from " + className + "(intPrimitive in [1:2], intBoxed in (1,2), doubleBoxed between 2 and 3)";
        parseAndWalkEPL(expression);

        expression = "select * from " + className + "(intPrimitive not in [1:2], intBoxed not in (1,2), doubleBoxed not between 2 and 3)";
        parseAndWalkEPL(expression);
    }

    public void testSubselect() throws Exception
    {
        String expression = "select (select a from B(id=1) where cox=mox) from C";
        EPLTreeWalker walker = parseAndWalkEPL(expression);
        SelectClauseExprRawSpec element = getSelectExprSpec(walker.getStatementSpec(), 0);
        ExprSubselectNode exprNode = (ExprSubselectNode) element.getSelectExpression();

        // check select expressions
        StatementSpecRaw spec = exprNode.getStatementSpecRaw();
        assertEquals(1, spec.getSelectClauseSpec().getSelectExprList().size());

        // check filter
        assertEquals(1, spec.getStreamSpecs().size());
        FilterStreamSpecRaw filter = (FilterStreamSpecRaw) spec.getStreamSpecs().get(0);
        assertEquals("B", filter.getRawFilterSpec().getEventTypeName());
        assertEquals(1, filter.getRawFilterSpec().getFilterExpressions().size());

        // check where clause
        assertTrue(spec.getFilterRootNode() instanceof ExprEqualsNode);
    }

    public void testWalkPatternObject() throws Exception
    {
        String expression = "select * from pattern [" + SupportBean.class.getName() + " -> timer:interval(100)]";
        parseAndWalkEPL(expression);

        expression = "select * from pattern [" + SupportBean.class.getName() + " where timer:within(100)]";
        parseAndWalkEPL(expression);
    }

    private double tryInterval(String interval) throws Exception
    {
        String text = "select * from " + SupportBean.class.getName() + ".win:time(" + interval + ")";

        EPLTreeWalker walker = parseAndWalkEPL(text);
        ViewSpec viewSpec = ((FilterStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0)).getViewSpecs().get(0);
        assertEquals("win", viewSpec.getObjectNamespace());
        assertEquals("time", viewSpec.getObjectName());
        assertEquals(1, viewSpec.getObjectParameters().size());
        ExprTimePeriod exprNode = (ExprTimePeriod) viewSpec.getObjectParameters().get(0);
        exprNode.validate(null, null, null, null, null, null);
        return ((Double) exprNode.evaluate(null, true, null)).doubleValue();
    }

    private String tryWalkGetPropertyPattern(String stmt) throws Exception
    {
        EPLTreeWalker walker = parseAndWalkPattern(stmt);

        assertEquals(1, walker.getStatementSpec().getStreamSpecs().size());
        PatternStreamSpecRaw patternStreamSpec = (PatternStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0);

        EvalFilterNode filterNode = (EvalFilterNode) patternStreamSpec.getEvalNode();
        assertEquals(1, filterNode.getRawFilterSpec().getFilterExpressions().size());
        ExprNode node = filterNode.getRawFilterSpec().getFilterExpressions().get(0);
        ExprIdentNode identNode = (ExprIdentNode) node.getChildNodes().get(0);
        return identNode.getUnresolvedPropertyName();
    }

    private static EPLTreeWalker parseAndWalkPattern(String expression) throws Exception
    {
        log.debug(".parseAndWalk Trying text=" + expression);
        Tree ast = SupportParserHelper.parsePattern(expression);
        log.debug(".parseAndWalk success, tree walking...");
        SupportParserHelper.displayAST(ast);

        EPLTreeWalker walker = SupportEPLTreeWalkerFactory.makeWalker(ast);
        walker.startPatternExpressionRule();
        return walker;
    }

    public static EPLTreeWalker parseAndWalkEPL(String expression) throws Exception
    {
        return parseAndWalkEPL(expression, new EngineImportServiceImpl(true), new VariableServiceImpl(0, null, SupportEventAdapterService.getService(), null));
    }

    private static EPLTreeWalker parseAndWalkEPL(String expression, EngineImportService engineImportService, VariableService variableService) throws Exception
    {
        log.debug(".parseAndWalk Trying text=" + expression);
        Tree ast = SupportParserHelper.parseEPL(expression);
        log.debug(".parseAndWalk success, tree walking...");
        SupportParserHelper.displayAST(ast);

        EventAdapterService eventAdapterService = SupportEventAdapterService.getService();
        eventAdapterService.addBeanType("SupportBean_N", SupportBean_N.class, true);

        EPLTreeWalker walker = SupportEPLTreeWalkerFactory.makeWalker(ast, engineImportService, variableService);
        walker.startEPLExpressionRule();
        return walker;
    }

    private Object tryBitWise(String equation) throws Exception
    {
        String expression = EXPRESSION + "where (" + equation + ")=win2.f2";

        EPLTreeWalker walker = parseAndWalkEPL(expression);
        ExprNode exprNode = walker.getStatementSpec().getFilterRootNode().getChildNodes().get(0);
        ExprBitWiseNode bitWiseNode = (ExprBitWiseNode) (exprNode);
        bitWiseNode.getValidatedSubtree(null, null, null, null, null, null);
        return bitWiseNode.evaluate(null, false, null);
    }

    private Object tryExpression(String equation) throws Exception
    {
        String expression = EXPRESSION + "where " + equation + "=win2.f2";

        EPLTreeWalker walker = parseAndWalkEPL(expression);
        ExprNode exprNode = (walker.getStatementSpec().getFilterRootNode().getChildNodes().get(0));
        exprNode = exprNode.getValidatedSubtree(null, null, null, null, null, null);
        return exprNode.evaluate(null, false, null);
    }

    private Object tryRelationalOp(String subExpr) throws Exception
    {
        String expression = EXPRESSION + "where " + subExpr;

        EPLTreeWalker walker = parseAndWalkEPL(expression);
        ExprNode filterExprNode = walker.getStatementSpec().getFilterRootNode();
        filterExprNode.getValidatedSubtree(null, null, null, null, null, null);
        return filterExprNode.evaluate(null, false, null);
    }

    private SelectClauseExprRawSpec getSelectExprSpec(StatementSpecRaw statementSpec, int index)
    {
        SelectClauseElementRaw raw = statementSpec.getSelectClauseSpec().getSelectExprList().get(index);
        return (SelectClauseExprRawSpec) raw;
    }

    private static final Log log = LogFactory.getLog(TestEPLTreeWalker.class);
}
