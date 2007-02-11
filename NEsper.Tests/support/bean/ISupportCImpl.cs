using System;

namespace net.esper.support.bean
{
	
	[Serializable]
	public class ISupportCImpl : ISupportC
	{
		virtual public String c
		{
			get
			{
				return valueC;
			}
			
		}
		private String valueC;
		
		public ISupportCImpl(String valueC)
		{
			this.valueC = valueC;
		}
	}
}
