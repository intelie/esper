/*
 * Created on Apr 23, 2006
 *
 */

using System;
using System.Collections.Generic;
using System.IO;

using net.esper.example.transaction;

namespace net.esper.example.transaction.sim
{
    /// <summary>
    /// Interface to output events in your preferred format.
    /// </summary>

    public interface OutputStream
    {
        void Output(IList<TxnEventBase> bucket);
    }
}
