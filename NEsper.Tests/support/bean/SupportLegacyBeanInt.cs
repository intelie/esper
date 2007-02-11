using System;

namespace net.esper.support.bean
{
	public class SupportLegacyBeanInt
	{
		virtual public int intPrimitive
		{
			get { return _fieldIntPrimitive; }
			
		}
		public int _fieldIntPrimitive;
		
		public SupportLegacyBeanInt(int fieldIntPrimitive)
		{
			this._fieldIntPrimitive = fieldIntPrimitive;
		}
		
		public virtual int readIntPrimitive()
		{
			return _fieldIntPrimitive;
		}
	}
}
