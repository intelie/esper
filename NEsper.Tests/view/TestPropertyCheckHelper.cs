using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view
{
	
	[TestFixture]
	public class TestPropertyCheckHelper 
	{
		[Test]
		public virtual void  testCheckNumeric()
		{
			EventType mySchema = SupportEventTypeFactory.createBeanType(typeof(SupportMarketDataBean));
			
			Assert.IsTrue(PropertyCheckHelper.checkNumeric(mySchema, "dummy") != null);
			Assert.IsTrue(PropertyCheckHelper.checkNumeric(mySchema, "symbol") != null);
			
			Assert.IsTrue(PropertyCheckHelper.checkNumeric(mySchema, "volume") == null);
			Assert.IsTrue(PropertyCheckHelper.checkNumeric(mySchema, "price") == null);
			
			Assert.IsTrue(PropertyCheckHelper.checkNumeric(mySchema, "dummy", "dummy2") != null);
			Assert.IsTrue(PropertyCheckHelper.checkNumeric(mySchema, "symbol", "dummy2") != null);
			Assert.IsTrue(PropertyCheckHelper.checkNumeric(mySchema, "symbol", "price") != null);
			Assert.IsTrue(PropertyCheckHelper.checkNumeric(mySchema, "price", "dummy") != null);
			Assert.IsTrue(PropertyCheckHelper.checkNumeric(mySchema, "dummy", "price") != null);
			
			Assert.IsTrue(PropertyCheckHelper.checkNumeric(mySchema, "price", "price") == null);
			Assert.IsTrue(PropertyCheckHelper.checkNumeric(mySchema, "price", "volume") == null);
			Assert.IsTrue(PropertyCheckHelper.checkNumeric(mySchema, "volume", "price") == null);
		}
		
		[Test]
		public virtual void  testCheckLong()
		{
			EventType mySchema = SupportEventTypeFactory.createBeanType(typeof(SupportBean));
			
			Assert.AreEqual(null, PropertyCheckHelper.checkLong(mySchema, "longPrimitive"));
			Assert.AreEqual(null, PropertyCheckHelper.checkLong(mySchema, "longBoxed"));
			Assert.AreEqual(null, PropertyCheckHelper.checkLong(mySchema, "longBoxed"));
			Assert.IsTrue(PropertyCheckHelper.checkLong(mySchema, "dummy") != null);
			Assert.IsTrue(PropertyCheckHelper.checkLong(mySchema, "intPrimitive") != null);
			Assert.IsTrue(PropertyCheckHelper.checkLong(mySchema, "doubleBoxed") != null);
		}
		
		[Test]
		public virtual void  testFieldExist()
		{
			EventType mySchema = SupportEventTypeFactory.createBeanType(typeof(SupportBean));
			
			Assert.AreEqual(null, PropertyCheckHelper.exists(mySchema, "longPrimitive"));
			Assert.IsTrue(PropertyCheckHelper.exists(mySchema, "dummy") != null);
		}
		
		[Test]
		public virtual void  test2FieldExist()
		{
			EventType mySchema = SupportEventTypeFactory.createBeanType(typeof(SupportBean));
			
			Assert.AreEqual(null, PropertyCheckHelper.exists(mySchema, "longPrimitive", "longBoxed"));
			Assert.IsTrue(PropertyCheckHelper.exists(mySchema, "dummy", "longPrimitive") != null);
			Assert.IsTrue(PropertyCheckHelper.exists(mySchema, "longPrimitive", "dummy") != null);
			Assert.IsTrue(PropertyCheckHelper.exists(mySchema, "dummy", "dummy") != null);
		}
	}
}
