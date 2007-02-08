/// <summary>
/// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.
/// http://esper.codehaus.org
/// ----------------------------------------------------------------------------------
/// The software in this package is published under the terms of the GPL license
/// a copy of which has been included with this distribution in the license.txt file.
/// ----------------------------------------------------------------------------------
/// </summary>

using System;

namespace net.esper.client
{
	/// <summary> This exception is thrown to indicate a problem in administration and runtime. </summary>
	[Serializable]
	public class EPException:SystemException
	{
		/// <summary> Ctor.</summary>
		/// <param name="message">- error message
		/// </param>
		public EPException(String message)
			: base(message)
		{
		}
		
		/// <summary> Ctor for an inner exception and message.</summary>
		/// <param name="message">- error message
		/// </param>
		/// <param name="cause">- inner exception
		/// </param>
		public EPException(String message, System.Exception cause)
			: base(message, cause)
		{
		}
		
		/// <summary> Ctor - just an inner exception.</summary>
		/// <param name="cause">- inner exception
		/// </param>
		public EPException(System.Exception cause)
			: base(String.Empty, cause)
		{
		}
	}
}
