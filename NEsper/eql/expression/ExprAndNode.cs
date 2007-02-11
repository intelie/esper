using System;

using net.esper.eql.core;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents And-condition.
    /// </summary>

    public class ExprAndNode : ExprNode
    {
        override public Type ReturnType
        {
            get { return typeof(bool?); }

        }
        
        public override void validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            // Sub-nodes must be returning boolean
            foreach (ExprNode child in this.ChildNodes)
            {
                Type childType = child.ReturnType;
                if (! TypeHelper.IsBoolean( childType ))
                {
                    throw new ExprValidationException("Incorrect use of AND clause, sub-expressions do not return boolean");
                }
            }

            if (this.ChildNodes.Count <= 1)
            {
                throw new ExprValidationException("The AND operator requires at least 2 child expressions");
            }
        }

        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            foreach (ExprNode child in this.ChildNodes)
            {
                Boolean evaluated = (Boolean)child.Evaluate(eventsPerStream);
                if (!evaluated)
                {
                    return false;
                }
            }
            return true;
        }

        public override String ExpressionString
        {
            get
            {
                System.Text.StringBuilder buffer = new System.Text.StringBuilder();
                buffer.Append("(");

                String appendStr = "";
                foreach (ExprNode child in this.ChildNodes)
                {
                    buffer.Append(appendStr);
                    buffer.Append(child.ExpressionString);
                    appendStr = " AND ";
                }

                buffer.Append(")");
                return buffer.ToString();
            }
        }

        public override bool EqualsNode(ExprNode node)
        {
            if (!(node is ExprAndNode))
            {
                return false;
            }

            return true;
        }
    }
}