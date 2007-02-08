using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.events
{
	/// <summary> Event type for events that itself have event properties that are event wrappers.
	/// <p>
	/// For use in pattern expression statements in which multiple events match a pattern. There the
	/// composite event indicates that the whole patterns matched, and indicates the
	/// individual events that caused the pattern as event properties to the event.
	/// </summary>
	
	public class CompositeEventType : EventType
	{
		virtual public Type UnderlyingType
		{
			get { return typeof(System.Collections.IDictionary); }
		}
		
		virtual public ICollection<String> PropertyNames
		{
			get { return taggedEventTypes.Keys; }
		}

		private readonly EDictionary<String, EventType> taggedEventTypes ;
		
		/// <summary> Ctor.</summary>
		/// <param name="taggedEventTypes">is a map of name tags and event type per tag 
		/// </param>

		public CompositeEventType( EDictionary<String, EventType> taggedEventTypes )
		{
			this.taggedEventTypes = taggedEventTypes ;
		}
		
		public virtual Type GetPropertyType(String propertyName)
		{
            EventType result = taggedEventTypes.Fetch(propertyName);
			if (result != null)
			{
				return result.UnderlyingType;
			}
			
			// see if this is a nested property
			int index = propertyName.IndexOf('.');
			if (index == - 1)
			{
				return null;
			}
			
			// Take apart the nested property into a map key and a nested value class property name
			String propertyMap = propertyName.Substring(0, (index) - (0));
			String propertyNested = propertyName.Substring(index + 1, (propertyName.Length) - (index + 1));
            result = taggedEventTypes.Fetch(propertyMap);
            if (result == null)
			{
				return null;
			}
			
			// ask the nested class to resolve the property
			return result.GetPropertyType(propertyNested);
		}
		
		public virtual EventPropertyGetter GetGetter(String propertyName)
		{
			// see if this is a nested property
			int index = propertyName.IndexOf('.');
			if (index == - 1)
			{
                EventType result = taggedEventTypes.Fetch(propertyName);
                if (result == null)
				{
					return null;
				}
				
				// Not a nested property, return tag's underlying value
				String tag = propertyName;
				return new TagEventPropertyGetter( tag ) ;
			}
			
			// Take apart the nested property into a map key and a nested value class property name
			String propertyMap = propertyName.Substring(0, (index) - (0));
			String propertyNested = propertyName.Substring(index + 1, (propertyName.Length) - (index + 1));

            EventType result2 = taggedEventTypes.Fetch(propertyMap);
			if ( result2 == null )
			{
				return null;
			}
			
			EventPropertyGetter getterNested = result2.GetGetter(propertyNested);
			if (getterNested == null)
			{
				return null;
			}
			
			return new NestedEventPropertyGetter( propertyMap, getterNested ) ;
		}
		
		public virtual bool isProperty(String propertyName)
		{
			Type propertyType = GetPropertyType(propertyName);
			return propertyType != null;
		}
		
		public virtual IEnumerable<EventType> SuperTypes
		{
            get { return null; }
		}

		public IEnumerable<EventType> DeepSuperTypes
		{
            get { return EventTypeArray.Empty ; }
		}
		
		/// <summary>
		/// An EventPropertyGetter that is based upon a named tag.
		/// </summary>
		
		internal class TagEventPropertyGetter : EventPropertyGetter
		{
			private string tag ;
			
			public TagEventPropertyGetter(string tag)
			{
				this.tag = tag;
			}
			
			public Object GetValue( EventBean obj )
			{
				IDataDictionary map = obj.Underlying as IDataDictionary ;
				if ( map == null )
				{
					throw new PropertyAccessException(
						"Mismatched property getter to event bean type, " +
						"the underlying data object is not of type IDataDictionary");
				}
	
	
				// If the map does not contain the key, this is allowed and represented as null
				EventBean wrapper = (EventBean) map.Fetch( tag, null ) ;
				if (wrapper !=  null)
				{
					return wrapper.Underlying;
				}
	
				return null;
			}
		}

		/// <summary>
		/// An EventPropertyGetter that is based upon a named tag
		/// and a nester EventPropertyGetter.
		/// </summary>
		
		internal class NestedEventPropertyGetter : EventPropertyGetter
		{
			private string tag ;
			private EventPropertyGetter nestedGetter ;
			
			public NestedEventPropertyGetter(string tag, EventPropertyGetter nestedGetter)
			{
				this.tag = tag;
				this.nestedGetter = nestedGetter;
			}
			
			public Object GetValue( EventBean obj )
			{
				IDataDictionary map = obj.Underlying as IDataDictionary ;
				if ( map == null )
				{
					throw new PropertyAccessException(
						"Mismatched property getter to event bean type, " +
						"the underlying data object is not of type IDataDictionary");
				}

				// If the map does not contain the key, this is allowed and represented as null
				EventBean wrapper = (EventBean) map.Fetch( tag, null ) ;
				if (wrapper !=  null)
				{
					return nestedGetter.GetValue(wrapper);
				}
	
				return null;
			}
		}
	}
}
