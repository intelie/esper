using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.client.time;

using org.apache.commons.logging;

namespace net.esper.example.linearroad
{
	[TestFixture]
	public class TestAccidentNotify
	{
	    private EPServiceProvider epService;
	    private EPStatement joinView;
	
	    [SetUp]
	    public void setUp()
	    {
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	
	        string carLocEvent = typeof(CarLocEvent).FullName;
	
	        /**
	         * An accident has occurred if a car reports the same location four consecutive times.
	         * When an accident is detected by the system, all the cars in 5 upstream segments have to be
	         * notified of the accident.
	         *
	         * TODO: How does the accident get cleared?
	         * TODO: If 2 cars report the same loc 4 times, then this joins twice
	         */
	        string joinStatement = "select * from " +
	            carLocEvent + ".std:groupby('carId').win:length(4).std:groupby({'expressway', 'direction', 'segment'}).std:size() as accSeg," +
	            carLocEvent + ".win:time(30 sec).std:unique('carId') as curCarSeg" +
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
	
	        joinView = epService.EPAdministrator.CreateEQL(joinStatement);
	        joinView.AddListener(new AccidentNotifyListener().Update);
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
	        log.Info(".sendEvent carId=" + carId +
	                " expressway=" + expressway +
	                " direction=" + direction +
	                " segment=" + segment);
	        CarLocEvent eventBean = new CarLocEvent(carId, 50, expressway, 1, direction, segment);  // same speed and lane
	        epService.EPRuntime.SendEvent(eventBean);
	    }
	
       private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
 	}
}
