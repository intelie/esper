using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.client.time;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.example.linearroad
{
	[TestFixture]
	public class TestCarSegmentCount
	{
		private EPServiceProvider epService;
	    private EPStatement joinView;
	    private CarSegmentCountListener listener;
	
	    // TODO: select clause may contain a list of fields and functions
	    // TODO: Check for group/unique by property check - index optimization
	    // TODO: write doc
	
	    public void setUp()
	    {
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	
	        string carLocEvent = typeof(CarLocEvent).FullName;
	
	        // Average speed of the cars in each segment over the last 5 minutes (300 sec).
	        // Same car id can count twice, thats ok.
	
	        // Number of cars currently in each segment (volume per segment)
	        // Each car sends an update every 30 seconds therefore each car must be counted exactly once.
	
	        string joinStatement = "select * from " +
	            carLocEvent + ".win:time(5 min).std:groupby({'expressway', 'direction', 'segment'}).stat:uni('speed') as segAvgSpeed," +
	            carLocEvent + ".win:time(30 sec).std:unique('carId').std:groupby({'expressway', 'direction', 'segment'}).std:size() as segVolView" +
	            " where segAvgSpeed.expressway = segVolView.expressway" +
	            "   and segAvgSpeed.direction = segVolView.direction" +
	            "   and segAvgSpeed.segment = segVolView.segment";
	
	        joinView = epService.EPAdministrator.CreateEQL(joinStatement);
	        listener = new CarSegmentCountListener();
	        joinView.AddListener(listener.Update);
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
	        listener.Reset();
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
	        ArrayAssertionUtil.assertEqualsAnyOrder(expectedNew, listener.GetAndClearNew());
	        ArrayAssertionUtil.assertEqualsAnyOrder(expectedOld, listener.GetAndClearOld());
	    }
	
	    private void sendEvent(long carId, int expressway, int direction, int segment, int speed)
	    {
	        log.Info(".sendEvent carId=" + carId +
	                " expressway=" + expressway +
	                " direction=" + direction +
	                " segment=" + segment +
	                " speed=" + speed);
	        CarLocEvent eventBean = new CarLocEvent(carId, speed, expressway, 1, direction, segment);
	        epService.EPRuntime.SendEvent(eventBean);
	    }
	
	    private void sendTimer(long time)
	    {
	        log.Info(".sendEvent time=" + time);
	        CurrentTimeEvent eventBean = new CurrentTimeEvent(time);
	        epService.EPRuntime.SendEvent(eventBean);
	    }

	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
