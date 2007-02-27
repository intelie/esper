using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestViewStartStop
	{
		private EPServiceProvider epService;
		private SupportUpdateListener testListener;
		private EPStatement sizeView;

		[SetUp]
		public virtual void setUp()
		{
			testListener = new SupportUpdateListener();
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();

			String viewExpr = "select * from " + typeof( SupportBean ).FullName + ".std:size()";

			sizeView = epService.EPAdministrator.CreateEQL( viewExpr );
		}

        public virtual T First<T>(IEnumerable<T> item)
        {
            return CollectionHelper.First(item);
        }

		[Test]
		public virtual void testStartStop()
		{
			// View created is automatically Started
            Assert.AreEqual(0L, First(sizeView)["size"]);
			sizeView.Stop();

			// Send an event, view Stopped
			SendEvent();
			Assert.IsNull( sizeView.GetEnumerator() );

			// Start view
			sizeView.Start();
            Assert.AreEqual(0L, First(sizeView)["size"]);

			// Send event
			SendEvent();
            Assert.AreEqual(1L, First(sizeView)["size"]);

			// Stop view
			sizeView.Stop();
			Assert.IsNull( sizeView.GetEnumerator() );

			// Start again, iterator is zero
			sizeView.Start();
            Assert.AreEqual(0L, First(sizeView)["size"]);
		}

		[Test]
		public virtual void testAddRemoveListener()
		{
			// View is Started when created

			// Add listener send event
            sizeView.AddListener(testListener.Update);
			Assert.IsNull( testListener.LastNewData );
            Assert.AreEqual(0L, First(sizeView)["size"]);
			SendEvent();
			Assert.AreEqual( 1L, testListener.getAndResetLastNewData()[0]["size"] );
            Assert.AreEqual(1L, First(sizeView)["size"]);

			// Stop view, send event, view
			sizeView.Stop();
			SendEvent();
			Assert.IsNull( sizeView.GetEnumerator() );
			Assert.IsNull( testListener.LastNewData );

			// Start again
            sizeView.RemoveListener(testListener.Update);
            sizeView.AddListener(testListener.Update);
			sizeView.Start();

			SendEvent();
			Assert.AreEqual( 1L, testListener.getAndResetLastNewData()[0]["size"] );
            Assert.AreEqual(1L, First(sizeView)["size"]);

			// Stop again, leave listeners
			sizeView.Stop();
			sizeView.Start();
			SendEvent();
			Assert.AreEqual( 1L, testListener.getAndResetLastNewData()[0]["size"] );

			// Remove listener, send event
            sizeView.RemoveListener(testListener.Update);
			SendEvent();
			Assert.IsNull( testListener.LastNewData );

			// Add listener back, send event
            sizeView.AddListener(testListener.Update);
			SendEvent();
			Assert.AreEqual( 3L, testListener.getAndResetLastNewData()[0]["size"] );
		}

		private void SendEvent()
		{
			epService.EPRuntime.SendEvent( new SupportBean() );
		}
	}
}
