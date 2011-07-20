/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.event;

import java.util.Set;
import java.util.LinkedHashSet;

/**
 * Provides metadata for event types.
 */
public class EventTypeMetadata
{
    private final String publicName;
    private final String primaryName;
    private final Set<String> optionalSecondaryNames;
    private final TypeClass typeClass;
    private final boolean isApplicationPreConfiguredStatic;
    private final boolean isApplicationPreConfigured;
    private final boolean isApplicationConfigured;
    private final ApplicationType optionalApplicationType;
    private final boolean isPropertyAgnostic;   // Type accepts any property name (i.e. no-schema XML type)

    /**
     * Ctor.
     * @param primaryName the primary name by which the type became known.
     * @param secondaryNames a list of additional names for the type, such as fully-qualified class name
     * @param typeClass type of the type
     * @param applicationConfigured true if configured by the application
     * @param applicationType type of application class or null if not an application type
     * @param isPropertyAgnostic true for types that accept any property name as a valid property (unchecked type)
     */
    protected EventTypeMetadata(String primaryName, Set<String> secondaryNames, TypeClass typeClass, boolean isApplicationPreConfiguredStatic, boolean applicationPreConfigured, boolean applicationConfigured, ApplicationType applicationType, boolean isPropertyAgnostic)
    {
        this.publicName = primaryName;
        this.primaryName = primaryName;
        this.optionalSecondaryNames = secondaryNames;
        this.typeClass = typeClass;
        this.isApplicationConfigured = applicationConfigured;
        this.isApplicationPreConfigured = applicationPreConfigured;
        this.isApplicationPreConfiguredStatic = isApplicationPreConfiguredStatic;
        this.optionalApplicationType = applicationType;
        this.isPropertyAgnostic = isPropertyAgnostic;
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
        return new EventTypeMetadata(name, null, typeClass, true, true, true, null, false);
    }

    /**
     * Factory for a bean type.
     * @param name type name
     * @param clazz java class
     * @param isConfigured whether the class was made known or is discovered
     * @return instance
     */
    public static EventTypeMetadata createBeanType(String name, Class clazz, boolean isPreConfiguredStatic, boolean isPreConfigured, boolean isConfigured)
    {
        Set<String> secondaryNames = null;
        if (name == null)
        {
            name = clazz.getName();
        }
        else
        {
            if (!name.equals(clazz.getName()))
            {
                secondaryNames = new LinkedHashSet<String>();
                secondaryNames.add(clazz.getName());
            }
        }
        return new EventTypeMetadata(name, secondaryNames, TypeClass.APPLICATION, isPreConfiguredStatic, isPreConfigured, isConfigured, ApplicationType.CLASS, false);
    }

    /**
     * Factory for a XML type.
     * @param name type name
     * @param isPropertyAgnostic true for types that accept any property name as a valid property (unchecked type)
     * @return instance
     */
    public static EventTypeMetadata createXMLType(String name, boolean isPreconfiguredStatic, boolean isPropertyAgnostic)
    {
        return new EventTypeMetadata(name, null, TypeClass.APPLICATION, isPreconfiguredStatic, true, true, ApplicationType.XML, isPropertyAgnostic);
    }

    /**
     * Factory for an anonymous type.
     * @param associationName what the type is associated with
     * @return instance
     */
    public static EventTypeMetadata createAnonymous(String associationName)
    {
        return new EventTypeMetadata(associationName, null, TypeClass.ANONYMOUS, false, false, false, null, false);
    }

    /**
     * Factory for a wrapper type.
     * @param eventTypeName insert-into of create-window name
     * @param namedWindow true for named window
     * @param insertInto true for insert-into
     * @param isPropertyAgnostic true for types that accept any property name as a valid property (unchecked type)
     * @return instance
     */
    public static EventTypeMetadata createWrapper(String eventTypeName, boolean namedWindow, boolean insertInto, boolean isPropertyAgnostic)
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
        return new EventTypeMetadata(eventTypeName, null, typeClass, false, false, false, null, isPropertyAgnostic);
    }

    /**
     * Factory for a map type.
     * @param name insert-into of create-window name
     * @param namedWindow true for named window
     * @param insertInto true for insert-into
     * @param configured whether the made known or is discovered
     * @return instance
     */
    public static EventTypeMetadata createMapType(String name, boolean preconfiguredStatic, boolean preconfigured, boolean configured, boolean namedWindow, boolean insertInto)
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
            typeClass = TypeClass.ANONYMOUS;
        }
        return new EventTypeMetadata(name, null, typeClass, preconfiguredStatic, preconfigured, configured, applicationType, false);
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
     * Returns the name provided through #EventType.getName.
     * @return name or null if no public name
     */
    public String getPublicName()
    {
        return publicName;
    }

    /**
     * Returns true for types that accept any property name as a valid property (unchecked type).
     * @return indicator whether type is unchecked (agnostic to property)
     */
    public boolean isPropertyAgnostic()
    {
        return isPropertyAgnostic;
    }

    /**
     * Returns true to indicate the type is pre-configured, i.e. added through static or runtime configuration.
     * @return indicator
     */
    public boolean isApplicationPreConfigured() {
        return isApplicationPreConfigured;
    }

    /**
     * Returns true to indicate the type is pre-configured, i.e. added through static configuration but not runtime configuation.
     * @return indicator
     */
    public boolean isApplicationPreConfiguredStatic() {
        return isApplicationPreConfiguredStatic;
    }

    /**
     * Metatype.
     */
    public static enum TypeClass
    {
        /**
         * A type that represents the information made available via insert-into.
         */
        STREAM(true),

        /**
         * A revision event type.
         */
        REVISION(true),

        /**
         * A variant stream event type.
         */
        VARIANT(true),

        /**
         * An application-defined event type such as JavaBean or legacy Java, XML or Map.
         */
        APPLICATION(true),

        /**
         * A type representing a named window.
         */
        NAMED_WINDOW(true),

        /**
         * An anonymous event type.
         */
        ANONYMOUS(false);

        private boolean isPublic;

        /**
         * Returns true to indicate this is a public type that may be queried for name.
         * @return indicator public type versus anonymous
         */
        public boolean isPublic()
        {
            return isPublic;
        }

        TypeClass(boolean isPublic)
        {
            this.isPublic = isPublic;
        }
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
