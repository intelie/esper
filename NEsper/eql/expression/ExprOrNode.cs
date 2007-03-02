using System;
using System.Collections.Generic;
using System.Text;

using net.esper.eql.core;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents an OR expression in a filter expression tree.
    /// </summary>

    public class ExprOrNode : ExprNode
    {
        override public Type ReturnType
        {
            get { return typeof(bool?); }
        }

        public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            // Sub-nodes must be returning boolean
            foreach (ExprNode child in this.ChildNodes)
            {
                Type childType = child.ReturnType;
                if (!TypeHelper.IsBoolean(childType))
                {
                    throw new ExprValidationException("Incorrect use of OR clause, sub-expressions do not return boolean");
                }
            }

            if (this.ChildNodes.Count <= 1)
            {
                throw new ExprValidationException("The OR operator requires at least 2 child expressions");
            }
        }

        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            // At least one child must evaluate to true
            foreach (ExprNode child in this.ChildNodes)
            {
                Boolean evaluated = (Boolean)child.Evaluate(eventsPerStream);
                if (evaluated)
                {
                    return true;
                }
            }
            return false;
        }

        public override String ExpressionString
        {
            get
            {
                StringBuilder buffer = new StringBuilder();
                buffer.Append("(");

                String appendStr = "";
                foreach (ExprNode child in this.ChildNodes)
                {
                    buffer.Append(appendStr);
                    buffer.Append(child.ExpressionString);
                    appendStr = " OR ";
                }

                buffer.Append(")");
                return buffer.ToString();
            }
        }

        public override bool EqualsNode(ExprNode node)
        {
            if (!(node is ExprOrNode))
            {
                return false;
            }

            return true;
        }
    }
}