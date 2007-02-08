using System;
using System.Data.Common;
using System.Text.RegularExpressions;

using StreamTypeService = net.esper.eql.core.StreamTypeService;
using AutoImportService = net.esper.eql.core.AutoImportService;
using EventBean = net.esper.events.EventBean;
using TypeHelper = net.esper.util.TypeHelper;
using EPException = net.esper.client.EPException;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents the regexp-clause in an expression tree.
    /// </summary>

    public class ExprRegexpNode : ExprNode
    {
        override public Type ReturnType
        {
            get { return typeof(Boolean); }
        }

        private readonly bool isNot;

        private Regex pattern;
        private bool isNumericValue;
        private bool isConstantPattern;

        /// <summary> Ctor.</summary>
        /// <param name="not">is true if the it's a "not regexp" expression, of false for regular regexp 
        /// </param>
        public ExprRegexpNode(bool not)
        {
            this.isNot = not;
        }

        public override void validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
        {
            if (this.ChildNodes.Count != 2)
            {
                throw new ExprValidationException("The regexp operator requires 2 child expressions");
            }

            // check pattern child node
            ExprNode patternChildNode = this.ChildNodes[1];
            Type patternChildType = patternChildNode.ReturnType;
            if (patternChildType != typeof(String))
            {
                throw new ExprValidationException("The regexp operator requires a String-type pattern expression");
            }
            if (this.ChildNodes[1] is ExprConstantNode)
            {
                isConstantPattern = true;
            }

            // check eval child node - can be String or numeric
            Type evalChildType = this.ChildNodes[0].ReturnType;
            isNumericValue = TypeHelper.IsNumeric(evalChildType);
            if ((evalChildType != typeof(String)) && (!isNumericValue))
            {
                throw new ExprValidationException("The regexp operator requires a String or numeric type left-hand expression");
            }
        }

        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            if (pattern == null)
            {
                String patternText = (String)this.ChildNodes[1].Evaluate(eventsPerStream);
                if (patternText == null)
                {
                    return null;
                }
                try
                {
                	pattern = new Regex(patternText);
                }
                catch (ArgumentException ex)
                {
                    throw new EPException("Error compiling regex pattern '" + patternText + "'", ex);
                }
            }
            else
            {
                if (!isConstantPattern)
                {
                    String patternText = (String)this.ChildNodes[1].Evaluate(eventsPerStream);
                    if (patternText == null)
                    {
                        return null;
                    }
                    try
                    {
                        pattern = new Regex(patternText);
                    }
                    catch (ArgumentException ex)
                    {
                        throw new EPException("Error compiling regex pattern '" + patternText + "'", ex);
                    }
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

            Boolean result = pattern.IsMatch((String) evalValue);

            if (isNot)
            {
                return !result;
            }

            return result;
        }

        public override bool EqualsNode(ExprNode node_)
        {
            if (!(node_ is ExprRegexpNode))
            {
                return false;
            }

            ExprRegexpNode other = (ExprRegexpNode)node_;
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
                System.Text.StringBuilder buffer = new System.Text.StringBuilder();

                buffer.Append(this.ChildNodes[0].ExpressionString);

                if (isNot)
                {
                    buffer.Append(" not");
                }
                buffer.Append(" regexp ");
                buffer.Append(this.ChildNodes[1].ExpressionString);

                return buffer.ToString();
            }
        }
    }
}
