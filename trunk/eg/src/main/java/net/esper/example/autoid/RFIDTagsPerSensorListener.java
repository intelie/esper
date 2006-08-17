package net.esper.example.autoid;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RFIDTagsPerSensorListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        logRate(newEvents[0]);
    }

    private void logRate(EventBean event)
    {
        String sensorId = (String) event.get("sensorId");
        double numTags = (Double) event.get("numTagsPerSensor");

        log.info("Sensor " + sensorId + " totals " + numTags + " tags");
    }

    private static final Log log = LogFactory.getLog(RFIDTagsPerSensorListener.class);
}
