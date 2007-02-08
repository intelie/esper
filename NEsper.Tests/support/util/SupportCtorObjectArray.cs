using System;

namespace net.esper.support.util
{
	
	public class SupportCtorObjectArray
	{
		virtual public Object[] Arguments
		{
			get
			{
				return arguments;
			}
			
		}
		private Object[] arguments;
		
		public SupportCtorObjectArray(Object[] arguments)
		{
			this.arguments = arguments;
		}
	}
}
