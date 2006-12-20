package net.esper.eql.view;

import net.esper.support.core.SupportInternalEventRouter;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.view.SupportMapView;
import net.esper.event.EventBean;
import junit.framework.TestCase;

public class TestInternalRouteView extends TestCase
{
    private InternalRouteView viewIStream;
    private InternalRouteView viewRStream;
    private SupportInternalEventRouter supportRouter;
    private SupportMapView childView = new SupportMapView();

    public void setUp()
    {
        supportRouter = new SupportInternalEventRouter();

        viewIStream = new InternalRouteView(true, supportRouter);
        viewRStream = new InternalRouteView(false, supportRouter);

        childView = new SupportMapView();
        viewIStream.addView(childView);
    }

    public void testUpdate()
    {
        EventBean events[] = SupportEventBeanFactory.makeEvents(new String[] {"a", "b"});

        viewIStream.update(events, null);
        assertEquals(2, supportRouter.getRouted().size());
        supportRouter.reset();
        assertEquals(events, childView.getLastNewData());

        viewIStream.update(null, events);
        assertEquals(0, supportRouter.getRouted().size());
        supportRouter.reset();

        viewRStream.update(null, events);
        assertEquals(2, supportRouter.getRouted().size());
        supportRouter.reset();

        viewRStream.update(events, null);
        assertEquals(0, supportRouter.getRouted().size());
        supportRouter.reset();
    }
}
