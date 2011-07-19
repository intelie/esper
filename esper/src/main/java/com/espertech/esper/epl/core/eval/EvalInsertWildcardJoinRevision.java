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
import com.espertech.esper.epl.core.SelectExprJoinWildcardProcessor;
import com.espertech.esper.epl.core.SelectExprProcessor;
import com.espertech.esper.event.vaevent.ValueAddEventProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

public class EvalInsertWildcardJoinRevision extends EvalBase implements SelectExprProcessor {

    private static final Log log = LogFactory.getLog(EvalInsertWildcardJoinRevision.class);

    private final SelectExprJoinWildcardProcessor joinWildcardProcessor;
    private final ValueAddEventProcessor vaeProcessor;

    public EvalInsertWildcardJoinRevision(SelectExprContext selectExprContext, EventType resultEventType, SelectExprJoinWildcardProcessor joinWildcardProcessor, ValueAddEventProcessor vaeProcessor) {
        super(selectExprContext, resultEventType);
        this.joinWildcardProcessor = joinWildcardProcessor;
        this.vaeProcessor = vaeProcessor;
    }

    public EventBean processSpecific(Map<String, Object> props, EventBean[] eventsPerStream, boolean isNewData, boolean isSynthesize)
    {
        EventBean event = joinWildcardProcessor.process(eventsPerStream, isNewData, isSynthesize);
        return vaeProcessor.getValueAddEventBean(event);
    }
}