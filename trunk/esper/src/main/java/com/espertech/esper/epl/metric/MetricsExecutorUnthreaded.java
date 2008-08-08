/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.metric;

/**
 * Metrics executor executing in-thread.
 */
public class MetricsExecutorUnthreaded implements MetricsExecutor
{
    public void execute(MetricExec execution, MetricExecutionContext executionContext)
    {
        execution.execute(executionContext);
    }

    public void destroy()
    {
        // no action required, nothing to stop
    }
}
