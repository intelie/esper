using System;

namespace net.esper.support.bean
{
	
	public class SupportBeanWithEnum
	{
		virtual public String String
		{
			get
			{
				return stringValue;
			}
			
		}
		virtual public SupportEnum SupportEnum
		{
			get
			{
				return supportEnum;
			}
			
		}
		private String stringValue;
		private SupportEnum supportEnum;
		
		public SupportBeanWithEnum(String stringValue, SupportEnum supportEnum)
		{
			this.stringValue = stringValue;
			this.supportEnum = supportEnum;
		}
	}
}
