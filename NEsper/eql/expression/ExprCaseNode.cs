using System;
using System.Collections.Generic;
using System.Text;

using net.esper.compat;
using net.esper.collection;
using net.esper.eql.core;
using net.esper.events;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents the case-when-then-else control flow function is an expression tree.
    /// </summary>
    
    public class ExprCaseNode : ExprNode
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
		
		public override bool IsConstantResult
	    {
	        get { return false; }
	    }

        private readonly bool isCase2;
        private IList<UniformPair<ExprNode>> whenThenNodeList;
        private ExprNode optionalCompareExprNode;
        private ExprNode optionalElseExprNode;
        private Type resultType;
        private bool isNumericResult;
        private Type coercionType;
        private bool mustCoerce;

        /// <summary> Ctor.</summary>
        /// <param name="isCase2">is an indicator of which Case statement we are working on.
        /// <para> True indicates a 'Case2' statement with syntax "case a when a1 then b1 else b2".</para>
        /// <para> False indicates a 'Case1' statement with syntax "case when a=a1 then b1 else b2".</para>
        /// </param>
        public ExprCaseNode(bool isCase2)
        {
            this.isCase2 = isCase2;
        }

        /// <summary>
        /// Validates the specified stream type service_.
        /// </summary>
        /// <param name="streamTypeService_">The stream type service_.</param>
        /// <param name="methodResolutionService">The method resolution service.</param>
        /// <param name="viewResourceDelegate">The view resource delegate.</param>
        public override void Validate(StreamTypeService streamTypeService_, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
        {
            if (isCase2)
            {
                ValidateCaseTwo();
            }
            else
            {
                ValidateCaseOne();
            }

            // Determine type of each result (then-node and else node) child node expression
            List<Type> childTypes = new List<Type>();
            foreach (UniformPair<ExprNode> pair in whenThenNodeList)
            {
                childTypes.Add(pair.Second.ReturnType);
            }
            if (optionalElseExprNode != null)
            {
                childTypes.Add(optionalElseExprNode.ReturnType);
            }

            // Determine common denominator type
            try
            {
                resultType = TypeHelper.GetCommonCoercionType(childTypes.ToArray());
                if (TypeHelper.IsNumeric(resultType))
                {
                    isNumericResult = true;
                }
            }
            catch (CoercionException ex)
            {
                throw new ExprValidationException("Implicit conversion not allowed: " + ex.Message);
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
            if (!isCase2)
            {
                return EvaluateCaseSyntax1(eventsPerStream, isNewData);
            }
            else
            {
                return EvaluateCaseSyntax2(eventsPerStream, isNewData);
            }
        }

        /// <summary>
        /// Returns true if the nodes are equal.
        /// </summary>
        /// <param name="node_"></param>
        /// <returns></returns>
        public override bool EqualsNode(ExprNode node_)
        {
            if (!(node_ is ExprCaseNode))
            {
                return false;
            }

            ExprCaseNode otherExprCaseNode = (ExprCaseNode)node_;
            return this.isCase2 == otherExprCaseNode.isCase2;
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
                buffer.Append("case");
                if (isCase2)
                {
                    buffer.Append(" ");
                    buffer.Append(this.ChildNodes[0].ExpressionString);
                }
                foreach (UniformPair<ExprNode> p in whenThenNodeList)
                {
                    buffer.Append(" when ");
                    buffer.Append(p.First.ExpressionString);
                    buffer.Append(" then ");
                    buffer.Append(p.Second.ExpressionString);
                }
                if (optionalElseExprNode != null)
                {
                    buffer.Append(" else ");
                    buffer.Append(optionalElseExprNode.ExpressionString);
                }
                buffer.Append(" end");
                return buffer.ToString();
            }
        }

        private void ValidateCaseOne()
        {
            // Case 1 expression example:
            //      case when a=b then x [when c=d then y...] [else y]
            //
            IList<ExprNode> children = CollectionHelper.RandomAccessList<ExprNode>( this.ChildNodes ) ;
            if (children.Count < 2)
            {
                throw new ExprValidationException("Case node must have at least 2 child nodes");
            }

            whenThenNodeList = new List<UniformPair<ExprNode>>();
            int numWhenThen = children.Count >> 1;
            for (int i = 0; i < numWhenThen; i++)
            {
                ExprNode whenExpr = children[(i << 1)];
                ExprNode thenExpr = children[(i << 1) + 1];
                if (!TypeHelper.IsBoolean(whenExpr.ReturnType))
                {
                    throw new ExprValidationException("Case node 'when' expressions must return a bool value");
                }
                whenThenNodeList.Add(new UniformPair<ExprNode>(whenExpr, thenExpr));
            }
            if (children.Count % 2 == 1)
            {
                optionalElseExprNode = children[children.Count - 1];
            }
        }

        private void ValidateCaseTwo()
        {
            // Case 2 expression example:
            //      case p when p1 then x [when p2 then y...] [else z]
            //
            IList<ExprNode> children = CollectionHelper.RandomAccessList<ExprNode>( this.ChildNodes ) ;
            if (children.Count < 3)
            {
                throw new ExprValidationException("Case node must have at least 3 child nodes");
            }

            optionalCompareExprNode = children[0];

            whenThenNodeList = new List<UniformPair<ExprNode>>();
            int numWhenThen = (children.Count - 1) / 2;
            for (int i = 0; i < numWhenThen; i++)
            {
                whenThenNodeList.Add(new UniformPair<ExprNode>(children[i * 2 + 1], children[i * 2 + 2]));
            }
            if (numWhenThen * 2 + 1 < children.Count)
            {
                optionalElseExprNode = children[children.Count - 1];
            }

            // validate we can compare result types
            List<Type> comparedTypes = new List<Type>();
            comparedTypes.Add(optionalCompareExprNode.ReturnType);
            foreach (UniformPair<ExprNode> pair in whenThenNodeList)
            {
                comparedTypes.Add(pair.First.ReturnType);
            }

            // Determine common denominator type
            try
            {
                coercionType = TypeHelper.GetCommonCoercionType(comparedTypes.ToArray()) ;
                
                // Determine if we need to coerce numbers when one type doesn't match any other type
                if (TypeHelper.IsNumeric(coercionType))
                {
                    mustCoerce = false;
                    foreach (Type comparedType in comparedTypes)
                    {
                        if (comparedType != coercionType)
                        {
                            mustCoerce = true;
                        }
                    }
                }
            }
            catch (CoercionException ex)
            {
                throw new ExprValidationException("Implicit conversion not allowed: " + ex.Message);
            }
        }

        private Object EvaluateCaseSyntax1(EventBean[] eventsPerStream, bool isNewData)
        {
            // Case 1 expression example:
            //      case when a=b then x [when c=d then y...] [else y]

            Object caseResult = null;
            bool matched = false;
            foreach (UniformPair<ExprNode> p in whenThenNodeList)
            {
                Boolean whenResult = (Boolean)p.First.Evaluate(eventsPerStream, isNewData);

                // If the 'when'-expression returns true
                if (whenResult)
                {
                    caseResult = p.Second.Evaluate(eventsPerStream, isNewData);
                    matched = true;
                    break;
                }
            }

            if ((!matched) && (optionalElseExprNode != null))
            {
                caseResult = optionalElseExprNode.Evaluate(eventsPerStream, isNewData);
            }

            if (caseResult == null)
            {
                return null;
            }

            if ((caseResult.GetType() != resultType) && (isNumericResult))
            {
                return TypeHelper.CoerceBoxed(caseResult, resultType);
            }

            return caseResult;
        }

        private Object EvaluateCaseSyntax2(EventBean[] eventsPerStream, bool isNewData)
        {
            // Case 2 expression example:
            //      case p when p1 then x [when p2 then y...] [else z]

            Object checkResult = optionalCompareExprNode.Evaluate(eventsPerStream, isNewData);
            Object caseResult = null;
            bool matched = false;

            foreach (UniformPair<ExprNode> p in whenThenNodeList)
            {
                Object whenResult = p.First.Evaluate(eventsPerStream, isNewData);

                if (Compare(checkResult, whenResult))
                {
                    caseResult = p.Second.Evaluate(eventsPerStream, isNewData);
                    matched = true;
                    break;
                }
            }

            if ((!matched) && (optionalElseExprNode != null))
            {
                caseResult = optionalElseExprNode.Evaluate(eventsPerStream, isNewData);
            }

            if (caseResult == null)
            {
                return null;
            }

            if ((caseResult.GetType() != resultType) && (isNumericResult))
            {
                return TypeHelper.CoerceBoxed(caseResult, resultType);
            }
            return caseResult;
        }

        private bool Compare(Object leftResult, Object rightResult)
        {
            if (leftResult == null)
            {
                return (rightResult == null);
            }

            if (rightResult == null)
            {
                return (leftResult == null);
            }

            if (!mustCoerce)
            {
                return leftResult.Equals(rightResult);
            }

            Object left = TypeHelper.CoerceBoxed(leftResult, coercionType);
            Object right = TypeHelper.CoerceBoxed(rightResult, coercionType);
            return Object.Equals(left, right);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
