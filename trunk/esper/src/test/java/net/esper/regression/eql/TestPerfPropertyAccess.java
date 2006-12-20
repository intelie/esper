package net.esper.regression.eql;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanCombinedProps;
import net.esper.support.util.SupportUpdateListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestPerfPropertyAccess extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        updateListener = new SupportUpdateListener();
    }

    public void testPerfPropertyAccess()
    {
        String methodName = ".testPerfPropertyAccess";

        String joinStatement = "select * from " +
                SupportBeanCombinedProps.class.getName() + ".win:length(1)" +
            " where indexed[0].mapped('a').value = 'dummy'";

        joinView = epService.getEPAdministrator().createEQL(joinStatement);
        joinView.addListener(updateListener);

        // Send events for each stream
        SupportBeanCombinedProps event = SupportBeanCombinedProps.makeDefaultBean();
        log.info(methodName + " Sending events");

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++)
        {
            sendEvent(event);
        }
        log.info(methodName + " Done sending events");

        long endTime = System.currentTimeMillis();
        log.info(methodName + " delta=" + (endTime - startTime));

        // Stays at 250, below 500ms
        assertTrue((endTime - startTime) < 1000);
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }

    private static final Log log = LogFactory.getLog(TestPerfPropertyAccess.class);
}
