///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.view;

namespace net.esper.eql.core
{
	/// <summary>
	/// Coordinates between view factories and requested resource (by expressions) the
	/// availability of view resources to expressions.
	/// </summary>
	public class ViewResourceDelegateImpl : ViewResourceDelegate
	{
	    private ViewFactoryChain[] viewFactories;

	    /// <summary>Ctor.</summary>
	    /// <param name="viewFactories">array of view factory chains, one for each stream</param>
	    public ViewResourceDelegateImpl(ViewFactoryChain[] viewFactories)
	    {
	        this.viewFactories = viewFactories;
	    }

	    public bool RequestCapability(int streamNumber, ViewCapability requestedCabability, ViewResourceCallback resourceCallback)
	    {
	        ViewFactoryChain factories = viewFactories[streamNumber];

	        // first we give the capability implementation a chance to inspect the view factory chain
	        // it can deny by returning false
	        if (!(requestedCabability.Inspect(factories.FactoryChain)))
	        {
	            return false;
	        }

	        // then ask each view in turn to support the capability
	        foreach (ViewFactory factory in factories.FactoryChain)
	        {
	            if (factory.CanProvideCapability(requestedCabability))
	            {
                    factory.SetProvideCapability(requestedCabability, resourceCallback);
	                return true;
	            }
	        }

	        return false;
	    }
	}
} // End of namespace
