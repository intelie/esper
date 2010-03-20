/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.qos_sla.monitor;

import com.espertech.esper.client.*;
import com.espertech.esper.example.qos_sla.eventbean.OperationMeasurement;
import com.espertech.esper.client.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorRateMonitor
{
    private ErrorRateMonitor()
    {
    }

    public static void start()
    {
        EPAdministrator admin = EPServiceProviderManager.getDefaultProvider().getEPAdministrator();

        EPStatement pattern = admin.createPattern("every timer:at(*, *, *, *, *, */10)");
        final EPStatement view = admin.createEPL("select count(*) as size from " + OperationMeasurement.class.getName() +
                "(success=false).win:time(10 min)");

        pattern.addListener(new UpdateListener()
        {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                long count = (Long) view.iterator().next().get("size");

                log.info(".update Info, error rate in the last 10 minutes is " + count);
            }
        });
    }

    private static final Log log = LogFactory.getLog(ErrorRateMonitor.class);
}
