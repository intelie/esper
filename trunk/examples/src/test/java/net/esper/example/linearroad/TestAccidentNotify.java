package net.esper.example.linearroad;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.Configuration;
import net.esper.client.time.TimerControlEvent;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestAccidentNotify extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;

    public void setUp()
    {
        // This code runs as part of the automated regression test suite; Therefore disable internal timer theading to safe resources
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        epService = EPServiceProviderManager.getDefaultProvider(config);

        String carLocEvent = CarLocEvent.class.getName();

        /**
         * An accident has occurred if a car reports the same location four consecutive times.
         * When an accident is detected by the system, all the cars in 5 upstream segments have to be
         * notified of the accident.
         *
         * TODO: How does the accident get cleared?
         * TODO: If 2 cars report the same loc 4 times, then this joins twice
         */
        String joinStatement = "select * from " +
            carLocEvent + ".std:groupby(carId).win:length(4).std:groupby(expressway, direction, segment).std:size() as accSeg," +
            carLocEvent + ".win:time(30 sec).std:unique(carId) as curCarSeg" +
                " where accSeg.size >= 4" +
                "   and accSeg.expressway = curCarSeg.expressway" +
                "   and accSeg.direction = curCarSeg.direction" +
                "   and (" +
                        "(accSeg.direction=0 " +
                        " and curCarSeg.segment < accSeg.segment" +
                        " and curCarSeg.segment > accSeg.segment - 5)" +
                    " or " +
                        "(accSeg.direction=1 " +
                        " and curCarSeg.segment > accSeg.segment" +
                        " and curCarSeg.segment < accSeg.segment + 5)" +
                    ")";

        joinView = epService.getEPAdministrator().createEQL(joinStatement);
        joinView.addListener(new AccidentNotifyListener());
    }

    public void testSendEvents()
    {
        sendEvent(101, 10, 0, 30);
        sendEvent(101, 10, 0, 30);
        sendEvent(101, 10, 0, 30);
        sendEvent(101, 10, 0, 30);
        sendEvent(102, 10, 0, 29);
        sendEvent(102, 10, 0, 26);
        sendEvent(102, 10, 0, 31);
        sendEvent(102, 10, 0, 25);

        sendEvent(115, 8, 1, 40);
        sendEvent(116, 8, 1, 40);
        sendEvent(115, 8, 1, 40);
        sendEvent(115, 8, 1, 40);
        sendEvent(115, 8, 1, 40);
        sendEvent(116, 8, 1, 40);
        sendEvent(116, 8, 1, 40);
        sendEvent(116, 8, 1, 40);
        sendEvent(117, 8, 1, 45);
        sendEvent(117, 8, 1, 44);

    }

    private void sendEvent(long carId, int expressway, int direction, int segment)
    {
        log.info(".sendEvent carId=" + carId +
                " expressway=" + expressway +
                " direction=" + direction +
                " segment=" + segment);
        CarLocEvent event = new CarLocEvent(carId, 50, expressway, 1, direction, segment);  // same speed and lane
        epService.getEPRuntime().sendEvent(event);
    }

    private static final Log log = LogFactory.getLog(TestAccidentNotify.class);
}
