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

        /// <summary>
        /// Constructor.
        /// </summary>
        /// <param name="descriptor">The descriptor.</param>
        /// <param name="key">is the key to supply as parameter to the mapped property getter</param>
		
		public KeyedPropertyGetter(IndexedPropertyDescriptor descriptor, Object key)
		{
			this.key = key;
			this.descriptor = descriptor;
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
			catch (TargetException e)
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

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override String ToString()
		{
			return
				"KeyedMethodPropertyGetter " +
				" descriptor=" + descriptor.ToString() +
				" key=" + key;
		}
	}
}
