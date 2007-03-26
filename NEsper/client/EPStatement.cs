// ************************************************************************************
// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
// http://esper.codehaus.org                                                          *
// ---------------------------------------------------------------------------------- *
// The software in this package is published under the terms of the GPL license       *
// a copy of which has been included with this distribution in the license.txt file.  *
// ************************************************************************************

using System;

namespace net.esper.client
{
	/// <summary>
	/// Interface to add and remove update listeners receiving events for an EP statement.
	/// </summary>
    
	public interface EPStatement : EPIterable, EPListenable
    {
        /// <summary> Returns the underlying expression text or XML.</summary>
        /// <returns> expression text
        /// </returns>
        
        String Text { get ; }

        /// <summary> Start the statement.</summary>
        void Start();

        /// <summary> Stop the statement.</summary>
        void Stop();
    }
}