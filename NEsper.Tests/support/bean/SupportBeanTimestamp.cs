using System;

namespace net.esper.support.bean
{
	public class SupportBeanTimestamp
	{
		virtual public String Id
		{
            get { return _id; }
		}

		virtual public long Timestamp
		{
            get { return _timestamp; }
		}

		private String _id;
		private long _timestamp;
		
		public SupportBeanTimestamp(String id, long timestamp)
		{
            this._id = id;
            this._timestamp = timestamp;
		}
	}
}
