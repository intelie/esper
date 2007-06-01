///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using net.esper.view.std;

namespace net.esper.view
{
	/// <summary>Describes that we need random access into a data window by index.</summary>
	public class ViewCapDataWindowAccess : ViewCapability
	{
	    private Integer optionalIndexConstant;

	    /// <summary>Ctor.</summary>
	    /// <param name="optionalIndexConstant">
	    /// is the index, or null if expression-supplied index and not constant
	    /// </param>
	    public ViewCapDataWindowAccess(Integer optionalIndexConstant)
	    {
	        this.optionalIndexConstant = optionalIndexConstant;
	    }

	    public bool Inspect(List<ViewFactory> viewFactories)
	    {
	        // We allow the capability only if
	        //  - 1 view
	        //  - 2 views and the first view is a group-by (for window-per-group access)
	        if (viewFactories.Size() == 1)
	        {
	            return true;
	        }
	        if (viewFactories.Size() == 2)
	        {
	            if (viewFactories.Get(0) is GroupByViewFactory)
	            {
	                return true;
	            }
	            return false;
	        }
	        return true;
	    }
	}
} // End of namespace
