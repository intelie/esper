using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.compat;

namespace net.esper.collection
{
	/// <summary>
    /// Simple queue implementation based on a Linked List per thread.
	/// Objects can be added to the queue tail or queue head.
	/// </summary>

    public class ThreadWorkQueue
    {
        [ThreadStatic]
        private static ELinkedList<Object> threadQueue;

        private static ELinkedList<Object> LocalThreadQueue
        {
            get
            {
                if (threadQueue == null)
                {
                    threadQueue = new ELinkedList<Object>();
                }
                return threadQueue;
            }
        }

        /**
         * Adds event to the end of the event queue.
         * @param event to add
         */
        public void Add(Object ev)
        {
            ELinkedList<Object> queue = LocalThreadQueue;
            queue.Push(ev);
        }

        /**
         * Adds event to the front of the queue.
         * @param event to add
         */

        public void AddFront(Object ev)
        {
            ELinkedList<Object> queue = LocalThreadQueue;
            queue.Insert(0, ev);
        }

        /**
         * Returns the next event to getSelectListEvents, or null if there are no more events.
         * @return next event to getSelectListEvents
         */

        public Object Next()
        {
            ELinkedList<Object> queue = LocalThreadQueue;
            
            try
            {
            	return
            		( queue.Count != 0 ) ?
            		( queue.RemoveFirst() ) :
            		( null ) ;
            }
            catch( C5.NoSuchItemException )
            {
            	// This should not occur since the queue is local to
            	// the thread.  If it does occur, it indicates the queue
            	// was modified after the count check was performed.
            	return null ;
            }
        }
    }
}
