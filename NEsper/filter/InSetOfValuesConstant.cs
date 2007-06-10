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
	    
		public override int GetHashCode()
		{
			return 
				constant != null ?
				base.GetHashCode() * 31 + constant.GetHashCode() :
				base.GetHashCode() ;
		}
	}
} // End of namespace
