using System;
using System.Reflection;

namespace net.esper.events
{
	/// <summary>
    /// Property getter for fields using Java's vanilla reflection.
    /// </summary>
	
    public sealed class ReflectionPropFieldGetter : EventPropertyGetter
	{
		private readonly FieldInfo field;
		
		/// <summary> Constructor.</summary>
		/// <param name="field">is the regular reflection field to use to obtain values for a property
		/// </param>
		
        public ReflectionPropFieldGetter(FieldInfo field)
		{
			this.field = field;
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
                return field.GetValue(underlying);
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
		
		public override String ToString()
		{
			return "ReflectionPropFieldGetter " + "field=" + field.ToString();
		}
	}
}
