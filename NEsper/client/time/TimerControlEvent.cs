/// <summary>***********************************************************************************
/// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
/// http://esper.codehaus.org                                                          *
/// ---------------------------------------------------------------------------------- *
/// The software in this package is published under the terms of the GPL license       *
/// a copy of which has been included with this distribution in the license.txt file.  *
/// ************************************************************************************
/// </summary>

using System;

namespace net.esper.client.time
{
    /// <summary>
    /// Event for controlling clocking, i.e. to enable and disable external clocking.
    /// </summary>

    public sealed class TimerControlEvent : TimerEvent
    {
        public enum ClockTypeEnum
        {
            /// <summary> For external clocking.</summary>
            CLOCK_EXTERNAL,
            /// <summary> For internal clocking.</summary>
            CLOCK_INTERNAL
        };

        private readonly ClockTypeEnum clockType;

        /// <summary> Constructor takes a clocking type as parameter.</summary>
        /// <param name="clockType">for internal or external clocking
        /// </param>
        public TimerControlEvent(ClockTypeEnum clockType)
        {
            this.clockType = clockType;
        }

        /// <summary> Returns clocking type.</summary>
        /// <returns> clocking type
        /// </returns>
        public ClockTypeEnum ClockType
        {
            get { return clockType; }
        }
    }
}