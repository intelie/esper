package com.espertech.esper.event;

import java.util.Set;
import java.util.LinkedHashSet;

public class EventTypeMetadata
{
    private final String primaryAssociationName;
    private final Set<String> optionalSecondaryNames;
    private final TypeClass typeClass;
    private final boolean isApplicationConfigured;
    private final ApplicationType optionalApplicationType;

    private EventTypeMetadata(String primaryName, Set<String> secondaryNames, TypeClass typeClass, boolean applicationConfigured, ApplicationType applicationType)
    {
        this.primaryAssociationName = primaryName;
        this.optionalSecondaryNames = secondaryNames;
        this.typeClass = typeClass;
        isApplicationConfigured = applicationConfigured;
        this.optionalApplicationType = applicationType;
    }

    public static EventTypeMetadata createValueAdd(String name, TypeClass typeClass)
    {
        if ((typeClass != TypeClass.VARIANT) && (typeClass != TypeClass.REVISION))
        {
            throw new IllegalArgumentException("Type class " + typeClass + " invalid");
        }
        return new EventTypeMetadata(name, null, typeClass, true, null);
    }

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

    public static EventTypeMetadata createXMLType(String alias)
    {
        return new EventTypeMetadata(alias, null, TypeClass.APPLICATION, true, ApplicationType.XML);
    }

    public static EventTypeMetadata createAnonymous(String associationName)
    {
        return new EventTypeMetadata(associationName, null, TypeClass.ANONYMOUS, false, null);
    }

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

    public String getPrimaryAssociationName()
    {
        return primaryAssociationName;
    }

    public Set<String> getOptionalSecondaryNames()
    {
        return optionalSecondaryNames;
    }

    public TypeClass getTypeClass()
    {
        return typeClass;
    }

    public boolean isApplicationConfigured()
    {
        return isApplicationConfigured;
    }

    public ApplicationType getOptionalApplicationType()
    {
        return optionalApplicationType;
    }

    public static enum TypeClass
    {
        STREAM,
        REVISION,
        VARIANT,
        APPLICATION,
        NAMED_WINDOW,
        ANONYMOUS
    }

    public static enum ApplicationType
    {
        XML,
        MAP,
        CLASS,
    }
}
