using System;

using net.esper.eql.agg;
using net.esper.eql.core;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents the sum(...) aggregate function is an expression tree.
    /// </summary>

    public class ExprSumNode : ExprAggregateNode
    {
        /// <summary>
        /// Returns the aggregation function name for representation in a generate expression string.
        /// </summary>
        /// <value></value>
        /// <returns> aggregation function name
        /// </returns>
		protected override String AggregationFunctionName
        {
            get { return "sum"; }
        }

        /// <summary> Ctor.</summary>
        /// <param name="distinct">flag indicating unique or non-unique value aggregation
        /// </param>
        public ExprSumNode(bool distinct)
            : base(distinct)
        {
        }

		protected override AggregationMethod ValidateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService)
		{
            Type childType = base.ValidateSingleNumericChild(streamTypeService);
			return methodResolutionService.MakeSumAggregator(childType);
        }

        /// <summary>
        /// Return true if a expression aggregate node semantically equals the current node, or false if not.
        /// For use by the EqualsNode implementation which compares the distinct flag.
        /// </summary>
        /// <param name="node">to compare to</param>
        /// <returns>
        /// true if semantically equal, or false if not equals
        /// </returns>
        public override bool EqualsNodeAggregate(ExprAggregateNode node)
        {
            if (!(node is ExprSumNode))
            {
                return false;
            }

            return true;
        }
    }
}
