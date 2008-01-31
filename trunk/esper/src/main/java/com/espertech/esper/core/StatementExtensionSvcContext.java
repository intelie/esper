package com.espertech.esper.core;

import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.schedule.ScheduleBucket;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.view.ViewResolutionService;
import com.espertech.esper.view.StatementStopService;
import com.espertech.esper.pattern.PatternObjectResolutionService;
import com.espertech.esper.pattern.PatternContextFactory;
import com.espertech.esper.eql.core.MethodResolutionService;
import com.espertech.esper.eql.join.JoinSetComposerFactory;
import com.espertech.esper.eql.view.OutputConditionFactory;
import com.espertech.esper.filter.FilterService;

/**
 * Statement-level extension services.
 */
public interface StatementExtensionSvcContext
{
}
