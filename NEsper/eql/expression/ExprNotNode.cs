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
        /// <param name="autoImportService">for resolving class names in library method invocations</param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
        public override void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
        {
            // Must have a single child node
            if (this.ChildNodes.Count != 1)
            {
                throw new ExprValidationException("The NOT node requires exactly 1 child node");
            }

            Type childType = this.ChildNodes[0].ReturnType;
            if (! TypeHelper.IsBoolean(childType))
            {
                throw new ExprValidationException("Incorrect use of NOT clause, sub-expressions do not return bool");
            }
        }

        /// <summary>
        /// Evaluate event tuple and return result.
        /// </summary>
        /// <param name="eventsPerStream">event tuple</param>
        /// <returns>
        /// evaluation result, a bool value for OR/AND-type evalution nodes.
        /// </returns>
        public override Object Evaluate(EventBean[] eventsPerStream, bool isNewData)
        {
            Boolean evaluated = (Boolean)this.ChildNodes[0].Evaluate(eventsPerStream);
            return !evaluated;
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
                buffer.Append("NOT(");
                buffer.Append(this.ChildNodes[0].ExpressionString);
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
            if (!(node is ExprNotNode))
            {
                return false;
            }

            return true;
        }
    }
}
