using System;

using net.esper.eql.core;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents the sum(...) aggregate function is an expression tree.
    /// </summary>
    
    public class ExprSumNode : ExprAggregateNode
    {
        /// <summary>
        /// Returns the aggregation function name for representation in a generate expression string.
        /// </summary>
        /// <value></value>
        /// <returns> aggregation function name
        /// </returns>
		override protected internal String AggregationFunctionName
        {
            get { return "sum"; }
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
            get { return computer.ValueType; }
        }
        
        private Aggregator computer;

        /// <summary> Ctor.</summary>
        /// <param name="distinct">flag indicating unique or non-unique value aggregation
        /// </param>
        public ExprSumNode(bool distinct)
            : base(distinct)
        {
        }

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="autoImportService">for resolving class names in library method invocations</param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
        public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            Type childType = base.ValidateSingleNumericChild(streamTypeService);

            computer = getSumComputer(childType);
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
            if ((type == typeof(long?)) || (type == typeof(long)))
            {
                return new LongSum();
            }
            if ((type == typeof(int?)) || (type == typeof(int)))
            {
                return new IntegerSum();
            }
            if ((type == typeof(double?)) || (type == typeof(double)))
            {
                return new DoubleSum();
            }
            if ((type == typeof(float?)) || (type == typeof(float)))
            {
                return new FloatSum();
            }
            return new NumberIntegerSum();
        }

        /// <summary> Sum for any number value.</summary>
        public class NumberIntegerSum : Aggregator
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
                    if (numDataPoints == 0)
                    {
                        return null;
                    }
                    int? value = sum;
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
                    return typeof(int?);
                }

            }
            private int sum;
            private long numDataPoints;

            /// <summary>
            /// Enters the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
            public virtual void Enter(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints++;
                ValueType number = (ValueType)_object;
                sum += Convert.ToInt32(number);
            }

            /// <summary>
            /// Leaves the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
            public virtual void Leave(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints--;
                ValueType number = (ValueType)_object;
                sum -= Convert.ToInt32(number);
            }

            /// <summary>
            /// Make a new, initalized aggregation state.
            /// </summary>
            /// <returns>initialized copy of the aggregator</returns>
            public virtual Aggregator NewAggregator()
            {
                return new NumberIntegerSum();
            }
        }

        /// <summary> Sum for integer values.</summary>
        public class IntegerSum : Aggregator
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
                    if (numDataPoints == 0)
                    {
                        return null;
                    }

                    int? value = sum;
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
                    return typeof(int?);
                }
            }

            private int sum;
            private long numDataPoints;

            /// <summary>
            /// Enters the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
            public virtual void Enter(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints++;
                sum += (Int32)_object;
            }

            /// <summary>
            /// Leaves the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
            public virtual void Leave(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints--;
                sum -= (Int32)_object;
            }

            /// <summary>
            /// Make a new, initalized aggregation state.
            /// </summary>
            /// <returns>initialized copy of the aggregator</returns>
            public virtual Aggregator NewAggregator()
            {
                return new IntegerSum();
            }
        }

        /// <summary> Sum for double values.</summary>
        public class DoubleSum : Aggregator
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
                    if (numDataPoints == 0)
                    {
                        return null;
                    }
                    double? value = sum;
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
                    return typeof(double?);
                }
            }

            private double sum;
            private long numDataPoints;

            /// <summary>
            /// Enters the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
            public virtual void Enter(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints++;
                sum += (Double)_object;
            }

            /// <summary>
            /// Leaves the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
            public virtual void Leave(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints--;
                sum -= (Double)_object;
            }

            /// <summary>
            /// Make a new, initalized aggregation state.
            /// </summary>
            /// <returns>initialized copy of the aggregator</returns>
            public virtual Aggregator NewAggregator()
            {
                return new DoubleSum();
            }
        }

        /// <summary>
        /// Sum for long values.
        /// </summary>
        
        public class LongSum : Aggregator
        {
            /// <summary>
            /// Constructs a new long sum aggregator
            /// </summary>

            public LongSum()
            {
                ;
            }

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
                    if (numDataPoints == 0)
                    {
                        return null;
                    }

                    long? value = sum;
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
                    return typeof(long?);
                }
            }

            private long sum;
            private long numDataPoints;

            /// <summary>
            /// Enters the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
            public virtual void Enter(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints++;
                sum += (long)_object;
            }

            /// <summary>
            /// Leaves the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
            public virtual void Leave(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints--;
                sum -= (long)_object;
            }

            /// <summary>
            /// Make a new, initalized aggregation state.
            /// </summary>
            /// <returns>initialized copy of the aggregator</returns>
            public virtual Aggregator NewAggregator()
            {
                return new LongSum();
            }
        }

        /// <summary> 
        /// Sum for float values.
        /// </summary>
        
        public class FloatSum : Aggregator
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
                    if (numDataPoints == 0)
                    {
                        return null;
                    }
                    float? value = sum;
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
                    return typeof(float?);
                }
            }

            private float sum;
            private long numDataPoints;

            /// <summary>
            /// Enters the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
            public virtual void Enter(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints++;
                sum += (Single)_object;
            }

            /// <summary>
            /// Leaves the specified _object.
            /// </summary>
            /// <param name="_object">The _object.</param>
            public virtual void Leave(Object _object)
            {
                if (_object == null)
                {
                    return;
                }
                numDataPoints--;
                sum -= (Single)_object;
            }

            /// <summary>
            /// Make a new, initalized aggregation state.
            /// </summary>
            /// <returns>initialized copy of the aggregator</returns>
            public virtual Aggregator NewAggregator()
            {
                return new FloatSum();
            }
        }
    }
}
