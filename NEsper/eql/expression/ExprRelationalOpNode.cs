using System;

using net.esper.compat;
using net.esper.eql.core;
using net.esper.events;
using net.esper.type;
using net.esper.util;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents a lesser or greater then (&lt;/&lt;=/&gt;/&gt;=) expression in a filter expression tree.
    /// </summary>

    public class ExprRelationalOpNode : ExprNode
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
            get { return typeof(bool?); }
        }

	    public override bool IsConstantResult
	    {
	        get { return false; }
	    }

	    /// <summary>Returns the type of relational op used.</summary>
	    /// <returns>enum with relational op type</returns>
	    public RelationalOpEnum RelationalOpEnum
	    {
	        get { return relationalOpEnum; }
	    }

		private readonly RelationalOpEnum relationalOpEnum;
        private RelationalOpEnum.Computer computer;

        /// <summary> Ctor.</summary>
        /// <param name="relationalOpEnum">type of compare, ie. lt, gt, le, ge
        /// </param>
        public ExprRelationalOpNode(RelationalOpEnum relationalOpEnum)
        {
            this.relationalOpEnum = relationalOpEnum;
        }

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="methodResolutionService">for resolving class names in library method invocations</param>
        /// <param name="viewResourceDelegate">The view resource delegate.</param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
        public override void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
        {
            // Must have 2 child nodes
            if (this.ChildNodes.Count != 2)
            {
                throw new IllegalStateException("Relational op node does not have exactly 2 child nodes");
            }

            // Must be either numeric or string
            Type typeOne = TypeHelper.GetBoxedType(this.ChildNodes[0].ReturnType);
            Type typeTwo = TypeHelper.GetBoxedType(this.ChildNodes[1].ReturnType);

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
            computer = relationalOpEnum.GetComputer(compareType);
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
            Object valueLeft = this.ChildNodes[0].Evaluate(eventsPerStream, isNewData);
            Object valueRight = this.ChildNodes[1].Evaluate(eventsPerStream, isNewData);

            if ((valueLeft == null) || (valueRight == null))
            {
                return false;
            }

            return computer(valueLeft, valueRight);
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

                buffer.Append(this.ChildNodes[0].ExpressionString);
                buffer.Append(relationalOpEnum.ExpressionText);
                buffer.Append(this.ChildNodes[1].ExpressionString);

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
