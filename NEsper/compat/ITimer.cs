using System;
using System.Collections.Generic;
using System.Text;

namespace net.esper.compat
{
    /// <summary>
    /// An object that represents a timer.  Timers must be
    /// disposable.
    /// </summary>

    public interface ITimer : IDisposable
    {
    }
}
