package net.esper.eql.core;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.event.EventAdapterService;
import net.esper.support.bean.SupportBean;
import net.esper.support.eql.SupportSelectExprFactory;
import net.esper.support.eql.SupportStreamTypeSvc1Stream;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.eql.spec.InsertIntoDesc;
import net.esper.eql.spec.SelectExprElementCompiledSpec;

public class TestSelectExprEvalProcessor extends TestCase
{
    private SelectExprEvalProcessor methodOne;
    private SelectExprEvalProcessor methodTwo;

    public void setUp() throws Exception
    {
        List<SelectExprElementCompiledSpec> selectList = SupportSelectExprFactory.makeNoAggregateSelectList();
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

        EventBean result = methodOne.process(events, true);
        assertEquals(8.8d, result.get("resultOne"));
        assertEquals(12, result.get("resultTwo"));

        result = methodTwo.process(events, true);
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
