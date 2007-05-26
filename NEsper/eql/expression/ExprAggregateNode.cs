using System;
using System.Collections.Generic;
using System.Text;

using net.esper.compat;
using net.esper.eql.core;
using net.esper.eql.agg;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.expression
{
    ///	<summary>
    /// Base expression node that represents an aggregation function such as 'sum' or 'count'.
    ///
    /// In terms of validation each concrete aggregation node must implement it's own validation.
    ///
    /// In terms of evaluation this base class will ask the assigned
    /// <seealso cref="net.esper.eql.core.AggregationResultFuture"/>
    /// for the current state, using a column number assigned to the node.
    ///
    /// Concrete subclasses must supply an aggregation state prototype node <seealso cref="Aggregator"/>
    /// that reflects each group's (there may be group-by critera) current aggregation state.
    ///	</summary>

    public abstract class ExprAggregateNode : ExprNode
    {
        private AggregationResultFuture aggregationResultFuture;
        private int column;

        ///	<summary>
        ///	Indicator for whether the aggregation is distinct - i.e. only unique
        /// values are considered.
        ///	</summary>

        protected internal bool isDistinct;

        /// <summary>Returns the aggregation state prototype for use in grouping aggregation states per group-by keys.</summary>
        /// <returns>prototype aggregation state as a factory for aggregation states per group-by key value
        /// </returns>

        virtual public Aggregator PrototypeAggregator
        {
            get
            {
                if (!isDistinct)
                {
                    return AggregationFunction;
                }
                else
                {
                    return new UniqueValueAggregator(AggregationFunction);
                }
            }
        }

        /// <summary> Returns the aggregation function name for representation in a generate expression string.</summary>
        /// <returns> aggregation function name
        /// </returns>

        protected internal abstract String AggregationFunctionName
        {
            get;
        }

        /// <summary>
        /// Returns true if the aggregation node is only aggregatig distinct values, or false if
        /// aggregating all values.
        /// </summary>
        /// <returns> true if 'distinct' keyword was given, false if not
        /// </returns>

        virtual public bool IsDistinct
        {
            get { return isDistinct; }
        }

        /// <summary> Ctor.</summary>
        /// <param name="distinct">sets the flag indicatating whether only unique values should be aggregated
        /// </param>

        protected internal ExprAggregateNode(bool distinct)
        {
            isDistinct = distinct;
        }

        /// <summary> Returns the aggregation state prototype for use in grouping aggregation states per group-by keys.</summary>
        /// <returns> prototype aggregation state as a factory for aggregation states per group-by key value
        /// </returns>

        public abstract Aggregator AggregationFunction { get; }

        /// <summary>
        /// Return true if a expression aggregate node semantically equals the current node, or false if not.
        /// For use by the EqualsNode implementation which compares the distinct flag.
        /// </summary>
        /// <param name="node">to compare to</param>
        /// <returns> true if semantically equal, or false if not equals</returns>

        public abstract bool EqualsNodeAggregate(ExprAggregateNode node);

        /// <summary> Assigns to the node the future which can be queried for the current aggregation state at evaluation time.</summary>
        /// <param name="aggregationResultFuture">future containing state</param>
        /// <param name="column">column to hand to future for easy access</param>

        public virtual void SetAggregationResultFuture(AggregationResultFuture aggregationResultFuture, int column)
        {
            this.aggregationResultFuture = aggregationResultFuture;
            this.column = column;
        }

        /// <summary>
        /// Evaluates the specified events.
        /// </summary>
        /// <param name="events">The events.</param>
        /// <returns></returns>
        public override Object Evaluate(EventBean[] events)
        {
            return aggregationResultFuture.GetValue(column);
        }

        /// <summary> Populates into the supplied list all aggregation functions within this expression, if any.
        /// <para>Populates by going bottom-up such that nested aggregates appear first.</para>
        /// <para>I.e. sum(volume * sum(price)) would put first A then B into the list with A=sum(price) and B=sum(volume * A)</para>
        /// </summary>
        /// <param name="topNode">is the expression node to deep inspect
        /// </param>
        /// <param name="aggregateNodes">is a list of node to populate into
        /// </param>
        public static void GetAggregatesBottomUp(ExprNode topNode, IList<ExprAggregateNode> aggregateNodes)
        {
            // Map to hold per level of the node (1 to N depth) of expression node a list of aggregation expr nodes, if any
            // exist at that level
            ETreeDictionary<int, List<ExprAggregateNode>> aggregateExprPerLevel = new ETreeDictionary<int, List<ExprAggregateNode>>();

            // Recursively enter all aggregate functions and their level into map
            RecursiveAggregateEnter(topNode, aggregateExprPerLevel, 1);

            // Done if none found
            if (aggregateExprPerLevel.Count == 0)
            {
                return;
            }

            // From the deepest (highest) level to the lowest, add aggregates to list
            int deepLevel = aggregateExprPerLevel.LastKey;
            for (int i = deepLevel; i >= 1; i--)
            {
                IList<ExprAggregateNode> list = aggregateExprPerLevel.Fetch(i, null);
                if (list == null)
                {
                    continue;
                }

                foreach (ExprAggregateNode node in list)
                {
                    aggregateNodes.Add(node);
                }
            }
        }

        /// <summary>
        /// Return true if a expression node semantically equals the current node, or false if not.
        /// Concrete implementations should compare the type and any additional information
        /// that impact the evaluation of a node.
        /// </summary>
        /// <param name="node">to compare to</param>
        /// <returns>
        /// true if semantically equal, or false if not equals
        /// </returns>
        public override bool EqualsNode(ExprNode node)
        {
            if (!(node is ExprAggregateNode))
            {
                return false;
            }

            ExprAggregateNode other = (ExprAggregateNode)node;

            if (other.isDistinct != this.isDistinct)
            {
                return false;
            }

            return this.EqualsNodeAggregate(other);
        }

        private static void RecursiveAggregateEnter(ExprNode currentNode, IDictionary<Int32, List<ExprAggregateNode>> aggregateExprPerLevel, int currentLevel)
        {
            // ask all child nodes to enter themselves
            foreach (ExprNode node in currentNode.ChildNodes)
            {
                RecursiveAggregateEnter(node, aggregateExprPerLevel, currentLevel + 1);
            }

            if (!(currentNode is ExprAggregateNode))
            {
                return;
            }

            // Add myself to list, I'm an aggregate function
            List<ExprAggregateNode> aggregates = null;
            if (!aggregateExprPerLevel.TryGetValue(currentLevel, out aggregates))
            {
                aggregates = new List<ExprAggregateNode>();
                aggregateExprPerLevel[currentLevel] = aggregates;
            }

            aggregates.Add((ExprAggregateNode)currentNode);
        }

        /// <summary> For use by implementing classes, validates the aggregation node expecting
        /// a single numeric-type child node.
        /// </summary>
        /// <param name="streamTypeService">types represented in streams
        /// </param>
        /// <returns> numeric type of single child
        /// </returns>
        /// <throws> ExprValidationException if the validation failed </throws>
        protected internal Type ValidateSingleNumericChild(StreamTypeService streamTypeService)
        {
            if (this.ChildNodes.Count != 1)
            {
                throw new ExprValidationException(AggregationFunctionName + " node must have exactly 1 child node");
            }

            ExprNode child = this.ChildNodes[0];
            Type childType = child.ReturnType;
            if (!TypeHelper.IsNumeric(childType))
            {
                throw new ExprValidationException("Implicit conversion from datatype '" + childType.FullName + "' to numeric is not allowed");
            }

            return childType;
        }

        /// <summary> Renders the aggregation function expression.</summary>
        /// <returns> expression string is the textual rendering of the aggregation function and it's sub-expression
        /// </returns>
        public override String ExpressionString
        {
            get
            {
                StringBuilder buffer = new StringBuilder();
                buffer.Append(AggregationFunctionName);
                buffer.Append("(");

                if (isDistinct)
                {
                    buffer.Append("distinct ");
                }

                if (this.ChildNodes.Count > 0)
                {
                    buffer.Append(this.ChildNodes[0].ExpressionString);
                }
                else
                {
                    buffer.Append("*");
                }

                buffer.Append(")");

                return buffer.ToString();
            }
        }
    }
}