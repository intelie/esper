using System;

namespace net.esper.support.util
{
	public class SupportCtorInt
	{
		virtual public int SomeValue
		{
			get
			{
				return someValue;
			}
		}

		private int someValue;
		
		public SupportCtorInt(int someValue)
		{
			this.someValue = someValue;
		}
	}
}
