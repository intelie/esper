package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;

public class TestMultiOpAndJoin extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;
    private SupportUpdateListener updateListener;

    private final int[][] eventData = {{1, 100},
                                       {2, 100},
                                       {1, 200},
                                       {2, 200}};
    private SupportBean eventsA[] = new SupportBean[eventData.length];
    private SupportBean eventsB[] = new SupportBean[eventData.length];

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String eventClass = SupportBean.class.getName();

        String joinStatement = "select * from " +
            eventClass + "(string='A').win:length(3) as streamA," +
            eventClass + "(string='B').win:length(3) as streamB" +
            " where streamA.intPrimitive = streamB.intPrimitive " +
               "and streamA.intBoxed = streamB.intBoxed";

        joinView = epService.getEPAdministrator().createEQL(joinStatement);
        joinView.addListener(updateListener);

        for (int i = 0; i < eventData.length; i++)
        {
            eventsA[i] = new SupportBean();
            eventsA[i].setString("A");
            eventsA[i].setIntPrimitive(eventData[i][0]);
            eventsA[i].setIntBoxed(eventData[i][1]);

            eventsB[i] = new SupportBean();
            eventsB[i].setString("B");
            eventsB[i].setIntPrimitive(eventData[i][0]);
            eventsB[i].setIntBoxed(eventData[i][1]);
        }
    }

    public void testJoin()
    {
        sendEvent(eventsA[0]);
        sendEvent(eventsB[1]);
        sendEvent(eventsB[2]);
        sendEvent(eventsB[3]);
        assertNull(updateListener.getLastNewData());    // No events expected
    }

    public void testEventType()
    {
        assertEquals(SupportBean.class, joinView.getEventType().getPropertyType("streamA"));
        assertEquals(SupportBean.class, joinView.getEventType().getPropertyType("streamB"));
        assertEquals(2, joinView.getEventType().getPropertyNames().length);
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }
}
