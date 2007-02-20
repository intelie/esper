using System;

using NUnit.Core;
using NUnit.Framework;

using net.esper.support.eql;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprCountNode : TestExprAggregateNodeAdapter
	{
		private ExprCountNode wildcardCount;
		
		[SetUp]
		public virtual void  setUp()
		{
			base.validatedNodeToTest = makeNode(5, typeof(Int32));
			
			wildcardCount = new ExprCountNode(false);
			wildcardCount.validate(null, null);
		}
		
		[Test]
		public virtual void  testGetType()
		{
			Assert.AreEqual(typeof(long?), validatedNodeToTest.ReturnType);
			Assert.AreEqual(typeof(long?), wildcardCount.ReturnType);
		}
		
		[Test]
		public virtual void  testToExpressionString()
		{
			Assert.AreEqual("count(5)", validatedNodeToTest.ExpressionString);
			Assert.AreEqual("count(*)", wildcardCount.ExpressionString);
		}
		
		[Test]
		public virtual void  testEqualsNode()
		{
			Assert.IsTrue(validatedNodeToTest.EqualsNode(validatedNodeToTest));
			Assert.IsFalse(validatedNodeToTest.EqualsNode(new ExprSumNode(false)));
			Assert.IsTrue(wildcardCount.EqualsNode(wildcardCount));
		}
		
		private ExprCountNode makeNode(Object value, Type type)
		{
			ExprCountNode countNode = new ExprCountNode(false);
			countNode.AddChildNode(new SupportExprNode(value, type));
			countNode.validate(null, null);
			return countNode;
		}
	}
}
