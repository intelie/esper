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

package com.espertech.esper.support.epl;

import com.espertech.esper.epl.core.ViewResourceCallback;

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
