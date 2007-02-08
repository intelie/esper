using System;

using net.esper.eql.core;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents the sum(...) aggregate function is an expression tree.
    /// </summary>
    
    public class ExprSumNode : ExprAggregateNode
    {
		override protected internal String AggregationFunctionName
        {
            get
            {
                return "sum";
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
        public ExprSumNode(bool distinct)
            : base(distinct)
        {
        }

        public override void validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            Type childType = base.validateSingleNumericChild(streamTypeService);

            computer = getSumComputer(childType);
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
            if (!(node is ExprSumNode))
            {
                return false;
            }

            return true;
        }

        /// <summary> Creates the right aggregator instance for a given type of input to the aggregator.</summary>
        /// <param name="type">is the class for which to generate an aggregator for
        /// </param>
        /// <returns> aggregator for the type
        /// </returns>
        private Aggregator getSumComputer(Type type)
        {
            if ((type == typeof(long)) || (type == typeof(long)))
            {
                return new LongSum();
            }
            if ((type == typeof(Int32)) || (type == typeof(int)))
            {
                return new IntegerSum();
            }
            if ((type == typeof(Double)) || (type == typeof(double)))
            {
                return new DoubleSum();
            }
            if ((type == typeof(Single)) || (type == typeof(float)))
            {
                return new FloatSum();
            }
            return new NumberIntegerSum();
        }

        /// <summary> Sum for any number value.</summary>
        public class NumberIntegerSum : Aggregator
        {
            virtual public Object Value
            {
                get
                {
                    if (numDataPoints == 0)
                    {
                        return null;
                    }
                    return sum;
                }

            }
            virtual public Type ValueType
            {
                get
                {
                    return typeof(Int32);
                }

            }
            private int sum;
            private long numDataPoints;

            public virtual void enter(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints++;
                ValueType number = (ValueType)_object;
                sum += Convert.ToInt32(number);
            }

            public virtual void leave(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints--;
                ValueType number = (ValueType)_object;
                sum -= Convert.ToInt32(number);
            }

            public virtual Aggregator newAggregator()
            {
                return new NumberIntegerSum();
            }
        }

        /// <summary> Sum for integer values.</summary>
        public class IntegerSum : Aggregator
        {
            virtual public Object Value
            {
                get
                {
                    if (numDataPoints == 0)
                    {
                        return null;
                    }
                    return sum;
                }

            }
            virtual public Type ValueType
            {
                get
                {
                    return typeof(Int32);
                }

            }
            private int sum;
            private long numDataPoints;

            public virtual void enter(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints++;
                sum += (Int32)_object;
            }

            public virtual void leave(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints--;
                sum -= (Int32)_object;
            }

            public virtual Aggregator newAggregator()
            {
                return new IntegerSum();
            }
        }

        /// <summary> Sum for double values.</summary>
        public class DoubleSum : Aggregator
        {
            virtual public Object Value
            {
                get
                {
                    if (numDataPoints == 0)
                    {
                        return null;
                    }
                    return sum;
                }

            }
            virtual public Type ValueType
            {
                get
                {
                    return typeof(Double);
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
                sum += (Double)_object;
            }

            public virtual void leave(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints--;
                sum -= (Double)_object;
            }

            public virtual Aggregator newAggregator()
            {
                return new DoubleSum();
            }
        }

        /// <summary> Sum for long values.</summary>
        public class LongSum : Aggregator
        {
            virtual public Object Value
            {
                get
                {
                    if (numDataPoints == 0)
                    {
                        return null;
                    }
                    return sum;
                }

            }
            virtual public Type ValueType
            {
                get
                {
                    return typeof(long);
                }

            }
            private long sum;
            private long numDataPoints;

            public virtual void enter(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints++;
                sum += (long)_object;
            }

            public virtual void leave(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints--;
                sum -= (long)_object;
            }

            public virtual Aggregator newAggregator()
            {
                return new LongSum();
            }
        }

        /// <summary> Sum for float values.</summary>
        public class FloatSum : Aggregator
        {
            virtual public Object Value
            {
                get
                {
                    if (numDataPoints == 0)
                    {
                        return null;
                    }
                    return sum;
                }

            }
            virtual public Type ValueType
            {
                get
                {
                    return typeof(Single);
                }

            }
            private float sum;
            private long numDataPoints;

            public virtual void enter(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints++;
                sum += (Single)_object;
            }

            public virtual void leave(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints--;
                sum -= (Single)_object;
            }

            public virtual Aggregator newAggregator()
            {
                return new FloatSum();
            }
        }
    }
}
