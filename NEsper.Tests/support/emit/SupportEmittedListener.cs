using System;
using System.Collections.Generic;

using net.esper.client;

namespace net.esper.support.emit
{
    public class SupportEmittedListener
    {
        IList<Object> emittedObjects = new List<Object>();

        public virtual void HandleEmit(Object _object)
        {
            emittedObjects.Add(_object);
        }

        public IList<Object> getEmittedObjects()
        {
            return emittedObjects;
        }
    }
}
