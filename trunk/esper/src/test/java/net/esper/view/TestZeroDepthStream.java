package net.esper.view;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.view.SupportSchemaNeutralView;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.event.SupportEventBeanFactory;

public class TestZeroDepthStream extends TestCase
{
    private ZeroDepthStream stream;
    private SupportSchemaNeutralView testChildView;
    private EventType eventType;

    private EventBean eventBean;

    public void setUp()
    {
        eventType = SupportEventTypeFactory.createBeanType(SupportBean_A.class);

        stream = new ZeroDepthStream(eventType);

        testChildView = new SupportSchemaNeutralView();
        stream.addView(testChildView);
        testChildView.setParent(stream);

        eventBean = SupportEventBeanFactory.createObject(new SupportBean_A("a1"));
    }

    public void testInsert()
    {
        testChildView.clearLastNewData();
        stream.insert(eventBean);

        assertTrue(testChildView.getLastNewData() != null);
        assertEquals(1, testChildView.getLastNewData().length);
        assertEquals(eventBean, testChildView.getLastNewData()[0]);

        // Remove view
        testChildView.clearLastNewData();
        stream.removeView(testChildView);
        stream.insert(eventBean);
        assertTrue(testChildView.getLastNewData() == null);
    }
}
