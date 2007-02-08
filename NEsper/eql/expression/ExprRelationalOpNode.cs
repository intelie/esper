using System;

using net.esper.eql.core;
using net.esper.events;
using net.esper.type;
using net.esper.util;

namespace net.esper.eql.expression
{

    /// <summary>
    /// Represents a lesser or greater then (</<=/>/>=) expression in a filter expression tree.
    /// </summary>

    public class ExprRelationalOpNode : ExprNode
    {
        override public Type ReturnType
        {
            get { return typeof(Boolean); }
        }

        private readonly RelationalOpEnum relationalOpEnum;
        private RelationalOpEnum.Computer computer;

        /// <summary> Ctor.</summary>
        /// <param name="relationalOpEnum">- type of compare, ie. lt, gt, le, ge
        /// </param>
        public ExprRelationalOpNode(RelationalOpEnum relationalOpEnum)
        {
            this.relationalOpEnum = relationalOpEnum;
        }

        public override void validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            // Must have 2 child nodes
            if (this.ChildNodes.Count != 2)
            {
                throw new SystemException("Relational op node does not have exactly 2 child nodes");
            }

            // Must be either numeric or string
            Type typeOne = this.ChildNodes[0].ReturnType;
            Type typeTwo = this.ChildNodes[1].ReturnType;

            if ((typeOne != typeof(String)) || (typeTwo != typeof(String)))
            {
                if (!TypeHelper.IsNumeric(typeOne))
                {
                    throw new ExprValidationException("Implicit conversion from datatype '" + typeOne.FullName + "' to numeric is not allowed");
                }
                if (!TypeHelper.IsNumeric(typeTwo))
                {
                    throw new ExprValidationException("Implicit conversion from datatype '" + typeTwo.FullName + "' to numeric is not allowed");
                }
            }

            Type compareType = TypeHelper.GetCompareToCoercionType(typeOne, typeTwo);
            computer = relationalOpEnum.getComputer(compareType);
        }

        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            Object valueLeft = this.ChildNodes[0].Evaluate(eventsPerStream);
            Object valueRight = this.ChildNodes[1].Evaluate(eventsPerStream);

            if ((valueLeft == null) || (valueRight == null))
            {
                return false;
            }

            return computer(valueLeft, valueRight);
        }

        public override String ExpressionString
        {
            get
            {
                System.Text.StringBuilder buffer = new System.Text.StringBuilder();

                buffer.Append(this.ChildNodes[0].ExpressionString);
                buffer.Append(relationalOpEnum.getExpressionText());
                buffer.Append(this.ChildNodes[1].ExpressionString);

                return buffer.ToString();
            }
        }

        public override bool EqualsNode(ExprNode node)
        {
            if (!(node is ExprRelationalOpNode))
            {
                return false;
            }

            ExprRelationalOpNode other = (ExprRelationalOpNode)node;

            if (other.relationalOpEnum != this.relationalOpEnum)
            {
                return false;
            }

            return true;
        }
    }
}
