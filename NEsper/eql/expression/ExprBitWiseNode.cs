using System;
using System.Text;

using net.esper.eql.core;
using net.esper.events;
using net.esper.type;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents the bit-wise operators in an expression tree.
    /// </summary>

    public class ExprBitWiseNode : ExprNode
    {
        override public Type ReturnType
        {
            get { return _resultType; }
        }

        private readonly BitWiseOpEnum _bitWiseOpEnum;
        private BitWiseOpEnum.Computer _bitWiseOpEnumComputer;
        private Type _resultType;

        /// <summary> Ctor.</summary>
        /// <param name="bitWiseOpEnum_">- type of math
        /// </param>
        public ExprBitWiseNode(BitWiseOpEnum bitWiseOpEnum_)
        {
            _bitWiseOpEnum = bitWiseOpEnum_;
        }

        public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            if (this.ChildNodes.Count != 2)
            {
                throw new ExprValidationException("BitWise node must have 2 child nodes");
            }

            foreach (ExprNode child in ChildNodes)
            {
                Type childType = child.ReturnType;
                if ((!TypeHelper.IsBoolean(childType)) && (!TypeHelper.IsNumeric(childType)))
                {
                    throw new ExprValidationException("Invalid datatype for bitwise " + childType.FullName + " is not allowed");
                }
            }

            // Determine result type, set up compute function
            Type childTypeOne = this.ChildNodes[0].ReturnType;
            Type childTypeTwo = this.ChildNodes[1].ReturnType;
            if ((TypeHelper.IsFloatingPointClass(childTypeOne)) || (TypeHelper.IsFloatingPointClass(childTypeTwo)))
            {
                throw new ExprValidationException("Invalid type for bitwise " + _bitWiseOpEnum.getComputeDescription() + " operator");
            }
            else
            {
                Type childBoxedTypeOne = TypeHelper.GetBoxedType(childTypeOne);
                Type childBoxedTypeTwo = TypeHelper.GetBoxedType(childTypeTwo);

                if (childBoxedTypeOne == childBoxedTypeTwo)
                {
                    _resultType = childBoxedTypeOne;
                    _bitWiseOpEnumComputer = _bitWiseOpEnum.GetComputer(_resultType);
                }
                else
                {
                    throw new ExprValidationException("Both nodes muts be of the same type for bitwise " + _bitWiseOpEnum.getComputeDescription() + " operator");
                }
            }
        }

        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            Object valueChildOne = this.ChildNodes[0].Evaluate(eventsPerStream);
            Object valueChildTwo = this.ChildNodes[1].Evaluate(eventsPerStream);

            if ((valueChildOne == null) || (valueChildTwo == null))
            {
                return null;
            }

            // bitWiseOpEnumComputer is initialized by validation
            Object result = _bitWiseOpEnumComputer( valueChildOne, valueChildTwo);
            return result;
        }

        public override bool EqualsNode(ExprNode node)
        {
            if (!(node is ExprBitWiseNode))
            {
                return false;
            }

            ExprBitWiseNode other = (ExprBitWiseNode)node;

            if (other._bitWiseOpEnum != _bitWiseOpEnum)
            {
                return false;
            }

            return true;
        }

        public override String ExpressionString
        {
            get
            {
                StringBuilder buffer = new StringBuilder();
                buffer.Append("(");

                buffer.Append(ChildNodes[0].ExpressionString);
                buffer.Append(_bitWiseOpEnum.getComputeDescription());
                buffer.Append(ChildNodes[1].ExpressionString);

                buffer.Append(")");
                return buffer.ToString();
            }
        }

        private static readonly Log log = LogFactory.GetLog(typeof(ExprBitWiseNode));
    }
}
