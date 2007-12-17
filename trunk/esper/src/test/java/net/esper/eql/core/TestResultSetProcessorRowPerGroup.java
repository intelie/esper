package net.esper.eql.core;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import net.esper.collection.Pair;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.eql.SupportAggregationService;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.support.eql.SupportSelectExprFactory;
import net.esper.support.eql.SupportStreamTypeSvc1Stream;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.eql.core.ResultSetProcessorRowPerGroup;
import net.esper.eql.core.SelectExprEvalProcessor;
import net.esper.eql.core.SelectExprProcessor;
import net.esper.eql.expression.ExprNode;

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

        processor = new ResultSetProcessorRowPerGroup(selectProcessor, null, supportAggregationService, groupKeyNodes, null, false, false);
    }

    public void testProcess()
    {
        EventBean[] newData = new EventBean[] {makeEvent(1, 2), makeEvent(3, 4)};
        EventBean[] oldData = new EventBean[] {makeEvent(1, 2), makeEvent(1, 10)};

        Pair<EventBean[], EventBean[]> result = processor.processViewResult(newData, oldData);

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
