using System;
using System.Reflection;

namespace net.esper.events
{
	
	/// <summary> Property getter using CGLib's FastMethod instance.</summary>
	public class CGLibPropertyGetter : EventPropertyGetter
	{
		//UPGRADE_NOTE: Final was removed from the declaration of 'fastMethod '. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1003'"
		private FastMethod fastMethod;
		
		/// <summary> Constructor.</summary>
		/// <param name="fastMethod">is the method to use to retrieve a value from the object.
		/// </param>
		public CGLibPropertyGetter(FastMethod fastMethod)
		{
			this.fastMethod = fastMethod;
		}
		
		public Object GetValue(EventBean obj)
		{
			Object underlying = obj.Underlying;
			
			try
			{
				return fastMethod.invoke(underlying, null);
			}
			catch (System.InvalidCastException e)
			{
				throw new PropertyAccessException("Mismatched getter instance to event bean type");
			}
			catch (System.Reflection.TargetInvocationException e)
			{
				throw new PropertyAccessException(e);
			}
		}
		
		public override String ToString()
		{
			return "CGLibPropertyGetter " + "fastMethod=" + fastMethod.ToString();
		}
	}
}