using System;
using System.Collections.Generic;

using net.esper.eql.expression;
using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.core
{
	
	[TestFixture]
	public class TestAliasNodeSwapper 
	{
		internal ExprNode exprTree;
		internal String alias;
		internal ExprNode fullExpr;
		internal ExprNode resultingTree;
		
		[SetUp]
		public virtual void  setUp()
		{
			fullExpr = new ExprIdentNode("full expression");
		}
		
		[Test]
		public virtual void  testWholeReplaced()
		{
			exprTree = new ExprIdentNode("alias");
			alias = "alias";
			resultingTree = AliasNodeSwapper.Swap(exprTree, alias, fullExpr);
			Assert.IsTrue(resultingTree == fullExpr);
		}
		
		[Test]
		public virtual void  testPartReplaced()
		{
			exprTree = SupportExprNodeFactory.MakeEqualsNode();
			alias = "intPrimitive";
            resultingTree = AliasNodeSwapper.Swap(exprTree, alias, fullExpr);
			
			Assert.IsTrue(resultingTree == exprTree);
			IList<ExprNode> childNodes = resultingTree.ChildNodes;
			IList<ExprNode> oldChildNodes = exprTree.ChildNodes;
			Assert.IsTrue(childNodes.Count == 2);
			Assert.IsTrue(childNodes[0] == fullExpr);
			Assert.IsTrue(childNodes[1] == oldChildNodes[1]);
			
			exprTree = resultingTree;
			alias = "intBoxed";
            resultingTree = AliasNodeSwapper.Swap(exprTree, alias, fullExpr);
			childNodes = resultingTree.ChildNodes;
			Assert.IsTrue(childNodes.Count == 2);
			Assert.IsTrue(childNodes[0] == fullExpr);
			Assert.IsTrue(childNodes[1] == fullExpr);
			
			exprTree = resultingTree;
			ExprNode newFullExpr = new ExprIdentNode("new full expr");
			alias = "full expression";
            resultingTree = AliasNodeSwapper.Swap(exprTree, alias, newFullExpr);
			childNodes = resultingTree.ChildNodes;
			Assert.IsTrue(childNodes.Count == 2);
			Assert.IsTrue(childNodes[0] == newFullExpr);
			Assert.IsTrue(childNodes[1] == newFullExpr);
		}
	}
}
