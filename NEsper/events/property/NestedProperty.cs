using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;

namespace net.esper.events.property
{
	
	/// <summary> This class represents a nested property, each nesting level made up of a property instance that
	/// can be of type indexed, mapped or simple itself.
	/// <p>
	/// The syntax for nested properties is as follows.
	/// <pre>
	/// a.n
	/// a[1].n
	/// a('1').n
	/// </pre>
	/// </summary>

	public class NestedProperty : Property
	{
		private IList<Property> properties;
		private BeanEventAdapter beanEventAdapter;
		
		/// <summary> Ctor.</summary>
		/// <param name="properties">is the list of Property instances representing each nesting level
		/// </param>
		/// <param name="beanEventAdapter">is the chache and factory for event bean types and event wrappers
		/// </param>

		public NestedProperty( IList<Property> properties, BeanEventAdapter beanEventAdapter)
		{
			this.properties = properties;
			this.beanEventAdapter = beanEventAdapter;
		}
		
		/// <summary> Returns the list of property instances making up the nesting levels.</summary>
		/// <returns> list of Property instances
		/// </returns>

		public IList<Property> Properties
		{
            get { return properties; }
		}
		
		public virtual EventPropertyGetter GetGetter(BeanEventType eventType)
		{
			IList<EventPropertyGetter> getters = new List<EventPropertyGetter>();

            IEnumerator<Property> propertyEnum = properties.GetEnumerator() ;
            if (propertyEnum.MoveNext())
            {
                bool hasNext = true;

                do
                {
                    Property property = propertyEnum.Current;
                    EventPropertyGetter getter = property.GetGetter(eventType);
                    if (getter == null)
                    {
                        return null;
                    }

                    hasNext = propertyEnum.MoveNext();
                    if ( hasNext )
                    {
                        Type type = property.GetPropertyType(eventType);
                        if (type == null)
                        {
                            // if the property is not valid, return null
                            return null;
                        }

                        // DataDictionary cannot be used to further nest as the type cannot be determined
                        if (type == typeof(IDataDictionary))
                        {
                            return null;
                        }

                        if (type.IsArray)
                        {
                            return null;
                        }
                        eventType = (BeanEventType)beanEventAdapter.CreateOrGetBeanType(type);
                    }

                    getters.Add(getter);
                } while (hasNext);
            }

			return new NestedPropertyGetter(getters, beanEventAdapter);
		}
		
		public virtual Type GetPropertyType(BeanEventType eventType)
		{
			Type result = null;

            IEnumerator<Property> propertyEnum = properties.GetEnumerator();
            if (propertyEnum.MoveNext())
            {
                bool hasNext = true;

                do
                {
                    Property property = propertyEnum.Current;
                    result = property.GetPropertyType(eventType);

                    if (result == null)
                    {
                        // property not found, return null
                        return null;
                    }

                    hasNext = propertyEnum.MoveNext();
                    if (hasNext)
                    {
                        // Map cannot be used to further nest as the type cannot be determined
                        if (result == typeof(IDataDictionary))
                        {
                            return null;
                        }

                        if (result.IsArray)
                        {
                            return null;
                        }

                        eventType = beanEventAdapter.CreateOrGetBeanType(result);
                    }
                } while (hasNext);
            }
            
            return result;
		}
	}
}
