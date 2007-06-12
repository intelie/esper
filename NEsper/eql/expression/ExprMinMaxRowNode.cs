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
        /// <summary>
        /// Returns the type that the node's evaluate method returns an instance of.
        /// </summary>
        /// <value>The type.</value>
        /// <returns> type returned when evaluated
        /// </returns>
        /// <throws>ExprValidationException thrown when validation failed </throws>
        public override Type ReturnType
        {
            get { return resultType; }
        }

        /// <summary>
        /// Returns true if the expression node's evaluation value doesn't depend on any events data,
        /// as must be determined at validation time, which is bottom-up and therefore
        /// reliably allows each node to determine constant value.
        /// </summary>
        /// <value></value>
        /// <returns>
        /// true for constant evaluation value, false for non-constant evaluation value
        /// </returns>
	    public override bool IsConstantResult
	    {
	        get { return false; }
	    } 

        private MinMaxTypeEnum minMaxTypeEnum;
        private Type resultType;

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="minMaxTypeEnum">type of compare</param>
        public ExprMinMaxRowNode(MinMaxTypeEnum minMaxTypeEnum)
        {
            this.minMaxTypeEnum = minMaxTypeEnum;
        }

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="methodResolutionService">for resolving class names in library method invocations</param>
        /// <param name="viewResourceDelegate"></param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
        public override void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
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

        /// <summary>
        /// Evaluate event tuple and return result.
        /// </summary>
        /// <param name="eventsPerStream">event tuple</param>
        /// <param name="isNewData">indicates whether we are dealing with new data (istream) or old data (rstream)</param>
        /// <returns>
        /// evaluation result, a bool value for OR/AND-type evalution nodes.
        /// </returns>
        public override Object Evaluate(EventBean[] eventsPerStream, bool isNewData)
        {
            ValueType valueChildOne = (ValueType) this.ChildNodes[0].Evaluate(eventsPerStream, isNewData);
            ValueType valueChildTwo = (ValueType) this.ChildNodes[1].Evaluate(eventsPerStream, isNewData);

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
                    ValueType valueChild = (ValueType)this.ChildNodes[i].Evaluate(eventsPerStream, isNewData);
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
                    ValueType valueChild = (ValueType)this.ChildNodes[i].Evaluate(eventsPerStream, isNewData);
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

            return TypeHelper.CoerceBoxed(result, resultType);
        }

        /// <summary>
        /// Returns the expression node rendered as a string.
        /// </summary>
        /// <value></value>
        /// <returns> string rendering of expression
        /// </returns>
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

        /// <summary>
        /// Return true if a expression node semantically equals the current node, or false if not.
        /// Concrete implementations should compare the type and any additional information
        /// that impact the evaluation of a node.
        /// </summary>
        /// <param name="node">to compare to</param>
        /// <returns>
        /// true if semantically equal, or false if not equals
        /// </returns>
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
