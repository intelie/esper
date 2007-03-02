using System;

using net.esper.eql.core;
using net.esper.events;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents a constant in a filter expressiun tree.
    /// </summary>
    
    public class ExprConstantNode : ExprNode
    {
        override public Type ReturnType
        {
            get
            {
                if (value == null)
                {
                    return null;
                }
                return value.GetType();
            }
        }

        private Object value;

        /// <summary> Ctor.</summary>
        /// <param name="value">is the constant's value.
        /// </param>
        public ExprConstantNode(Object value)
        {
            this.value = value;
        }

        public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
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

                if (value == null)
                {
                    return "null";
                }

                return value.ToString();
            }
        }

        public override bool EqualsNode(ExprNode node)
        {
            if (!(node is ExprConstantNode))
            {
                return false;
            }

            ExprConstantNode other = (ExprConstantNode)node;

            if ((other.value == null) && (this.value != null))
            {
                return false;
            }
            if ((other.value != null) && (this.value == null))
            {
                return false;
            }
            if ((other.value == null) && (this.value == null))
            {
                return true;
            }
            return other.value.Equals(this.value);
        }
    }
}