using System;
using System.ComponentModel;
using System.Reflection;

using net.esper.events.property;

namespace net.esper.events
{
	/// <summary>
    /// Encapsulates the event property information.  In the .NET version we rely upon
    /// reflection to provide us with the basic abstraction thats necessary here.
	/// </summary>
	
    public class EventPropertyDescriptor
	{
        private String propertyName;
        private String listedName;
        private PropertyDescriptor propertyDesc;
        private EventPropertyType propertyType;
        
        /// <summary> Return the property name, for mapped and indexed properties this is just the property name
		/// without parantheses or brackets.
		/// </summary>
		/// <returns> property name
		/// </returns>
		
        virtual public String PropertyName
		{
			get { return propertyName; }
		}

		/// <summary> Returns the listed name, which is the name the property would show up as when asking an
		/// event type for the set of valid properties. The listed name for indexed properties
		/// is "name[]" since an index is required for valid property access.
		/// The listed name for mapped properties is "name()".
		/// </summary>
		/// <returns> listed name
		/// </returns>
		
        virtual public String ListedName
		{
			get { return listedName; }			
		}

		/// <summary> Returns an enum indicating the type of property: simple, mapped, indexed.</summary>
		/// <returns> enum with property type info
		/// </returns>
		
        virtual public EventPropertyType PropertyType
		{
			get { return propertyType; }
		}

        /// <summary>
        /// Gets the property descriptor.
        /// </summary>
        /// <value>The descriptor.</value>
        
        virtual public PropertyDescriptor Descriptor
        {
        	get { return propertyDesc ; }
        }

        /// <summary>
        /// Returns the type of the underlying method or field of the event property.
        /// </summary>
        /// <value>The type of the return.</value>
        /// <returns> return type
        /// </returns>

        virtual public Type ReturnType
		{
			get
			{
				return propertyDesc.PropertyType ;
			}
		}

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="propertyName">name of property, from getter method</param>
        /// <param name="listedName">name the property may show up when listed as a valid property, such as indexed[], mapped()</param>
        /// <param name="property">component property descriptor</param>
        /// <param name="propertyType">type of property</param>
		
        public EventPropertyDescriptor(String propertyName, String listedName, PropertyDescriptor propertyDesc, EventPropertyType propertyType)
		{
			this.propertyName = propertyName;
			this.listedName = listedName;
			this.propertyDesc = propertyDesc;
			this.propertyType = propertyType;
		}

        public EventPropertyDescriptor(String propertyName, String listedName, MethodInfo methodInfo, EventPropertyType propertyType)
        {
            PropertyDescriptor descriptor =
                (propertyType == EventPropertyType.SIMPLE) ?
                ((PropertyDescriptor)(new SimpleAccessorPropertyDescriptor(propertyName, methodInfo))) :
                ((PropertyDescriptor)(new IndexedAccessorPropertyDescriptor(propertyName, methodInfo)));

            this.propertyName = propertyName;
            this.listedName = listedName;
            this.propertyType = propertyType;
            this.propertyDesc = descriptor;
        }

        public EventPropertyDescriptor(String propertyName, String listedName, FieldInfo fieldInfo, EventPropertyType propertyType)
        {
            this.propertyName = propertyName;
            this.listedName = listedName;
            this.propertyType = propertyType;
            this.propertyDesc = new SimpleFieldPropertyDescriptor(fieldInfo);
        }

		
		public override String ToString()
		{
			return
                "propertyName=" + propertyName + 
                " listedName=" + listedName + 
                " propertyDesc=" + propertyDesc + 
                " propertyType=" + propertyType;
		}
		
		public override bool Equals(Object other)
		{
			if (!(other is EventPropertyDescriptor))
			{
				return false;
			}

			EventPropertyDescriptor otherDesc = (EventPropertyDescriptor) other;
			
            if (otherDesc.propertyName != propertyName)
			{
				return false;
			}
			if (otherDesc.listedName != listedName)
			{
				return false;
			}

            if ( ! Object.Equals( propertyDesc, otherDesc.propertyDesc ) )
			{
				return false;
			}

            if (otherDesc.propertyType != propertyType)
			{
				return false;
			}
			
            return true;
		}
		
		public override int GetHashCode()
		{
			return base.GetHashCode();
		}
	}
}
