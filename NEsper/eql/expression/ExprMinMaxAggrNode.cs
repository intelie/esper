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
		override public Type ReturnType
		{
			get { return computer.ValueType; }
		}

		override protected internal String AggregationFunctionName
		{
			get { return minMaxTypeEnum.ExpressionText; }
		}

		private readonly MinMaxTypeEnum minMaxTypeEnum;
		private Aggregator computer;
		
		/// <summary> Ctor.</summary>
		/// <param name="distinct">- indicator whether distinct values of all values min/max
		/// </param>
		/// <param name="minMaxTypeEnum">- enum for whether to minimum or maximum compute
		/// </param>
		
		public ExprMinMaxAggrNode(bool distinct, MinMaxTypeEnum minMaxTypeEnum):base(distinct)
		{
			this.minMaxTypeEnum = minMaxTypeEnum;
		}
		
		public override void validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
		{
			if (this.ChildNodes.Count != 1)
			{
				throw new ExprValidationException(minMaxTypeEnum.ToString() + " node must have exactly 1 child node");
			}
			
			ExprNode child = this.ChildNodes[0];

            computer = new MinMaxAggregator(minMaxTypeEnum, child.ReturnType);
		}
		
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
			
            virtual public Type ValueType
			{
				get { return returnType; }
			}

			private readonly MinMaxTypeEnum minMaxTypeEnum;
			private readonly Type returnType;
			
			private SortedRefCountedSet<Object> refSet;
			
			/// <summary> Ctor.</summary>
			/// <param name="minMaxTypeEnum">- enum indicating to return minimum or maximum values
			/// </param>
			/// <param name="returnType">- is the value type returned by aggregator
			/// </param>
			
            public MinMaxAggregator(MinMaxTypeEnum minMaxTypeEnum, Type returnType)
			{
				this.minMaxTypeEnum = minMaxTypeEnum;
				this.returnType = returnType;
                this.refSet = new SortedRefCountedSet<Object>();
			}
			
			public virtual void enter(Object _object)
			{
				if (_object == null)
				{
					return ;
				}
				refSet.Add(_object);
			}
			
			public virtual void leave(Object _object)
			{
				if (_object == null)
				{
					return ;
				}
				refSet.Remove(_object);
			}
			
			public virtual Aggregator newAggregator()
			{
				return new MinMaxAggregator(minMaxTypeEnum, returnType);
			}
		}
	}
}
