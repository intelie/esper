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
	    private int? indexConstant;

	    /// <summary>Ctor.</summary>
	    /// <param name="indexConstant">
	    /// is the index of the prior event, with zero being the current event.
	    /// </param>
	    public ViewCapPriorEventAccess(int? indexConstant)
	    {
	        this.indexConstant = indexConstant;
	    }

	    /// <summary>Index or the prior event we are asking for.</summary>
	    /// <returns>prior event index constant</returns>
	    public int? IndexConstant
	    {
	    	get { return indexConstant; }
	    }

        /// <summary>
        /// Inspect view factories returning false to indicate that view factories do not meet
        /// view resource requirements, or true to indicate view capability and view factories can be compatible.
        /// </summary>
        /// <param name="viewFactories">is a list of view factories that originate the final views</param>
        /// <returns>
        /// true to indicate inspection success, or false to indicate inspection failure
        /// </returns>
	    public bool Inspect(IList<ViewFactory> viewFactories)
	    {
	        bool unboundStream = viewFactories.Count == 0;

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
