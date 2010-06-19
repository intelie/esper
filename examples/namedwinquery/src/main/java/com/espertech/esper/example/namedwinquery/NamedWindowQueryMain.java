package com.espertech.esper.example.namedwinquery;

import com.espertech.esper.client.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class NamedWindowQueryMain
{
    private static final Log log = LogFactory.getLog(NamedWindowQueryMain.class);

    public static void main(String[] args)
    {
        NamedWindowQueryMain main = new NamedWindowQueryMain();

        try
        {
            main.runExample(false, "NamedWindowQuery");
        }
        catch (Exception ex)
        {
            log.error("Unexpected error occured running example:" + ex.getMessage(), ex);
        }
    }

    public void runExample(boolean isRunFromUnitTest, String engineURI)
    {
        int numEventsToLoad = 100000;
        int numFireAndForgetExecutions = 100;
        int numOnEventQueryExecutions = 100000;
        if (isRunFromUnitTest)
        {
            numEventsToLoad = 1000;
            numFireAndForgetExecutions = 5;
            numOnEventQueryExecutions = 5;
        }

        EPServiceProvider epService = EPServiceProviderManager.getProvider(engineURI);

        // This example initializes the engine instance as it is running within an overall test suite.
        // This step would not be required unless re-using the same engine instance with different configurations. 
        epService.initialize();

        // define event type - this example uses Map event representation
        //
        Map<String, Object> definition = new LinkedHashMap<String, Object>();
        definition.put("sensor", String.class);
        definition.put("temperature", double.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SensorEvent", definition);

        // define a named window to hold the last 1000000 (1M) events
        //
        String stmtText = "create window SensorWindow.win:keepall() as select * from SensorEvent";
        log.info("Creating named window : " + stmtText);
        epService.getEPAdministrator().createEPL(stmtText);

        stmtText = "insert into SensorWindow select * from SensorEvent";
        log.info("Creating insert statement for named window : " + stmtText);
        epService.getEPAdministrator().createEPL(stmtText);

        // load 1M events
        //
        Random random = new Random();
        String[] sensors = "s1,s2,s3,s4,s5,s6".split(",");

        log.info("Generating " + numEventsToLoad + " sensor events for the named window");
        List<Map<String, Object>> events = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < numEventsToLoad; i++)
        {
            double temperature = random.nextDouble() * 10 + 80;
            String sensor = sensors[random.nextInt(sensors.length)];

            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put("temperature", temperature);
            data.put("sensor", sensor);

            events.add(data);
        }
        log.info("Completed generating sensor events");

        log.info("Sending " + events.size() + " sensor events into engine");
        for (Map<String, Object> event : events)
        {
            epService.getEPRuntime().sendEvent(event, "SensorEvent");
        }
        log.info("Completed sending sensor events");

        // prepare on-demand query
        //
        double sampleTemperature = (Double) events.get(0).get("temperature");
        stmtText = "select * from SensorWindow where temperature = " + sampleTemperature;
        log.info("Preparing fire-and-forget query : " + stmtText);
        EPOnDemandPreparedQuery onDemandQuery = epService.getEPRuntime().prepareQuery(stmtText);

        log.info("Executing fire-and-forget query " + numFireAndForgetExecutions + " times");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numFireAndForgetExecutions; i++)
        {
            EPOnDemandQueryResult result = onDemandQuery.execute();
            if (result.getArray().length != 1)
            {
                throw new RuntimeException("Failed assertion of result, expected a single row returned from query");
            }
        }
        long endTime = System.currentTimeMillis();
        double deltaSec = (endTime - startTime) / 1000.0;        
        log.info("Executing fire-and-forget query " + numFireAndForgetExecutions + " times took " + deltaSec + " seconds");

        // prepare on-select
        //
        Map<String, Object> definitionQuery = new LinkedHashMap<String, Object>();
        definitionQuery.put("querytemp", double.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SensorQueryEvent", definitionQuery);

        stmtText = "on SensorQueryEvent select sensor from SensorWindow where temperature = querytemp";
        log.info("Creating on-select statement for named window : " + stmtText);
        EPStatement onSelectStmt = epService.getEPAdministrator().createEPL(stmtText);
        onSelectStmt.setSubscriber(this);

        log.info("Executing on-select query " + numOnEventQueryExecutions + " times");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < numOnEventQueryExecutions; i++)
        {
            Map<String, Object> queryParams = new HashMap<String, Object>();
            queryParams.put("querytemp", sampleTemperature);

            epService.getEPRuntime().sendEvent(queryParams, "SensorQueryEvent");
        }
        endTime = System.currentTimeMillis();
        deltaSec = (endTime - startTime) / 1000.0;
        log.info("Executing on-select query " + numOnEventQueryExecutions + " times took " + deltaSec + " seconds");
    }

    public void update(String result)
    {
        // No action taken here
        // System.out.println(result);
    }
}

