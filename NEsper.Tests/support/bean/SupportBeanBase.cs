using System;

namespace net.esper.support.bean
{
	
	public class SupportBeanBase
	{
		virtual public String Id
		{
			get
			{
				return id;
			}
			
			set
			{
				this.id = value;
			}
			
		}
		private String id;
		
		public SupportBeanBase(String id)
		{
			this.id = id;
		}
		
		public override String ToString()
		{
			return "id=" + id;
		}
	}
}
