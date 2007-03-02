using System;

using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.support.eql
{
    public class SupportExprNode : ExprNode
    {
        public static int ValidateCount
        {
            set
            {
                SupportExprNode.validateCount = value;
            }

        }

        public override Type ReturnType
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

        public SupportExprNode(Type type)
        {
            this.type = type;
            this.value = null;
        }

        public SupportExprNode(Object value)
        {
            this.type = value.GetType();
            this.value = value;
        }

        public SupportExprNode(Object value, Type type)
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

        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            return value;
        }

        public override String ExpressionString
        {
            get
            {
                if (value is String)
                {
                    return "\"" + value + "\"";
                }

                return value.ToString();
            }
        }

        public override bool EqualsNode(ExprNode node)
        {
            throw new System.NotSupportedException("not implemented");
        }
    }
}
