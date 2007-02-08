using System;

namespace net.esper.support.bean
{
	
	public class SupportBeanTimestamp
	{
		virtual public String Id
		{
			get
			{
				return id;
			}
			
		}
		virtual public long Timestamp
		{
			get
			{
				return timestamp;
			}
			
		}
		private String id;
		private long timestamp;
		
		public SupportBeanTimestamp(String id, long timestamp)
		{
			this.id = id;
			this.timestamp = timestamp;
		}
	}
}
