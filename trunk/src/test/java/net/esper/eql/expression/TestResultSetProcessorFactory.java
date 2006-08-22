package net.esper.eql.expression;

import net.esper.support.eql.SupportSelectExprFactory;
import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.eql.SupportStreamTypeSvc1Stream;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.event.EventAdapterService;
import net.esper.event.EventAdapterServiceImpl;
import net.esper.collection.Pair;

import junit.framework.TestCase;

import java.util.List;
import java.util.LinkedList;

public class TestResultSetProcessorFactory extends TestCase
{
    private StreamTypeService typeService1Stream;
    private StreamTypeService typeService3Stream;
    private List<ExprNode> groupByList;
    private EventAdapterService eventAdapterService;
    private List<Pair<ExprNode, Boolean>> orderByList;

    public void setUp()
    {
        typeService1Stream = new SupportStreamTypeSvc1Stream();
        typeService3Stream = new SupportStreamTypeSvc3Stream();
        groupByList = new LinkedList<ExprNode>();
        eventAdapterService = new EventAdapterServiceImpl();
        orderByList = new LinkedList<Pair<ExprNode, Boolean>>();
    }

    public void testGetProcessorNoProcessorRequired() throws Exception
    {
        // single stream, empty group-by and wildcard select, no having clause, no need for any output processing
        List<SelectExprElement> wildcardSelect = new LinkedList<SelectExprElement>();
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(wildcardSelect, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);
        assertNull(processor);
    }

    public void testGetProcessorSimpleSelect() throws Exception
    {
        // empty group-by and no event properties aggregated in select clause (wildcard), no having clause
        List<SelectExprElement> wildcardSelect = new LinkedList<SelectExprElement>();
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(wildcardSelect, null, groupByList, null, null, orderByList, typeService3Stream, eventAdapterService, null);
        assertTrue(processor instanceof ResultSetProcessorSimple);

        // empty group-by with select clause elements
        List<SelectExprElement> selectList = SupportSelectExprFactory.makeNoAggregateSelectList();
        processor = ResultSetProcessorFactory.getProcessor(selectList, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);
        assertTrue(processor instanceof ResultSetProcessorSimple);

        // non-empty group-by and wildcard select, group by ignored
        groupByList.add(SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0"));
        processor = ResultSetProcessorFactory.getProcessor(wildcardSelect, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);
        assertTrue(processor instanceof ResultSetProcessorSimple);
    }

    public void testGetProcessorAggregatingAll() throws Exception
    {
        // empty group-by but aggragating event properties in select clause (output per event), no having clause
        // and one or more properties in the select clause is not aggregated
        List<SelectExprElement> selectList = SupportSelectExprFactory.makeAggregateMixed();
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(selectList, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);
        assertTrue(processor instanceof ResultSetProcessorAggregateAll);

        // test a case where a property is both aggregated and non-aggregated: select volume, sum(volume)
        selectList = SupportSelectExprFactory.makeAggregatePlusNoAggregate();
        processor = ResultSetProcessorFactory.getProcessor(selectList, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);
        assertTrue(processor instanceof ResultSetProcessorAggregateAll);
    }

    public void testGetProcessorRowForAll() throws Exception
    {
        // empty group-by but aggragating event properties in select clause (output per event), no having clause
        // and all properties in the select clause are aggregated
        List<SelectExprElement> selectList = SupportSelectExprFactory.makeAggregateSelectListWithProps();
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(selectList, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);
        assertTrue(processor instanceof ResultSetProcessorRowForAll);
    }

    public void testGetProcessorRowPerGroup() throws Exception
    {
        // with group-by and the non-aggregated event properties are all listed in the group by (output per group)
        // no having clause
        List<SelectExprElement> selectList = SupportSelectExprFactory.makeAggregateMixed();
        groupByList.add(SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0"));
        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(selectList, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);
        assertTrue(processor instanceof ResultSetProcessorRowPerGroup);
    }

    public void testGetProcessorAggregatingGrouped() throws Exception
    {
        // with group-by but either
        //      wildcard
        //      or one or more non-aggregated event properties are not in the group by (output per event)
        List<SelectExprElement> selectList = SupportSelectExprFactory.makeAggregateMixed();
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("string", "s0");
        selectList.add(new SelectExprElement(identNode, null));

        groupByList.add(SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0"));

        ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(selectList, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);

        assertTrue(processor instanceof ResultSetProcessorAggregateGrouped);
    }

    public void testGetProcessorInvalid() throws Exception
    {
        // invalid select clause
        try
        {
            ResultSetProcessorFactory.getProcessor(SupportSelectExprFactory.makeInvalidSelectList(), null, groupByList, null, null, orderByList, typeService3Stream, eventAdapterService, null);
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
            ResultSetProcessorFactory.getProcessor(SupportSelectExprFactory.makeNoAggregateSelectList(), null, groupByList, null, null, orderByList, typeService3Stream, eventAdapterService, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }

        // Test group by having properties that are aggregated in select clause, should fail
        groupByList.clear();
        groupByList.add(SupportExprNodeFactory.makeSumAggregateNode());

        List<SelectExprElement> selectList = new LinkedList<SelectExprElement>();
        selectList.add(new SelectExprElement(SupportExprNodeFactory.makeSumAggregateNode(), null));

        try
        {
            ResultSetProcessorFactory.getProcessor(selectList, null, groupByList, null, null, orderByList, typeService3Stream, eventAdapterService, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }
}
