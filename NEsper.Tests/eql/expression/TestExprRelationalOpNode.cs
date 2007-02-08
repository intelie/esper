using System;

using net.esper.support.eql;
using net.esper.type;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
	
	[TestFixture]
	public class TestExprRelationalOpNode 
	{
		private ExprRelationalOpNode opNode;
		
		[SetUp]
		public virtual void  setUp()
		{
			opNode = new ExprRelationalOpNode(RelationalOpEnum.GE);
		}
		
		[Test]
		public virtual void  testGetType()
		{
			opNode.AddChildNode(new SupportExprNode(typeof(Int64)));
			opNode.AddChildNode(new SupportExprNode(typeof(int)));
			Assert.AreEqual( typeof( bool ), opNode.ReturnType );
		}
		
		[Test]
		public virtual void  testValidate()
		{
			// Test success
			opNode.AddChildNode(new SupportExprNode(typeof(String)));
			opNode.AddChildNode(new SupportExprNode(typeof(String)));
			opNode.validate(null, null);
			
			opNode.ChildNodes.Clear();
			opNode.AddChildNode(new SupportExprNode(typeof(String)));
			
			// Test too few nodes under this node
			try
			{
				opNode.validate(null, null);
				Assert.Fail();
			}
			catch (System.SystemException ex)
			{
				// Expected
			}
			
			// Test mismatch type
			opNode.AddChildNode(new SupportExprNode(typeof(Int32)));
			try
			{
				opNode.validate(null, null);
				Assert.Fail();
			}
			catch (ExprValidationException ex)
			{
				// Expected
			}
			
			// Test type cannot be compared
			opNode.ChildNodes.Clear();
			opNode.AddChildNode(new SupportExprNode(typeof(bool)));
			opNode.AddChildNode(new SupportExprNode(typeof(bool)));
			
			try
			{
				opNode.validate(null, null);
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
			SupportExprNode childOne = new SupportExprNode("d");
			SupportExprNode childTwo = new SupportExprNode("c");
			opNode.AddChildNode(childOne);
			opNode.AddChildNode(childTwo);
			opNode.validate(null, null); // Type initialization
			
			Assert.AreEqual(true, opNode.Evaluate(null));
			
			childOne.Value = "c";
			Assert.AreEqual(true, opNode.Evaluate(null));
			
			childOne.Value = "b";
			Assert.AreEqual(false, opNode.Evaluate(null));
			
			opNode = makeNode(null, typeof(Int32), 2, typeof(Int32));
			Assert.AreEqual(false, opNode.Evaluate(null));
			opNode = makeNode(1, typeof(Int32), null, typeof(Int32));
			Assert.AreEqual(false, opNode.Evaluate(null));
			opNode = makeNode((Object) null, typeof(Int32), (Object) null, typeof(Int32));
			Assert.AreEqual(false, opNode.Evaluate(null));
		}
		
		[Test]
		public virtual void  testToExpressionString()
		{
			opNode.AddChildNode(new SupportExprNode(10));
			opNode.AddChildNode(new SupportExprNode(5));
			Assert.AreEqual("10>=5", opNode.ExpressionString);
		}
		
		private ExprRelationalOpNode makeNode(Object valueLeft, Type typeLeft, Object valueRight, Type typeRight)
		{
			ExprRelationalOpNode relOpNode = new ExprRelationalOpNode(RelationalOpEnum.GE);
			relOpNode.AddChildNode(new SupportExprNode(valueLeft, typeLeft));
			relOpNode.AddChildNode(new SupportExprNode(valueRight, typeRight));
			return relOpNode;
		}
		
		[Test]
		public virtual void  testEqualsNode()
		{
			Assert.IsTrue(opNode.EqualsNode(opNode));
			Assert.IsFalse(opNode.EqualsNode(new ExprRelationalOpNode(RelationalOpEnum.LE)));
			Assert.IsFalse(opNode.EqualsNode(new ExprOrNode()));
		}
	}
}
