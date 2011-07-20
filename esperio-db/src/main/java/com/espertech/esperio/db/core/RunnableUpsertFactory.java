/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.db.core;

import com.espertech.esper.client.EventBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.Executor;

public class RunnableUpsertFactory implements RunnableFactory
{
    private static Log log = LogFactory.getLog(RunnableUpsertFactory.class);

    private final RunnableUpsertContext context;

    public RunnableUpsertFactory(RunnableUpsertContext context)
    {
        this.context = context;
    }

    public RunnableUpsertContext getContext()
    {
        return context;
    }

    public Runnable makeRunnable(EventBean eventBean) {
        return new RunnableUpsert(context, eventBean);
    }
}