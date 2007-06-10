///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Text;

using net.esper.compat;

namespace net.esper.util
{
	/// <summary>
	/// Generates a UUID.
	///<p>
	/// A Universally Unique Identifier (UUID) is a 128 bit number generated
	/// according to an algorithm that is guaranteed to be unique in time and
	/// space from all other UUIDs.
	/// </p>
	/// </summary>
	/// <author>
	/// <a href="mailto:jboner@codehaus.org">Jonas Bonér</a>
	/// </author>
    public class UuidGenerator
    {
        /// <summary>Returns a unique uuid.</summary>
        /// <param name="obj">the calling object (this)</param>
        /// <returns>a unique uuid</returns>
        public static String Generate(Object obj)
        {
        	Guid guid = Guid.NewGuid() ;
        	return guid.ToString() ;
        }
    }
} // End of namespace
