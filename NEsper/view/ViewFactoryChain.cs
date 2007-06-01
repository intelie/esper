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

namespace net.esper.view
{



	/// <summary>Holder for the logical chain of view factories.</summary>
	public class ViewFactoryChain
	{
	    private List<ViewFactory> viewFactoryChain;
	    private EventType streamEventType;

	    /// <summary>Ctor.</summary>
	    /// <param name="streamEventType">is the event type of the event stream</param>
	    /// <param name="viewFactoryChain">is the chain of view factories</param>
	    public ViewFactoryChain(EventType streamEventType, List<ViewFactory> viewFactoryChain)
	    {
	        this.streamEventType = streamEventType;
	        this.viewFactoryChain = viewFactoryChain;
	    }

	    /// <summary>
	    /// Returns the final event type which is the event type of the last view factory in the chain,
	    /// or if the chain is empty then the stream's event type.
	    /// </summary>
	    /// <returns>final event type of the last view or stream</returns>
	    public EventType GetEventType()
	    {
	        if (viewFactoryChain.IsEmpty())
	        {
	            return streamEventType;
	        }
	        else
	        {
	            return viewFactoryChain.Get(viewFactoryChain.Size() - 1).EventType;
	        }
	    }

	    /// <summary>Returns the chain of view factories.</summary>
	    /// <returns>view factory list</returns>
	    public List<ViewFactory> GetViewFactoryChain()
	    {
	        return viewFactoryChain;
	    }
	}
} // End of namespace
