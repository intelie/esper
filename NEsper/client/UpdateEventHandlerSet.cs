using System;

using net.esper.compat;

namespace net.esper.client
{
    public class UpdateEventHandlerSet : HashSet<UpdateEventHandler>
    {
        /// <summary> Add an listener that observes events.</summary>
        /// <param name="listener">to add
        /// </param>
        public void AddListener(UpdateListener listener)
        {
            if (listener == null)
            {
                throw new ArgumentException("Null listener reference supplied");
            }

            Add(listener.Update);
        }

        /// <summary> Remove an listener that observes events.</summary>
        /// <param name="listener">to remove
        /// </param>
        public void RemoveListener(UpdateListener listener)
        {
            if (listener == null)
            {
                throw new ArgumentException("Null listener reference supplied");
            }

            Remove(listener.Update);
        }
    }
}
