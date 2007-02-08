using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{
	
	[TestFixture]
	public class TestFilterSpecParamConstant 
	{
		[Test]
		public virtual void  testConstruct()
		{
			new FilterSpecParamConstant("a", FilterOperator.GREATER, 5);
			
			try
			{
				new FilterSpecParamConstant("a", FilterOperator.RANGE_CLOSED, 5);
				Assert.IsTrue(false);
			}
			catch (ArgumentException ex)
			{
				// Expected exception
			}
		}
		
		[Test]
		public virtual void  testEquals()
		{
			FilterSpecParam c1 = new FilterSpecParamConstant("a", FilterOperator.GREATER, 5);
			FilterSpecParam c2 = new FilterSpecParamConstant("a", FilterOperator.GREATER, 6);
			FilterSpecParam c3 = new FilterSpecParamConstant("b", FilterOperator.GREATER, 5);
			FilterSpecParam c4 = new FilterSpecParamConstant("a", FilterOperator.EQUAL, 5);
			FilterSpecParam c5 = new FilterSpecParamConstant("a", FilterOperator.GREATER, 5);
			
			Assert.IsFalse(c1.Equals(c2));
			Assert.IsFalse(c1.Equals(c3));
			Assert.IsFalse(c1.Equals(c4));
			Assert.IsTrue(c1.Equals(c5));
		}
	}
}
