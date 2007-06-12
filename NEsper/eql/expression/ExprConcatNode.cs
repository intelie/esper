using System;
using System.Text;

using net.esper.eql.core;
using net.esper.events;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents a simple Math (+/-/divide/*) in a filter expression tree.
    /// </summary>

    public class ExprConcatNode : ExprNode
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
            get
            {
                return typeof(String);
            }
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

        private StringBuilder buffer;

        /// <summary>
        /// Ctor.
        /// </summary>

        public ExprConcatNode()
        {
            buffer = new StringBuilder();
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
                throw new ExprValidationException("Concat node must have at least 2 child nodes");
            }

            for (int i = 0; i < this.ChildNodes.Count; i++)
            {
                Type childType = this.ChildNodes[i].ReturnType;
                if (childType != typeof(String))
                {
                    throw new ExprValidationException("Implicit conversion from datatype '" + childType.FullName + "' to string is not allowed");
                }
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
            buffer.Remove(0, buffer.Length - 0);
            foreach (ExprNode child in this.ChildNodes)
            {
                String result = (String)child.Evaluate(eventsPerStream, isNewData);
                if (result == null)
                {
                    return null;
                }
                buffer.Append(result);
            }
            return buffer.ToString();
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
                StringBuilder buffer = new StringBuilder();
                String delimiter = "(";
                foreach (ExprNode child in this.ChildNodes)
                {
                    buffer.Append(delimiter);
                    buffer.Append(child.ExpressionString);
                    delimiter = "||";
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
            if (!(node is ExprConcatNode))
            {
                return false;
            }

            return true;
        }
    }
}
