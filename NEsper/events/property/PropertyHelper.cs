using System;
using System.ComponentModel;
using System.Collections.Generic;
using System.Reflection;

using net.esper.compat;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.events.property
{
    /// <summary>
    /// This class offers utililty methods around introspection.
    /// </summary>

    public class PropertyHelper
    {
        /// <summary>
        /// Return getter for the given member
        /// </summary>
        /// <param name="propertyDesc">The property descriptor.</param>
        /// <returns></returns>

        public static EventPropertyGetter GetGetter( PropertyDescriptor propertyDesc )
        {
        	return new ComponentPropertyDescriptorGetter( propertyDesc ) ;
        }

        /// <summary>
        /// Introspects the given class and returns event property descriptors for each property found
        /// in the class itself, it's superclasses and all interfaces this class and the superclasses implements.
		/// </summary>
        /// <param name="type">the Class to introspect</param>
		/// <returns>list of properties</returns>
        
		public static IList<EventPropertyDescriptor> GetProperties(Type type)
        {
            // Determine all interfaces implemented and the interface's parent interfaces if any
            Set<Type> propertyOrigClasses = new HashSet<Type>();
            GetImplementedInterfaceParents(type, propertyOrigClasses);

            // Add class itself
            propertyOrigClasses.Add(type);

            // Get the set of property names for all classes
            IList<EventPropertyDescriptor> properties = GetPropertiesForClasses(propertyOrigClasses);

            return properties;
        }

		/// <summary>
		/// Gets the implemented interface parents.
		/// </summary>
		/// <param name="type">The type.</param>
		/// <param name="typesResult">The classes result.</param>
        
		private static void GetImplementedInterfaceParents(Type type, Set<Type> typesResult)
        {
			Type[] interfaces = type.GetInterfaces();

            if (interfaces == null)
            {
                return;
            }

			foreach( Type typeInterface in interfaces )
            {
                typesResult.Add(typeInterface);
                GetImplementedInterfaceParents( typeInterface, typesResult);
            }
        }

		/// <summary>
		/// Gets the properties for classes.
		/// </summary>
		/// <param name="propertyClasses">The property classes.</param>
		/// <returns></returns>
        
		private static IList<EventPropertyDescriptor> GetPropertiesForClasses(Set<Type> propertyClasses)
        {
            IList<EventPropertyDescriptor> result = new List<EventPropertyDescriptor>();

            foreach (Type type in propertyClasses)
            {
                AddSimpleProperties(type, result);
                AddMappedProperties(type, result);
                AddIndexedProperties(type, result);
            }

            RemoveDuplicateProperties(result);
            RemoveClrProperties(result);

            return result;
        }

        /// <summary>
        /// Remove CLR specific properties from the given list of property descriptors.
        /// </summary>
        /// <param name="properties">is the list of property descriptors</param>
        
        public static void RemoveClrProperties(IList<EventPropertyDescriptor> properties)
        {
            IList<EventPropertyDescriptor> toRemove = new List<EventPropertyDescriptor>();

            // add removed entries to separate list
            foreach (EventPropertyDescriptor desc in properties)
            {
                switch( desc.PropertyName ) {
                    case "type":
                    case "hashCode":
                    case "toString": // does not occur naturally (added for completeness)
                        toRemove.Add(desc);
                        break ;
                }
            }

            // remove
            foreach (EventPropertyDescriptor desc in toRemove)
            {
                properties.Remove(desc);
            }
        }

        /// <summary>
        /// Removes duplicate properties using the property name to find unique properties.
        /// </summary>
        /// <param name="properties">is a list of property descriptors</param>

        public static void RemoveDuplicateProperties(IList<EventPropertyDescriptor> properties)
        {
            IDictionary<String, EventPropertyDescriptor> set = new Dictionary<String, EventPropertyDescriptor>();
            IList<EventPropertyDescriptor> toRemove = new List<EventPropertyDescriptor>();

            // add duplicates to separate list
            foreach (EventPropertyDescriptor desc in properties)
            {
                if (set.ContainsKey(desc.PropertyName))
                {
                    toRemove.Add(desc);
                    continue;
                }

                set[desc.PropertyName] = desc;
            }

            // remove duplicates
            foreach (EventPropertyDescriptor desc in toRemove)
            {
                properties.Remove(desc);
            }
        }

        /// <summary>
        /// Adds to the given list of property descriptors the properties of the
        /// given class using the introspected properties. This also finds array
        /// and indexed properties.
        /// </summary>
        /// <param name="type">to introspect</param>
        /// <param name="result">is the list to add to</param>

        public static void AddSimpleProperties(Type type, IList<EventPropertyDescriptor> result)
        {
            // Get the standard set of properties that are returned by
            // the TypeDescriptor.

            foreach (PropertyDescriptor baseProperty in TypeDescriptor.GetProperties(type))
            {
            	String propertyName = baseProperty.Name;
                result.Add( new EventPropertyDescriptor( propertyName, propertyName, baseProperty, EventPropertyType.SIMPLE ) ) ;
            }

            // Get the simple fields
            
            foreach ( FieldInfo fieldInfo in GetSimpleFields( type ) ) 
            {
            	String propertyName = fieldInfo.Name;
            	PropertyDescriptor property = new SimpleFieldPropertyDescriptor( propertyName, fieldInfo ) ;
            	result.Add( new EventPropertyDescriptor( propertyName, propertyName, property, EventPropertyType.SIMPLE ) ) ;
            }
            
            // Get non-standard set of properties that are obtained by
            // looking at methods with the getXXX accessor naming pattern.

            foreach (MethodInfo methodInfo in GetSimpleAccessors(type))
            {
            	String propertyName = GetAccessorPropertyName( methodInfo ) ;
            	PropertyDescriptor property = new SimpleAccessorPropertyDescriptor( propertyName, methodInfo ) ;
                result.Add( new EventPropertyDescriptor( propertyName, propertyName, property, EventPropertyType.SIMPLE ) ) ;
            }
        }
		
		/// <summary>
		/// Adds to the given list of property descriptors the indexed properties, i.e.
		/// properties have a getter method taking a single Int value as a parameter.
		/// </summary>
		/// <param name="type"></param>
		/// <param name="result"></param>

        public static void AddIndexedProperties(Type type, IList<EventPropertyDescriptor> result)
		{
            HashSet<String> uniquePropertyNames = new HashSet<String>();

            foreach( MethodInfo methodInfo in GetIndexedAccessors(type) )
            {
            	String inferredName = GetAccessorPropertyName( methodInfo ) ;
                if (uniquePropertyNames.Contains(inferredName))
                {
                    continue;
                }

                String listedName = inferredName + "[]";    // indexed properties add [] to name

                PropertyDescriptor descriptor = new IndexedAccessorPropertyDescriptor( inferredName, methodInfo ) ;
                result.Add( new EventPropertyDescriptor( inferredName, listedName, descriptor, EventPropertyType.INDEXED ) ) ;
                uniquePropertyNames.Add(inferredName);
            }
		}
        
        /// <summary>
        /// Adds to the given list of property descriptors the mapped properties, ie.
        /// properties that have a getter method taking a single String value as a parameter.
        /// </summary>
        /// <param name="type">type to introspect</param>
        /// <param name="result">the list to add to</param>

        public static void AddMappedProperties(Type type, IList<EventPropertyDescriptor> result)
        {
            HashSet<String> uniquePropertyNames = new HashSet<String>();

            foreach( MethodInfo methodInfo in GetMappedAccessors(type) )
            {
            	String inferredName = GetAccessorPropertyName( methodInfo ) ;
                if (uniquePropertyNames.Contains(inferredName))
                {
                    continue;
                }

                String listedName = inferredName + "()";    // mapped properties add () to name

                PropertyDescriptor descriptor = new IndexedAccessorPropertyDescriptor( inferredName, methodInfo ) ;
                result.Add( new EventPropertyDescriptor( inferredName, listedName, descriptor, EventPropertyType.MAPPED ) ) ;
                uniquePropertyNames.Add(inferredName);
            }
        }

        /// <summary>
        /// Gets the name that should be assigned to the property bound to the accessorMethod
        /// </summary>
        /// <param name="accessorMethod"></param>
        /// <returns></returns>
        
        public static string GetAccessorPropertyName( MethodInfo accessorMethod )
        {
        	// Start by removing the "get" from the front of the accessorMethod name
        	String inferredName = accessorMethod.Name.Substring( 3 );
            String newInferredName = null;
            // Leave uppercase inferred names such as URL
            if (inferredName.Length >= 2)
            {
                if (Char.IsUpper(inferredName[0]) &&
            	    Char.IsUpper(inferredName[1]))
                {
                    newInferredName = inferredName;
                }
            }
            // camelCase the inferred name
            if (newInferredName == null)
            {
                newInferredName = Char.ToString(Char.ToLower(inferredName[0]));
                if (inferredName.Length > 1)
                {
                	newInferredName += inferredName.Substring(1) ;
                }
            }
            
            return newInferredName;
        }
        
        /// <summary>
        /// Returns all simple fields
        /// </summary>
        /// <param name="type"></param>
        /// <returns></returns>
        
        public static IEnumerable<FieldInfo> GetSimpleFields( Type type )
        {
        	foreach( FieldInfo fieldInfo in type.GetFields( BindingFlags.Public | BindingFlags.GetField ) )
        	{
        		yield return fieldInfo ;
        	}
        }
        
        /// <summary>
        /// Returns an enumerable that provides all accessors that have the
        /// no parameters.
        /// </summary>
        /// <param name="type"></param>
        /// <returns></returns>

        public static IEnumerable<MethodInfo> GetSimpleAccessors(Type type)
        {
            foreach (MethodInfo methodInfo in GetAccessors(type))
            {
                ParameterInfo[] methodParams = methodInfo.GetParameters();
                int length = methodParams.Length;
                if (methodParams.Length == 0)
                {
                    switch (methodInfo.Name)
                    {
                        case "GetType":
                        case "GetHashCode":
                            break;
                        default:
                            yield return methodInfo;
                            break;
                    }
                }
            }
        }

        /// <summary>
        /// Returns an enumerable that provides all accessors that take one
        /// parameter of type int.
        /// </summary>
        /// <param name="type"></param>
        /// <returns></returns>

        public static IEnumerable<MethodInfo> GetIndexedAccessors( Type type ) 
        {
        	foreach( MethodInfo methodInfo in GetAccessors( type ) ) {
        		ParameterInfo[] methodParams = methodInfo.GetParameters() ;
        		if (( methodParams.Length == 1 ) &&
        		    ( methodParams[0].ParameterType == typeof( int ))) {
        			yield return methodInfo ;
        		}
        	}
        }

        /// <summary>
        /// Returns an enumerable that provides all accessors that take one
        /// parameter of type string.
        /// </summary>
        /// <param name="type"></param>
        /// <returns></returns>

        public static IEnumerable<MethodInfo> GetMappedAccessors( Type type )
        {
        	foreach( MethodInfo methodInfo in GetAccessors( type ) ) {
        		ParameterInfo[] methodParams = methodInfo.GetParameters() ;
        		if (( methodParams.Length == 1 ) &&
        		    ( methodParams[0].ParameterType == typeof( string ))) {
        			yield return methodInfo ;
        		}
        	}
        }
        
        /// <summary>
        /// Enumerates all accessor methods for a type
        /// </summary>
        /// <param name="type"></param>
        /// <returns></returns>
        
        public static IEnumerable<MethodInfo> GetAccessors( Type type )
        {
        	foreach( MethodInfo methodInfo in type.GetMethods() )
        	{
        	    string methodName = methodInfo.Name.ToLower();

            	if ((methodInfo.IsSpecialName == false) &&
                    (methodName.StartsWith("get")) &&
            	    (methodName != "get"))
                {
        			yield return methodInfo ;
        		}
        	}
        }
        

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
