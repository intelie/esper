using System;
using System.Text;

using net.esper.eql.core;
using net.esper.events;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents a simple Math (+/-/divide/*) in a filter expression tree.
    /// </summary>

    public class ExprConcatNode : ExprNode
    {
        override public Type ReturnType
        {
            get
            {
                return typeof(String);
            }
        }

        private StringBuilder buffer;

        /// <summary>
        /// Ctor.
        /// </summary>

        public ExprConcatNode()
        {
            buffer = new StringBuilder();
        }

        public override void validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            if (this.ChildNodes.Count < 2)
            {
                throw new ExprValidationException("Concat node must have at least 2 child nodes");
            }

            for (int i = 0; i < this.ChildNodes.Count; i++)
            {
                Type childType = this.ChildNodes[i].ReturnType;
                if (childType != typeof(String))
                {
                    throw new ExprValidationException("Implicit conversion from datatype '" + childType.FullName + "' to string is not allowed");
                }
            }
        }

        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            buffer.Remove(0, buffer.Length - 0);
            foreach (ExprNode child in this.ChildNodes)
            {
                String result = (String)child.Evaluate(eventsPerStream);
                if (result == null)
                {
                    return null;
                }
                buffer.Append(result);
            }
            return buffer.ToString();
        }

        public override String ExpressionString
        {
            get
            {
                StringBuilder buffer = new StringBuilder();
                String delimiter = "(";
                foreach (ExprNode child in this.ChildNodes)
                {
                    buffer.Append(delimiter);
                    buffer.Append(child.ExpressionString);
                    delimiter = "||";
                }
                buffer.Append(")");
                return buffer.ToString();
            }
        }

        public override bool EqualsNode(ExprNode node)
        {
            if (!(node is ExprConcatNode))
            {
                return false;
            }

            return true;
        }
    }
}