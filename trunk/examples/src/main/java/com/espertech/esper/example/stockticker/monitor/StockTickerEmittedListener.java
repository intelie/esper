/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.stockticker.monitor;

import java.util.List;
import java.util.Collections;
import java.util.LinkedList;

import com.espertech.esper.client.EmittedListener;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class StockTickerEmittedListener implements EmittedListener
{
    private List<Object> matchEvents = Collections.synchronizedList(new LinkedList<Object>());

    public void emitted(Object object)
    {
        log.info(".emitted Received emitted " + object);
        matchEvents.add(object);
    }

    public int getSize()
    {
        return matchEvents.size();
    }

    public List getMatchEvents()
    {
        return matchEvents;
    }

    public void clearMatched()
    {
        matchEvents.clear();
    }

    private static final Log log = LogFactory.getLog(StockTickerEmittedListener.class);
}
