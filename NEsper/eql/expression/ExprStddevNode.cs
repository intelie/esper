using System;

using net.esper.eql.core;

namespace net.esper.eql.expression
{

	/// <summary>
	/// Represents the stddev(...) aggregate function is an expression tree.
	/// </summary>

	public class ExprStddevNode : ExprAggregateNode
	{
		override public Type ReturnType
		{
			get { return computer.ValueType; }
		}

		protected internal override string AggregationFunctionName
		{
			get { return "stddev"; }
		}

		private Aggregator computer;

		/// <summary> Ctor.</summary>
		/// <param name="distinct">- flag indicating unique or non-unique value aggregation
		/// </param>
		
		public ExprStddevNode( bool distinct )
			: base( distinct )
		{
		}

		public override void Validate( StreamTypeService streamTypeService, AutoImportService autoImportService )
		{
			base.ValidateSingleNumericChild( streamTypeService );
			computer = new DoubleStddev();
		}

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

			virtual public Type ValueType
			{
				get { return typeof( double? ); }
			}

			private double sum;
			private double sumSq;
			private long numDataPoints;

			public virtual void enter( Object _object )
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

			public virtual void leave( Object _object )
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

			public virtual Aggregator newAggregator()
			{
				return new DoubleStddev();
			}
		}
	}
}