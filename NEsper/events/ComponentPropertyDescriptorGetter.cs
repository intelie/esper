using System;
using System.ComponentModel;

namespace net.esper.events
{
    /// <summary>
    /// An EventPropertyGetter that uses the internal PropertyDescriptor
    /// provided by the ComponentModel.
    /// </summary>

	public class ComponentPropertyDescriptorGetter : EventPropertyGetter
	{
		private readonly PropertyDescriptor propDesc;
		
		/// <summary>Constructor.</summary>
		
        public ComponentPropertyDescriptorGetter(PropertyDescriptor propDesc)
		{
        	this.propDesc = propDesc;
		}

        /// <summary>
        /// Gets the <see cref="System.Object"/> with the specified bean as the key.
        /// </summary>
        /// <value></value>

        public Object GetValue(EventBean eventBean)
        {
            Object underlying = eventBean.Underlying;

            try
            {
                return propDesc.GetValue(underlying);
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
			return 
				"ComponentPropertyDescriptorGetter " +
				"propDesc=" + propDesc.ToString();
		}
	}
}
