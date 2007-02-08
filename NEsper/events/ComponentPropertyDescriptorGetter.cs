using System;
using System.ComponentModel;

namespace net.esper.events
{
	public class ComponentPropertyDescriptorGetter : EventPropertyGetter
	{
		private readonly PropertyDescriptor propDesc;
		
		/// <summary> Constructor.</summary>
		
        public ComponentPropertyDescriptorGetter(PropertyDescriptor propDesc)
		{
        	this.propDesc = propDesc;
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
        }
		
		public override String ToString()
		{
			return 
				"ComponentPropertyDescriptorGetter " +
				"propDesc=" + propDesc.ToString();
		}
	}
}
