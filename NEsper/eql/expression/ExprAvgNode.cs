using System;

using net.esper.eql.core;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents the avg(...) aggregate function is an expression tree.
    /// </summary>

    public class ExprAvgNode : ExprAggregateNode
    {
        override protected internal String AggregationFunctionName
        {
            get
            {
                return "avg";
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
        public ExprAvgNode(bool distinct)
            : base(distinct)
        {
        }

        public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            base.ValidateSingleNumericChild(streamTypeService);
            computer = new DoubleAvg();
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
            if (!(node is ExprAvgNode))
            {
                return false;
            }

            return true;
        }

        /// <summary> Average always generates double-types numbers.</summary>
        public class DoubleAvg : Aggregator
        {
            virtual public Object Value
            {
                get
                {
                    if (numDataPoints == 0)
                    {
                        return null;
                    }

                    double? value = sum / numDataPoints;
                    return value;
                }

            }
            virtual public Type ValueType
            {
                get
                {
                    return typeof(double?);
                }

            }
            private double sum;
            private long numDataPoints;

            public virtual void enter(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints++;
                sum += Convert.ToDouble(_object);
            }

            public virtual void leave(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints--;
                sum -= Convert.ToDouble(_object);
            }

            public virtual Aggregator newAggregator()
            {
                return new DoubleAvg();
            }
        }
    }
}
