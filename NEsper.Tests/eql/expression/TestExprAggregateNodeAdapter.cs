using System;

using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
	public abstract class TestExprAggregateNodeAdapter 
	{
		protected internal ExprAggregateNode validatedNodeToTest;
		
		[Test]
		public virtual void testEvaluate()
		{
			SupportAggregationResultFuture future = new SupportAggregationResultFuture(new Object[]{10, 20});
			validatedNodeToTest.setAggregationResultFuture(future, 1);
			
			Assert.AreEqual(20, validatedNodeToTest.Evaluate(null));
		}
	}
}
