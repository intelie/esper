using System;

using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.support.eql
{
    public class SupportAggregateExprNode : ExprAggregateNode
    {
        public static int ValidateCount
        {
            set
            {
                SupportAggregateExprNode.validateCount = value;
            }
        }

        override public Type ReturnType
        {
            get
            {
                return type;
            }
        }

        virtual public int ValidateCountSnapshot
        {
            get
            {
                return validateCountSnapshot;
            }
        }

        override public Aggregator AggregationFunction
        {
            get
            {
                return null;
            }
        }

        override protected String AggregationFunctionName
        {
            get
            {
                return "support";
            }

        }
        virtual public Object Value
        {
            set
            {
                this.value = value;
            }

        }
        private static int validateCount;

        private Type type;
        private Object value;
        private int validateCountSnapshot;

        public SupportAggregateExprNode(Type type)
            : base(false)
        {
            this.type = type;
            this.value = null;
        }

        public SupportAggregateExprNode(Object value)
            : base(false)
        {
            this.type = value.GetType();
            this.value = value;
        }

        public SupportAggregateExprNode(Object value, Type type)
            : base(false)
        {
            this.value = value;
            this.type = type;
        }

        public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            // Keep a count for if and when this was validated
            validateCount++;
            validateCountSnapshot = validateCount;
        }

        public override bool EqualsNodeAggregate(ExprAggregateNode node)
        {
            throw new System.NotSupportedException("not implemented");
        }

        public virtual void evaluateEnter(EventBean[] eventsPerStream)
        {
        }

        public virtual void evaluateLeave(EventBean[] eventsPerStream)
        {
        }
    }
}
