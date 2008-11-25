/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern.observer;

import com.espertech.esper.client.EPException;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.pattern.MatchedEventConvertor;
import com.espertech.esper.pattern.MatchedEventMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for applications to extend to implement a pattern observer.
 */
public abstract class EventObserverSupport implements EventObserver
{
    private static Log log = LogFactory.getLog(EventObserverSupport.class);
}
