using System;
using System.Reflection;

using net.esper.events;

namespace net.esper.events.property
{
	/// <summary>
	/// Getter for an array property identified by a given index, using vanilla reflection.
	/// </summary>
	
	public class ArrayMethodPropertyGetter : EventPropertyGetter
	{
		private readonly MethodInfo method;
		private readonly int index;

		/// <summary>
		/// Constructor.
		/// </summary>
		/// <param name="method">is the method to use to retrieve a value from the object</param>
		/// <param name="index">is tge index within the array to get the property from</param>
		
		public ArrayMethodPropertyGetter(MethodInfo method, int index)
		{
			this.index = index;
			this.method = method;
			
			if (index < 0)
			{
				throw new ArgumentException("Invalid negative index value");
			}
		}

		/// <summary>
		/// Gets the value.
		/// </summary>
		/// <param name="obj">The obj.</param>
		/// <returns></returns>
		
		public Object GetValue(EventBean obj)
		{
			Object underlying = obj.Underlying;
			
			try
			{
				Object value = method.Invoke(underlying, (Object[]) null);
				if (((Array) value).Length <= index)
				{
					return null;
				}

				return ((Array) value).GetValue(index);
			}
			catch (InvalidCastException)
			{
				throw new PropertyAccessException("Mismatched getter instance to event bean type");
			}
			catch (TargetInvocationException e)
			{
				throw new PropertyAccessException(e);
			}
			catch (UnauthorizedAccessException e)
			{
				throw new PropertyAccessException(e);
			}
			catch (ArgumentException e)
			{
				throw new PropertyAccessException(e);
			}
		}
		
		public override String ToString()
		{
			return "ArrayMethodPropertyGetter " + " method=" + method.ToString() + " index=" + index;
		}
	}
}