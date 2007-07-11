using System;
using System.Text;

using net.esper.eql.core;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents the like-clause in an expression tree.
    /// </summary>

    public class ExprLikeNode : ExprNode
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
                return typeof(bool?);
            }
        }

	    public override bool IsConstantResult
	    {
	        get { return false; }
	    }

        private readonly bool isNot;

        private bool isNumericValue;
        private bool isConstantPattern;
        private LikeUtil likeUtil;

        /// <summary> Ctor.</summary>
        /// <param name="not">is true if this is a "not like", or false if just a like
        /// </param>
        public ExprLikeNode(bool not)
        {
            this.isNot = not;
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
            if ((this.ChildNodes.Count != 2) && (this.ChildNodes.Count != 3))
            {
                throw new ExprValidationException("The 'like' operator requires 2 (no escape) or 3 (with escape) child expressions");
            }

            // check eval child node - can be String or numeric
            Type evalChildType = this.ChildNodes[0].ReturnType;
            isNumericValue = TypeHelper.IsNumeric(evalChildType);
            if ((evalChildType != typeof(String)) && (!isNumericValue))
            {
                throw new ExprValidationException("The 'like' operator requires a String or numeric type left-hand expression");
            }

            // check pattern child node
            ExprNode patternChildNode = this.ChildNodes[1];
            Type patternChildType = patternChildNode.ReturnType;
            if (patternChildType != typeof(String))
            {
                throw new ExprValidationException("The 'like' operator requires a String-type pattern expression");
            }
            if (this.ChildNodes[1].IsConstantResult)
            {
                isConstantPattern = true;
            }

            // check escape character node
            if (this.ChildNodes.Count == 3)
            {
                ExprNode escapeChildNode = this.ChildNodes[2];
                if (escapeChildNode.ReturnType != typeof(String))
                {
                    throw new ExprValidationException("The 'like' operator escape parameter requires a character-type value");
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
            if (likeUtil == null)
            {
                String patternVal = (String)this.ChildNodes[1].Evaluate(eventsPerStream, isNewData);
                if (patternVal == null)
                {
                    return null;
                }
                String escape = "\\";

                char? escapeCharacter = null;
                if (this.ChildNodes.Count == 3)
                {
                    escape = ((String)this.ChildNodes[2].Evaluate(eventsPerStream, isNewData));
                }
                if (escape.Length > 0)
                {
                    escapeCharacter = escape[0];
                }

                likeUtil = new LikeUtil(patternVal, escapeCharacter, false);
            }
            else
            {
                if (!isConstantPattern)
                {
                    String patternVal = (String)this.ChildNodes[1].Evaluate(eventsPerStream, isNewData);
                    if (patternVal == null)
                    {
                        return null;
                    }
                    likeUtil.ResetPattern(patternVal);
                }
            }

            Object evalValue = this.ChildNodes[0].Evaluate(eventsPerStream, isNewData);
            if (evalValue == null)
            {
                return null;
            }

            if (isNumericValue)
            {
                evalValue = evalValue.ToString();
            }

            bool? result = likeUtil.Compare((String)evalValue);

            if (isNot)
            {
                return !result;
            }

            return result;
        }

        /// <summary>
        /// Returns true if the objects are equal.
        /// </summary>
        /// <param name="node_">the node to compare against</param>
        /// <returns></returns>
        public override bool EqualsNode(ExprNode node_)
        {
            if (!(node_ is ExprLikeNode))
            {
                return false;
            }

            ExprLikeNode other = (ExprLikeNode)node_;
            if (this.isNot != other.isNot)
            {
                return false;
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
                StringBuilder buffer = new StringBuilder();
                buffer.Append(this.ChildNodes[0].ExpressionString);

                if (isNot)
                {
                    buffer.Append(" not");
                }

                buffer.Append(" like ");
                buffer.Append(this.ChildNodes[1].ExpressionString);

                if (this.ChildNodes.Count == 3)
                {
                    buffer.Append(" escape ");
                    buffer.Append(this.ChildNodes[2].ExpressionString);
                }

                return buffer.ToString();
            }
        }
    }
}
