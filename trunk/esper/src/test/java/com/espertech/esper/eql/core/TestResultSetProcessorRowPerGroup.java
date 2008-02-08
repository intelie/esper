package com.espertech.esper.eql.core;

import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.eql.expression.ExprNode;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.eql.SupportAggregationService;
import com.espertech.esper.support.eql.SupportExprNodeFactory;
import com.espertech.esper.support.eql.SupportSelectExprFactory;
import com.espertech.esper.support.eql.SupportStreamTypeSvc1Stream;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

public class TestResultSetProcessorRowPerGroup extends TestCase
{
    private ResultSetProcessorRowPerGroup processor;
    private SupportAggregationService supportAggregationService;

    public void setUp() throws Exception
    {
        SelectExprProcessor selectProcessor = new SelectExprEvalProcessor(SupportSelectExprFactory.makeSelectListFromIdent("string", "s0"),
        		null, false, new SupportStreamTypeSvc1Stream(), SupportEventAdapterService.getService());
        supportAggregationService = new SupportAggregationService();

        List<ExprNode> groupKeyNodes = new LinkedList<ExprNode>();
        groupKeyNodes.add(SupportExprNodeFactory.makeIdentNode("intPrimitive", "s0"));
        groupKeyNodes.add(SupportExprNodeFactory.makeIdentNode("intBoxed", "s0"));

        processor = new ResultSetProcessorRowPerGroup(selectProcessor, null, supportAggregationService, groupKeyNodes, null, true);
    }

    public void testProcess()
    {
        EventBean[] newData = new EventBean[] {makeEvent(1, 2), makeEvent(3, 4)};
        EventBean[] oldData = new EventBean[] {makeEvent(1, 2), makeEvent(1, 10)};

        UniformPair<EventBean[]> result = processor.processViewResult(newData, oldData, false);

        assertEquals(2, supportAggregationService.getEnterList().size());
        assertEquals(2, supportAggregationService.getLeaveList().size());

        assertEquals(3, result.getFirst().length);
        assertEquals(3, result.getSecond().length);
    }

    private EventBean makeEvent(int intPrimitive, int intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        return SupportEventBeanFactory.createObject(bean);
    }
}
