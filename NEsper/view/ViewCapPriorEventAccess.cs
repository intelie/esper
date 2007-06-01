///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using net.esper.view.internals;

namespace net.esper.view
{
	/// <summary>
	/// Describes that we need access to prior events (result events published by views),
	/// for use by the &quot;prior&quot; expression function.
	/// </summary>
	public class ViewCapPriorEventAccess : ViewCapability
	{
	    private Integer indexConstant;

	    /// <summary>Ctor.</summary>
	    /// <param name="indexConstant">
	    /// is the index of the prior event, with zero being the current event.
	    /// </param>
	    public ViewCapPriorEventAccess(Integer indexConstant)
	    {
	        this.indexConstant = indexConstant;
	    }

	    /// <summary>Index or the prior event we are asking for.</summary>
	    /// <returns>prior event index constant</returns>
	    public Integer GetIndexConstant()
	    {
	        return indexConstant;
	    }

	    public bool Inspect(List<ViewFactory> viewFactories)
	    {
	        bool unboundStream = viewFactories.IsEmpty();

	        // Find the prior event view to see if it has already been added
	        PriorEventViewFactory factory = null;
	        foreach (ViewFactory viewFactory in viewFactories)
	        {
	            if (viewFactory is PriorEventViewFactory)
	            {
	                factory = (PriorEventViewFactory) viewFactory;
	            }
	        }

	        if (factory == null)
	        {
	            factory = new PriorEventViewFactory(unboundStream);
	            viewFactories.Add(factory);
	        }

	        return true;
	    }
	}
} // End of namespace
