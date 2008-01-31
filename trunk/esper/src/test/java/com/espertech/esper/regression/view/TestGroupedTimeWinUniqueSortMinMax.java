package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.support.client.SupportConfigFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestGroupedTimeWinUniqueSortMinMax extends TestCase {

    private Configuration setup()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventTypeAlias("Sensor", Sensor.class);
        return config;
    }

    private void logEvent (Object event) {
        log.info("Sending " + event);
    }

    public void testSensorQuery() throws Exception {
        log.info ("testSensorQuery...........");
        Configuration configuration = setup();

        EPServiceProvider epService = EPServiceProviderManager.getProvider("testSensorQuery", configuration);
        MatchListener listener = new MatchListener();

        String stmtString =
              "SELECT max(high.type) as type, \n" +
              " max(high.measurement) as highMeasurement, max(high.confidence) as confidenceOfHigh, max(high.device) as deviceOfHigh\n" +
              ",min(low.measurement) as lowMeasurement, min(low.confidence) as confidenceOfLow, min(low.device) as deviceOfLow\n" +
              "FROM\n" +
              " Sensor.std:groupby(type).win:time(1 hour).std:unique(device).ext:sort(measurement,true,1) as high " +
              ",Sensor.std:groupby(type).win:time(1 hour).std:unique(device).ext:sort(measurement,false,1) as low ";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtString);
        log.info(stmtString);
        stmt.addListener(listener);

        EPRuntime runtime = epService.getEPRuntime();
        List<Sensor> events = new ArrayList<Sensor>();
        events.add(new Sensor("Temperature", "Device1", 68.0, 96.5));
        events.add(new Sensor("Temperature", "Device2", 65.0, 98.5));
        events.add(new Sensor("Temperature", "Device1", 62.0, 95.3));
        events.add(new Sensor("Temperature", "Device2", 71.3, 99.3));
        for (Sensor event : events) {
            logEvent (event);
            runtime.sendEvent(event);
        }
        Map lastEvent = (Map) listener.getLastEvent();
        assertTrue (lastEvent != null);
        assertEquals (62.0,lastEvent.get("lowMeasurement"));
        assertEquals ("Device1",lastEvent.get("deviceOfLow"));
        assertEquals (95.3,lastEvent.get("confidenceOfLow"));
        assertEquals (71.3,lastEvent.get("highMeasurement"));
        assertEquals ("Device2",lastEvent.get("deviceOfHigh"));
        assertEquals (99.3,lastEvent.get("confidenceOfHigh"));
    }

    static public class Sensor {

        public Sensor() {
        }

        public Sensor(String type, String device, Double measurement, Double confidence) {
            this.type = type;
            this.device = device;
            this.measurement = measurement;
            this.confidence = confidence;
         }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getDevice() {
            return device;
        }

        public void setMeasurement(Double measurement) {
            this.measurement = measurement;
        }

        public Double getMeasurement() {
            return measurement;
        }

        public void setConfidence(Double confidence) {
            this.confidence = confidence;
        }

        public Double getConfidence() {
            return confidence;
        }

        private String type;
        private String device;
        private Double measurement;
        private Double confidence;
    }

    class MatchListener implements UpdateListener {
        private int count = 0;
        private Object lastEvent = null;

        public void update(EventBean[] newEvents, EventBean[] oldEvents) {
            log.info("New events.................");
            if (newEvents != null) {
                for (int i = 0; i < newEvents.length; i++) {
                    EventBean e = newEvents[i];
                    EventType t = e.getEventType();
                    String[] propNames = t.getPropertyNames();
                    log.info("event[" + i + "] of type " + t);
                    for (int j=0; j < propNames.length; j++) {
                        log.info("    " + propNames[j] + ": " + e.get(propNames[j]));
                    }
                    count++;
                    lastEvent = e.getUnderlying();
                }
            }
            log.info("Removing events.................");
            if (oldEvents != null) {
                for (int i = 0; i < oldEvents.length; i++) {
                    EventBean e = oldEvents[i];
                    EventType t = e.getEventType();
                    String[] propNames = t.getPropertyNames();
                    log.info("event[" + i + "] of type " + t);
                    for (int j=0; j < propNames.length; j++) {
                        log.info("    " + propNames[j] + ": " + e.get(propNames[j]));
                    }
                    count--;
                }
            }
            log.info("......................................");
        }

        public int getCount() {
            return count;
        }

        public Object getLastEvent() {
            return lastEvent;
        }
    }

    private static final Log log = LogFactory.getLog(TestGroupedTimeWinUniqueSortMinMax.class);
}
