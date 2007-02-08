using System;
using System.Text;

using net.esper.eql.core;
using net.esper.events;
using net.esper.type;
using net.esper.util;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents a simple Math (+/-/divide/*) in a filter expression tree.
    /// </summary>

    public class ExprMathNode : ExprNode
    {
        override public Type ReturnType
        {
            get { return resultType; }
        }

        private readonly MathArithTypeEnum mathArithTypeEnum;

        private MathArithTypeEnum.Computer arithTypeEnumComputer;
        private Type resultType;

        /// <summary> Ctor.</summary>
        /// <param name="mathArithTypeEnum">- type of math
        /// </param>
        public ExprMathNode(MathArithTypeEnum mathArithTypeEnum)
        {
            this.mathArithTypeEnum = mathArithTypeEnum;
        }

        public override void validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            if (this.ChildNodes.Count != 2)
            {
                throw new ExprValidationException("Arithmatic node must have 2 child nodes");
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

            if (childTypeOne.Equals(childTypeTwo))
            {
                resultType = childTypeTwo;
            }
            else
            {
                resultType = TypeHelper.GetArithmaticCoercionType(childTypeOne, childTypeTwo);
            }
            arithTypeEnumComputer = mathArithTypeEnum.getComputer(resultType);
        }

        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            Object valueChildOne = this.ChildNodes[0].Evaluate(eventsPerStream);
            Object valueChildTwo = this.ChildNodes[1].Evaluate(eventsPerStream);

            if ((valueChildOne == null) || (valueChildTwo == null))
            {
                return null;
            }

            // arithTypeEnumComputer is initialized by validation
            Object result = arithTypeEnumComputer((ValueType) valueChildOne, (ValueType) valueChildTwo);
            return result;
        }

        public override String ExpressionString
        {
            get
            {
                StringBuilder buffer = new StringBuilder();
                buffer.Append("(");

                buffer.Append(this.ChildNodes[0].ExpressionString);
                buffer.Append(mathArithTypeEnum.getExpressionText());
                buffer.Append(this.ChildNodes[1].ExpressionString);

                buffer.Append(")");
                return buffer.ToString();
            }
        }

        public override bool EqualsNode(ExprNode node)
        {
            if (!(node is ExprMathNode))
            {
                return false;
            }

            ExprMathNode other = (ExprMathNode)node;

            if (other.mathArithTypeEnum != this.mathArithTypeEnum)
            {
                return false;
            }

            return true;
        }
    }
}
