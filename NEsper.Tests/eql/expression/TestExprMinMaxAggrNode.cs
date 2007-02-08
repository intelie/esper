using System;

using NUnit.Core;
using NUnit.Framework;

using net.esper.support.eql;
using net.esper.type;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprMinMaxAggrNode : TestExprAggregateNodeAdapter
	{
		private ExprMinMaxAggrNode maxNode;
		private ExprMinMaxAggrNode minNode;
		
		[SetUp]
		public virtual void  setUp()
		{
			maxNode = new ExprMinMaxAggrNode(false, MinMaxTypeEnum.MAX);
			minNode = new ExprMinMaxAggrNode(false, MinMaxTypeEnum.MIN);
			
			base.validatedNodeToTest = makeNode(MinMaxTypeEnum.MAX, 5, typeof(Int32));
		}
		
		[Test]
		public virtual void  testGetType()
		{
			maxNode.AddChildNode(new SupportExprNode(typeof(Int32)));
			maxNode.validate(null, null);
			Assert.AreEqual(typeof(Int32), maxNode.ReturnType);
			
			minNode.AddChildNode(new SupportExprNode(typeof(System.Single)));
			minNode.validate(null, null);
			Assert.AreEqual( typeof( System.Single ), minNode.ReturnType );
			
			maxNode = new ExprMinMaxAggrNode(false, MinMaxTypeEnum.MAX);
			maxNode.AddChildNode(new SupportExprNode(typeof(Int16)));
			maxNode.validate(null, null);
			Assert.AreEqual( typeof( Int16 ), maxNode.ReturnType );
		}
		
		[Test]
		public virtual void  testToExpressionString()
		{
			// Build sum(4-2)
			ExprMathNode arithNodeChild = new ExprMathNode(MathArithTypeEnum.SUBTRACT);
			arithNodeChild.AddChildNode(new SupportExprNode(4));
			arithNodeChild.AddChildNode(new SupportExprNode(2));
			
			maxNode.AddChildNode(arithNodeChild);
			Assert.AreEqual("max((4-2))", maxNode.ExpressionString);
			minNode.AddChildNode(arithNodeChild);
			Assert.AreEqual("min((4-2))", minNode.ExpressionString);
		}
		
		[Test]
		public virtual void  testValidate()
		{
			// Must have exactly 1 subnodes
			try
			{
				minNode.validate(null, null);
				Assert.Fail();
			}
			catch (ExprValidationException ex)
			{
				// Expected
			}
			
			// Must have only number-type subnodes
			minNode.AddChildNode(new SupportExprNode(typeof(String)));
			minNode.AddChildNode(new SupportExprNode(typeof(Int32)));
			try
			{
				minNode.validate(null, null);
				Assert.Fail();
			}
			catch (ExprValidationException ex)
			{
				// Expected
			}
		}
		
		[Test]
		public virtual void  testMakeAggregator()
		{
			MinMaxTypeEnum type = MinMaxTypeEnum.MAX;
			Assert.IsTrue(makeNode(type, 5, typeof(Int32)).PrototypeAggregator is ExprMinMaxAggrNode.MinMaxAggregator);
		}
		
		[Test]
		public virtual void  testEqualsNode()
		{
			Assert.IsTrue(minNode.EqualsNode(minNode));
			Assert.IsFalse(maxNode.EqualsNode(minNode));
			Assert.IsFalse(minNode.EqualsNode(new ExprSumNode(false)));
		}
		
		private ExprMinMaxAggrNode makeNode(MinMaxTypeEnum minMaxType, Object value, Type type)
		{
			ExprMinMaxAggrNode minMaxNode = new ExprMinMaxAggrNode(false, minMaxType);
			minMaxNode.AddChildNode(new SupportExprNode(value, type));
			minMaxNode.validate(null, null);
			return minMaxNode;
		}
	}
}
