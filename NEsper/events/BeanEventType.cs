using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.collection;
using net.esper.compat;
using net.esper.events.property;

using org.apache.commons.logging;

namespace net.esper.events
{
    /// <summary>
    /// Implementation of the EventType interface for handling JavaBean-type classes.
    /// </summary>

    public class BeanEventType : EventType
    {
        private readonly Type type;
        private readonly BeanEventAdapter beanEventAdapter;
        private readonly ConfigurationEventTypeLegacy optionalLegacyDef;

        private String[] propertyNames;
        private EDictionary<String, Type> simplePropertyTypes;
        private EDictionary<String, EventPropertyGetter> simplePropertyGetters;
        private EDictionary<String, EventPropertyDescriptor> simplePropertyDescriptors;
        private EDictionary<String, EventPropertyDescriptor> mappedPropertyDescriptors;
        private EDictionary<String, EventPropertyDescriptor> indexedPropertyDescriptors;
        private IList<EventType> superTypes;
        private ICollection<EventType> deepSuperTypes;

        /// <summary>Constructor takes a object type as an argument.</summary>
        /// <param name="type">the type of an object</param>
        /// <param name="beanEventAdapter">the cache and factory for event bean types and event wrappers</param>
        /// <param name="optionalLegacyDef">optional configuration supplying legacy event type information</param>

        public BeanEventType(Type type,
                              BeanEventAdapter beanEventAdapter,
                              ConfigurationEventTypeLegacy optionalLegacyDef)
        {
            this.type = type;
            this.beanEventAdapter = beanEventAdapter;
            this.optionalLegacyDef = optionalLegacyDef;

            Initialize();
        }

        /// <summary>
        /// Gets the type of the property.
        /// </summary>
        /// <param name="propertyName">Name of the property.</param>
        /// <returns></returns>

        public Type GetPropertyType(String propertyName)
        {
            Type propertyType = simplePropertyTypes.Fetch(propertyName);
            if (propertyType != null)
            {
                return propertyType;
            }

            Property prop = PropertyParser.parse(propertyName, beanEventAdapter);
            if (prop is SimpleProperty)
            {
                // there is no such property since it wasn't in simplePropertyTypes
                return null;
            }
            return prop.GetPropertyType(this);
        }

        /// <summary>
        /// Determines whether the specified property name is property.
        /// </summary>
        /// <param name="propertyName">Name of the property.</param>
        /// <returns>
        /// 	<c>true</c> if the specified property name is property; otherwise, <c>false</c>.
        /// </returns>

        public Boolean IsProperty(String propertyName)
        {
            if (GetPropertyType(propertyName) == null)
            {
                return false;
            }
            return true;
        }

        /// <summary>
        /// Gets the type of the underlying.
        /// </summary>
        /// <returns></returns>

        public Type UnderlyingType
        {
            get
            {
                return type;
            }
        }

        /// <summary>
        /// Gets the getter.
        /// </summary>
        /// <param name="propertyName">Name of the property.</param>
        /// <returns></returns>

        public EventPropertyGetter GetGetter(String propertyName)
        {
            EventPropertyGetter getter = simplePropertyGetters.Fetch(propertyName);
            if (getter != null)
            {
                return getter;
            }

            Property prop = PropertyParser.parse(propertyName, beanEventAdapter);
            if (prop is SimpleProperty)
            {
                // there is no such property since it wasn't in simplePropertyGetters
                return null;
            }
            return prop.GetGetter(this);
        }

        /// <summary>
        /// Looks up and returns a cached simple property's descriptor.
        /// </summary>
        /// <param name="propertyName">propertyName to look up</param>
        /// <returns>property descriptor</returns>

        public EventPropertyDescriptor GetSimpleProperty(String propertyName)
        {
            EventPropertyDescriptor descriptor = simplePropertyDescriptors.Fetch(propertyName);
            return descriptor;
        }

        /// <summary>
        /// Looks up and returns a cached mapped property's descriptor.
        /// </summary>
        /// <param name="propertyName">Name of the property.</param>
        /// <returns></returns>

        public EventPropertyDescriptor GetMappedProperty(String propertyName)
        {
            EventPropertyDescriptor descriptor = mappedPropertyDescriptors.Fetch(propertyName);
            return descriptor;
        }

        /// <summary>
        /// Looks up and returns a cached indexed property's descriptor.
        /// </summary>
        /// <param name="propertyName">Name of the property.</param>
        /// <returns></returns>

        public EventPropertyDescriptor GetIndexedProperty(String propertyName)
        {
            EventPropertyDescriptor descriptor = indexedPropertyDescriptors.Fetch(propertyName);
            return descriptor;
        }

        /// <summary>
        /// Get all valid property names for the event type.
        /// </summary>
        /// <value>The property names.</value>
        /// <returns> A string array containing the property names of this typed event data object.
        /// </returns>
        public ICollection<String> PropertyNames
        {
            get { return propertyNames; }
        }

        /// <summary>
        /// Returns an array of event types that are super to this event type, from which this event type
        /// inherited event properties.  For object instances underlying the event this method returns the
        /// event types for all superclasses extended by the object and all interfaces implemented by the
        /// object.
        /// </summary>
        /// <value></value>
        /// <returns>an array of event types</returns>
        public IEnumerable<EventType> SuperTypes
        {
            get { return superTypes; }
        }

