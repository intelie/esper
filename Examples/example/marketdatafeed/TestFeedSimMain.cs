using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.client.time;
using net.esper.support;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.example.marketdatafeed
{
	[TestFixture]
	public class TestFeedSimMain
	{
		[Test]
		public void testRun()
	    {
	        FeedSimMain main = new FeedSimMain(100, 50, 5);
	        main.Run();
	    }
	}
}
