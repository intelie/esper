// ************************************************************************************
// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
// http://esper.codehaus.org                                                          *
// ---------------------------------------------------------------------------------- *
// The software in this package is published under the terms of the GPL license       *
// a copy of which has been included with this distribution in the license.txt file.  *
// ************************************************************************************

using System;

using net.esper.compat;

namespace net.esper.client.time
{
	/// <summary> Event for externally controlling the time within an <seealso cref="net.esper.client.EPRuntime"/> instance.
	/// External clocking must be enabled via <seealso cref="TimerControlEvent"/> before this class can be used
	/// to externally feed time.
	/// </summary>
	
	public sealed class CurrentTimeEvent:TimerEvent
	{
		/// <summary> Returns the time in milliseconds.</summary>
		/// <returns> time in milliseconds
		/// </returns>
		public long TimeInMillis
		{
			get { return timeInMillis; }
		}

		private readonly long timeInMillis;
		
		/// <summary> Constructor.</summary>
		/// <param name="timeInMillis">is the time in milliseconds
		/// </param>
		public CurrentTimeEvent(long timeInMillis)
		{
			this.timeInMillis = timeInMillis;
		}

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override String ToString()
		{
			return DateTimeHelper.TimeFromMillis( timeInMillis ).ToString();
		}
	}
}
