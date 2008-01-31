package com.espertech.esper.support.eql;

import com.espertech.esper.eql.core.ViewResourceCallback;

import java.util.List;
import java.util.LinkedList;

public class SupportViewResourceCallback implements ViewResourceCallback
{
    private List<Object> resources = new LinkedList<Object>();

    public void setViewResource(Object resource)
    {
        resources.add(resources);
    }

    public List<Object> getResources()
    {
        return resources;
    }
}
