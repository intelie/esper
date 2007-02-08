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
	/// <summary>
	/// Interface to add and remove update listeners.
	/// </summary>

	public interface EPListenable
	{
		/// <summary> Add an listener that observes events.</summary>
		/// <param name="listener">to add
		/// </param>
		void AddListener( UpdateListener listener );

		/// <summary> Remove an listener that observes events.</summary>
		/// <param name="listener">to remove
		/// </param>
		void RemoveListener( UpdateListener listener );

		/// <summary> Remove all listeners.</summary>
		void RemoveAllListeners();
	}
}