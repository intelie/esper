using System;

using net.esper.support.eql;
using net.esper.type;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{	
	[TestFixture]
	public class TestExprMinMaxRowNode 
	{
		private ExprMinMaxRowNode minMaxNode;
		
		[SetUp]
		public virtual void  setUp()
		{
			minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
		}
		
		[Test]
		public virtual void  testGetType()
		{
			minMaxNode.AddChildNode(new SupportExprNode(typeof(Double)));
			minMaxNode.AddChildNode(new SupportExprNode(typeof(Int32)));
			minMaxNode.validate(null, null);
			Assert.AreEqual( typeof( Double ), minMaxNode.ReturnType );
			
			minMaxNode.AddChildNode(new SupportExprNode(typeof(Double)));
			minMaxNode.validate(null, null);
			Assert.AreEqual( typeof( Double ), minMaxNode.ReturnType );
		}
		
		[Test]
		public virtual void  testToExpressionString()
		{
			minMaxNode.AddChildNode(new SupportExprNode(9d));
			minMaxNode.AddChildNode(new SupportExprNode(6));
			Assert.AreEqual("max(9,6)", minMaxNode.ExpressionString);
			minMaxNode.AddChildNode(new SupportExprNode(0.5d));
			Assert.AreEqual("max(9,6,0.5)", minMaxNode.ExpressionString);
		}
		
		[Test]
		public virtual void  testValidate()
		{
			// Must have 2 or more subnodes
			try
			{
				minMaxNode.validate(null, null);
				Assert.Fail();
			}
			catch (ExprValidationException ex)
			{
				// Expected
			}
			
			// Must have only number-type subnodes
			minMaxNode.AddChildNode(new SupportExprNode(typeof(String)));
			minMaxNode.AddChildNode(new SupportExprNode(typeof(Int32)));
			try
			{
				minMaxNode.validate(null, null);
				Assert.Fail();
			}
			catch (ExprValidationException ex)
			{
				// Expected
			}
		}
		
		[Test]
		public virtual void  testEvaluate()
		{
			minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
			setupNode(minMaxNode, 10, 1.5, null);
			Assert.AreEqual(10d, minMaxNode.Evaluate(null));
			
			minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
			setupNode(minMaxNode, 1, 1.5, null);
			Assert.AreEqual(1.5d, minMaxNode.Evaluate(null));
			
			minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MIN);
			setupNode(minMaxNode, 1, 1.5, null);
			Assert.AreEqual(1d, minMaxNode.Evaluate(null));
			
			minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
			setupNode(minMaxNode, 1, 1.5, 2.0f);
			Assert.AreEqual(2.0d, minMaxNode.Evaluate(null));
			
			minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MIN);
			setupNode(minMaxNode, 6, 3.5, 2.0f);
			Assert.AreEqual(2.0d, minMaxNode.Evaluate(null));
			
			minMaxNode = makeNode(null, typeof(Int32), 5, typeof(Int32), 6, typeof(Int32));
			Assert.IsNull(minMaxNode.Evaluate(null));
			minMaxNode = makeNode(7, typeof(Int32), null, typeof(Int32), 6, typeof(Int32));
			Assert.IsNull(minMaxNode.Evaluate(null));
			minMaxNode = makeNode(3, typeof(Int32), 5, typeof(Int32), null, typeof(Int32));
			Assert.IsNull(minMaxNode.Evaluate(null));
			minMaxNode = makeNode((Object) null, typeof(Int32), (Object) null, typeof(Int32), (Object) null, typeof(Int32));
			Assert.IsNull(minMaxNode.Evaluate(null));
		}
		
		[Test]
		public virtual void  testEqualsNode()
		{
			Assert.IsTrue(minMaxNode.EqualsNode(minMaxNode));
			Assert.IsFalse(minMaxNode.EqualsNode(new ExprMinMaxRowNode(MinMaxTypeEnum.MIN)));
			Assert.IsFalse(minMaxNode.EqualsNode(new ExprOrNode()));
		}
		
		private static void  setupNode(ExprMinMaxRowNode nodeMin, int intValue, double doubleValue, float? floatValue)
		{
			nodeMin.AddChildNode(new SupportExprNode(intValue));
			nodeMin.AddChildNode(new SupportExprNode(doubleValue));
			if (floatValue != null)
			{
				nodeMin.AddChildNode(new SupportExprNode( floatValue.Value ));
			}
			nodeMin.GetValidatedSubtree(null, null);
		}
		
		private ExprMinMaxRowNode makeNode(Object valueOne, Type typeOne, Object valueTwo, Type typeTwo, Object valueThree, Type typeThree)
		{
			ExprMinMaxRowNode maxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
			maxNode.AddChildNode(new SupportExprNode(valueOne, typeOne));
			maxNode.AddChildNode(new SupportExprNode(valueTwo, typeTwo));
			maxNode.AddChildNode(new SupportExprNode(valueThree, typeThree));
			return maxNode;
		}
	}
}
