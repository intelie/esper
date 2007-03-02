using System;
using System.Text;

using net.esper.eql.core;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents the COALESCE(a,b,...) function is an expression tree.
    /// </summary>

    public class ExprCoalesceNode : ExprNode
    {
        override public Type ReturnType
        {
            get { return resultType; }
        }

        private Type resultType;
        private bool[] isNumericCoercion;

        public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            if (this.ChildNodes.Count < 2)
            {
                throw new ExprValidationException("Coalesce node must have at least 2 child nodes");
            }

            // get child expression types
            Type[] childTypes = new Type[ChildNodes.Count];
            int count = 0;
            foreach (ExprNode child in this.ChildNodes)
            {
                childTypes[count] = child.ReturnType;
                count++;
            }

            // determine coercion type
            try
            {
                resultType = TypeHelper.GetCommonCoercionType(childTypes);
            }
            catch (CoercionException ex)
            {
                throw new ExprValidationException("Implicit conversion not allowed: " + ex.Message);
            }

            // determine which child nodes need numeric coercion
            isNumericCoercion = new bool[ChildNodes.Count];
            count = 0;
            
            foreach (ExprNode child in this.ChildNodes)
            {
                if ((TypeHelper.GetBoxedType(child.ReturnType) != resultType) &&
                    (child.ReturnType != null) &&
                    (resultType != null))
                {
                    if (!TypeHelper.IsNumeric(resultType))
                    {
                        throw new ExprValidationException("Implicit conversion from datatype '" + resultType + "' to " + child.ReturnType + " is not allowed");
                    }
                    isNumericCoercion[count] = true;
                }
                count++;
            }
        }

        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            Object value = null;

            // Look for the first non-null return value
            int count = 0;
            foreach (ExprNode childNode in this.ChildNodes)
            {
                value = childNode.Evaluate(eventsPerStream);

                if (value != null)
                {
                    // Check if we need to coerce
                    if (isNumericCoercion[count])
                    {
                        return TypeHelper.CoerceNumber(value, resultType);
                    }
                    return value;
                }
                count++;
            }

            return null;
        }

        public override String ExpressionString
        {
            get
            {
                StringBuilder buffer = new StringBuilder();
                for (int i = 2; i < this.ChildNodes.Count; i++)
                {
                    buffer.Append(",");
                    buffer.Append(this.ChildNodes[i].ExpressionString);
                }
                buffer.Append(")");
                return buffer.ToString();
            }
        }

        public override bool EqualsNode(ExprNode node)
        {
            if (!(node is ExprCoalesceNode))
            {
                return false;
            }

            return true;
        }
    }
}