using System;

namespace net.esper.support.bean
{
	public sealed class SupportBeanFinal
	{
		public int intPrimitive
		{
			get { return m_intPrimitive; }
		}

		private int m_intPrimitive;
		
		public SupportBeanFinal(int intPrimitive)
		{
			this.m_intPrimitive = intPrimitive;
		}
	}
}
