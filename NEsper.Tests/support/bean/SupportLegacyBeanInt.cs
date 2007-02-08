using System;

namespace net.esper.support.bean
{
	
	public class SupportLegacyBeanInt
	{
		virtual public int IntPrimitive
		{
			get
			{
				return fieldIntPrimitive;
			}
			
		}
		public int fieldIntPrimitive;
		
		public SupportLegacyBeanInt(int fieldIntPrimitive)
		{
			this.fieldIntPrimitive = fieldIntPrimitive;
		}
		
		public virtual int readIntPrimitive()
		{
			return fieldIntPrimitive;
		}
	}
}
