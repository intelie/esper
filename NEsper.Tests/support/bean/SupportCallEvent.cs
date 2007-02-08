using System;

namespace net.esper.support.bean
{
	public class SupportCallEvent
	{
		virtual public long CallId
		{
			get
			{
				return callId;
			}
		}
		
		virtual public String Source
		{
			get
			{
				return source;
			}
		}
		
		virtual public String Dest
		{
			get
			{
				return dest;
			}
		}
		
		virtual public long StartTime
		{
			get
			{
				return startTime;
			}
		}

		virtual public long EndTime
		{
			get
			{
				return endTime;
			}
		}
		
		private long callId;
		private String source;
		private String dest;
		private long startTime;
		private long endTime;
		
		public SupportCallEvent(long callId, String source, String destination, long startTime, long endTime)
		{
			this.callId = callId;
			this.source = source;
			this.dest = destination;
			this.startTime = startTime;
			this.endTime = endTime;
		}
	}
}
