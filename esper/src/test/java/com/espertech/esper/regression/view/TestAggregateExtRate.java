package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.TimerTask;

public class TestAggregateExtRate extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    // rate implementation does not require a data window (may have one)
    // advantage: not retaining events, only timestamp data points
    // disadvantage: output rate limiting without snapshot may be less accurate rate
    public void testRateDataNonWindowed()
    {
        sendTimer(0);

        String epl = "select rate(10) as myrate from SupportBean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        runAssertion();

        stmt.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        assertEquals(epl, model.toEPL());
        
        runAssertion();

        tryInvalid("select rate() from SupportBean",
                "Error starting statement: The rate aggregation function minimally requires a numeric constant or expression as a parameter. [select rate() from SupportBean]");
        tryInvalid("select rate(true) from SupportBean",
                "Error starting statement: The rate aggregation function requires a numeric constant or time period as the first parameter in the constant-value notation [select rate(true) from SupportBean]");
    }

    public void testRateDataWindowed()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String[] fields = "myrate,myqtyrate".split(",");
        String viewExpr = "select RATE(longPrimitive) as myrate, RATE(longPrimitive, intPrimitive) as myqtyrate from SupportBean.win:length(3)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(viewExpr);
        stmt.addListener(listener);

        sendEvent(1000, 10);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});

        sendEvent(1200, 0);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});

        sendEvent(1300, 0);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, null});

        sendEvent(1500, 14);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3*1000/500d, 14*1000/500d});

        sendEvent(2000, 11);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3*1000/800d, 25*1000/800d});

        tryInvalid("select rate(longPrimitive) as myrate from SupportBean",
                "Error starting statement: The rate aggregation function in the timestamp-property notation requires data windows [select rate(longPrimitive) as myrate from SupportBean]");
        tryInvalid("select rate(current_timestamp) as myrate from SupportBean.win:time(20)",
                "Error starting statement: The rate aggregation function does not allow the current engine timestamp as a parameter [select rate(current_timestamp) as myrate from SupportBean.win:time(20)]");
        tryInvalid("select rate(string) as myrate from SupportBean.win:time(20)",
                "Error starting statement: The rate aggregation function requires a property or expression returning a non-constant long-type value as the first parameter in the timestamp-property notation [select rate(string) as myrate from SupportBean.win:time(20)]");
        tryInvalid("select rate(string) as myrate from SupportBean.win:time(20)",
                "Error starting statement: The rate aggregation function requires a property or expression returning a non-constant long-type value as the first parameter in the timestamp-property notation [select rate(string) as myrate from SupportBean.win:time(20)]");
    }

    private void runAssertion() {
        String[] fields = "myrate".split(",");

        sendTimer(1000); sendEvent();
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null});

        sendTimer(1200); sendEvent();
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null});

        sendTimer(1600); sendEvent();
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null});

        sendTimer(1600); sendEvent();
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null});

        sendTimer(9000); sendEvent();
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null});

        sendTimer(9200); sendEvent();
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null});

        sendTimer(10999); sendEvent();
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null});

        sendTimer(11100); sendEvent();
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {0.7});

        sendTimer(11101); sendEvent();
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {0.8});

        sendTimer(11200); sendEvent();
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {0.8});

        sendTimer(11600); sendEvent();
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {0.7});
    }

    private void tryInvalid(String epl, String message) {
        try
        {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }

    /** Comment-in for rate testing with threading */
    /*
    public void testRateThreaded() throws Exception {

        Configuration config = new Configuration();
        config.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        RateSendRunnable runnable = new RateSendRunnable(epService.getEPRuntime());
        ScheduledThreadPoolExecutor timer = new ScheduledThreadPoolExecutor(1);

        //String viewExpr = "select RATE(longPrimitive) as myrate from SupportBean.win:time(10) output every 1 sec";
        String viewExpr = "select RATE(10) as myrate from SupportBean output snapshot every 1 sec";
        EPStatement stmt = epService.getEPAdministrator().createEPL(viewExpr);
        stmt.addListener(new UpdateListener() {
            public void update(EventBean[] newEvents, EventBean[] oldEvents) {
                System.out.println(newEvents[0].get("myrate"));                
            }
        });

        long rateDelay = 133;   // <== change here
        ScheduledFuture<?> future = timer.scheduleAtFixedRate(runnable, 0, rateDelay, TimeUnit.MILLISECONDS);
        Thread.sleep(2 * 60 * 1000);
        future.cancel(true);
    }
    */

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private void sendEvent(long longPrimitive, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setLongPrimitive(longPrimitive);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendEvent()
    {
        SupportBean bean = new SupportBean();
        epService.getEPRuntime().sendEvent(bean);
    }

    public static class RateSendRunnable extends TimerTask {

        private final EPRuntime runtime;

        public RateSendRunnable(EPRuntime runtime) {
            this.runtime = runtime;
        }

        public void run() {
            SupportBean bean = new SupportBean();
            bean.setLongPrimitive(System.currentTimeMillis());
            runtime.sendEvent(bean);
        }
    }
}
