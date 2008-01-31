package com.espertech.esper.eql.core;

import junit.framework.TestCase;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.eql.expression.ExprIdentNode;
import com.espertech.esper.eql.expression.ExprNode;
import com.espertech.esper.eql.expression.ExprValidationException;
import com.espertech.esper.eql.spec.*;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventAdapterServiceImpl;
import com.espertech.esper.support.eql.SupportExprNodeFactory;
import com.espertech.esper.support.eql.SupportSelectExprFactory;
import com.espertech.esper.support.eql.SupportStreamTypeSvc1Stream;
import com.espertech.esper.support.eql.SupportStreamTypeSvc3Stream;
import com.espertech.esper.support.view.SupportStatementContextFactory;

import java.util.LinkedList;
import java.util.List;

public class TestResultSetProcessorFactory extends TestCase
{
    private StreamTypeService typeService1Stream;
    private StreamTypeService typeService3Stream;
    private List<ExprNode> groupByList;
    private EventAdapterService eventAdapterService;
    private List<OrderByItem> orderByList;
    private MethodResolutionService methodResolutionService;
    private StatementContext stmtContext;

    public void setUp()
    {
        typeService1Stream = new SupportStreamTypeSvc1Stream();
        typeService3Stream = new SupportStreamTypeSvc3Stream();
        groupByList = new LinkedList<ExprNode>();
        eventAdapterService = new EventAdapterServiceImpl();
        orderByList = new LinkedList<OrderByItem>();
        stmtContext = SupportStatementContextFactory.makeContext();
    }

    public void testGetProcessorNoProcessorRequired() throws Exception
    {
        // single stream, empty group-by and wildcard select, no having clause, no need for any output processing
        List<SelectClauseElementCompiled> wildcardSelect = new LinkedList<SelectClauseElementCompiled>();
        wildcardSelect.add(new SelectClauseElementWildcard());
        StatementSpecCompiled spec = makeSpec(new SelectClauseSpecCompiled(wildcardSelect), null, groupByList, null, null, orderByList);
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null);
        assertTrue(processor instanceof ResultSetProcessorHandThrough);
    }

    public void testGetProcessorSimpleSelect() throws Exception
    {
        // empty group-by and no event properties aggregated in select clause (wildcard), no having clause
        List<SelectClauseElementCompiled> wildcardSelect = new LinkedList<SelectClauseElementCompiled>();
        wildcardSelect.add(new SelectClauseElementWildcard());
        StatementSpecCompiled spec = makeSpec(new SelectClauseSpecCompiled(wildcardSelect), null, groupByList, null, null, orderByList);
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService3Stream, null);
        assertTrue(processor instanceof ResultSetProcessorHandThrough);

        // empty group-by with select clause elements
        List<SelectClauseElementCompiled> selectList = SupportSelectExprFactory.makeNoAggregateSelectListUnnamed();
        spec = makeSpec(new SelectClauseSpecCompiled(selectList), null, groupByList, null, null, orderByList);
        processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null);
        assertTrue(processor instanceof ResultSetProcessorHandThrough);

        // non-empty group-by and wildcard select, group by ignored
        groupByList.add(SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0"));
        spec = makeSpec(new SelectClauseSpecCompiled(wildcardSelect), null, groupByList, null, null, orderByList);
        processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null);
        assertTrue(processor instanceof ResultSetProcessorSimple);
    }

    public void testGetProcessorAggregatingAll() throws Exception
    {
        // empty group-by but aggragating event properties in select clause (output per event), no having clause
        // and one or more properties in the select clause is not aggregated
        List<SelectClauseElementCompiled> selectList = SupportSelectExprFactory.makeAggregateMixed();
        StatementSpecCompiled spec = makeSpec(new SelectClauseSpecCompiled(selectList), null, groupByList, null, null, orderByList);
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null);
        assertTrue(processor instanceof ResultSetProcessorAggregateAll);

        // test a case where a property is both aggregated and non-aggregated: select volume, sum(volume)
        selectList = SupportSelectExprFactory.makeAggregatePlusNoAggregate();
        spec = makeSpec(new SelectClauseSpecCompiled(selectList), null, groupByList, null, null, orderByList);
        processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null);
        assertTrue(processor instanceof ResultSetProcessorAggregateAll);
    }

    public void testGetProcessorRowForAll() throws Exception
    {
        // empty group-by but aggragating event properties in select clause (output per event), no having clause
        // and all properties in the select clause are aggregated
        List<SelectClauseElementCompiled> selectList = SupportSelectExprFactory.makeAggregateSelectListWithProps();
        StatementSpecCompiled spec = makeSpec(new SelectClauseSpecCompiled(selectList), null, groupByList, null, null, orderByList);
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null);
        assertTrue(processor instanceof ResultSetProcessorRowForAll);
    }

    public void testGetProcessorRowPerGroup() throws Exception
    {
        // with group-by and the non-aggregated event properties are all listed in the group by (output per group)
        // no having clause
        List<SelectClauseElementCompiled> selectList = SupportSelectExprFactory.makeAggregateMixed();
        groupByList.add(SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0"));
        StatementSpecCompiled spec = makeSpec(new SelectClauseSpecCompiled(selectList), null, groupByList, null, null, orderByList);
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null);
        assertTrue(processor instanceof ResultSetProcessorRowPerGroup);
    }

    public void testGetProcessorAggregatingGrouped() throws Exception
    {
        // with group-by but either
        //      wildcard
        //      or one or more non-aggregated event properties are not in the group by (output per event)
        List<SelectClauseElementCompiled> selectList = SupportSelectExprFactory.makeAggregateMixed();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("string", "s0");
        selectList.add(new SelectClauseExprCompiledSpec(identNode, null));

        groupByList.add(SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0"));
        StatementSpecCompiled spec = makeSpec(new SelectClauseSpecCompiled(selectList), null, groupByList, null, null, orderByList);
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService1Stream, null);
        assertTrue(processor instanceof ResultSetProcessorAggregateGrouped);
    }

    public void testGetProcessorInvalid() throws Exception
    {
        StatementSpecCompiled spec = makeSpec(new SelectClauseSpecCompiled(SupportSelectExprFactory.makeInvalidSelectList()), null, groupByList, null, null, orderByList);
        // invalid select clause
        try
        {
            ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService3Stream, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }

        // invalid group-by
        groupByList.add(new ExprIdentNode("xxxx", "s0"));
        try
        {
            spec = makeSpec(new SelectClauseSpecCompiled(SupportSelectExprFactory.makeNoAggregateSelectListUnnamed()), null, groupByList, null, null, orderByList);
            ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService3Stream, null);
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
        selectList.add(new SelectClauseExprCompiledSpec(SupportExprNodeFactory.makeSumAggregateNode(), null));

        try
        {
            spec = makeSpec(new SelectClauseSpecCompiled(selectList), null, groupByList, null, null, orderByList);
            ResultSetProcessorFactory.getProcessor(spec, stmtContext, typeService3Stream, null);
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
                null,  // create var
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
                false);
    }
}
