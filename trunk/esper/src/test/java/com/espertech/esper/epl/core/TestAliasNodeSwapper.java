package com.espertech.esper.epl.core;

import java.util.List;

import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.epl.core.AliasNodeSwapper;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprIdentNode;
import junit.framework.TestCase;

public class TestAliasNodeSwapper extends TestCase 
{
	ExprNode exprTree;
	String alias;
	ExprNode fullExpr;
	ExprNode resultingTree;
	
	public void setUp() throws Exception
	{
		fullExpr = new ExprIdentNode("full expression");
	}
	
	public void testWholeReplaced() throws Exception
	{
		exprTree = new ExprIdentNode("alias");
		alias = "alias";
		resultingTree = AliasNodeSwapper.swap(exprTree, alias, fullExpr);
		assertTrue(resultingTree == fullExpr);
	}
	
	public void testPartReplaced() throws Exception
	{
		exprTree = SupportExprNodeFactory.makeEqualsNode();
		alias = "intPrimitive";
		resultingTree = AliasNodeSwapper.swap(exprTree, alias, fullExpr);
		
		assertTrue(resultingTree == exprTree);
		List<ExprNode> childNodes = resultingTree.getChildNodes();
		List<ExprNode> oldChildNodes = exprTree.getChildNodes();
		assertTrue(childNodes.size() == 2);
		assertTrue(childNodes.get(0) == fullExpr);
		assertTrue(childNodes.get(1) == oldChildNodes.get(1));
		
		exprTree = resultingTree;
		alias = "intBoxed";
		resultingTree = AliasNodeSwapper.swap(exprTree, alias, fullExpr);
		childNodes = resultingTree.getChildNodes();
		assertTrue(childNodes.size() == 2);
		assertTrue(childNodes.get(0) == fullExpr);
		assertTrue(childNodes.get(1) == fullExpr);
		
		exprTree = resultingTree;
		ExprNode newFullExpr = new ExprIdentNode("new full expr");
		alias = "full expression";
		resultingTree = AliasNodeSwapper.swap(exprTree, alias, newFullExpr);
		childNodes = resultingTree.getChildNodes();
		assertTrue(childNodes.size() == 2);
		assertTrue(childNodes.get(0) == newFullExpr);
		assertTrue(childNodes.get(1) == newFullExpr);
	}
	
}
