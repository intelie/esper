using System;

using net.esper.eql.core;

namespace net.esper.eql.expression
{
	/// <summary>
	/// Represents the count(...) and count(*) and count(distinct ...) aggregate function is an expression tree.
	/// </summary>

	public class ExprCountNode : ExprAggregateNode
	{
		override protected internal String AggregationFunctionName
		{
			get
			{
				return "count";
			}

		}
		override public Type ReturnType
		{
			get
			{
				return computer.ValueType;
			}

		}
		private Aggregator computer;

		/// <summary> Ctor.</summary>
		/// <param name="distinct">- flag indicating unique or non-unique value aggregation
		/// </param>
		public ExprCountNode( bool distinct )
			: base( distinct )
		{
		}

		public override void validate( StreamTypeService streamTypeService, AutoImportService autoImportService )
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
			if ( !( node is ExprCountNode ) )
			{
				return false;
			}

			return true;
		}

		/// <summary> Counts all datapoints including null values.</summary>
		public class DatapointAggregator : Aggregator
		{
			virtual public Object Value
			{
				get
				{
					return numDataPoints;
				}

			}
			virtual public Type ValueType
			{
				get
				{
					return typeof( long );
				}

			}
			private long numDataPoints;

			public virtual void enter( Object _object )
			{
				numDataPoints++;
			}

			public virtual void leave( Object _object )
			{
				numDataPoints--;
			}

			public virtual Aggregator newAggregator()
			{
				return new DatapointAggregator();
			}
		}

		/// <summary> Count all non-null values.</summary>
		public class NonNullDatapointAggregator : Aggregator
		{
			virtual public Object Value
			{
				get
				{
					return numDataPoints;
				}

			}
			virtual public Type ValueType
			{
				get
				{
					return typeof( long );
				}

			}
			private long numDataPoints;

			public virtual void enter( Object _object )
			{
				if ( _object == null )
				{
					return;
				}
				numDataPoints++;
			}

			public virtual void leave( Object _object )
			{
				if ( _object == null )
				{
					return;
				}
				numDataPoints--;
			}

			public virtual Aggregator newAggregator()
			{
				return new NonNullDatapointAggregator();
			}
		}
	}
}