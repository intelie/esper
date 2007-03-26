using System;

using net.esper.eql.core;

namespace net.esper.eql.expression
{

	/// <summary>
	/// Represents the stddev(...) aggregate function is an expression tree.
	/// </summary>

	public class ExprStddevNode : ExprAggregateNode
	{
        /// <summary>
        /// Returns the type that the node's evaluate method returns an instance of.
        /// </summary>
        /// <value>The type.</value>
        /// <returns> type returned when evaluated
        /// </returns>
        /// <throws>ExprValidationException thrown when validation failed </throws>
		override public Type ReturnType
		{
			get { return computer.ValueType; }
		}

        /// <summary>
        /// Returns the aggregation function name for representation in a generate expression string.
        /// </summary>
        /// <value></value>
        /// <returns> aggregation function name
        /// </returns>
		protected internal override string AggregationFunctionName
		{
			get { return "stddev"; }
		}

		private Aggregator computer;

		/// <summary> Ctor.</summary>
		/// <param name="distinct">flag indicating unique or non-unique value aggregation
		/// </param>
		
		public ExprStddevNode( bool distinct )
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
			base.ValidateSingleNumericChild( streamTypeService );
			computer = new DoubleStddev();
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
			if ( !( node is ExprStddevNode ) )
			{
				return false;
			}

			return true;
		}

		/// <summary>
		/// Standard deviation always generates double-types numbers.
		/// </summary>
		
		public class DoubleStddev : Aggregator
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
					if ( numDataPoints < 2 )
					{
						return null;
					}

					double variance = ( sumSq - sum * sum / numDataPoints ) / ( numDataPoints - 1 );
                    double? stddev = Math.Sqrt( variance );
                    return stddev;
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
				get { return typeof( double? ); }
			}

			private double sum;
			private double sumSq;
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

				double value = Convert.ToDouble( _object );

				numDataPoints++;
				sum += value;
				sumSq += value * value;
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

				double value = Convert.ToDouble( _object );

				numDataPoints--;
				sum -= value;
				sumSq -= value * value;
			}

            /// <summary>
            /// Make a new, initalized aggregation state.
            /// </summary>
            /// <returns>initialized copy of the aggregator</returns>
			public virtual Aggregator NewAggregator()
			{
				return new DoubleStddev();
			}
		}
	}
}