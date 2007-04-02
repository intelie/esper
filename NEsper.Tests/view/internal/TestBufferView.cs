using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.internals
{
	
	[TestFixture]
	public class TestBufferView 
	{
		private BufferView bufferView;
		private SupportBufferObserver observer;
		
		[SetUp]
		public virtual void  setUp()
		{
			observer = new SupportBufferObserver();
			bufferView = new BufferView(1);
			bufferView.Observer = observer;
		}
		
		[Test]
		public virtual void  testUpdate()
		{
			// Observer Starts with no data
			Assert.IsFalse(observer.GetAndResetHasNewData());
			
			// Send some data
			EventBean[] newEvents = makeBeans("n", 1);
			EventBean[] oldEvents = makeBeans("o", 1);
			bufferView.Update(newEvents, oldEvents);
			
			// make sure received
			Assert.IsTrue(observer.GetAndResetHasNewData());
			Assert.AreEqual(1, observer.GetAndResetStreamId());
            Assert.IsNotNull(observer.GetAndResetNewEventBuffer());
            Assert.IsNotNull(observer.GetAndResetOldEventBuffer());
			
			// Reset and send null data
			Assert.IsFalse(observer.GetAndResetHasNewData());
            bufferView.Update(null, null);
			Assert.IsTrue(observer.GetAndResetHasNewData());
		}
		
		[Test]
		public virtual void  testViewAttachesTo()
		{
			Assert.IsNull(bufferView.AttachesTo(null));
		}
		
		private EventBean[] makeBeans(String id, int numTrades)
		{
			EventBean[] trades = new EventBean[numTrades];
			for (int i = 0; i < numTrades; i++)
			{
				SupportBean_A bean = new SupportBean_A(id + i);
				trades[i] = SupportEventBeanFactory.createObject(bean);
			}
			return trades;
		}
	}
}
