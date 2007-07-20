using System;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestJoinPropertyAccess
	{
		private EPServiceProvider epService;
		private SupportUpdateListener testListener;

		[SetUp]
		public virtual void setUp()
		{
            PropertyResolutionStyleHelper.DefaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_INSENSITIVE;

			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
		}

		[Test]
		public void testRegularJoin()
		{
			SupportBeanCombinedProps combined = SupportBeanCombinedProps.MakeDefaultBean();
			SupportBeanComplexProps complex = SupportBeanComplexProps.MakeDefaultBean();
			Assert.AreEqual( "0ma0", combined.GetIndexed( 0 ).GetMapped( "0ma" ).Value );

			String viewExpr =
				"select nested.nested, s1.indexed[0], nested.indexed[1] from " +
                typeof(SupportBeanComplexProps).FullName + ".win:length(3) nested, " +
                typeof(SupportBeanCombinedProps).FullName + ".win:length(3) s1" +
				" where mapped('keyOne') = indexed[2].mapped('2ma').value and" +
				" indexed[0].mapped('0ma').value = '0ma0'";

			EPStatement testView = epService.EPAdministrator.CreateEQL( viewExpr );
			testListener = new SupportUpdateListener();
            testView.AddListener(testListener);

			epService.EPRuntime.SendEvent( combined );
			epService.EPRuntime.SendEvent( complex );

			EventBean _event = testListener.GetAndResetLastNewData()[0];
			Assert.AreSame( complex.Nested, _event["nested.nested"] );
			Assert.AreSame( combined.GetIndexed( 0 ), _event["s1.indexed[0]"] );
			Assert.AreEqual( complex.GetIndexed( 1 ), _event["nested.indexed[1]"] );
		}

		[Test]
		public void testOuterJoin()
		{
			String viewExpr =
				"select * from " +
                typeof(SupportBeanComplexProps).FullName + ".win:length(3) s0" + " left outer join " +
                typeof(SupportBeanCombinedProps).FullName + ".win:length(3) s1" +
				" on mapped('keyOne') = indexed[2].mapped('2ma').value";

			EPStatement testView = epService.EPAdministrator.CreateEQL( viewExpr );
			testListener = new SupportUpdateListener();
            testView.AddListener(testListener);

            SupportBeanCombinedProps combined = SupportBeanCombinedProps.MakeDefaultBean();
			epService.EPRuntime.SendEvent( combined );
			SupportBeanComplexProps complex = SupportBeanComplexProps.MakeDefaultBean();
			epService.EPRuntime.SendEvent( complex );

			// double check that outer join criteria match
			Assert.AreEqual( complex.GetMapped( "keyOne" ), combined.GetIndexed( 2 ).GetMapped( "2ma" ).Value );

			EventBean _event = testListener.GetAndResetLastNewData()[0];
			Assert.AreSame( complex, _event["s0"] );
			Assert.AreSame( combined, _event["s1"] );
		}
	}
}
