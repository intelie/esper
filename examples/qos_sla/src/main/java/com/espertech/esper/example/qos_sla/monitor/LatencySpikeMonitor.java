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

public class LatencySpikeMonitor
{
    private LatencySpikeMonitor() {}

    public static void start()
    {
        EPAdministrator admin = EPServiceProviderManager.getDefaultProvider().getEPAdministrator();

        EPStatement latencyAlert = admin.createPattern(
                "every alert=" + OperationMeasurement.class.getName() + "(latency > 20000)");

        latencyAlert.addListener(new LatencySpikeListener());
    }
}
