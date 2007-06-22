using System;
using System.Threading;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.support.util
{

    /// <summary> Singleton class for testing out multi-threaded code.
    /// Allows reservation and de-reservation of any Object. Reserved objects are added to a HashSet and
    /// removed from the HashSet upon de-reservation.
    /// </summary>
    public class ObjectReservationSingleton
    {
        public static ObjectReservationSingleton Instance
        {
            get
            {
                return ourInstance;
            }
        }

        private HashSet<Object> reservedObjects = new HashSet<Object>();
        private Object reservedIdsLock = new Object();

        private static ObjectReservationSingleton ourInstance = new ObjectReservationSingleton();

        private ObjectReservationSingleton()
        {
        }

        /// <summary> Reserve an object, returning true when successfully reserved or false when the object is already reserved.</summary>
        /// <param name="object">object to reserve
        /// </param>
        /// <returns> true if reserved, false to indicate already reserved
        /// </returns>

        public virtual bool Reserve(Object _object)
        {
            lock (reservedIdsLock)
            {
                if (reservedObjects.Contains(_object))
                {
                    return false;
                }

                reservedObjects.Add(_object);
            }

            return true;
        }

        /// <summary> Unreserve an object. Logs a fatal error if the unreserve failed.</summary>
        /// <param name="_object">object to unreserve
        /// </param>
        public virtual void Unreserve(Object _object)
        {
            lock (reservedIdsLock)
            {
                if (!reservedObjects.Contains(_object))
                {
                    log.Fatal(".unreserve FAILED, object=" + _object);
                    return;
                }

                reservedObjects.Remove(_object);
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
