using System;

using net.esper.compat;
using net.esper.schedule;

using org.apache.commons.logging;

namespace net.esper.support.schedule
{

    public class SupportSchedulingServiceImpl : SchedulingService
    {
        private EDictionary<long, ScheduleCallback> added = new EHashDictionary<long, ScheduleCallback>();
        private long currentTime;

        virtual public long Time
        {
            get
            {
                log.Debug(".getTime Time is " + currentTime);
                return this.currentTime;
            }

            set
            {
                log.Debug(".setTime Setting new time, currentTime=" + value);
                this.currentTime = value;
            }
        }

        public EDictionary<long, ScheduleCallback> getAdded()
        {
            return added;
        }

        public virtual void Add(long afterMSec, ScheduleCallback callback, ScheduleSlot slot)
        {
            log.Debug(".Add Not implemented, afterMSec=" + afterMSec + " callback=" + callback.GetType().Name);
            added.Put(afterMSec, callback);
        }

        public virtual void Add(ScheduleSpec scheduleSpecification, ScheduleCallback callback, ScheduleSlot slot)
        {
            log.Debug(".Add Not implemented, scheduleSpecification=" + scheduleSpecification + " callback=" + callback.GetType().Name);
        }

        public virtual void Remove(ScheduleCallback callback, ScheduleSlot slot)
        {
            log.Debug(".Remove Not implemented, callback=" + callback.GetType().Name);
        }

        public virtual void Evaluate()
        {
            log.Debug(".evaluate Not implemented");
        }

        public virtual ScheduleBucket allocateBucket()
        {
            return new ScheduleBucket(0);
        }

        private static readonly Log log = LogFactory.GetLog(typeof(SupportSchedulingServiceImpl));
    }
}
