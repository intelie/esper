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
        override public Type ReturnType
        {
            get
            {
                return typeof(bool?);
            }
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

        public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
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
            if (this.ChildNodes[1] is ExprConstantNode)
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

        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            if (likeUtil == null)
            {
                String patternVal = (String)this.ChildNodes[1].Evaluate(eventsPerStream);
                if (patternVal == null)
                {
                    return null;
                }
                String escape = "\\";

                char? escapeCharacter = null;
                if (this.ChildNodes.Count == 3)
                {
                    escape = ((String)this.ChildNodes[2].Evaluate(eventsPerStream));
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
                    String patternVal = (String)this.ChildNodes[1].Evaluate(eventsPerStream);
                    if (patternVal == null)
                    {
                        return null;
                    }
                    likeUtil.resetPattern(patternVal);
                }
            }

            Object evalValue = this.ChildNodes[0].Evaluate(eventsPerStream);
            if (evalValue == null)
            {
                return null;
            }

            if (isNumericValue)
            {
                evalValue = evalValue.ToString();
            }

            bool? result = likeUtil.compare((String)evalValue);

            if (isNot)
            {
                return !result;
            }

            return result;
        }

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