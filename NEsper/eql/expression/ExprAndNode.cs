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

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="methodResolutionService">for resolving class names in library method invocations</param>
        /// <param name="viewResourceDelegate">The view resource delegate.</param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
        public override void Validate(
			StreamTypeService streamTypeService,
			MethodResolutionService methodResolutionService,
			ViewResourceDelegate viewResourceDelegate)
        {
            // Sub-nodes must be returning bool
            foreach (ExprNode child in this.ChildNodes)
            {
                Type childType = child.ReturnType;
                if (! TypeHelper.IsBoolean( childType ))
                {
                    throw new ExprValidationException("Incorrect use of AND clause, sub-expressions do not return bool");
                }
            }

            if (this.ChildNodes.Count <= 1)
            {
                throw new ExprValidationException("The AND operator requires at least 2 child expressions");
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
            foreach (ExprNode child in this.ChildNodes)
            {
	            bool evaluated = (bool) child.Evaluate(eventsPerStream, isNewData);
	            if (evaluated == null)
	            {
	                return null;
	            }
                if (!evaluated)
                {
                    return false;
                }
            }
            return true;
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
            if (!(node is ExprAndNode))
            {
                return false;
            }

            return true;
        }
    }
}