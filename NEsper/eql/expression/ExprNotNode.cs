using System;

using net.esper.events;
using net.esper.eql.core;
using net.esper.util;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents a NOT expression in an expression tree.
    /// </summary>

    public class ExprNotNode : ExprNode
    {
        override public Type ReturnType
        {
            get { return typeof(bool?); }
        }

        public override void validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            // Must have a single child node
            if (this.ChildNodes.Count != 1)
            {
                throw new ExprValidationException("The NOT node requires exactly 1 child node");
            }

            Type childType = this.ChildNodes[0].ReturnType;
            if (! TypeHelper.IsBoolean(childType))
            {
                throw new ExprValidationException("Incorrect use of NOT clause, sub-expressions do not return boolean");
            }
        }

        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            Boolean evaluated = (Boolean)this.ChildNodes[0].Evaluate(eventsPerStream);
            return !evaluated;
        }

        public override String ExpressionString
        {
            get
            {
                System.Text.StringBuilder buffer = new System.Text.StringBuilder();
                buffer.Append("NOT(");
                buffer.Append(this.ChildNodes[0].ExpressionString);
                buffer.Append(")");
                return buffer.ToString();
            }
        }

        public override bool EqualsNode(ExprNode node)
        {
            if (!(node is ExprNotNode))
            {
                return false;
            }

            return true;
        }
    }
}