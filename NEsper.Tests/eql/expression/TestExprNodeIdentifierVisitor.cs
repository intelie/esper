using System;

using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
	
	[TestFixture]
	public class TestExprNodeIdentifierVisitor 
	{
		private ExprNode exprNode;
		
		[SetUp]
		public virtual void  setUp()
		{
			exprNode = SupportExprNodeFactory.makeMathNode();
		}
		
		[Test]
		public virtual void  testVisit()
		{
			// test without aggregation nodes
			ExprNodeIdentifierVisitor visitor = new ExprNodeIdentifierVisitor(false);
			exprNode.Accept(visitor);
			
			Assert.AreEqual(2, visitor.ExprProperties.Count);
			Assert.AreEqual(0, (Object) visitor.ExprProperties[0].First);
			Assert.AreEqual("intBoxed", (Object) visitor.ExprProperties[0].Second);
			Assert.AreEqual(0, (Object) visitor.ExprProperties[1].First);
			Assert.AreEqual("intPrimitive", (Object) visitor.ExprProperties[1].Second);
			
			// test with aggregation nodes, such as "intBoxed * sum(intPrimitive)"
			exprNode = SupportExprNodeFactory.makeSumAndFactorNode();
			visitor = new ExprNodeIdentifierVisitor(true);
			exprNode.Accept(visitor);
			Assert.AreEqual(2, visitor.ExprProperties.Count);
			Assert.AreEqual("intBoxed", (Object) visitor.ExprProperties[0].Second);
			Assert.AreEqual("intPrimitive", (Object) visitor.ExprProperties[1].Second);
			
			visitor = new ExprNodeIdentifierVisitor(false);
			exprNode.Accept(visitor);
			Assert.AreEqual(1, visitor.ExprProperties.Count);
			Assert.AreEqual("intBoxed", (Object) visitor.ExprProperties[0].Second);
		}
	}
}
