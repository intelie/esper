using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.expression;

namespace net.esper.eql.core
{
    /// <summary> A utility class for replacing select-clause aliases with their
    /// definitions in expression node trees.
    /// </summary>

    public class AliasNodeSwapper
    {
        /// <summary> Replace all instances of the node representing the alias with 
        /// the full expression. 
        /// </summary>
        /// <param name="exprTree">the expression node tree to make the changes in 
        /// </param>
        /// <param name="alias">the select-clause alias that is to be expanded
        /// </param>
        /// <param name="fullExpr">the full expression that the alias represents
        /// </param>
        /// <returns> exprTree with the appropriate swaps performed, or fullExpr, 
        /// if all of exprTree needed to be swapped
        /// </returns>
        public static ExprNode Swap(ExprNode exprTree, String alias, ExprNode fullExpr)
        {
            if (fullExpr == null)
            {
                throw new ArgumentException("fullExpr was null", "fullExpr");
            }

            if (IsAliasNode(exprTree, alias))
            {
                return fullExpr;
            }
            else
            {
                VisitChildren(exprTree, alias, fullExpr);
            }

            return exprTree;
        }

        /// <summary> A recursive function that works on the child nodes of a given
        /// node, replacing any instances of the node representing the alias, 
        /// and visiting the children of all other nodes.
        /// </summary>
        /// <param name="node">the node whose children are to be examined for aliases
        /// </param>
        /// <param name="alias">the alias to replace
        /// </param>
        /// <param name="fullExpr">the full expression corresponding to the alias
        /// </param>
        private static void VisitChildren(ExprNode node, String alias, ExprNode fullExpr)
        {
            IList<ExprNode> childNodes = node.ChildNodes;
            int childNodesLength = childNodes.Count;

            for( int ii = 0 ; ii < childNodesLength ; ii++ )
            {
                ExprNode childNode = childNodes[ii];
                if (IsAliasNode(childNode, alias))
                {
                    childNodes[ii] = fullExpr;
                }
                else
                {
                    VisitChildren(childNode, alias, fullExpr);
                }
            }
        }

        private static bool IsAliasNode(ExprNode node, String alias)
        {
            if (node is ExprIdentNode)
            {
            	if (node.ChildNodes.Count != 0)
                {
                    throw new IllegalStateException("Ident node has unexpected child nodes");
                }
                return ((ExprIdentNode)node).UnresolvedPropertyName.Equals(alias);
            }
            else
            {
                return false;
            }
        }
    }
}
