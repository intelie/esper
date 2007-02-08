using System;

namespace net.esper.support.bean
{
	
	public class SupportRFIDEvent
	{
		virtual public String Mac
		{
			get
			{
				return mac;
			}
			
		}
		virtual public String ZoneID
		{
			get
			{
				return zoneID;
			}
			
		}
		private String mac;
		private String zoneID;
		
		public SupportRFIDEvent(String mac, String zoneID)
		{
			this.mac = mac;
			this.zoneID = zoneID;
		}
	}
}
