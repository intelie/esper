using System;

using net.esper.eql.core;
using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
	
	[TestFixture]
	public class TestUniqueValueAggregator 
	{
		private UniqueValueAggregator agg;
		
		[SetUp]
		public virtual void  setUp()
		{
			agg = new UniqueValueAggregator(new SupportAggregator());
		}
		
		[Test]
		public virtual void  testEnter()
		{
			agg.enter(1);
			agg.enter(10);
			agg.enter(null);
		}
		
		[Test]
		public virtual void  testLeave()
		{
			agg.enter(1);
			agg.leave(1);
			
			try
			{
				agg.leave(1);
				Assert.Fail();
			}
			catch (System.SystemException ex)
			{
				// expected
			}
		}
		
		[Test]
		public virtual void  testNewAggregator()
		{
			agg.enter(1);
			Assert.AreNotSame(agg, agg.newAggregator());
			Assert.AreEqual(0, agg.newAggregator().Value);
		}
		
		[Test]
		public virtual void  testGetValue()
		{
			Assert.AreEqual(0, agg.Value);
			
			agg.enter(10);
			Assert.AreEqual(10, agg.Value);
			
			agg.enter(10);
			Assert.AreEqual(10, agg.Value);
			
			agg.enter(2);
			Assert.AreEqual(12, agg.Value);
			
			agg.leave(10);
			Assert.AreEqual(12, agg.Value);
			
			agg.leave(10);
			Assert.AreEqual(2, agg.Value);
			
			agg.leave(2);
			Assert.AreEqual(0, agg.Value);
		}
		
		[Test]
		public virtual void  testGetType()
		{
			Assert.AreEqual(typeof(Int32), agg.ValueType);
		}
	}
}
