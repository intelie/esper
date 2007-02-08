using System;

using net.esper.client;
using net.esper.events;

namespace net.esper.support.events
{
	
	public class SupportEventAdapterService
	{
		public static EventAdapterService Service
		{
			get
			{
				return eventAdapterService;
			}
			
		}
		private static EventAdapterService eventAdapterService;
		static SupportEventAdapterService()
		{
			{
				eventAdapterService = new EventAdapterServiceImpl(null);
			}
		}
	}
}
