using System;
using System.Collections.Generic;
using System.Text;

namespace net.esper.compat
{
    /// <summary>
    /// A general purpose exception for timer events
    /// </summary>

    public class TimerException : Exception
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="TimerException"/> class.
        /// </summary>
        public TimerException() : base() { }
        /// <summary>
        /// Initializes a new instance of the <see cref="TimerException"/> class.
        /// </summary>
        /// <param name="message">The message.</param>
        public TimerException(string message) : base(message) { }
    }
}
