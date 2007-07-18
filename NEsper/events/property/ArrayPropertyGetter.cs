using System;
using System.ComponentModel;
using System.Reflection;

using net.esper.events;

namespace net.esper.events.property
{
	/// <summary>
	/// Getter for an array property, identified by a given index,
	/// using vanilla reflection.
	/// </summary>
	
	public class ArrayPropertyGetter : EventPropertyGetter
	{
		private readonly PropertyDescriptor property;
		private readonly int index;
		
		/// <summary> Constructor.</summary>
		/// <param name="prop">the property to use to retrieve a value from the object</param>
		/// <param name="index">the index within the array to get the property from</param>

		public ArrayPropertyGetter(PropertyDescriptor prop, int index)
		{
			this.index = index;
			this.property = prop;
			
			if (index < 0)
			{
				throw new ArgumentOutOfRangeException("Invalid negative index value");
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
                Object value = property.GetValue(underlying);
                if (value is Array)
                {
                    Array arrayValue = value as Array;
                    return
                        (arrayValue.Length > index)
                            ?
                                (arrayValue.GetValue(index))
                            :
                                (null);
                }
                else if (value is System.Collections.IList)
                {
                    System.Collections.IList listValue = value as System.Collections.IList;
                    return
                        (listValue.Count > index)
                            ?
                                (listValue[index])
                            :
                                (null);
                }
                else
                {
                    return null;
                }
            }
            catch( TargetInvocationException e )
            {
                throw new PropertyAccessException(e);
            }
        	catch (InvalidCastException)
			{
				throw new PropertyAccessException("Mismatched getter instance to event bean type");
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
				"ArrayFieldPropertyGetter " +
				" property=" + property.ToString() +
				" index=" + index;
		}
	}
}
