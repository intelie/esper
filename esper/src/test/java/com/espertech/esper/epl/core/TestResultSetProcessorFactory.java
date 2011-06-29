package com.espertech.esper.epl.core;

import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.spec.*;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.support.epl.SupportSelectExprFactory;
import com.espertech.esper.support.epl.SupportStreamTypeSvc1Stream;
import com.espertech.esper.support.epl.SupportStreamTypeSvc3Stream;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import junit.framework.TestCase;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.lang.annotation.Annotation;

public class TestResultSetProcessorFactory extends TestCase
{
    private StreamTypeService typeService1Stream;
    private StreamTypeService typeService3Stream;
    private List<ExprNode> groupByList;
    private List<OrderByItem> orderByList;
    private StatementContext stmtContext;

    public void setUp()
    {
        typeService1Stream = new SupportStreamTypeSvc1Stream();
        typeService3Stream = new SupportStreamTypeSvc3Stream();
        groupByList = new LinkedList<ExprNode>();
        orderByList = new LinkedList<OrderByItem>();
        stmtContext = SupportStatementContextFactory.makeContext();
    }

    public void testGetProcessorNoProcessorRequired() throws Exception
    {
        // single stream, empty group-by and wildcard select, no having clause, no need for any output processing
        List<SelectClauseElementCompiled> wildcardSelect = new LinkedList<SelectClauseElementCompiled>();
        wildcardSelect.add(new SelectClauseElementWildcard());
        StatementSpecCompiled spec = makeSpec(new SelectClauseSpecCompiled(wildcardSelect, false), null, groupByList, null, null, orderByList);
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null, new boolean[0], true);
        assertTrue(processor instanceof ResultSetProcessorHandThrough);
    }

    public void testGetProcessorSimpleSelect() throws Exception
    {
        // empty group-by and no event properties aggregated in select clause (wildcard), no having clause
        List<SelectClauseElementCompiled> wildcardSelect = new LinkedList<SelectClauseElementCompiled>();
        wildcardSelect.add(new SelectClauseElementWildcard());
        StatementSpecCompiled spec = makeSpec(new SelectClauseSpecCompiled(wildcardSelect, false), null, groupByList, null, null, orderByList);
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService3Stream, null, new boolean[0], true);
        assertTrue(processor instanceof ResultSetProcessorHandThrough);

        // empty group-by with select clause elements
        List<SelectClauseElementCompiled> selectList = SupportSelectExprFactory.makeNoAggregateSelectListUnnamed();
        spec = makeSpec(new SelectClauseSpecCompiled(selectList, false), null, groupByList, null, null, orderByList);
        processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null, new boolean[0], true);
        assertTrue(processor instanceof ResultSetProcessorHandThrough);

        // non-empty group-by and wildcard select, group by ignored
        groupByList.add(SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0"));
        spec = makeSpec(new SelectClauseSpecCompiled(wildcardSelect, false), null, groupByList, null, null, orderByList);
        processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null, new boolean[0], true);
        assertTrue(processor instanceof ResultSetProcessorSimple);
    }

    public void testGetProcessorAggregatingAll() throws Exception
    {
        // empty group-by but aggragating event properties in select clause (output per event), no having clause
        // and one or more properties in the select clause is not aggregated
        List<SelectClauseElementCompiled> selectList = SupportSelectExprFactory.makeAggregateMixed();
        StatementSpecCompiled spec = makeSpec(new SelectClauseSpecCompiled(selectList, false), null, groupByList, null, null, orderByList);
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null, new boolean[0], true);
        assertTrue(processor instanceof ResultSetProcessorAggregateAll);

        // test a case where a property is both aggregated and non-aggregated: select volume, sum(volume)
        selectList = SupportSelectExprFactory.makeAggregatePlusNoAggregate();
        spec = makeSpec(new SelectClauseSpecCompiled(selectList, false), null, groupByList, null, null, orderByList);
        processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null, new boolean[0], true);
        assertTrue(processor instanceof ResultSetProcessorAggregateAll);
    }

    public void testGetProcessorRowForAll() throws Exception
    {
        // empty group-by but aggragating event properties in select clause (output per event), no having clause
        // and all properties in the select clause are aggregated
        List<SelectClauseElementCompiled> selectList = SupportSelectExprFactory.makeAggregateSelectListWithProps();
        StatementSpecCompiled spec = makeSpec(new SelectClauseSpecCompiled(selectList, false), null, groupByList, null, null, orderByList);
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null, new boolean[0], true);
        assertTrue(processor instanceof ResultSetProcessorRowForAll);
    }

    public void testGetProcessorRowPerGroup() throws Exception
    {
        // with group-by and the non-aggregated event properties are all listed in the group by (output per group)
        // no having clause
        List<SelectClauseElementCompiled> selectList = SupportSelectExprFactory.makeAggregateMixed();
        groupByList.add(SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0"));
        StatementSpecCompiled spec = makeSpec(new SelectClauseSpecCompiled(selectList, false), null, groupByList, null, null, orderByList);
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null, new boolean[0], true);
        assertTrue(processor instanceof ResultSetProcessorRowPerGroup);
    }

    public void testGetProcessorAggregatingGrouped() throws Exception
    {
        // with group-by but either
        //      wildcard
        //      or one or more non-aggregated event properties are not in the group by (output per event)
        List<SelectClauseElementCompiled> selectList = SupportSelectExprFactory.makeAggregateMixed();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("string", "s0");
        selectList.add(new SelectClauseExprCompiledSpec(identNode, null, null));

        groupByList.add(SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0"));
        StatementSpecCompiled spec = makeSpec(new SelectClauseSpecCompiled(selectList, false), null, groupByList, null, null, orderByList);
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null, new boolean[0], true);
        assertTrue(processor instanceof ResultSetProcessorAggregateGrouped);
    }

    public void testGetProcessorInvalid() throws Exception
    {
        StatementSpecCompiled spec = makeSpec(new SelectClauseSpecCompiled(SupportSelectExprFactory.makeInvalidSelectList(), false), null, groupByList, null, null, orderByList);
        // invalid select clause
        try
        {
            ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService3Stream, null, new boolean[0], true);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }

        // invalid group-by
        groupByList.add(new ExprIdentNodeImpl("xxxx", "s0"));
        try
        {
            spec = makeSpec(new SelectClauseSpecCompiled(SupportSelectExprFactory.makeNoAggregateSelectListUnnamed(), false), null, groupByList, null, null, orderByList);
            ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService3Stream, null, new boolean[0], true);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }

        // Test group by having properties that are aggregated in select clause, should fail
        groupByList.clear();
        groupByList.add(SupportExprNodeFactory.makeSumAggregateNode());

        List<SelectClauseElementCompiled> selectList = new LinkedList<SelectClauseElementCompiled>();
        selectList.add(new SelectClauseExprCompiledSpec(SupportExprNodeFactory.makeSumAggregateNode(), null, null));

        try
        {
            spec = makeSpec(new SelectClauseSpecCompiled(selectList, false), null, groupByList, null, null, orderByList);
            ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService3Stream, null, new boolean[0], true);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }

    private StatementSpecCompiled makeSpec(SelectClauseSpecCompiled selectClauseSpec,
                                                  InsertIntoDesc insertIntoDesc,
                                               	  List<ExprNode> groupByNodes,
                                               	  ExprNode optionalHavingNode,
                                               	  OutputLimitSpec outputLimitSpec,
                                               	  List<OrderByItem> orderByList)
    {
        return new StatementSpecCompiled(null, // on trigger
                null,  // create win
                null,  // create index
                null,  // create var
                null,  // create schema
                insertIntoDesc,
                SelectClauseStreamSelectorEnum.ISTREAM_ONLY,
                selectClauseSpec,
                null,  // stream specs
                null,  // outer join
                null,
                groupByNodes,
                optionalHavingNode,
                outputLimitSpec,
                orderByList,
                null,
                null,
                null,
                null,
                new HashSet<String>(),
                new Annotation[0],
                null,
                null,
                null,
                null);
    }
}
