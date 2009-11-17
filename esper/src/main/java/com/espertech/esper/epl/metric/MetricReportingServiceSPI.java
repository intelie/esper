/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.metric;

import com.espertech.esper.core.StatementResultListener;

import java.util.Set;

public interface MetricReportingServiceSPI extends MetricReportingService
{
    public void addStatementResultListener(StatementResultListener listener);
    public void removeStatementResultListener(StatementResultListener listener);
    public Set<StatementResultListener> getStatementOutputHooks();
}