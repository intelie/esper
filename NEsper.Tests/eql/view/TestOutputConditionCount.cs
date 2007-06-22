using System;

using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.view
{
	
	[TestFixture]
	public class TestOutputConditionCount 
	{
		private OutputConditionCount fireEvery1;
		private OutputConditionCount fireEvery2;
		private OutputConditionCount fireEvery3;
		private SupportUpdateListener listener1;
		private SupportUpdateListener listener2;
		private SupportUpdateListener listener3;
		private OutputCallback callback1;
		private OutputCallback callback2;
		private OutputCallback callback3;
		
		[SetUp]
		public virtual void  setUp()
		{
			listener1 = new SupportUpdateListener();
			listener3 = new SupportUpdateListener();
			listener2 = new SupportUpdateListener();

            callback1 = new OutputCallback(
                delegate(bool doOutput, bool forceUpdate)
                {
                    listener1.Update(null, null);
                });

            callback2 = new OutputCallback(
                delegate(bool doOutput, bool forceUpdate)
                {
                    listener2.Update(null, null);
                });

            callback3 = new OutputCallback(
                delegate(bool doOutput, bool forceUpdate)
                {
                    listener3.Update(null, null);
                });

			fireEvery1 = new OutputConditionCount(1, callback1);
			fireEvery2 = new OutputConditionCount(2, callback2);
			fireEvery3 = new OutputConditionCount(3, callback3);
		}
		
		[Test]
		public virtual void  testUpdateCondition()
		{
			// send 1 new _event, 0 old events
			sendEventToAll(1, 0);
			
			Assert.IsTrue(listener1.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery1.NewEventsCount);
			Assert.AreEqual(0, fireEvery1.OldEventsCount);
			
			Assert.IsFalse(listener2.GetAndClearIsInvoked());
			Assert.AreEqual(1, fireEvery2.NewEventsCount);
			Assert.AreEqual(0, fireEvery2.OldEventsCount);
			
			Assert.IsFalse(listener3.GetAndClearIsInvoked());
			Assert.AreEqual(1, fireEvery3.NewEventsCount);
			Assert.AreEqual(0, fireEvery3.OldEventsCount);
			
			// send 1 new _event, 0 old events
			sendEventToAll(1, 0);
			
			Assert.IsTrue(listener1.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery1.NewEventsCount);
			Assert.AreEqual(0, fireEvery1.OldEventsCount);
			
			Assert.IsTrue(listener2.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery2.NewEventsCount);
			Assert.AreEqual(0, fireEvery2.OldEventsCount);
			
			Assert.IsFalse(listener3.GetAndClearIsInvoked());
			Assert.AreEqual(2, fireEvery3.NewEventsCount);
			Assert.AreEqual(0, fireEvery3.OldEventsCount);
			
			// send 1 new _event, 0 old events
			sendEventToAll(1, 0);
			
			Assert.IsTrue(listener1.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery1.NewEventsCount);
			Assert.AreEqual(0, fireEvery1.OldEventsCount);
			
			Assert.IsFalse(listener2.GetAndClearIsInvoked());
			Assert.AreEqual(1, fireEvery2.NewEventsCount);
			Assert.AreEqual(0, fireEvery2.OldEventsCount);
			
			Assert.IsTrue(listener3.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery3.NewEventsCount);
			Assert.AreEqual(0, fireEvery3.OldEventsCount);
			
			// send 0 new _event, 1 old events
			sendEventToAll(0, 1);
			
			Assert.IsTrue(listener1.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery1.NewEventsCount);
			Assert.AreEqual(0, fireEvery1.OldEventsCount);
			
			Assert.IsFalse(listener2.GetAndClearIsInvoked());
			Assert.AreEqual(1, fireEvery2.NewEventsCount);
			Assert.AreEqual(1, fireEvery2.OldEventsCount);
			
			Assert.IsFalse(listener3.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery3.NewEventsCount);
			Assert.AreEqual(1, fireEvery3.OldEventsCount);
			
			// send 0 new _event, 1 old events
			sendEventToAll(0, 1);
			
			Assert.IsTrue(listener1.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery1.NewEventsCount);
			Assert.AreEqual(0, fireEvery1.OldEventsCount);
			
			Assert.IsTrue(listener2.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery2.NewEventsCount);
			Assert.AreEqual(0, fireEvery2.OldEventsCount);
			
			Assert.IsFalse(listener3.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery3.NewEventsCount);
			Assert.AreEqual(2, fireEvery3.OldEventsCount);
			
			// send 0 new _event, 1 old events
			sendEventToAll(0, 1);
			
			Assert.IsTrue(listener1.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery1.NewEventsCount);
			Assert.AreEqual(0, fireEvery1.OldEventsCount);
			
			Assert.IsFalse(listener2.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery2.NewEventsCount);
			Assert.AreEqual(1, fireEvery2.OldEventsCount);
			
			Assert.IsTrue(listener3.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery3.NewEventsCount);
			Assert.AreEqual(0, fireEvery3.OldEventsCount);
			
			// send 5 new, 5 old events
			sendEventToAll(5, 5);
			
			Assert.IsTrue(listener1.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery1.NewEventsCount);
			Assert.AreEqual(0, fireEvery1.OldEventsCount);
			
			Assert.IsTrue(listener2.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery2.NewEventsCount);
			Assert.AreEqual(0, fireEvery2.OldEventsCount);
			
			Assert.IsTrue(listener3.GetAndClearIsInvoked());
			Assert.AreEqual(0, fireEvery3.NewEventsCount);
			Assert.AreEqual(0, fireEvery3.OldEventsCount);
		}
		
		private void  sendEventToAll(int newEventsLength, int oldEventsLength)
		{
			fireEvery1.UpdateOutputCondition(newEventsLength, oldEventsLength);
            fireEvery2.UpdateOutputCondition(newEventsLength, oldEventsLength);
            fireEvery3.UpdateOutputCondition(newEventsLength, oldEventsLength);
		}
		
		[Test]
		public virtual void  testIncorrectUse()
		{
			try
			{
				fireEvery1 = new OutputConditionCount(0, callback1);
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// Expected exception
			}
			try
			{
				fireEvery1 = new OutputConditionCount(1, null);
				Assert.Fail();
			}
			catch (System.NullReferenceException ex)
			{
				// Expected exception
			}
		}
	}
}
