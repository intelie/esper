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
		/// <param name="field">is the regular reflection field to use to obtain values for a property
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
        }
		
		public override String ToString()
		{
			return "ReflectionPropPropertyGetter " + "property=" + property.ToString();
		}
	}
}
