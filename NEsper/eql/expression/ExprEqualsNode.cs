using System;
using System.Text;

using net.esper.eql.core;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.expression
{
    /// <summary> 
    /// Represents an equals (=) comparator in a filter expressiun tree.
    /// </summary>

    public class ExprEqualsNode : ExprNode
    {
        /// <summary> Returns true if this is a NOT EQUALS node, false if this is a EQUALS node.</summary>
        /// <returns> true for !=, false for =
        /// </returns>
        virtual public bool NotEquals
        {
            get { return isNotEquals; }
        }

        override public Type ReturnType
        {
            get { return typeof(bool?); }
        }

        private readonly bool isNotEquals;

        private bool mustCoerce;
        private Type coercionType;

        /// <summary> Ctor.</summary>
        /// <param name="isNotEquals">- true if this is a (!=) not equals rather then equals, false if its a '=' equals
        /// </param>
        public ExprEqualsNode(bool isNotEquals)
        {
            this.isNotEquals = isNotEquals;
        }

        public override void validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            // Must have 2 child nodes
            if (this.ChildNodes.Count != 2)
            {
                throw new SystemException("Equals node does not have exactly 2 child nodes");
            }

            // Must be the same boxed type returned by expressions under this
            Type typeOne = TypeHelper.GetBoxedType(this.ChildNodes[0].ReturnType);
            Type typeTwo = TypeHelper.GetBoxedType(this.ChildNodes[1].ReturnType);

            // Null constants can be compared for any type
            if ((typeOne == null) || (typeTwo == null))
            {
                return;
            }

            // Get the common type such as Bool, String or Double and Long
            try
            {
                coercionType = TypeHelper.GetCompareToCoercionType(typeOne, typeTwo);
            }
            catch (ArgumentException)
            {
                throw new ExprValidationException("Implicit conversion from datatype '" + typeOne.FullName + "' to '" + typeTwo.FullName + "' is not allowed");
            }

            // Check if we need to coerce
            if ((coercionType == TypeHelper.GetBoxedType(typeOne)) &&
                (coercionType == TypeHelper.GetBoxedType(typeTwo)))
            {
                mustCoerce = false;
            }
            else
            {
                if (!TypeHelper.IsNumeric(coercionType))
                {
                    throw new SystemException("Coercion type " + coercionType + " not numeric");
                }
                mustCoerce = true;
            }
        }

        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            Object leftResult = this.ChildNodes[0].Evaluate(eventsPerStream);
            Object rightResult = this.ChildNodes[1].Evaluate(eventsPerStream);

            if (leftResult == null)
            {
                return (rightResult == null) ^ isNotEquals;
            }
            if (rightResult == null)
            {
                return (leftResult == null) ^ isNotEquals;
            }

            if (!mustCoerce)
            {
                return leftResult.Equals(rightResult) ^ isNotEquals;
            }
            else
            {
                Object left = TypeHelper.CoerceNumber(leftResult, coercionType);
                Object right = TypeHelper.CoerceNumber(rightResult, coercionType);
                return left.Equals(right) ^ isNotEquals;
            }
        }

        public override String ExpressionString
        {
            get
            {
                StringBuilder buffer = new StringBuilder();

                buffer.Append(this.ChildNodes[0].ExpressionString);
                buffer.Append(" = ");
                buffer.Append(this.ChildNodes[1].ExpressionString);

                return buffer.ToString();
            }
        }

        public override bool EqualsNode(ExprNode node)
        {
            if (!(node is ExprEqualsNode))
            {
                return false;
            }

            ExprEqualsNode other = (ExprEqualsNode)node;

            if (other.isNotEquals != this.isNotEquals)
            {
                return false;
            }

            return true;
        }
    }
}