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

public class EPServiceProviderIsolatedImpl implements EPServiceProviderIsolated
{
    private static final Log log = LogFactory.getLog(EPRuntimeImpl.class);

    private final String name;
    private final EPRuntimeIsolatedImpl runtime;
    private final EPAdministratorIsolatedImpl admin;

    public EPServiceProviderIsolatedImpl(String name, EPIsolationUnitServices svc, EPServicesContext unisolatedSvc)
    {
        this.name = name;
        runtime = new EPRuntimeIsolatedImpl(svc, unisolatedSvc);
        admin = new EPAdministratorIsolatedImpl(name, svc, unisolatedSvc, runtime);
    }

    public EPRuntimeIsolated getEPRuntime()
    {
        return runtime;
    }

    public EPAdministratorIsolated getEPAdministrator()
    {
        return admin;
    }

    public String getName()
    {
        return name;
    }
}
