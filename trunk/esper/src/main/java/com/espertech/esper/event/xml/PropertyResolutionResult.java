package com.espertech.esper.event.xml;

import com.espertech.esper.client.EventPropertyGetter;

// TODO - delete me
public class PropertyResolutionResult
{
    private EventPropertyGetter getter;
    private Class returnType;
    private SchemaItem optionalSchemaItem;

    public PropertyResolutionResult(EventPropertyGetter getter, Class returnType, SchemaItem optionalSchemaItem)
    {
        this.getter = getter;
        this.returnType = returnType;
        this.optionalSchemaItem = optionalSchemaItem;
    }

    public EventPropertyGetter getGetter()
    {
        return getter;
    }

    public Class getReturnType()
    {
        return returnType;
    }

    public SchemaItem getOptionalSchemaItem()
    {
        return optionalSchemaItem;
    }
}
