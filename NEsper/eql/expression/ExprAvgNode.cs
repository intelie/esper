using System;

using net.esper.eql.core;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents the avg(...) aggregate function is an expression tree.
    /// </summary>

    public class ExprAvgNode : ExprAggregateNode
    {
        /// <summary>
        /// Returns the aggregation function name for representation in a generate expression string.
        /// </summary>
        /// <value></value>
        /// <returns> aggregation function name
        /// </returns>
        
        override protected internal String AggregationFunctionName
        {
            get
            {
                return "avg";
            }
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
            get
            {
                return computer.ValueType;
            }
        }

        private Aggregator computer;

        /// <summary> Ctor.</summary>
        /// <param name="distinct">flag indicating unique or non-unique value aggregation
        /// </param>
        public ExprAvgNode(bool distinct)
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
            base.ValidateSingleNumericChild(streamTypeService);
            computer = new DoubleAvg();
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
            if (!(node is ExprAvgNode))
            {
                return false;
            }

            return true;
        }

        /// <summary>
        /// Average always generates double-types numbers.
        /// </summary>
        
        public class DoubleAvg : Aggregator
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

                    double? value = sum / numDataPoints;
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
                sum += Convert.ToDouble(_object);
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
                sum -= Convert.ToDouble(_object);
            }

            /// <summary>
            /// Make a new, initalized aggregation state.
            /// </summary>
            /// <returns>initialized copy of the aggregator</returns>
            public virtual Aggregator NewAggregator()
            {
                return new DoubleAvg();
            }
        }
    }
}
