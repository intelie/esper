using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.eql.agg;
using net.esper.eql.core;
using net.esper.type;

namespace net.esper.eql.expression
{
	/// <summary>
    /// Represents the min/max(distinct? ...) aggregate function is an expression tree.
    /// </summary>

    public class ExprMinMaxAggrNode : ExprAggregateNode
	{
        /// <summary>
        /// Returns the aggregation function name for representation in a generate expression string.
        /// </summary>
        /// <value></value>
        /// <returns> aggregation function name
        /// </returns>
		public override String AggregationFunctionName
		{
			get { return minMaxTypeEnum.ExpressionText; }
		}

		private readonly MinMaxTypeEnum minMaxTypeEnum;

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="distinct">indicator whether distinct values of all values min/max</param>
        /// <param name="minMaxTypeEnum">enum for whether to minimum or maximum compute</param>

		public ExprMinMaxAggrNode(bool distinct, MinMaxTypeEnum minMaxTypeEnum):base(distinct)
		{
			this.minMaxTypeEnum = minMaxTypeEnum;
		}

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="methodResolutionService">used for resolving method and function names</param>
        /// <returns>aggregation function use</returns>
        /// <throws>ExprValidationException thrown when validation failed </throws>
		protected override AggregationMethod ValidateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService)
		{
			if (this.ChildNodes.Count != 1)
			{
				throw new ExprValidationException(minMaxTypeEnum.ToString() + " node must have exactly 1 child node");
			}

	        ExprNode child = this.ChildNodes[0];
	        return methodResolutionService.MakeMinMaxAggregator(minMaxTypeEnum, child.ReturnType);
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
			if (!(node is ExprMinMaxAggrNode))
			{
				return false;
			}

			ExprMinMaxAggrNode other = (ExprMinMaxAggrNode) node;
			return other.minMaxTypeEnum == this.minMaxTypeEnum;
		}
	}
}
