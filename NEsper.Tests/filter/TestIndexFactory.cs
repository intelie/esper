using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{
	
	[TestFixture]
	public class TestIndexFactory 
	{
		internal EventType eventType;
		
		[SetUp]
		public virtual void  setUp()
		{
			eventType = SupportEventTypeFactory.createBeanType(typeof(SupportBean));
		}
		
		[Test]
		public virtual void  testCreateIndex()
		{
			// Create a "greater" index
			FilterParamIndex index = IndexFactory.CreateIndex(eventType, "intPrimitive", FilterOperator.GREATER);
			
			Assert.IsTrue(index != null);
			Assert.IsTrue(index is FilterParamIndexCompare);
			Assert.IsTrue(index.PropertyName.Equals("intPrimitive"));
			Assert.IsTrue(index.FilterOperator == FilterOperator.GREATER);
			
			// Create an "equals" index
            index = IndexFactory.CreateIndex(eventType, "str", FilterOperator.EQUAL);
			
			Assert.IsTrue(index != null);
			Assert.IsTrue(index is FilterParamIndexEquals);
            Assert.IsTrue(index.PropertyName.Equals("str"));
			Assert.IsTrue(index.FilterOperator == FilterOperator.EQUAL);
			
			// Create an "not equals" index
            index = IndexFactory.CreateIndex(eventType, "str", FilterOperator.NOT_EQUAL);
			
			Assert.IsTrue(index != null);
			Assert.IsTrue(index is FilterParamIndexNotEquals);
            Assert.IsTrue(index.PropertyName.Equals("str"));
			Assert.IsTrue(index.FilterOperator == FilterOperator.NOT_EQUAL);
			
			// Create a range index
			index = IndexFactory.CreateIndex(eventType, "doubleBoxed", FilterOperator.RANGE_CLOSED);
			Assert.IsTrue(index is FilterParamIndexRange);
		}
	}
}
