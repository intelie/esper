using System;
using System.Reflection;

namespace net.esper.events
{
	/// <summary>
    /// Property getter for properties using vanilla reflection.
    /// </summary>
	
    public sealed class ReflectionPropPropertyGetter : EventPropertyGetter
	{
		private readonly PropertyInfo property;
		
		/// <summary> Constructor.</summary>
		/// <param name="property">is the reflection property info to use to obtain values for a property
		/// </param>
		
        public ReflectionPropPropertyGetter(PropertyInfo property)
		{
            this.property = property;
		}

        /// <summary>
        /// Gets the <see cref="System.Object"/> with the specified bean as the key.
        /// </summary>
        /// <value></value>

        public Object GetValue(EventBean obj)
        {
            Object underlying = obj.Underlying;

            try
            {
                return property.GetValue(underlying, null);
            }
            catch (ArgumentException)
            {
                throw new PropertyAccessException("Mismatched getter instance to event bean type");
            }
            catch (UnauthorizedAccessException e)
            {
                throw new PropertyAccessException(e);
            }
            catch (Exception e)
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
			return "ReflectionPropPropertyGetter " + "property=" + property.ToString();
		}
	}
}
