using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;

namespace net.esper.events.property
{
	
	/// <summary> This class represents a nested property, each nesting level made up of a property instance that
	/// can be of type indexed, mapped or simple itself.
	/// <para>
	/// The syntax for nested properties is as follows.
	/// <c>
	/// a.n
	/// a[1].n
	/// a('1').n
	/// </c>
    /// </para>
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

        /// <summary>
        /// Returns value getter for the property of an event of the given event type.
        /// </summary>
        /// <param name="eventType">is the type of event to make a getter for</param>
        /// <returns>fast property value getter for property</returns>
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

                        if ((type.IsAssignableFrom(typeof(System.Collections.IDictionary))) ||
                            (type.IsAssignableFrom(typeof(System.Collections.Generic.IDictionary<string, Object>))) ||
                            (type.IsAssignableFrom(typeof(System.Collections.Generic.IDictionary<string, EventBean>))))
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

        /// <summary>
        /// Returns the property type.
        /// </summary>
        /// <param name="eventType">is the event type representing the JavaBean</param>
        /// <returns>property type class</returns>
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
                        if ((result.IsAssignableFrom(typeof(System.Collections.IDictionary))) ||
                            (result.IsAssignableFrom(typeof(System.Collections.Generic.IDictionary<string, Object>))) ||
                            (result.IsAssignableFrom(typeof(System.Collections.Generic.IDictionary<string, EventBean>))))
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
