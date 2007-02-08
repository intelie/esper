using System;

namespace net.esper.support.bean
{
	public sealed class SupportBeanFinal
	{
		public int IntPrimitive
		{
			get { return intPrimitive; }
		}

		private int intPrimitive;
		
		public SupportBeanFinal(int intPrimitive)
		{
			this.intPrimitive = intPrimitive;
		}
	}
}
