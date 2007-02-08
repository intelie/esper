using System;

using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestJoinNoWhereClause
	{
		private EPServiceProvider epService;
		private EPStatement joinView;
		private SupportUpdateListener updateListener;

		private Object[] setOne = new Object[5];
		private Object[] setTwo = new Object[5];

		[SetUp]
		public virtual void setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
			updateListener = new SupportUpdateListener();

			for ( int i = 0 ; i < setOne.Length ; i++ )
			{
				setOne[i] = new SupportMarketDataBean( "IBM", 0, (long) i, "" );

				SupportBean _event = new SupportBean();
				_event.LongBoxed = (long) i ;
				setTwo[i] = _event;
			}
		}

		[Test]
		public virtual void testJoinNoWhereClause()
		{
			String joinStatement = 
				"select * from " +
				typeof( SupportMarketDataBean ).FullName + ".win:length(3)," +
				typeof( SupportBean ).FullName + "().win:length(3)";

			joinView = epService.EPAdministrator.createEQL( joinStatement );
			joinView.AddListener( updateListener );

			// Send 2 events, should join on second one
			SendEvent( setOne[0] );
			SendEvent( setTwo[0] );
			Assert.AreEqual( 1, updateListener.LastNewData.Length );
			Assert.AreEqual( setOne[0], updateListener.LastNewData[0]["stream_0"] );
			Assert.AreEqual( setTwo[0], updateListener.LastNewData[0]["stream_1"] );
			updateListener.reset();

			SendEvent( setOne[1] );
			SendEvent( setOne[2] );
			SendEvent( setTwo[1] );
			Assert.AreEqual( 3, updateListener.LastNewData.Length );
		}

		private void SendEvent( Object _event )
		{
			epService.EPRuntime.SendEvent( _event );
		}
	}
}
