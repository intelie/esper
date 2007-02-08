using System;

using net.esper.collection;
using net.esper.eql.core;

namespace net.esper.eql.expression
{
	/// <summary>
	/// Represents the median(...) aggregate function is an expression tree.
	/// </summary>
	
	public class ExprMedianNode : ExprAggregateNode
	{
		override protected internal String AggregationFunctionName
		{
			get { return "median"; }
		}

		override public Type ReturnType
		{
			get { return computer.ValueType; }
		}

		private Aggregator computer;

		/// <summary> Ctor.</summary>
		/// <param name="distinct">- flag indicating unique or non-unique value aggregation
		/// </param>
		public ExprMedianNode( bool distinct )
			: base( distinct )
		{
		}

		public override void validate( StreamTypeService streamTypeService, AutoImportService autoImportService )
		{
			base.validateSingleNumericChild( streamTypeService );
			computer = new DoubleMedian();
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
			if ( !( node is ExprMedianNode ) )
			{
				return false;
			}

			return true;
		}

		/// <summary> Average always generates double-types numbers.</summary>
		public class DoubleMedian : Aggregator
		{
			virtual public Object Value
			{
				get
				{
					if ( vector.Count == 0 )
					{
						return null;
					}
					if ( vector.Count == 1 )
					{
						return vector[0];
					}

					int middle = vector.Count / 2;
					if ( vector.Count % 2 == 0 )
					{
						return ( vector[middle - 1] + vector[middle] ) / 2;
					}
					else
					{
						return vector[middle];
					}
				}

			}

			virtual public Type ValueType
			{
				get
				{
					return typeof( Double );
				}

			}
			private SortedDoubleVector vector;

			/// <summary> Ctor.</summary>
			public DoubleMedian()
			{
				this.vector = new SortedDoubleVector();
			}

			public virtual void enter( Object _object )
			{
				if ( _object == null )
				{
					return;
				}
				double value = Convert.ToDouble( _object );
				vector.Add( value );
			}

			public virtual void leave( Object _object )
			{
				if ( _object == null )
				{
					return;
				}
				double value = Convert.ToDouble( _object );
				vector.Remove( value );
			}

			public virtual Aggregator newAggregator()
			{
				return new DoubleMedian();
			}
		}
	}
}
