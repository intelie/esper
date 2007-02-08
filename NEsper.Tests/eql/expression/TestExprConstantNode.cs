using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
	
	[TestFixture]
	public class TestExprConstantNode 
	{
		private ExprConstantNode constantNode;
		
		[SetUp]
		public virtual void  setUp()
		{
			constantNode = new ExprConstantNode("5");
		}
		
		[Test]
		public virtual void  testGetType()
		{
			Assert.AreEqual( typeof( String ), constantNode.ReturnType );
			
			constantNode = new ExprConstantNode(null);
			Assert.IsNull( constantNode.ReturnType );
		}
		
		[Test]
		public virtual void  testValidate()
		{
			constantNode.validate(null, null);
		}
		
		[Test]
		public virtual void  testEvaluate()
		{
			Assert.AreEqual("5", constantNode.Evaluate(null));
		}
		
		[Test]
		public virtual void  testToExpressionString()
		{
			constantNode = new ExprConstantNode("5");
			Assert.AreEqual("\"5\"", constantNode.ExpressionString);
			
			constantNode = new ExprConstantNode(10);
			Assert.AreEqual("10", constantNode.ExpressionString);
		}
		
		[Test]
		public virtual void  testEqualsNode()
		{
			Assert.IsTrue(constantNode.EqualsNode(new ExprConstantNode("5")));
			Assert.IsFalse(constantNode.EqualsNode(new ExprOrNode()));
			Assert.IsFalse(constantNode.EqualsNode(new ExprConstantNode(null)));
			Assert.IsFalse(constantNode.EqualsNode(new ExprConstantNode(3)));
			
			constantNode = new ExprConstantNode(null);
			Assert.IsTrue(constantNode.EqualsNode(new ExprConstantNode(null)));
		}
	}
}
