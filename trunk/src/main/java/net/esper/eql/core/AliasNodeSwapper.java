package net.esper.eql.core;

import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprIdentNode;

import java.util.List;
import java.util.ListIterator;

/**
 * A utility class for replacing select-clause aliases with their
 * definitions in expression node trees.
 */
public class AliasNodeSwapper 
{
	/**
	 * Replace all instances of the node representing the alias with 
	 * the full expression. 
	 * @param exprTree - the expression node tree to make the changes in 
	 * @param alias - the select-clause alias that is to be expanded
	 * @param fullExpr - the full expression that the alias represents
	 * @return exprTree with the appropriate swaps performed, or fullExpr, 
	 *         if all of exprTree needed to be swapped
	 */
	public static ExprNode swap(ExprNode exprTree, String alias, ExprNode fullExpr)
	{
		if(fullExpr == null)
		{
			throw new NullPointerException();
		}
		
		if(isAliasNode(exprTree, alias))
		{
			return fullExpr;
		}
		else
		{
			visitChildren(exprTree, alias, fullExpr);
		}
		
		return exprTree;
	}
	
	/**
	 * A recursive function that works on the child nodes of a given
	 * node, replacing any instances of the node representing the alias, 
	 * and visiting the children of all other nodes.
	 * @param node - the node whose children are to be examined for aliases
	 * @param alias - the alias to replace
	 * @param fullExpr - the full expression corresponding to the alias
	 */
	private static void visitChildren(ExprNode node, String alias, ExprNode fullExpr)
	{
		List<ExprNode> childNodes = node.getChildNodes();

		for (ListIterator<ExprNode> itor = childNodes.listIterator(); itor.hasNext(); )
		{
			ExprNode childNode = itor.next();
			if(isAliasNode(childNode, alias))
			{
				itor.set(fullExpr);
			}
			else
			{
				visitChildren(childNode, alias, fullExpr);
			}
		}
	}

	private static boolean isAliasNode(ExprNode node, String alias)
	{
		if(node instanceof ExprIdentNode)
		{
			if(!node.getChildNodes().isEmpty())
			{
				throw new IllegalStateException("Ident node has unexpected child nodes");
			}
			return ((ExprIdentNode) node).getUnresolvedPropertyName().equals(alias);
		}
		else
		{
			return false;
		}
	}
}
