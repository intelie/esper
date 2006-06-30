package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.event.EventType;
import net.esper.event.EventBean;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestPerRowFunc extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testGetEventType()
    {
        setUpMinMax();
        EventType type = selectTestView.getEventType();
        log.debug(".testGetEventType properties=" + Arrays.toString(type.getPropertyNames()));
        assertEquals(Long.class, type.getPropertyType("myMax"));
        assertEquals(Long.class, type.getPropertyType("myMin"));
        assertEquals(Long.class, type.getPropertyType("myMinEx"));
        assertEquals(Long.class, type.getPropertyType("myMaxEx"));
    }

    public void testWindowStats()
    {
        setUpMinMax();
        testListener.reset();

        sendEvent(10, 20, (short)4);
        EventBean received = testListener.getAndResetLastNewData()[0];
        assertEquals(20L, received.get("myMax"));
        assertEquals(10L, received.get("myMin"));
        assertEquals(4L, received.get("myMinEx"));
        assertEquals(20L, received.get("myMaxEx"));

        sendEvent(-10, -20, (short)-30);
        received = testListener.getAndResetLastNewData()[0];
        assertEquals(-10L, received.get("myMax"));
        assertEquals(-20L, received.get("myMin"));
        assertEquals(-30L, received.get("myMinEx"));
        assertEquals(-10L, received.get("myMaxEx"));
    }

    private void setUpMinMax()
    {
        String viewExpr = "select max(longBoxed, intBoxed) as myMax, " +
                                 "max(longBoxed, intBoxed, shortBoxed) as myMaxEx," +
                                 "min(longBoxed, intBoxed) as myMin," +
                                 "min(longBoxed, intBoxed, shortBoxed) as myMinEx" +
                          " from " + SupportBean.class.getName() + ".win:length(3) ";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);
    }

    private void sendEvent(long longBoxed, int intBoxed, short shortBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setLongBoxed(longBoxed);
        bean.setIntBoxed(intBoxed);
        bean.setShortBoxed(shortBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestViewSelectExprClause.class);
}
