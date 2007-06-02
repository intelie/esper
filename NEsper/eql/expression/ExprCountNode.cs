using System;

using net.esper.eql.agg;
using net.esper.eql.core;

namespace net.esper.eql.expression
{
	/// <summary>
	/// Represents the count(...) and count(*) and count(distinct ...) aggregate function is an expression tree.
	/// </summary>

	public class ExprCountNode : ExprAggregateNode
	{
        /// <summary>
        /// Returns the aggregation function name for representation in a generate expression string.
        /// </summary>
        /// <value></value>
        /// <returns> aggregation function name
        /// </returns>
		protected internal override String AggregationFunctionName
		{
			get
			{
				return "count";
			}
		}
		
		/// <summary> Ctor.</summary>
		/// <param name="distinct">flag indicating unique or non-unique value aggregation
		/// </param>
		public ExprCountNode( bool distinct )
			: base( distinct )
		{
		}

	    public override AggregationMethod ValidateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService)
	    {
	        // Empty child node list signals count(*), does not ignore nulls
	        if (this.ChildNodes.Count == 0)
	        {
	            return methodResolutionService.MakeCountAggregator(false);
	        }
	        else
	        {
	            // else ignore nulls
	            if (this.ChildNodes.Count != 1)
	            {
	                throw new ExprValidationException("Count node must have zero or 1 child nodes");
	            }
	            return methodResolutionService.makeCountAggregator(true);
	        }
	    }

        /// <summary>
        /// Returns the aggregation state prototype for use in grouping aggregation states per group-by keys.
        /// </summary>
        /// <value></value>
        /// <returns> prototype aggregation state as a factory for aggregation states per group-by key value
        /// </returns>
		public override Aggregator AggregationFunction
		{
			get
			{
				if ( computer == null )
				{
					throw new SystemException( "Node has not been initalized through validate call" );
				}
				return computer;
			}
		}

        /// <summary>
        /// Return true if a expression aggregate node semantically equals the current node, or false if not.
        /// For use by the EqualsNode implementation which compares the distinct flag.
        /// </summary>
        /// <param name="node">to compare to</param>
        /// <returns>
        /// true if semantically equal, or false if not equals
        /// </returns>
		public override bool EqualsNodeAggregate( ExprAggregateNode node )
		{
			if ( !( node is ExprCountNode ) )
			{
				return false;
			}

			return true;
		}
	}
}