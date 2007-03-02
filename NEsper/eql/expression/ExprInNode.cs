using System;
using System.Collections.Generic;
using System.Text;

using net.esper.compat;
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
        override public Type ReturnType
        {
            get { return typeof(bool?); }
        }

        private readonly bool isNotIn;

        private Type coercionType;

        /// <summary> Ctor.</summary>
        /// <param name="isNotIn">is true for "not in" and false for "in"
        /// </param>
        public ExprInNode(bool isNotIn)
        {
            this.isNotIn = isNotIn;
        }

        public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
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

        public override Object Evaluate(EventBean[] eventsPerStream)
        {
            // Evaluate first child which is the base value to compare to
            IEnumerator<ExprNode> it = this.ChildNodes.GetEnumerator() ;
            it.MoveNext() ;
            Object inPropResult = it.Current.Evaluate(eventsPerStream);
           	it.MoveNext() ;

            bool matched = false;
            do
            {
                ExprNode inSetValueExpr = it.Current;
                Object subExprResult = inSetValueExpr.Evaluate(eventsPerStream);

                if (compare(inPropResult, subExprResult))
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

        public override bool EqualsNode(ExprNode node_)
        {
            if (!(node_ is ExprInNode))
            {
                return false;
            }

            ExprInNode other = (ExprInNode)node_;
            return other.isNotIn == this.isNotIn;
        }

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

        private bool compare(Object leftResult, Object rightResult)
        {
        	if ((leftResult == null) && (rightResult == null)) {
        		return true ;
            }

        	if (( leftResult == null ) || ( rightResult == null )) {
        		return false ;
        	}

            return Object.Equals(
                TypeHelper.CoerceNumber(leftResult, coercionType),
                TypeHelper.CoerceNumber(rightResult, coercionType)
                ) ;
        }
    }
}
