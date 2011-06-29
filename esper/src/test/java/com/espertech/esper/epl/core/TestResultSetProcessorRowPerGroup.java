package com.espertech.esper.epl.core;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.epl.core.eval.SelectExprStreamDesc;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.spec.SelectClauseStreamCompiledSpec;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.epl.SupportAggregationService;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.support.epl.SupportSelectExprFactory;
import com.espertech.esper.support.epl.SupportStreamTypeSvc1Stream;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import junit.framework.TestCase;

import java.util.Collections;
import java.util.HashSet;

public class TestResultSetProcessorRowPerGroup extends TestCase
{
    private ResultSetProcessorRowPerGroup processor;
    private SupportAggregationService supportAggregationService;

    public void setUp() throws Exception
    {
        SelectExprEventTypeRegistry selectExprEventTypeRegistry = new SelectExprEventTypeRegistry(new HashSet<String>());
        SelectExprProcessorHelper factory = new SelectExprProcessorHelper(Collections.<Integer>emptyList(), SupportSelectExprFactory.makeSelectListFromIdent("string", "s0"),
        		Collections.<SelectExprStreamDesc>emptyList(), null, false, new SupportStreamTypeSvc1Stream(), SupportEventAdapterService.getService(), null, selectExprEventTypeRegistry, null, null, null);
        SelectExprProcessor selectProcessor = factory.getEvaluator();
        supportAggregationService = new SupportAggregationService();

        ExprEvaluator[] groupKeyNodes = new ExprEvaluator[2];
        groupKeyNodes[0] = SupportExprNodeFactory.makeIdentNode("intPrimitive", "s0").getExprEvaluator();
        groupKeyNodes[1] = SupportExprNodeFactory.makeIdentNode("intBoxed", "s0").getExprEvaluator();

        processor = new ResultSetProcessorRowPerGroup(selectProcessor, null, supportAggregationService, groupKeyNodes, null, true, false, null, null);
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
