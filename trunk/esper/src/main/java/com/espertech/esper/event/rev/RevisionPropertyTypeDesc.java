package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventPropertyGetter;

public class RevisionPropertyTypeDesc
{
    private final EventPropertyGetter revisionGetter;
    private final RevisionGetterParameters revisionGetterParams;
    private final Class propertyType;

    public RevisionPropertyTypeDesc(EventPropertyGetter revisionGetter, RevisionGetterParameters revisionGetterParams, Class propertyType)
    {
        this.revisionGetter = revisionGetter;
        this.revisionGetterParams = revisionGetterParams;
        this.propertyType = propertyType;
    }

    public EventPropertyGetter getRevisionGetter()
    {
        return revisionGetter;
    }

    public RevisionGetterParameters getRevisionGetterParams()
    {
        return revisionGetterParams;
    }

    public Class getPropertyType()
    {
        return propertyType;
    }
}
