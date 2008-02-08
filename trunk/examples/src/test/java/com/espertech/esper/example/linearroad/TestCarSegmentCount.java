package com.espertech.esper.example.linearroad;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.example.support.ArrayAssertionUtil;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestCarSegmentCount extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;
    private CarSegmentCountListener listener;

    // TODO: select clause may contain a list of fields and functions
    // TODO: Check for group/unique by property check - index optimization
    // TODO: write doc

    public void setUp()
    {
        // This code runs as part of the automated regression test suite; Therefore disable internal timer theading to safe resources
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String carLocEvent = CarLocEvent.class.getName();

        // Average speed of the cars in each segment over the last 5 minutes (300 sec).
        // Same car id can count twice, thats ok.

        // Number of cars currently in each segment (volume per segment)
        // Each car sends an update every 30 seconds therefore each car must be counted exactly once.

        String joinStatement = "select irstream * from " +
            carLocEvent + ".win:time(5 min).std:groupby(expressway, direction, segment).stat:uni(speed) as segAvgSpeed," +
            carLocEvent + ".win:time(30 sec).std:unique(carId).std:groupby(expressway, direction, segment).std:size() as segVolView" +
            " where segAvgSpeed.expressway = segVolView.expressway" +
            "   and segAvgSpeed.direction = segVolView.direction" +
            "   and segAvgSpeed.segment = segVolView.segment";

        joinView = epService.getEPAdministrator().createEQL(joinStatement);
        listener = new CarSegmentCountListener();
        joinView.addListener(listener);
    }

    public void testSendEvents()
    {
        sendTimer(0);

        sendEvent(101, 2, 0, 10, 55);
        checkResult(new Object[] {new CarSegmentCountResult(2, 0, 10, 55, 1)}, null);

        sendEvent(102, 2, 0, 10, 60);
        checkResult(new Object[] {new CarSegmentCountResult(2, 0, 10, 57.5, 2)},
                    new Object[] {new CarSegmentCountResult(2, 0, 10, 55, 1)});

        sendEvent(101, 2, 0, 10, 57);
        checkResult(new Object[] {new CarSegmentCountResult(2, 0, 10, 57 + 1/3d, 2)},
                    new Object[] {new CarSegmentCountResult(2, 0, 10, 57.5, 2)});

        sendEvent(101, 2, 0, 11, 50);
        checkResult(new Object[] {new CarSegmentCountResult(2, 0, 10, 57 + 1/3d, 1),
                                  new CarSegmentCountResult(2, 0, 11, 50, 1)},
                    new Object[] {new CarSegmentCountResult(2, 0, 10, 57 + 1/3d, 2)});

        sendTimer(35 * 1000);       // 35 sec
        checkResult(new Object[] {new CarSegmentCountResult(2, 0, 10, 57 + 1/3d, 0),
                                  new CarSegmentCountResult(2, 0, 11, 50, 0)},
                    new Object[] {new CarSegmentCountResult(2, 0, 10, 57 + 1/3d, 1),
                                  new CarSegmentCountResult(2, 0, 11, 50, 1)});

        sendEvent(110, 1, 5, 40, 40);
        sendEvent(111, 1, 5, 40, 60);
        listener.reset();
        sendEvent(112, 1, 5, 40, 53);
        checkResult(new Object[] {new CarSegmentCountResult(1, 5, 40, 51, 3)},
                    new Object[] {new CarSegmentCountResult(1, 5, 40, 50, 2)});

        sendEvent(111, 1, 5, 39, 65);
        checkResult(new Object[] {new CarSegmentCountResult(1, 5, 40, 51, 2),
                                  new CarSegmentCountResult(1, 5, 39, 65, 1)},
                    new Object[] {new CarSegmentCountResult(1, 5, 40, 51, 3)});

        sendTimer(400 * 1000);
        checkResult(new Object[] {new CarSegmentCountResult(2, 0, 11, Double.NaN, 0),
                                  new CarSegmentCountResult(2, 0, 10, Double.NaN, 0),
                                  new CarSegmentCountResult(1, 5, 39, Double.NaN, 0),
                                  new CarSegmentCountResult(1, 5, 40, Double.NaN, 0)
                                  },
                    new Object[] {new CarSegmentCountResult(2, 0, 11, 50, 0),
                                  new CarSegmentCountResult(2, 0, 10, 57 + 1/3d, 0),
                                  new CarSegmentCountResult(1, 5, 39, 65, 1),
                                  new CarSegmentCountResult(1, 5, 40, 51, 2),
                                  });
    }

    private void checkResult(Object[] expectedNew, Object[] expectedOld)
    {
        ArrayAssertionUtil.assertEqualsAnyOrder(expectedNew, listener.getAndClearNew());
        ArrayAssertionUtil.assertEqualsAnyOrder(expectedOld, listener.getAndClearOld());
    }

    private void sendEvent(long carId, int expressway, int direction, int segment, int speed)
    {
        log.info(".sendEvent carId=" + carId +
                " expressway=" + expressway +
                " direction=" + direction +
                " segment=" + segment +
                " speed=" + speed);
        CarLocEvent event = new CarLocEvent(carId, speed, expressway, 1, direction, segment);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendTimer(long time)
    {
        log.info(".sendEvent time=" + time);
        CurrentTimeEvent event = new CurrentTimeEvent(time);
        epService.getEPRuntime().sendEvent(event);
    }

    private static final Log log = LogFactory.getLog(TestCarSegmentCount.class);
}
