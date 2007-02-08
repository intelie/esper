using System;
using System.ComponentModel;
using System.Reflection;

using net.esper.events;

namespace net.esper.events.property
{
	
	/// <summary>
	/// Getter for a key property identified by a given key value, using vanilla reflection.
	/// </summary>
	
	public class KeyedPropertyGetter : EventPropertyGetter
	{
		private readonly IndexedPropertyDescriptor descriptor;
		private readonly Object key;
		
		/// <summary> Constructor.</summary>
		/// <param name="key">is the key to supply as parameter to the mapped property getter</param>
		
		public KeyedPropertyGetter(IndexedPropertyDescriptor descriptor, Object key)
		{
			this.key = key;
			this.descriptor = descriptor;
		}
		
		public Object GetValue(EventBean obj)
		{
			Object underlying = obj.Underlying;
			
			try
			{
				return descriptor.GetValue( underlying, key ) ;
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
			return
				"KeyedMethodPropertyGetter " +
				" descriptor=" + descriptor.ToString() +
				" key=" + key;
		}
	}
}
