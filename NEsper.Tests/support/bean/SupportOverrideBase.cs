using System;

namespace net.esper.support.bean
{
	
	public class SupportOverrideBase
	{
		virtual public String Val
		{
			get
			{
				return val;
			}
			
		}
		private String val;
		
		public SupportOverrideBase(String val)
		{
			this.val = val;
		}
	}
}
