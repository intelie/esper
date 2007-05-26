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
		override protected internal String AggregationFunctionName
		{
			get
			{
				return "count";
			}

		}
        /// <summary>
        /// Returns the type that the node's evaluate method returns an instance of.
        /// </summary>
        /// <value>The type.</value>
        /// <returns> type returned when evaluated
        /// </returns>
        /// <throws>ExprValidationException thrown when validation failed </throws>
		override public Type ReturnType
		{
			get
			{
				return computer.ValueType;
			}

		}
		private Aggregator computer;

		/// <summary> Ctor.</summary>
		/// <param name="distinct">flag indicating unique or non-unique value aggregation
		/// </param>
		public ExprCountNode( bool distinct )
			: base( distinct )
		{
		}

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="autoImportService">for resolving class names in library method invocations</param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
		public override void Validate( StreamTypeService streamTypeService, AutoImportService autoImportService )
		{
			// Empty child node list signals count(*)
			if ( this.ChildNodes.Count == 0 )
			{
				computer = new DatapointAggregator();
			}
			else
			{
				if ( this.ChildNodes.Count != 1 )
				{
					throw new ExprValidationException( "Count node must have zero or 1 child nodes" );
				}
				computer = new NonNullDatapointAggregator();
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

		/// <summary> Counts all datapoints including null values.</summary>
		public class DatapointAggregator : Aggregator
		{
            /// <summary>
            /// Returns the current value held.
            /// </summary>
            /// <value></value>
            /// <returns> current value
            /// </returns>
			virtual public Object Value
			{
				get
				{
                    long? value = numDataPoints;
                    return value;
				}

			}
            /// <summary>
            /// Returns the type of the current value.
            /// </summary>
            /// <value></value>
            /// <returns> type of values held
            /// </returns>
			virtual public Type ValueType
			{
				get
				{
					return typeof( long? );
				}

			}
			private long numDataPoints;

            /// <summary>
            /// Enters the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
			public virtual void Enter( Object _object )
			{
				numDataPoints++;
			}

            /// <summary>
            /// Leaves the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
			public virtual void Leave( Object _object )
			{
				numDataPoints--;
			}

            /// <summary>
            /// Make a new, initalized aggregation state.
            /// </summary>
            /// <returns>initialized copy of the aggregator</returns>
			public virtual Aggregator NewAggregator()
			{
				return new DatapointAggregator();
			}
		}

		/// <summary> Count all non-null values.</summary>
		public class NonNullDatapointAggregator : Aggregator
		{
            /// <summary>
            /// Returns the current value held.
            /// </summary>
            /// <value></value>
            /// <returns> current value
            /// </returns>
			virtual public Object Value
			{
				get
				{
                    long? value = numDataPoints;
                    return value;
				}

			}
            /// <summary>
            /// Returns the type of the current value.
            /// </summary>
            /// <value></value>
            /// <returns> type of values held
            /// </returns>
			virtual public Type ValueType
			{
				get
				{
					return typeof( long? );
				}

			}
			private long numDataPoints;

            /// <summary>
            /// Enters the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
			public virtual void Enter( Object _object )
			{
				if ( _object == null )
				{
					return;
				}
				numDataPoints++;
			}

            /// <summary>
            /// Leaves the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
			public virtual void Leave( Object _object )
			{
				if ( _object == null )
				{
					return;
				}
				numDataPoints--;
			}

            /// <summary>
            /// Make a new, initalized aggregation state.
            /// </summary>
            /// <returns>initialized copy of the aggregator</returns>
			public virtual Aggregator NewAggregator()
			{
				return new NonNullDatapointAggregator();
			}
		}
	}
}