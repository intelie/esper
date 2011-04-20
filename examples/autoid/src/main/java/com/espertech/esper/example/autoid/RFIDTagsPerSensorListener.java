/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.autoid;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RFIDTagsPerSensorListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        if (newEvents != null)
        {
            logRate(newEvents[0]);
        }
    }

    private void logRate(EventBean event)
    {
        String sensorId = (String) event.get("sensorId");
        double numTags = (Double) event.get("numTagsPerSensor");

        log.info("Sensor " + sensorId + " totals " + numTags + " tags");
    }

    private static final Log log = LogFactory.getLog(RFIDTagsPerSensorListener.class);
}
