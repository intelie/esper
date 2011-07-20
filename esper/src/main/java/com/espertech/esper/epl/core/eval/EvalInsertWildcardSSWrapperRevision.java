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

package com.espertech.esper.epl.core.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.SelectExprProcessor;
import com.espertech.esper.event.DecoratingEventBean;
import com.espertech.esper.event.vaevent.ValueAddEventProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class EvalInsertWildcardSSWrapperRevision extends EvalBase implements SelectExprProcessor {

    private static final Log log = LogFactory.getLog(EvalInsertWildcardSSWrapperRevision.class);

    private final ValueAddEventProcessor vaeProcessor;

    public EvalInsertWildcardSSWrapperRevision(SelectExprContext selectExprContext, EventType resultEventType, ValueAddEventProcessor vaeProcessor) {
        super(selectExprContext, resultEventType);
        this.vaeProcessor = vaeProcessor;
    }

    // In case of a wildcard and single stream that is itself a
    // wrapper bean, we also need to add the map properties
    public EventBean processSpecific(Map<String, Object> props, EventBean[] eventsPerStream, boolean isNewData, boolean isSynthesize)
    {
        DecoratingEventBean wrapper = (DecoratingEventBean)eventsPerStream[0];
        if(wrapper != null)
        {
            Map<String, Object> map = wrapper.getDecoratingProperties();
            if ((super.getExprNodes().length == 0) && (!map.isEmpty()))
            {
                // no action
            }
            else
            {
                props.putAll(map);
            }
        }

        EventBean event = eventsPerStream[0];
        return vaeProcessor.getValueAddEventBean(event);
    }
}