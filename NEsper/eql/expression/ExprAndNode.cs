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
        override public Type ReturnType
        {
            get { return typeof(bool?); }

        }

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="autoImportService">for resolving class names in library method invocations</param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
        public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            // Sub-nodes must be returning boolean
            foreach (ExprNode child in this.ChildNodes)
            {
                Type childType = child.ReturnType;
                if (! TypeHelper.IsBoolean( childType ))
                {
                    throw new ExprValidationException("Incorrect use of AND clause, sub-expressions do not return boolean");
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
        /// <returns>
        /// evaluation result, a boolean value for OR/AND-type evalution nodes.
        /// </returns>
        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            foreach (ExprNode child in this.ChildNodes)
            {
                Boolean evaluated = (Boolean)child.Evaluate(eventsPerStream);
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