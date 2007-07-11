using System;
using System.Collections.Generic;
using System.Text;

using net.esper.eql.core;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.expression
{
    /// <summary>
    /// Represents the in-clause (set check) function in an expression tree.
    /// </summary>

    public class ExprInNode : ExprNode
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

	    /// <summary>Returns the coercion type to use if coercion is required.</summary>
	    /// <returns>coercion type</returns>
	    public Type CoercionType
	    {
	        get { return coercionType; }
	    }

        private readonly bool isNotIn;

        private Type coercionType;

	    /// <summary>Returns true for not-in, false for regular in</summary>
	    /// <returns>
	    /// false for &quot;val in (a,b,c)&quot; or true for &quot;val not in (a,b,c)&quot;
	    /// </returns>
	    public bool IsNotIn
	    {
	        get { return isNotIn; }
	    }

	    public override bool IsConstantResult
	    {
	        get { return false; }
	    }

        /// <summary> Ctor.</summary>
        /// <param name="isNotIn">is true for "not in" and false for "in"
        /// </param>
        public ExprInNode(bool isNotIn)
        {
            this.isNotIn = isNotIn;
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
        	IList<ExprNode> children = this.ChildNodes ;

            if (children.Count < 2)
            {
                throw new ExprValidationException("The IN operator requires at least 2 child expressions");
            }

            List<Type> comparedTypes = new List<Type>();
            foreach( ExprNode node in children )
            {
            	comparedTypes.Add( node.ReturnType ) ;
            }

            // Determine common denominator type
            try
            {
            	coercionType = TypeHelper.GetCommonCoercionType( comparedTypes.ToArray() ) ;
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
        /// <param name="isNewData">is the data new</param>
        /// <returns>
        /// evaluation result, a bool value for OR/AND-type evalution nodes.
        /// </returns>
        public override Object Evaluate(EventBean[] eventsPerStream, bool isNewData)
        {
            // Evaluate first child which is the base value to compare to
            IEnumerator<ExprNode> it = this.ChildNodes.GetEnumerator() ;
            it.MoveNext() ;
            Object inPropResult = it.Current.Evaluate(eventsPerStream, isNewData);
           	it.MoveNext() ;

            bool matched = false;
            do
            {
                ExprNode inSetValueExpr = it.Current;
                Object subExprResult = inSetValueExpr.Evaluate(eventsPerStream, isNewData);

                if (Compare(inPropResult, subExprResult))
                {
                    matched = true;
                    break;
                }
            }
            while (it.MoveNext());

            if (isNotIn)
            {
                return !matched;
            }
            return matched;
        }

        /// <summary>
        /// Returns true if the objects are equal.
        /// </summary>
        /// <param name="node_">The node to compare against.</param>
        /// <returns></returns>
        public override bool EqualsNode(ExprNode node_)
        {
            if (!(node_ is ExprInNode))
            {
                return false;
            }

            ExprInNode other = (ExprInNode)node_;
            return other.isNotIn == this.isNotIn;
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
                String delimiter = "";

                IEnumerator<ExprNode> it = this.ChildNodes.GetEnumerator();
                it.MoveNext() ;

                buffer.Append(it.Current.ExpressionString);
                if (isNotIn)
                {
                    buffer.Append(" not in (");
                }
                else
                {
                    buffer.Append(" in (");
                }

                while( it.MoveNext() )
                {
                    ExprNode inSetValueExpr = it.Current;
                    buffer.Append(delimiter);
                    buffer.Append(inSetValueExpr.ExpressionString);
                    delimiter = ",";
                }

                buffer.Append(")");
                return buffer.ToString();
            }
        }

        /// <summary>
        /// Compares the specified left result.
        /// </summary>
        /// <param name="leftResult">The left result.</param>
        /// <param name="rightResult">The right result.</param>
        /// <returns></returns>
        private bool Compare(Object leftResult, Object rightResult)
        {
        	if ((leftResult == null) && (rightResult == null)) {
        		return true ;
            }

        	if (( leftResult == null ) || ( rightResult == null )) {
        		return false ;
        	}

            return Object.Equals(
                TypeHelper.CoerceBoxed(leftResult, coercionType),
                TypeHelper.CoerceBoxed(rightResult, coercionType)
                ) ;
        }
    }
}
