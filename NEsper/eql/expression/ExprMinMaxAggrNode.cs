using System;
using System.Collections.Generic;

using net.esper.collection;
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
		override protected internal String AggregationFunctionName
		{
			get { return minMaxTypeEnum.ExpressionText; }
		}

		private readonly MinMaxTypeEnum minMaxTypeEnum;
		private Aggregator computer;

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
        /// <param name="autoImportService">for resolving class names in library method invocations</param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
		public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
		{
			if (this.ChildNodes.Count != 1)
			{
				throw new ExprValidationException(minMaxTypeEnum.ToString() + " node must have exactly 1 child node");
			}
			
			ExprNode child = this.ChildNodes[0];

            computer = new MinMaxAggregator(minMaxTypeEnum, child.ReturnType);
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
                if (computer == null)
                {
                    throw new SystemException("Node has not been initalized through validate call");
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
		public override bool EqualsNodeAggregate(ExprAggregateNode node)
		{
			if (!(node is ExprMinMaxAggrNode))
			{
				return false;
			}
			
			ExprMinMaxAggrNode other = (ExprMinMaxAggrNode) node;
			return other.minMaxTypeEnum == this.minMaxTypeEnum;
		}
		
		/// <summary>
		/// Min/max aggregator for all values.
		/// </summary>
		
		public class MinMaxAggregator : Aggregator
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
					if (minMaxTypeEnum == MinMaxTypeEnum.MAX)
					{
						return refSet.MaxValue;
					}
					else
					{
						return refSet.MinValue;
					}
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
				get { return returnType; }
			}

			private readonly MinMaxTypeEnum minMaxTypeEnum;
			private readonly Type returnType;
			
			private SortedRefCountedSet<Object> refSet;
			
			/// <summary> Ctor.</summary>
			/// <param name="minMaxTypeEnum">enum indicating to return minimum or maximum values
			/// </param>
			/// <param name="returnType">is the value type returned by aggregator
			/// </param>
			
            public MinMaxAggregator(MinMaxTypeEnum minMaxTypeEnum, Type returnType)
			{
				this.minMaxTypeEnum = minMaxTypeEnum;
				this.returnType = returnType;
                this.refSet = new SortedRefCountedSet<Object>();
			}

            /// <summary>
            /// Enters the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
			public virtual void Enter(Object _object)
			{
				if (_object == null)
				{
					return ;
				}
				refSet.Add(_object);
			}

            /// <summary>
            /// Leaves the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
			public virtual void Leave(Object _object)
			{
				if (_object == null)
				{
					return ;
				}
				refSet.Remove(_object);
			}

            /// <summary>
            /// Make a new, initalized aggregation state.
            /// </summary>
            /// <returns>initialized copy of the aggregator</returns>
			public virtual Aggregator NewAggregator()
			{
				return new MinMaxAggregator(minMaxTypeEnum, returnType);
			}
		}
	}
}
