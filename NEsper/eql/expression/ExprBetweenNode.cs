using System;
using System.Collections.Generic;
using System.Text;

using net.esper.eql.core;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.expression
{
	/// <summary>
    /// Represents the between-clause function in an expression tree.
    /// </summary>
	
    public class ExprBetweenNode : ExprNode
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
		
	    private readonly bool isLowEndpointIncluded;
	    private readonly bool isHighEndpointIncluded;
 		private readonly bool isNotBetween;
		
		private bool isAlwaysFalse;
		private ExprBetweenNode.ExprBetweenComp computer;
		
	    /**
	     * Ctor.
	     * @param lowEndpointIncluded is true for the regular 'between' or false for "val in (a:b)" (open range), or
	     * false if the endpoint is not included
	     * @param highEndpointIncluded indicates whether the high endpoint is included
	     * @param notBetween is true for 'not between' or 'not in (a:b), or false for a regular between
	     */
	    public ExprBetweenNode(bool lowEndpointIncluded, bool highEndpointIncluded, bool notBetween)
	    {
	        isLowEndpointIncluded = lowEndpointIncluded;
	        isHighEndpointIncluded = highEndpointIncluded;
	        isNotBetween = notBetween;
	    }

	    public override bool IsConstantResult
	    {
	        get { return false; }
	    }

	    /**
	     * Returns true if the low endpoint is included, false if not
	     * @return indicator if endppoint is included
	     */
	    public bool IsLowEndpointIncluded
	    {
	        get { return isLowEndpointIncluded; }
	    }

	    /**
	     * Returns true if the high endpoint is included, false if not
	     * @return indicator if endppoint is included
	     */
	    public bool IsHighEndpointIncluded
	    {
	        get { return isHighEndpointIncluded; }
	    }

	    /**
	     * Returns true for inverted range, or false for regular (openn/close/half-open/half-closed) ranges.
	     * @return true for not betwene, false for between
	     */
	    public bool IsNotBetween
	    {
	        get { return isNotBetween; }
	    }

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="autoImportService">for resolving class names in library method invocations</param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
	    public override void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
	    {
			if (this.ChildNodes.Count != 3)
			{
				throw new ExprValidationException("The Between operator requires exactly 3 child expressions");
			}
			
			// Must be either numeric or string
            Type typeOne = TypeHelper.GetBoxedType(this.ChildNodes[0].ReturnType);
            Type typeTwo = TypeHelper.GetBoxedType(this.ChildNodes[1].ReturnType);
			Type typeThree = TypeHelper.GetBoxedType(this.ChildNodes[2].ReturnType);
			
			if (typeOne == null)
			{
				throw new ExprValidationException("Null value not allowed in between-clause");
			}
			
			Type compareType = null;
			if ((typeTwo == null) || (typeThree == null))
			{
				isAlwaysFalse = true;
			}
			else
			{
				if ((typeOne != typeof(String)) || (typeTwo != typeof(String)) || (typeThree != typeof(String)))
				{
					if (!TypeHelper.IsNumeric(typeOne))
					{
						throw new ExprValidationException("Implicit conversion from datatype '" + typeOne.FullName + "' to numeric is not allowed");
					}
					if (!TypeHelper.IsNumeric(typeTwo))
					{
						throw new ExprValidationException("Implicit conversion from datatype '" + typeTwo.FullName + "' to numeric is not allowed");
					}
					if (!TypeHelper.IsNumeric(typeThree))
					{
						throw new ExprValidationException("Implicit conversion from datatype '" + typeThree.FullName + "' to numeric is not allowed");
					}
				}
				
				Type intermedType = TypeHelper.GetCompareToCoercionType(typeOne, typeTwo);
				compareType = TypeHelper.GetCompareToCoercionType(intermedType, typeThree);
				computer = MakeComputer(compareType);
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
			if (isAlwaysFalse)
			{
				return false;
			}
			
			// Evaluate first child which is the base value to compare to
			IList<ExprNode> exprNodeList = this.ChildNodes ;
			
			Object value = exprNodeList[0].Evaluate(eventsPerStream, isNewData);
			if (value == null)
			{
				return false;
			}
			Object lower = exprNodeList[1].Evaluate(eventsPerStream, isNewData);
			Object higher = exprNodeList[2].Evaluate(eventsPerStream, isNewData);
			
			bool result = computer.IsBetween(value, lower, higher);
			if (isNotBetween)
			{
				return !result;
			}
			
			return result;
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
			if (!(node is ExprBetweenNode))
			{
				return false;
			}
			
			ExprBetweenNode other = (ExprBetweenNode) node;
			return other.isNotBetween == this.isNotBetween;
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

                IList<ExprNode> exprNodeList = this.ChildNodes ;

                buffer.Append(exprNodeList[0].ExpressionString);
                if (isNotBetween)
                {
                    buffer.Append(" not between ");
                }
                else
                {
                    buffer.Append(" between ");
                }

                buffer.Append(exprNodeList[1].ExpressionString);
                buffer.Append(" and ");
                buffer.Append(exprNodeList[2].ExpressionString);

                return buffer.ToString();
            }
		}
		
		private ExprBetweenNode.ExprBetweenComp MakeComputer(Type compareType)
		{
			ExprBetweenNode.ExprBetweenComp computer = null;
			
			if (compareType == typeof(string))
			{
				computer = new ExprBetweenCompString(isLowEndpointIncluded, isHighEndpointIncluded);
			}
			else if (compareType == typeof(long))
			{
				computer = new ExprBetweenCompLong(isLowEndpointIncluded, isHighEndpointIncluded);
			}
			else
			{
				computer = new ExprBetweenCompDouble(isLowEndpointIncluded, isHighEndpointIncluded);
			}
			return computer;
		}
		
		private interface ExprBetweenComp
		{
			bool IsBetween(Object value, Object lower, Object upper);
		}
		
		private class ExprBetweenCompString : ExprBetweenNode.ExprBetweenComp
		{
	        private bool isLowIncluded;
	        private bool isHighIncluded;

	        public ExprBetweenCompString(bool lowIncluded, bool isHighIncluded)
	        {
	            this.isLowIncluded = lowIncluded;
	            this.isHighIncluded = isHighIncluded;
	        }

			public virtual bool IsBetween(Object value, Object lower, Object upper)
			{
				if ((value == null) || (lower == null) || ((upper == null)))
				{
					return false;
				}
				
				String valueStr = (String) value;
				String lowerStr = (String) lower;
				String upperStr = (String) upper;
				
				if (String.CompareOrdinal(upperStr, lowerStr) < 0)
				{
	                String temp = upperStr;
	                upperStr = lowerStr;
	                lowerStr = temp;
	            }

	            if (String.CompareOrdinal(valueStr, lowerStr) < 0)
	            {
	                return false;
	            }
	            if (String.CompareOrdinal(valueStr, upperStr) > 0)
	            {
	                return false;
	            }
	            if (!(isLowIncluded))
	            {
	                if (valueStr.Equals(lowerStr))
	                {
	                    return false;
	                }
	            }
	            if (!(isHighIncluded))
	            {
	                if (valueStr.Equals(upperStr))
	                {
	                    return false;
	                }
	            }
	            return true;
			}
			
		    public bool IsEqualsEndpoint(Object value, Object endpoint)
	        {
	            return value.Equals(endpoint);
	        }
		}
		
		private class ExprBetweenCompDouble : ExprBetweenNode.ExprBetweenComp
		{
	        private bool isLowIncluded;
	        private bool isHighIncluded;

	        public ExprBetweenCompDouble(bool lowIncluded, bool highIncluded)
	        {
	            isLowIncluded = lowIncluded;
	            isHighIncluded = highIncluded;
	        }

			public virtual bool IsBetween(Object value, Object lower, Object upper)
			{
				if ((value == null) || (lower == null) || ((upper == null)))
				{
					return false;
				}
				
				double valueD = Convert.ToDouble(value);
				double lowerD = Convert.ToDouble(lower);
				double upperD = Convert.ToDouble(upper);
				
	            if (lowerD > upperD)
	            {
	                double temp = upperD;
	                upperD = lowerD;
	                lowerD = temp;
	            }

	            if (valueD > lowerD)
	            {
	                if (valueD < upperD)
	                {
	                    return true;
	                }
	                if (isHighIncluded)
	                {
	                    return valueD == upperD;
	                }
	                return false;
	            }
	            if ((isLowIncluded) && (valueD == lowerD))
	            {
	                return true;
	            }
	            return false;
			}
		}
		
		private class ExprBetweenCompLong : ExprBetweenNode.ExprBetweenComp
		{
	        private bool isLowIncluded;
	        private bool isHighIncluded;

	        public ExprBetweenCompLong(bool lowIncluded, bool highIncluded)
	        {
	            isLowIncluded = lowIncluded;
	            isHighIncluded = highIncluded;
	        }

			public virtual bool IsBetween(Object value, Object lower, Object upper)
			{
				if ((value == null) || (lower == null) || ((upper == null)))
				{
					return false;
				}
				long valueD = Convert.ToInt64(value);
				long lowerD = Convert.ToInt64(lower);
				long upperD = Convert.ToInt64(upper);
				
	            if (lowerD > upperD)
	            {
	                long temp = upperD;
	                upperD = lowerD;
	                lowerD = temp;
	            }

	            if (valueD > lowerD)
	            {
	                if (valueD < upperD)
	                {
	                    return true;
	                }
	                if (isHighIncluded)
	                {
	                    return valueD == upperD;
	                }
	                return false;
	            }
	            if ((isLowIncluded) && (valueD == lowerD))
	            {
	                return true;
	            }
	            return false;
			}
		}
	}
}
