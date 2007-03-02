using System;
using System.Text;

using net.esper.eql.core;
using net.esper.events;
using net.esper.type;
using net.esper.util;

namespace net.esper.eql.expression
{

    /// <summary>
    /// Represents the MAX(a,b) and MIN(a,b) functions is an expression tree.
    /// </summary>

    public class ExprMinMaxRowNode : ExprNode
    {
        override public Type ReturnType
        {
            get { return resultType; }
        }

        private MinMaxTypeEnum minMaxTypeEnum;
        private Type resultType;

        /// <summary> Ctor.</summary>
        /// <param name="minMaxTypeEnum">- type of compare
        /// </param>
        public ExprMinMaxRowNode(MinMaxTypeEnum minMaxTypeEnum)
        {
            this.minMaxTypeEnum = minMaxTypeEnum;
        }

        public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            if (this.ChildNodes.Count < 2)
            {
                throw new ExprValidationException("MinMax node must have at least 2 child nodes");
            }

            foreach (ExprNode child in this.ChildNodes)
            {
                Type childType = child.ReturnType;
                if (!TypeHelper.IsNumeric(childType))
                {
                    throw new ExprValidationException("Implicit conversion from datatype '" + childType.FullName + "' to numeric is not allowed");
                }
            }

            // Determine result type, set up compute function
            Type childTypeOne = this.ChildNodes[0].ReturnType;
            Type childTypeTwo = this.ChildNodes[1].ReturnType;
            resultType = TypeHelper.GetArithmaticCoercionType(childTypeOne, childTypeTwo);

            for (int i = 2; i < this.ChildNodes.Count ; i++)
            {
                resultType = TypeHelper.GetArithmaticCoercionType(resultType, this.ChildNodes[i].ReturnType);
            }
        }

        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            ValueType valueChildOne = (ValueType) this.ChildNodes[0].Evaluate(eventsPerStream);
            ValueType valueChildTwo = (ValueType) this.ChildNodes[1].Evaluate(eventsPerStream);

            if ((valueChildOne == null) || (valueChildTwo == null))
            {
                return null;
            }

            ValueType result = null;
            if (minMaxTypeEnum == MinMaxTypeEnum.MAX)
            {
                if (Convert.ToDouble(valueChildOne) > Convert.ToDouble(valueChildTwo))
                {
                    result = valueChildOne;
                }
                else
                {
                    result = valueChildTwo;
                }

                for (int i = 2; i < this.ChildNodes.Count; i++)
                {
                    ValueType valueChild = (ValueType)this.ChildNodes[i].Evaluate(eventsPerStream);
                    if (valueChild == null)
                    {
                        return null;
                    }
                    if (Convert.ToDouble(valueChild) > Convert.ToDouble(result))
                    {
                        result = valueChild;
                    }
                }
            }
            else
            {
                if (Convert.ToDouble(valueChildOne) > Convert.ToDouble(valueChildTwo))
                {
                    result = valueChildTwo;
                }
                else
                {
                    result = valueChildOne;
                }
                for (int i = 2; i < this.ChildNodes.Count; i++)
                {
                    ValueType valueChild = (ValueType)this.ChildNodes[i].Evaluate(eventsPerStream);
                    if (valueChild == null)
                    {
                        return null;
                    }
                    if (Convert.ToDouble(valueChild) < Convert.ToDouble(result))
                    {
                        result = valueChild;
                    }
                }
            }

            return TypeHelper.CoerceNumber(result, resultType);
        }

        public override String ExpressionString
        {
            get
            {
                System.Text.StringBuilder buffer = new System.Text.StringBuilder();
                buffer.Append(minMaxTypeEnum.ExpressionText);
                buffer.Append("(");

                buffer.Append(this.ChildNodes[0].ExpressionString);
                buffer.Append(",");
                buffer.Append(this.ChildNodes[1].ExpressionString);

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
            if (!(node is ExprMinMaxRowNode))
            {
                return false;
            }

            ExprMinMaxRowNode other = (ExprMinMaxRowNode)node;

            if (other.minMaxTypeEnum != this.minMaxTypeEnum)
            {
                return false;
            }

            return true;
        }
    }
}
