using System;

using net.esper.collection;
using net.esper.view.internal_Renamed;

namespace net.esper.support.view
{
	
	public class SupportBufferObserver : BufferObserver
	{
		virtual public bool AndResetHasNewData
		{
			get
			{
				bool result = hasNewData;
				hasNewData = false;
				return result;
			}
			
		}
		virtual public int AndResetStreamId
		{
			get
			{
				int id = streamId;
				streamId = 0;
				return id;
			}
			
		}
		virtual public EventBuffer AndResetNewEventBuffer
		{
			get
			{
				EventBuffer buf = newEventBuffer;
				newEventBuffer = null;
				return buf;
			}
			
		}
		virtual public EventBuffer AndResetOldEventBuffer
		{
			get
			{
				EventBuffer buf = oldEventBuffer;
				oldEventBuffer = null;
				return buf;
			}
			
		}
		private bool hasNewData;
		private int streamId;
		private EventBuffer newEventBuffer;
		private EventBuffer oldEventBuffer;
		
		public virtual void  newData(int streamId, EventBuffer newEventBuffer, EventBuffer oldEventBuffer)
		{
			if (hasNewData == true)
			{
				throw new System.SystemException("Observer already has new data");
			}
			
			hasNewData = true;
			this.streamId = streamId;
			this.newEventBuffer = newEventBuffer;
			this.oldEventBuffer = oldEventBuffer;
		}
	}
}
