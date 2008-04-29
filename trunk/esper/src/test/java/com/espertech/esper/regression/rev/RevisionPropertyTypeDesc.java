package com.espertech.esper.regression.rev;

import com.espertech.esper.event.EventPropertyGetter;

public class RevisionPropertyTypeDesc
{
    private final EventPropertyGetter revisionGetter;
    private final RevisionGetterParameters revisionGetterParams;

    public RevisionPropertyTypeDesc(EventPropertyGetter revisionGetter, RevisionGetterParameters revisionGetterParams)
    {
        this.revisionGetter = revisionGetter;
        this.revisionGetterParams = revisionGetterParams;
    }

    public EventPropertyGetter getRevisionGetter()
    {
        return revisionGetter;
    }

    public RevisionGetterParameters getRevisionGetterParams()
    {
        return revisionGetterParams;
    }
}
