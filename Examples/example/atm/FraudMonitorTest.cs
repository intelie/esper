using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.client.time;
using net.esper.compat;
using net.esper.support.util;
using net.esper.events;

namespace net.esper.example.atm
{
	[TestFixture]
	public class FraudMonitorTest
	{
		private EPServiceProvider epService;
		private SupportUpdateListener listener;

		[SetUp]
	    public void setUp()
    	{
	        Configuration configuration = new Configuration();
	        configuration.AddEventTypeAlias("FraudWarning", typeof(FraudWarning).FullName);
	        configuration.AddEventTypeAlias("Withdrawal", typeof(Withdrawal).FullName);
	
	        epService = EPServiceProviderManager.GetProvider("FraudMonitorTest", configuration);
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
	
	        this.listener = new SupportUpdateListener();
	        new FraudMonitor(epService, listener.Update);
	    }
	    
		[Test]
		public void testJoin()
	    {
	        const string FRAUD_TEXT = "card reported stolen";
	
	        sendWithdrawal(1001, 100);
	        sendFraudWarn(1004, FRAUD_TEXT);
	        sendWithdrawal(1001, 60);
	        sendWithdrawal(1002, 400);
	        sendWithdrawal(1001, 300);
	
	        Assert.IsFalse(listener.getAndClearIsInvoked());
	
	        sendWithdrawal(1004, 100);
	        Assert.IsTrue(listener.getAndClearIsInvoked());
	
	        Assert.AreEqual(1, listener.LastNewData.Length);
	        EventBean _event = listener.LastNewData[0];
	        Assert.AreEqual(1004L, _event["AccountNumber"]);
	        Assert.AreEqual(FRAUD_TEXT, _event["Warning"]);
	        Assert.AreEqual(100, _event["Amount"]);
	        Assert.IsTrue( ((long) _event["Timestamp"]) > (DateTimeHelper.CurrentTimeMillis - 100));
	        Assert.AreEqual("withdrawlFraudWarn", _event["Descr"]);
	    }
	
	    private void sendWithdrawal(long acctNum, int amount)
	    {
	        Withdrawal eventBean = new Withdrawal(acctNum, amount);
	        epService.EPRuntime.SendEvent(eventBean);
	    }
	
	    private void sendFraudWarn(long acctNum, String text)
	    {
	        FraudWarning eventBean = new FraudWarning(acctNum, text);
	        epService.EPRuntime.SendEvent(eventBean);
	    }
	}
}
