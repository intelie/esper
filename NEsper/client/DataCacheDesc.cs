using System;

namespace net.esper.client
{
    /// <summary>
    ///  Marker for different cache settings.
    /// </summary>
    
    public interface DataCacheDesc
    {
    }

    /// <summary>
    /// LRU cache settings.
    /// </summary>
    public class LRUCacheDesc : DataCacheDesc
    {
        /// <summary> Returns the maximum cache size.</summary>
        /// <returns> max cache size
        /// </returns>
        virtual public int Size
        {
            get
            {
                return size;
            }

        }
        private int size;

        /// <summary> Ctor.</summary>
        /// <param name="size">is the maximum cache size
        /// </param>
        public LRUCacheDesc(int size)
        {
            this.size = size;
        }

        public override String ToString()
        {
            return "LRUCacheDesc size=" + size;
        }
    }

    /// <summary>
    ///  Expiring cache settings.
    /// </summary>
    
    public class ExpiryTimeCacheDesc : DataCacheDesc
    {
        /// <summary> Returns the maximum age in seconds.</summary>
        /// <returns> number of seconds
        /// </returns>
        virtual public double MaxAgeSeconds
        {
            get
            {
                return maxAgeSeconds;
            }

        }
        /// <summary> Returns the purge interval length.</summary>
        /// <returns> purge interval in seconds
        /// </returns>
        virtual public double PurgeIntervalSeconds
        {
            get
            {
                return purgeIntervalSeconds;
            }

        }
        private double maxAgeSeconds;
        private double purgeIntervalSeconds;

        /// <summary> Ctor.</summary>
        /// <param name="maxAgeSeconds">is the maximum age in seconds
        /// </param>
        /// <param name="purgeIntervalSeconds">is the purge interval
        /// </param>
        public ExpiryTimeCacheDesc(double maxAgeSeconds, double purgeIntervalSeconds)
        {
            this.maxAgeSeconds = maxAgeSeconds;
            this.purgeIntervalSeconds = purgeIntervalSeconds;
        }

        public override String ToString()
        {
            return "ExpiryTimeCacheDesc maxAgeSeconds=" + maxAgeSeconds + " purgeIntervalSeconds=" + purgeIntervalSeconds;
        }
    }
}
