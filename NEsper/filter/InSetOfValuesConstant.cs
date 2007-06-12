///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.pattern;

namespace net.esper.filter
{
	/// <summary>Constant value in a list of values following an in-keyword.</summary>
	public class InSetOfValuesConstant : FilterSpecParamInValue
	{
	    private Object constant;

	    /// <summary>Ctor.</summary>
	    /// <param name="constant">is the constant value</param>
	    public InSetOfValuesConstant(Object constant)
	    {
	        this.constant = constant;
	    }

        /// <summary>
        /// Returns the actual value to filter for from prior matching events
        /// </summary>
        /// <param name="matchedEvents">is a map of matching events</param>
        /// <returns>filter-for value</returns>
	    public Object GetFilterValue(MatchedEventMap matchedEvents)
	    {
	        return constant;
	    }

	    /// <summary>Returns the constant value.</summary>
	    /// <returns>constant</returns>
	    public Object GetConstant()
	    {
	        return constant;
	    }

        /// <summary>
        /// Determines whether the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <param name="obj">The <see cref="T:System.Object"></see> to compare with the current <see cref="T:System.Object"></see>.</param>
        /// <returns>
        /// true if the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>; otherwise, false.
        /// </returns>
	    public override bool Equals(Object obj)
	    {
	        if (this == obj)
	        {
	            return true;
	        }

	        if (!(obj is InSetOfValuesConstant))
	        {
	            return false;
	        }

	        InSetOfValuesConstant other = (InSetOfValuesConstant) obj;
	        return other.constant.Equals(this.constant);
	    }

        /// <summary>
        /// Serves as a hash function for a particular type. <see cref="M:System.Object.GetHashCode"></see> is suitable for use in hashing algorithms and data structures like a hash table.
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override int GetHashCode()
		{
			return 
				constant != null ?
				base.GetHashCode() * 31 + constant.GetHashCode() :
				base.GetHashCode() ;
		}
	}
} // End of namespace
