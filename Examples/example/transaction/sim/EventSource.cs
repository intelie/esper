/*
 * Created on Apr 22, 2006
 *
 */

using System;
using System.Collections.Generic;

using net.esper.example.transaction;

namespace net.esper.example.transaction.sim
{
    /// <summary>
    /// An iterable source of events
    /// </summary>
    
    public abstract class EventSource : IEnumerable<TxnEventBase>
    {
        abstract public IEnumerator<TxnEventBase> GetEnumerator() ;
        
        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return this.GetEnumerator();
        }
    }   
}
