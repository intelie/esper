using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;

namespace net.esper.core
{
    /// <summary>
    /// Base class for an EPStatement - provides listener registration functions.
    /// </summary>

    public abstract class EPStatementSupport : EPListenable
    {
        private ISet<UpdateListener> listeners = new LinkedHashSet<UpdateListener>();

        /// <summary> Called when the last listener is removed.</summary>
        public abstract void listenerStop();

        /// <summary> Called when the first listener is added.</summary>
        public abstract void listenerStart();

        /// <summary> Returns the set of listeners to the statement.</summary>
        /// <returns> statement listeners
        /// </returns>
        
        public ISet<UpdateListener> Listeners
        {
            get { return listeners; }
        }

        /// <summary> Add a listener to the statement.</summary>
        /// <param name="listener">to add
        /// </param>

        public virtual void AddListener(UpdateListener listener)
        {
            if (listener == null)
            {
                throw new ArgumentException("Null listener reference supplied");
            }

            listeners.Add(listener);
            if (listeners.Count == 1)
            {
                listenerStart();
            }
        }

        /// <summary> Remove a listeners to a statement.</summary>
        /// <param name="listener">to remove
        /// </param>
        public virtual void RemoveListener(UpdateListener listener)
        {
            if (listener == null)
            {
                throw new ArgumentException("Null listener reference supplied");
            }

            listeners.Remove(listener);
            if (listeners.Count == 0)
            {
                listenerStop();
            }
        }

        /// <summary>
        /// Remove all listeners to a statement.
        /// </summary>

        public virtual void RemoveAllListeners()
        {
            listeners.Clear();
            listenerStop();
        }
    }
}