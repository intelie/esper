///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.core;
using net.esper.compat;
using net.esper.schedule;

using org.apache.commons.logging;

namespace net.esper.support.schedule
{
	public class SupportSchedulingServiceImpl : SchedulingService
	{
		private EDictionary<long, ScheduleHandle> added = new HashDictionary<long, ScheduleHandle>();
		private long currentTime;

		public EDictionary<long, ScheduleHandle> GetAdded()
		{
			return added;
		}

		public void EvaluateLock()
		{
			//To change body of implemented methods use File | Settings | File Templates.
		}

		public void EvaluateUnLock()
		{
			//To change body of implemented methods use File | Settings | File Templates.
		}

		public void Add(long afterMSec, ScheduleHandle callback, ScheduleSlot slot)
		{
			log.Debug(".add Not implemented, afterMSec=" + afterMSec + " callback=" + callback.GetType().FullName);
			added.Put(afterMSec, callback);
		}

		public void Add(ScheduleSpec scheduleSpecification, ScheduleHandle callback, ScheduleSlot slot)
		{
			log.Debug(".add Not implemented, scheduleSpecification=" + scheduleSpecification +
			          " callback=" + callback.GetType().FullName);
		}

		public void Remove(ScheduleHandle callback, ScheduleSlot slot)
		{
			log.Debug(".remove Not implemented, callback=" + callback.GetType().FullName);
		}

		public long Time {
			get {
				log.Debug(".getTime Time is " + currentTime);
				return this.currentTime;
			}
			set {
				log.Debug(".setTime Setting new time, currentTime=" + value);
				this.currentTime = value;
			}
		}

		public void Evaluate(ICollection<ScheduleHandle> handles)
		{
			log.Debug(".evaluate Not implemented");
		}

		public ScheduleBucket AllocateBucket()
		{
			return new ScheduleBucket(0);
		}

		public static void EvaluateSchedule(SchedulingService service)
		{
			IList<ScheduleHandle> handles = new List<ScheduleHandle>();
			service.Evaluate(handles);

			foreach (ScheduleHandle handle in handles)
			{
				if (handle is EPStatementHandleCallback)
				{
					EPStatementHandleCallback callback = (EPStatementHandleCallback) handle;
					callback.ScheduleCallback.ScheduledTrigger(null);
				}
				else
				{
					ScheduleHandleCallback cb = (ScheduleHandleCallback) handle;
					cb.ScheduledTrigger(null);
				}
			}
		}

		private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
