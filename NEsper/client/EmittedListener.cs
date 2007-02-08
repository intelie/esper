/// <summary>***********************************************************************************
/// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
/// http://esper.codehaus.org                                                          *
/// ---------------------------------------------------------------------------------- *
/// The software in this package is published under the terms of the GPL license       *
/// a copy of which has been included with this distribution in the license.txt file.  *
/// ************************************************************************************
/// </summary>
using System;
namespace net.esper.client
{
	
	/// <summary> Listener interface for events emitted from an {@link EPRuntime}.</summary>
	public interface EmittedListener
	{
		/// <summary> Called to indicate an event emitted.</summary>
		/// <param name="event">is the event emitted
		/// </param>
		void  emitted(Object _event);
	}
}