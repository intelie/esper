package com.espertech.esper.event;

import java.util.Set;
import java.util.LinkedHashSet;

/**
 * Provides metadata for event types.
 */
public class EventTypeMetadata
{
    private final String primaryName;
    private final Set<String> optionalSecondaryNames;
    private final TypeClass typeClass;
    private final boolean isApplicationConfigured;
    private final ApplicationType optionalApplicationType;

    /**
     * Ctor.
     * @param primaryName the primary name by which the type became known.
     * @param secondaryNames a list of additional names for the type, such as fully-qualified class name
     * @param typeClass type of the type
     * @param applicationConfigured true if configured by the application
     * @param applicationType type of application class or null if not an application type
     */
    private EventTypeMetadata(String primaryName, Set<String> secondaryNames, TypeClass typeClass, boolean applicationConfigured, ApplicationType applicationType)
    {
        this.primaryName = primaryName;
        this.optionalSecondaryNames = secondaryNames;
        this.typeClass = typeClass;
        isApplicationConfigured = applicationConfigured;
        this.optionalApplicationType = applicationType;
    }

    /**
     * Factory for a value-add type.
     * @param name type name
     * @param typeClass type of type
     * @return instance
     */
    public static EventTypeMetadata createValueAdd(String name, TypeClass typeClass)
    {
        if ((typeClass != TypeClass.VARIANT) && (typeClass != TypeClass.REVISION))
        {
            throw new IllegalArgumentException("Type class " + typeClass + " invalid");
        }
        return new EventTypeMetadata(name, null, typeClass, true, null);
    }

    /**
     * Factory for a bean type.
     * @param alias type name
     * @param clazz java class
     * @param isConfigured whether the class was made known or is discovered
     * @return instance
     */
    public static EventTypeMetadata createBeanType(String alias, Class clazz, boolean isConfigured)
    {
        Set<String> secondaryNames = null;
        if (alias == null)
        {
            alias = clazz.getName();
        }
        else
        {
            if (!alias.equals(clazz.getName()))
            {
                secondaryNames = new LinkedHashSet<String>();
                secondaryNames.add(clazz.getName());
            }
        }
        return new EventTypeMetadata(alias, secondaryNames, TypeClass.APPLICATION, isConfigured, ApplicationType.CLASS);
    }

    /**
     * Factory for a XML type.
     * @param alias type name
     * @return instance
     */
    public static EventTypeMetadata createXMLType(String alias)
    {
        return new EventTypeMetadata(alias, null, TypeClass.APPLICATION, true, ApplicationType.XML);
    }

    /**
     * Factory for an anonymous type.
     * @param associationName what the type is associated with
     * @return instance
     */
    public static EventTypeMetadata createAnonymous(String associationName)
    {
        return new EventTypeMetadata(associationName, null, TypeClass.ANONYMOUS, false, null);
    }

    /**
     * Factory for a wrapper type.
     * @param eventTypeAlias insert-into of create-window name
     * @param namedWindow true for named window
     * @param insertInto true for insert-into
     * @return instance
     */
    public static EventTypeMetadata createWrapper(String eventTypeAlias, boolean namedWindow, boolean insertInto)
    {
        TypeClass typeClass;
        if (namedWindow)
        {
            typeClass = TypeClass.NAMED_WINDOW;
        }
        else if (insertInto)
        {
            typeClass = TypeClass.STREAM;
        }
        else
        {
            throw new IllegalStateException("Unknown Wrapper type, cannot create metadata");
        }
        return new EventTypeMetadata(eventTypeAlias, null, typeClass, false, null);
    }

    /**
     * Factory for a map type.
     * @param alias insert-into of create-window name
     * @param namedWindow true for named window
     * @param insertInto true for insert-into
     * @param configured whether the made known or is discovered
     * @return instance
     */
    public static EventTypeMetadata createMapType(String alias, boolean configured, boolean namedWindow, boolean insertInto)
    {
        TypeClass typeClass;
        ApplicationType applicationType = null;
        if (configured)
        {
            typeClass = TypeClass.APPLICATION;
            applicationType = ApplicationType.MAP;
        }
        else if (namedWindow)
        {
            typeClass = TypeClass.NAMED_WINDOW;
        }
        else if (insertInto)
        {
            typeClass = TypeClass.STREAM;
        }
        else
        {
            throw new IllegalStateException("Unknown Map type, cannot create metadata");
        }
        return new EventTypeMetadata(alias, null, typeClass, configured, applicationType);
    }

    /**
     * Returns the name.
     * @return name
     */
    public String getPrimaryName()
    {
        return primaryName;
    }

    /**
     * Returns second names or null if none found.
     * @return further names
     */
    public Set<String> getOptionalSecondaryNames()
    {
        return optionalSecondaryNames;
    }

    /**
     * Returns the type of the type.
     * @return meta type
     */
    public TypeClass getTypeClass()
    {
        return typeClass;
    }

    /**
     * Returns true if the type originates in a configuration.
     * @return indicator whether configured or not
     */
    public boolean isApplicationConfigured()
    {
        return isApplicationConfigured;
    }

    /**
     * The type of the application event type or null if not an application event type.
     * @return application event type
     */
    public ApplicationType getOptionalApplicationType()
    {
        return optionalApplicationType;
    }

    /**
     * Metatype.
     */
    public static enum TypeClass
    {
        /**
         * A type that represents the information made available via insert-into.
         */
        STREAM,

        /**
         * A revision event type.
         */
        REVISION,

        /**
         * A variant stream event type.
         */
        VARIANT,

        /**
         * An application-defined event type such as JavaBean or legacy Java, XML or Map.
         */
        APPLICATION,

        /**
         * A type representing a named window.
         */
        NAMED_WINDOW,

        /**
         * An anonymous event type.
         */
        ANONYMOUS
    }

    /**
     * Application type.
     */
    public static enum ApplicationType
    {
        /**
         * Xml type.
         */
        XML,

        /**
         * Map type.
         */
        MAP,

        /**
         * Class type.
         */
        CLASS
    }
}