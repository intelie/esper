using System;

using net.esper.compat;
using net.esper.client;
using net.esper.events;
using net.esper.support.events;
using net.esper.support.util;

using NUnit.Framework;

namespace net.esper.core
{
	[TestFixture]
	public class TestPatternListenerDispatch
	{
		private PatternListenerDispatch dispatch;

		private EventBean _eventOne = SupportEventBeanFactory.CreateObject( "a" );
		private EventBean _eventTwo = SupportEventBeanFactory.CreateObject( "b" );

		private SupportUpdateListener listener = new SupportUpdateListener();

		[SetUp]
		public virtual void setUp()
		{
            Set<UpdateEventHandler> listeners = new HashSet<UpdateEventHandler>();
		    listeners.Add(listener.Update);
			dispatch = new PatternListenerDispatch( listeners );
		}

		[Test]
		public void testSingle()
		{
			listener.Reset();

			Assert.IsFalse( dispatch.HasData );
			dispatch.Add( _eventOne );
			Assert.IsTrue( dispatch.HasData );

			dispatch.Execute();

			Assert.IsFalse( dispatch.HasData );
			Assert.AreEqual( 1, listener.LastNewData.Length );
            Assert.AreEqual(_eventOne, listener.LastNewData[0]);
		}

        [Test]
        public void testTwo()
        {
            listener.Reset();
            Assert.IsFalse(dispatch.HasData);

            dispatch.Add(_eventOne);
            dispatch.Add(_eventTwo);
            Assert.IsTrue(dispatch.HasData);

            dispatch.Execute();

            Assert.IsFalse(dispatch.HasData);
            Assert.AreEqual(2, listener.LastNewData.Length);
            Assert.AreEqual(_eventOne, listener.LastNewData[0]);
            Assert.AreEqual(_eventTwo, listener.LastNewData[1]);
        }
	}
}
