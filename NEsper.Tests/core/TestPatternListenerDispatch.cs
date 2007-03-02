using System;

using net.esper.compat;
using net.esper.client;
using net.esper.events;
using net.esper.support.events;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.core
{
	[TestFixture]
	public class TestPatternListenerDispatch
	{
		private PatternListenerDispatch dispatch;

		private EventBean eventOne = SupportEventBeanFactory.createObject( "a" );
		private EventBean eventTwo = SupportEventBeanFactory.createObject( "b" );

		private SupportUpdateListener listener = new SupportUpdateListener();

		[SetUp]
		public virtual void setUp()
		{
			ISet<UpdateListener> listeners = new EHashSet<UpdateListener>();
            listeners.Add(listener.Update);
			dispatch = new PatternListenerDispatch( listeners );
		}

		[Test]
		public virtual void testSingle()
		{
			listener.reset();

			Assert.IsFalse( dispatch.hasData() );
			dispatch.Add( eventOne );
			Assert.IsTrue( dispatch.hasData() );

			dispatch.Execute();

			Assert.IsFalse( dispatch.hasData() );
			Assert.AreEqual( 1, listener.LastNewData.Length );
			Assert.AreEqual( eventOne, listener.LastNewData[0] );
		}

		[Test]
		public virtual void testTwo()
		{
			listener.reset();
			Assert.IsFalse( dispatch.hasData() );

			dispatch.Add( eventOne );
			dispatch.Add( eventTwo );
			Assert.IsTrue( dispatch.hasData() );

			dispatch.Execute();

			Assert.IsFalse( dispatch.hasData() );
			Assert.AreEqual( 2, listener.LastNewData.Length );
			Assert.AreEqual( eventOne, listener.LastNewData[0] );
			Assert.AreEqual( eventTwo, listener.LastNewData[1] );
		}
	}
}
