using System;

using NUnit.Core;
using NUnit.Framework;

using net.esper.support.eql;
using net.esper.type;

namespace net.esper.eql.expression
{
	[TestFixture]	
	public class TestExprSumNode : TestExprAggregateNodeAdapter
	{
		private ExprSumNode sumNode;
		
		[SetUp]
		public virtual void  setUp()
		{
			sumNode = new ExprSumNode(false);
			
			base.validatedNodeToTest = makeNode(5, typeof(Int32));
		}
		
		[Test]
		public virtual void  testGetType()
		{
			sumNode.AddChildNode(new SupportExprNode(typeof(Int32)));
			sumNode.validate(null, null);
			Assert.AreEqual( typeof( Int32 ), sumNode.ReturnType );
			
			sumNode = new ExprSumNode(false);
			sumNode.AddChildNode(new SupportExprNode(typeof(System.Single)));
			sumNode.validate(null, null);
			Assert.AreEqual( typeof( System.Single ), sumNode.ReturnType );
			
			sumNode = new ExprSumNode(false);
			sumNode.AddChildNode(new SupportExprNode(typeof(Int16)));
			sumNode.validate(null, null);
			Assert.AreEqual( typeof( Int32 ), sumNode.ReturnType );
		}
		
		[Test]
		public virtual void  testToExpressionString()
		{
			// Build sum(4-2)
			ExprMathNode arithNodeChild = new ExprMathNode(MathArithTypeEnum.SUBTRACT);
			arithNodeChild.AddChildNode(new SupportExprNode(4));
			arithNodeChild.AddChildNode(new SupportExprNode(2));
			
			sumNode = new ExprSumNode(false);
			sumNode.AddChildNode(arithNodeChild);
			
			Assert.AreEqual("sum((4-2))", sumNode.ExpressionString);
		}
		
		[Test]
		public virtual void  testValidate()
		{
			// Must have exactly 1 subnodes
			try
			{
				sumNode.validate(null, null);
				Assert.Fail();
			}
			catch (ExprValidationException ex)
			{
				// Expected
			}
			
			// Must have only number-type subnodes
			sumNode.AddChildNode(new SupportExprNode(typeof(String)));
			sumNode.AddChildNode(new SupportExprNode(typeof(Int32)));
			try
			{
				sumNode.validate(null, null);
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
			Assert.IsTrue(makeNode(5, typeof(Int32)).PrototypeAggregator is ExprSumNode.IntegerSum);
			Assert.IsTrue(makeNode(5, typeof(System.Single)).PrototypeAggregator is ExprSumNode.FloatSum);
			Assert.IsTrue(makeNode(5, typeof(Double)).PrototypeAggregator is ExprSumNode.DoubleSum);
			Assert.IsTrue(makeNode(5, typeof(Int16)).PrototypeAggregator is ExprSumNode.NumberIntegerSum);
			Assert.IsTrue(makeNode(5, typeof(Int64)).PrototypeAggregator is ExprSumNode.LongSum);
		}
		
		[Test]
		public virtual void  testEqualsNode()
		{
			Assert.IsTrue(sumNode.EqualsNode(sumNode));
			Assert.IsFalse(sumNode.EqualsNode(new ExprOrNode()));
		}
		
		private ExprSumNode makeNode(Object value, Type type)
		{
			ExprSumNode sumNode = new ExprSumNode(false);
			sumNode.AddChildNode(new SupportExprNode(value, type));
			sumNode.validate(null, null);
			return sumNode;
		}
	}
}