        /// <summary>
        /// Returns enumerable over all super types to event type, going up the hierarchy and including
        /// all interfaces (and their extended interfaces) and superclasses as EventType instances.
        /// </summary>
        /// <value></value>
        public IEnumerable<EventType> DeepSuperTypes
        {
            get { return deepSuperTypes; }
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return String.Format("BeanEventType type={0}", type.FullName);
        }

        private void Initialize()
        {
            PropertyListBuilder propertyListBuilder = PropertyListBuilderFactory.createBuilder(optionalLegacyDef);
            IList<EventPropertyDescriptor> properties = propertyListBuilder.AssessProperties(type);

            this.propertyNames = new String[properties.Count];
            this.simplePropertyTypes = new EHashDictionary<String, Type>();
            this.simplePropertyGetters = new EHashDictionary<String, EventPropertyGetter>();
            this.simplePropertyDescriptors = new EHashDictionary<String, EventPropertyDescriptor>();
            this.mappedPropertyDescriptors = new EHashDictionary<String, EventPropertyDescriptor>();
            this.indexedPropertyDescriptors = new EHashDictionary<String, EventPropertyDescriptor>();

            int count = 0;
            foreach (EventPropertyDescriptor desc in properties)
            {
                String propertyName = desc.PropertyName;
                propertyNames[count++] = desc.ListedName;

                switch (desc.PropertyType)
                {
                    case EventPropertyType.SIMPLE:
                        EventPropertyGetter getter = null;
                        getter = PropertyHelper.GetGetter(desc.Descriptor);
                        simplePropertyTypes[propertyName] = desc.ReturnType;
                        simplePropertyGetters[propertyName] = getter;
                        simplePropertyDescriptors[propertyName] = desc;
                        break;
                    case EventPropertyType.MAPPED:
                        mappedPropertyDescriptors[propertyName] = desc;
                        break;
                    case EventPropertyType.INDEXED:
                        indexedPropertyDescriptors[propertyName] = desc;
                        break;
                }
            }

            // Determine event type super types
            superTypes = GetSuperTypes(type, beanEventAdapter);

            // Determine deep supertypes
            // Get Java super types (superclasses and interfaces), deep get of all in the tree
            ISet<Type> supers = new EHashSet<Type>();
            GetSuper(type, supers);
            RemoveCoreLibInterfaces(supers);    // Remove core library super types

            // Cache the supertypes of this event type for later use
            deepSuperTypes = new EHashSet<EventType>();
            foreach (Type superClass in supers)
            {
                EventType superType = beanEventAdapter.CreateOrGetBeanType(superClass);
                deepSuperTypes.Add(superType);
            }
        }

        /// <summary>
        /// Gets the super types.
        /// </summary>
        /// <param name="type">The type.</param>
        /// <param name="beanEventAdapter">The bean event adapter.</param>
        /// <returns></returns>
        public static IList<EventType> GetSuperTypes(Type type, BeanEventAdapter beanEventAdapter)
        {
            IList<Type> superclasses = new List<Type>();

            // add superclass
            Type superClass = type.BaseType;
            if (superClass != null)
            {
                superclasses.Add(superClass);
            }

            // Add interfaces.  Under the CLR, implemented interfaces are flattened
            // directly under the type so the hierarchy that exists in Java is broken
            // by this method.  This is something to remember should there be issues.

            Type[] interfaces = type.GetInterfaces();
            for (int i = 0; i < interfaces.Length; i++)
            {
                superclasses.Add(interfaces[i]);
            }

            // Build event types, ignoring java language types
            IList<EventType> superTypes = new List<EventType>();
            foreach (Type superclass in superclasses)
            {
                if (superclass.Namespace != "System")
                {
                    EventType superType = beanEventAdapter.CreateOrGetBeanType(superclass);
                    superTypes.Add(superType);
                }
            }

            return superTypes;
        }

        /// <summary>
        /// Add the given class's implemented interfaces and superclasses to the
        /// result set of classes.
        /// </summary>
        /// <param name="type">The type to introspect.</param>
        /// <param name="result">The result.</param>

        public static void GetSuper(Type type, ISet<Type> result)
        {
            GetSuperInterfaces(type, result);
            GetSuperClasses(type, result);
        }

        private static void GetSuperInterfaces(Type type, ISet<Type> result)
        {
            foreach (Type interfaceType in type.GetInterfaces())
            {
                result.Add(interfaceType);
                GetSuperInterfaces(interfaceType, result);
            }
        }

        private static void GetSuperClasses(Type type, ISet<Type> result)
        {
            Type superClass = type.BaseType;
            if (superClass == null)
            {
                return;
            }

            result.Add(superClass);
            GetSuper(superClass, result);
        }

        private static void RemoveCoreLibInterfaces(ISet<Type> classes)
        {
            IList<Type> deadList = new List<Type>();

            foreach (Type type in classes)
            {
                if (type.FullName.StartsWith("System"))
                {
                    deadList.Add(type);
                }
            }

            foreach (Type type in deadList)
            {
                classes.Remove(type);
            }
        }

        private static readonly Log log = LogFactory.GetLog(typeof(BeanEventType));
    }
}