using System;

using net.esper.support.eql;
using net.esper.type;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
	
	[TestFixture]
	public class TestExprConcatNode 
	{
		private ExprConcatNode concatNode;
		
		[SetUp]
		public virtual void  setUp()
		{
			concatNode = new ExprConcatNode();
		}
		
		[Test]
		public virtual void  testGetType()
		{
			Assert.AreEqual( typeof( String ), concatNode.ReturnType );
		}
		
		[Test]
		public virtual void  testToExpressionString()
		{
			concatNode = new ExprConcatNode();
			concatNode.AddChildNode(new SupportExprNode("a"));
			concatNode.AddChildNode(new SupportExprNode("b"));
			Assert.AreEqual("(\"a\"||\"b\")", concatNode.ExpressionString);
			concatNode.AddChildNode(new SupportExprNode("c"));
			Assert.AreEqual("(\"a\"||\"b\"||\"c\")", concatNode.ExpressionString);
		}
		
		[Test]
		public virtual void  testValidate()
		{
			// Must have 2 or more String subnodes
			try
			{
				concatNode.validate(null, null);
				Assert.Fail();
			}
			catch (ExprValidationException ex)
			{
				// Expected
			}
			
			// Must have only string-type subnodes
			concatNode.AddChildNode(new SupportExprNode(typeof(String)));
			concatNode.AddChildNode(new SupportExprNode(typeof(Int32)));
			try
			{
				concatNode.validate(null, null);
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
			concatNode.AddChildNode(new SupportExprNode("x"));
			concatNode.AddChildNode(new SupportExprNode("y"));
			Assert.AreEqual("xy", concatNode.Evaluate(null));
			concatNode.AddChildNode(new SupportExprNode("z"));
			Assert.AreEqual("xyz", concatNode.Evaluate(null));
			concatNode.AddChildNode(new SupportExprNode(null));
			Assert.AreEqual(null, concatNode.Evaluate(null));
		}
		
		[Test]
		public virtual void  testEqualsNode()
		{
			Assert.IsTrue(concatNode.EqualsNode(concatNode));
			Assert.IsFalse(concatNode.EqualsNode(new ExprMathNode(MathArithTypeEnum.DIVIDE)));
		}
	}
}
