using System;

namespace net.esper.support.eql
{
	
	public class SupportStaticMethodLib
	{
		public static Object staticMethod(Object _object)
		{
			return _object;
		}
		
		public static void  throwException()
		{
			throw new System.Exception("SupportStaticMethod.exceptionThrower throwing a fit");
		}
	}
}
