using System;

using net.esper.filter;
using net.esper.pattern;
using net.esper.schedule;
using net.esper.support.events;
using net.esper.support.schedule;
using net.esper.view;

namespace net.esper.support.pattern
{
	
	public class SupportPatternContextFactory
	{
		public static PatternContext makeContext()
		{
			SupportSchedulingServiceImpl sched = new SupportSchedulingServiceImpl();
			return new PatternContext(null, sched, sched.AllocateBucket(), SupportEventAdapterService.Service);
		}
	}
}
