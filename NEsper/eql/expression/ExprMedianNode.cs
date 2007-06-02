using System;

using net.esper.collection;
using net.esper.eql.agg;
using net.esper.eql.core;

namespace net.esper.eql.expression
{
	/// <summary>
	/// Represents the median(...) aggregate function is an expression tree.
	/// </summary>
	
	public class ExprMedianNode : ExprAggregateNode
	{
        /// <summary>
        /// Returns the aggregation function name for representation in a generate expression string.
        /// </summary>
        /// <value></value>
        /// <returns> aggregation function name
        /// </returns>
		override protected internal String AggregationFunctionName
		{
			get { return "median"; }
		}

		/// <summary> Ctor.</summary>
		/// <param name="distinct">flag indicating unique or non-unique value aggregation
		/// </param>
		public ExprMedianNode( bool distinct )
			: base( distinct )
		{
		}

		public override AggregationMethod ValidateAggregationChild( StreamTypeService streamTypeService, AutoImportService autoImportService )
		{
			base.ValidateSingleNumericChild( streamTypeService );
			return methodResolutionService.makeMedianAggregator();
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
			if ( !( node is ExprMedianNode ) )
			{
				return false;
			}

			return true;
		}
	}
}
