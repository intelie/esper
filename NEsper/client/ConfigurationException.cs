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
	/// <summary> Thrown to indicate a configuration problem.</summary>
	[Serializable]
	sealed public class ConfigurationException:EPException
	{
		/// <summary> Ctor.</summary>
		/// <param name="message">error message
		/// </param>
		public ConfigurationException(String message):base(message)
		{
		}
		
		/// <summary> Ctor for an inner exception and message.</summary>
		/// <param name="message">error message
		/// </param>
		/// <param name="cause">inner exception
		/// </param>
		public ConfigurationException(String message, Exception cause):base(message, cause)
		{
		}
		
		/// <summary> Ctor - just an inner exception.</summary>
		/// <param name="cause">inner exception
		/// </param>
		public ConfigurationException(Exception cause):base(cause)
		{
		}
	}
}