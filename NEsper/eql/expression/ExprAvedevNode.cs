using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.eql.core;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents the avedev(...) aggregate function is an expression tree.
    /// </summary>

    public class ExprAvedevNode : ExprAggregateNode
    {
        override public Type ReturnType
        {
            get { return computer.ValueType; }
        }

        override protected internal String AggregationFunctionName
        {
            get { return "avedev"; }
        }

        private Aggregator computer;

        /// <summary> Ctor.</summary>
        /// <param name="distinct">- flag indicating unique or non-unique value aggregation
        /// </param>
        public ExprAvedevNode(bool distinct)
            : base(distinct)
        {
        }

        public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            base.ValidateSingleNumericChild(streamTypeService);
            computer = new DoubleAvedev();
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
            if (!(node is ExprAvedevNode))
            {
                return false;
            }

            return true;
        }

        /// <summary> Standard deviation always generates double-types numbers.</summary>
        public class DoubleAvedev : Aggregator
        {
            virtual public Object Value
            {
                get
                {
                    int datapoints = valueSet.Count;

                    if (datapoints == 0)
                    {
                        return null;
                    }

                    double total = 0;
                    double avg = sum / datapoints;

                    foreach( KeyValuePair<Double,Int32> entry in valueSet )
                    {
                        total += entry.Value * Math.Abs(entry.Key - avg);
                    }

                    double? value = total / datapoints;
                    return value;
                }

            }

            virtual public Type ValueType
            {
                get { return typeof(double?); }
            }

            private RefCountedSet<Double> valueSet;
            private double sum;

            /// <summary> Ctor.</summary>
            public DoubleAvedev()
            {
                valueSet = new RefCountedSet<Double>();
            }

            public virtual void enter(Object _object)
            {
                if (_object == null)
                {
                    return;
                }

                double value = Convert.ToDouble(_object);
                valueSet.Add(value);
                sum += value;
            }

            public virtual void leave(Object _object)
            {
                if (_object == null)
                {
                    return;
                }

                double value = Convert.ToDouble(_object);
                valueSet.Remove(value);
                sum -= value;
            }

            public virtual Aggregator newAggregator()
            {
                return new DoubleAvedev();
            }
        }
    }
}
