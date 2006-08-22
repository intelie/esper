package net.esper.eql.expression;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.event.EventAdapterService;
import net.esper.support.bean.SupportBean;
import net.esper.support.eql.SupportSelectExprFactory;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.event.SupportEventAdapterService;

public class TestSelectExprEvalProcessor extends TestCase
{
    private SelectExprEvalProcessor methodOne;
    private SelectExprEvalProcessor methodTwo;

    public void setUp() throws Exception
    {
        List<SelectExprElement> selectList = SupportSelectExprFactory.makeNoAggregateSelectList();
        EventAdapterService eventAdapterService = SupportEventAdapterService.getService();

        methodOne = new SelectExprEvalProcessor(selectList, null, eventAdapterService);

        InsertIntoDesc insertIntoDesc = new InsertIntoDesc(true, "Hello");
        insertIntoDesc.add("a");
        insertIntoDesc.add("b");

        methodTwo = new SelectExprEvalProcessor(selectList, insertIntoDesc, eventAdapterService);
    }

    public void testGetResultEventType()
    {
        EventType type = methodOne.getResultEventType();
        assertTrue(Arrays.equals(type.getPropertyNames(), new String[] {"s0.doubleBoxed", "result"}));
        assertEquals(Double.class, type.getPropertyType("s0.doubleBoxed"));
        assertEquals(Integer.class, type.getPropertyType("result"));

        type = methodTwo.getResultEventType();
        assertTrue(Arrays.equals(type.getPropertyNames(), new String[] {"a", "b"}));
        assertEquals(Double.class, type.getPropertyType("a"));
        assertEquals(Integer.class, type.getPropertyType("b"));
    }

    public void testProcess()
    {
        EventBean[] events = new EventBean[] {makeEvent(8.8, 3, 4)};

        EventBean result = methodOne.process(events);
        assertEquals(8.8d, result.get("s0.doubleBoxed"));
        assertEquals(12, result.get("result"));

        result = methodTwo.process(events);
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
