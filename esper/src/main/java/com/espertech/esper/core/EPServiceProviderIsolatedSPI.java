package com.espertech.esper.core;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.time.TimerEvent;
import com.espertech.esper.collection.ArrayBackedCollection;
import com.espertech.esper.collection.ArrayDequeJDK6Backport;
import com.espertech.esper.collection.ThreadWorkQueue;
import com.espertech.esper.filter.FilterHandle;
import com.espertech.esper.filter.FilterHandleCallback;
import com.espertech.esper.filter.FilterSet;
import com.espertech.esper.schedule.ScheduleHandle;
import com.espertech.esper.schedule.ScheduleHandleCallback;
import com.espertech.esper.schedule.ScheduleSet;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.util.ThreadLogUtil;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * SPU for isolated service provider.
 */
public interface EPServiceProviderIsolatedSPI extends EPServiceProviderIsolated
{
    /**
     * Return isolated services.
     * @return isolated services
     */
    public EPIsolationUnitServices getIsolatedServices(); 
}