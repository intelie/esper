using System;

using net.esper.eql.expression;

namespace net.esper.eql.join.plan
{

    /// <summary> Analyzes a filter expression and builds a query graph model.
    /// The 'equals' and 'and' expressions in the filter expression are extracted
    /// and placed in the query graph model as navigable relationships (by key and index
    /// properties) between streams.
    /// </summary>
    public class FilterExprAnalyzer
    {
        /// <summary> Analyzes filter expression to build query graph model.</summary>
        /// <param name="topNode">filter top node
        /// </param>
        /// <param name="queryGraph">model containing relationships between streams, to be written to
        /// </param>
        public static void Analyze(ExprNode topNode, QueryGraph queryGraph)
        {
            // Analyze relationships between streams. Relationships are properties in AND and EQUALS nodes of joins.
            if (topNode is ExprEqualsNode)
            {
                ExprEqualsNode EqualsNode = (ExprEqualsNode)topNode;
                if (!EqualsNode.NotEquals)
                {
                    AnalyzeEqualsNode(EqualsNode, queryGraph);
                }
            }
            else if (topNode is ExprAndNode)
            {
                ExprAndNode andNode = (ExprAndNode)topNode;
                AnalyzeAndNode(andNode, queryGraph);
            }
        }

        /// <summary> Analye EQUALS (=) node.</summary>
        /// <param name="EqualsNode">node to analyze
        /// </param>
        /// <param name="queryGraph">store relationships between stream properties
        /// </param>
        public static void AnalyzeEqualsNode(ExprEqualsNode EqualsNode, QueryGraph queryGraph)
        {
            if ((!(EqualsNode.ChildNodes[0] is ExprIdentNode)) || (!(EqualsNode.ChildNodes[1] is ExprIdentNode)))
            {
                return;
            }

            ExprIdentNode identNodeLeft = (ExprIdentNode)EqualsNode.ChildNodes[0];
            ExprIdentNode identNodeRight = (ExprIdentNode)EqualsNode.ChildNodes[1];

            queryGraph.Add(identNodeLeft.StreamId, identNodeLeft.ResolvedPropertyName, identNodeRight.StreamId, identNodeRight.ResolvedPropertyName);
        }

        /// <summary> Analyze the AND-node.</summary>
        /// <param name="andNode">node to analyze
        /// </param>
        /// <param name="queryGraph">to store relationships between stream properties
        /// </param>
        public static void AnalyzeAndNode(ExprAndNode andNode, QueryGraph queryGraph)
        {
            foreach (ExprNode childNode in andNode.ChildNodes)
            {
                ExprEqualsNode equalsNode = childNode as ExprEqualsNode;
                if ((equalsNode != null) && (!equalsNode.NotEquals))
                {
                    AnalyzeEqualsNode(equalsNode, queryGraph);
                }
            }
        }
    }
}
