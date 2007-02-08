using System;

namespace net.esper.support.util
{
	
	public class SupportCtorIntObjectArray
	{
		virtual public Object[] Arguments
		{
			get
			{
				return arguments;
			}
			
		}
		virtual public int SomeValue
		{
			get
			{
				return someValue;
			}
			
		}
		private Object[] arguments;
		private int someValue;
		
		public SupportCtorIntObjectArray(int someValue)
		{
			this.someValue = someValue;
		}
		
		public SupportCtorIntObjectArray(Object[] arguments)
		{
			this.arguments = arguments;
		}
	}
}
