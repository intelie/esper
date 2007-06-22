using System;

using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.view
{
	[TestFixture]
	public class TestOutputConditionNull 
	{
		private OutputConditionNull condition;
		private SupportUpdateListener listener;
		private OutputCallback callback;
		
		[SetUp]
		public virtual void  setUp()
		{
			listener = new SupportUpdateListener();
            callback = new OutputCallback(
                delegate(bool doOutput, bool forceUpdate)
                {
                    listener.Update(null, null);
                });
                
			condition = new OutputConditionNull(callback);
		}
		
		[Test]
		public virtual void  testUpdateCondition()
		{
			// the callback should be made regardles of the update
            condition.UpdateOutputCondition(1, 1);
			Assert.IsTrue(listener.GetAndClearIsInvoked());
            condition.UpdateOutputCondition(1, 0);
			Assert.IsTrue(listener.GetAndClearIsInvoked());
            condition.UpdateOutputCondition(0, 1);
			Assert.IsTrue(listener.GetAndClearIsInvoked());
            condition.UpdateOutputCondition(0, 0);
			Assert.IsTrue(listener.GetAndClearIsInvoked());
		}
		
		
		
		
		[Test]
		public virtual void  testIncorrectUse()
		{
			try
			{
				condition = new OutputConditionNull(null);
				Assert.Fail();
			}
			catch (System.NullReferenceException ex)
			{
				// Expected exception
			}
		}
	}
}
