package com.espertech.esper.epl.core;

import java.util.List;

import junit.framework.TestCase;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.epl.SupportSelectExprFactory;
import com.espertech.esper.support.epl.SupportStreamTypeSvc1Stream;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.epl.spec.InsertIntoDesc;
import com.espertech.esper.epl.spec.SelectClauseExprCompiledSpec;

public class TestSelectExprEvalProcessor extends TestCase
{
    private SelectExprEvalProcessor methodOne;
    private SelectExprEvalProcessor methodTwo;

    public void setUp() throws Exception
    {
        List<SelectClauseExprCompiledSpec> selectList = SupportSelectExprFactory.makeNoAggregateSelectList();
        EventAdapterService eventAdapterService = SupportEventAdapterService.getService();

        methodOne = new SelectExprEvalProcessor(selectList, null, false, new SupportStreamTypeSvc1Stream(), eventAdapterService);

        InsertIntoDesc insertIntoDesc = new InsertIntoDesc(true, "Hello");
        insertIntoDesc.add("a");
        insertIntoDesc.add("b");

        methodTwo = new SelectExprEvalProcessor(selectList, insertIntoDesc, false, new SupportStreamTypeSvc1Stream(), eventAdapterService);
    }

    public void testGetResultEventType()
    {
        EventType type = methodOne.getResultEventType();
        ArrayAssertionUtil.assertEqualsAnyOrder(type.getPropertyNames(), new String[] {"resultOne", "resultTwo"});
        assertEquals(Double.class, type.getPropertyType("resultOne"));
        assertEquals(Integer.class, type.getPropertyType("resultTwo"));

        type = methodTwo.getResultEventType();
        ArrayAssertionUtil.assertEqualsAnyOrder(type.getPropertyNames(), new String[] {"a", "b"});
        assertEquals(Double.class, type.getPropertyType("a"));
        assertEquals(Integer.class, type.getPropertyType("b"));
    }

    public void testProcess()
    {
        EventBean[] events = new EventBean[] {makeEvent(8.8, 3, 4)};

        EventBean result = methodOne.process(events, true, false);
        assertEquals(8.8d, result.get("resultOne"));
        assertEquals(12, result.get("resultTwo"));

        result = methodTwo.process(events, true, false);
        assertEquals(8.8d, result.get("a"));
        assertEquals(12, result.get("b"));
        assertSame(result.getEventType(), methodTwo.getResultEventType());
    }

    private EventBean makeEvent(double doubleBoxed, int intPrimitive, int intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setDoubleBoxed(doubleBoxed);
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        return SupportEventBeanFactory.createObject(bean);
    }
}
