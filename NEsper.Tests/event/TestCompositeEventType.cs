using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.events
{
	public class TestCompositeEventType : TestCompositeEventBase
	{
		[SetUp]
		public override void setUp()
		{
			base.setUp();
		}
		
		[Test]
		public virtual void testGetPropertyType()
		{
			Assert.AreEqual( typeof( SupportBean ), eventType.GetPropertyType( "a" ) );
			Assert.AreEqual( typeof( int ), eventType.GetPropertyType( "a.intPrimitive" ) );
			Assert.AreEqual( typeof( String ), eventType.GetPropertyType( "b.nested.nestedValue" ) );

			Assert.AreEqual( null, eventType.GetPropertyType( "b.nested.xxx" ) );
			Assert.AreEqual( null, eventType.GetPropertyType( "b.xxx" ) );
			Assert.AreEqual( null, eventType.GetPropertyType( "y.nested.xxx" ) );
			Assert.AreEqual( null, eventType.GetPropertyType( "y" ) );
		}

		[Test]
		public virtual void testGetGetter()
		{
            Assert.AreEqual(_event, eventType.GetGetter("a").GetValue(eventBeanComplete));
            Assert.AreEqual(1, eventType.GetGetter("a.intPrimitive").GetValue(eventBeanComplete));
            Assert.AreEqual("nestedValue", eventType.GetGetter("b.nested.nestedValue").GetValue(eventBeanComplete));

            Assert.AreEqual(_event, eventType.GetGetter("a").GetValue(eventBeanInComplete));
            Assert.AreEqual(1, eventType.GetGetter("a.intPrimitive").GetValue(eventBeanInComplete));
            Assert.AreEqual(null, eventType.GetGetter("b.nested.nestedValue").GetValue(eventBeanInComplete));
            Assert.AreEqual(null, eventType.GetGetter("b.nested").GetValue(eventBeanInComplete));
            Assert.AreEqual(null, eventType.GetGetter("b").GetValue(eventBeanInComplete));

			Assert.AreEqual( null, eventType.GetGetter( "b.nested.xxx" ) );
			Assert.AreEqual( null, eventType.GetGetter( "b.xxx" ) );
			Assert.AreEqual( null, eventType.GetGetter( "y.nested.xxx" ) );
			Assert.AreEqual( null, eventType.GetGetter( "y" ) );
		}

		[Test]
		public virtual void testGetPropertyNames()
		{
			ArrayAssertionUtil.assertEqualsAnyOrder(
                (ICollection<string>) new String[] { "a", "b" }, 
                (ICollection<string>) eventType.PropertyNames );
		}

		[Test]
		public virtual void testIsProperty()
		{
			Assert.IsTrue( eventType.isProperty( "b.nested.nestedValue" ) );
			Assert.IsFalse( eventType.isProperty( "b.nested.xxx" ) );
		}
	}
}
