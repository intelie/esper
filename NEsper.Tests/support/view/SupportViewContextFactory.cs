using System;

using net.esper.schedule;
using net.esper.support.events;
using net.esper.support.schedule;
using net.esper.view;

namespace net.esper.support.view
{
	
	public class SupportViewContextFactory
	{
		public static ViewServiceContext makeContext()
		{
			SupportSchedulingServiceImpl sched = new SupportSchedulingServiceImpl();
			ScheduleBucket bucket = sched.AllocateBucket();
			return new ViewServiceContext(sched, bucket, SupportEventAdapterService.Service);
		}
		
		public static ViewServiceContext makeContext(SupportSchedulingServiceImpl stub)
		{
			return new ViewServiceContext(stub, stub.AllocateBucket(), SupportEventAdapterService.Service);
		}
	}
}
