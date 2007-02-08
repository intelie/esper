using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view
{
	[TestFixture]
	public class TestViewSupport
	{
		private SupportSchemaNeutralView top;

		private SupportSchemaNeutralView child_1;
		private SupportSchemaNeutralView child_2;

		private SupportSchemaNeutralView child_2_1;
		private SupportSchemaNeutralView child_2_2;

		private SupportSchemaNeutralView child_2_1_1;
		private SupportSchemaNeutralView child_2_2_1;
		private SupportSchemaNeutralView child_2_2_2;

		[SetUp]
		public virtual void setUp()
		{
			top = new SupportSchemaNeutralView( "top" );

			child_1 = new SupportSchemaNeutralView( "1" );
			child_2 = new SupportSchemaNeutralView( "2" );
			top.AddView( child_1 );
			top.AddView( child_2 );

			child_2_1 = new SupportSchemaNeutralView( "2_1" );
			child_2_2 = new SupportSchemaNeutralView( "2_2" );
			child_2.AddView( child_2_1 );
			child_2.AddView( child_2_2 );

			child_2_1_1 = new SupportSchemaNeutralView( "2_1_1" );
			child_2_2_1 = new SupportSchemaNeutralView( "2_2_1" );
			child_2_2_2 = new SupportSchemaNeutralView( "2_2_2" );
			child_2_1.AddView( child_2_1_1 );
			child_2_2.AddView( child_2_2_1 );
			child_2_2.AddView( child_2_2_2 );
		}

		[Test]
		public virtual void testShallowCopyView()
		{
			// Copy a view based on a class
			SupportBeanClassView viewOne = new SupportBeanClassView( typeof( SupportMarketDataBean ) );
			SupportBeanClassView copyOne = (SupportBeanClassView) ViewSupport.shallowCopyView( viewOne );
			Assert.AreEqual( viewOne.EventType, copyOne.EventType );

			// Copy a view based on a map
			SupportMapView viewTwo = new SupportMapView( new EHashDictionary<String, Type>() );
            viewTwo.Parent = viewOne;
			View copyTwo = ViewSupport.shallowCopyView( viewTwo );
			Assert.IsTrue( copyTwo.Parent == null );
			Assert.AreEqual( viewTwo.EventType, copyTwo.EventType );

			// Copy a view with read/write property access
			SupportShallowCopyView viewThree = new SupportShallowCopyView( "avalue" );
			SupportShallowCopyView copyThree = (SupportShallowCopyView) ViewSupport.shallowCopyView( viewThree );

			Assert.AreEqual( "avalue", copyThree.SomeReadWriteValue );
			Assert.AreEqual( null, copyThree.SomeReadOnlyValue );
			Assert.IsTrue( copyThree.NullWriteOnlyValue );
		}

		[Test]
		public virtual void testFindDescendent()
		{
			// Test a deep find
			IList<View> descendents = ViewSupport.findDescendent( top, child_2_2_1 );
			Assert.AreEqual( 2, descendents.Count );
			Assert.AreEqual( child_2, descendents[0] );
			Assert.AreEqual( child_2_2, descendents[1] );

			descendents = ViewSupport.findDescendent( top, child_2_1_1 );
			Assert.AreEqual( 2, descendents.Count );
			Assert.AreEqual( child_2, descendents[0] );
			Assert.AreEqual( child_2_1, descendents[1] );

			descendents = ViewSupport.findDescendent( top, child_2_1 );
			Assert.AreEqual( 1, descendents.Count );
			Assert.AreEqual( child_2, descendents[0] );

			// Test a shallow find
			descendents = ViewSupport.findDescendent( top, child_2 );
			Assert.AreEqual( 0, descendents.Count );

			// Test a no find
			descendents = ViewSupport.findDescendent( top, new SupportSchemaNeutralView() );
			Assert.AreEqual( null, descendents );
		}
	}
}
