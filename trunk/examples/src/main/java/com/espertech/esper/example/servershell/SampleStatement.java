package com.espertech.esper.example.servershell;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SampleStatement
{
    private static Log log = LogFactory.getLog(SampleStatement.class);

    public static void createStatement(EPAdministrator admin)
    {
        EPStatement statement = admin.createEPL("select istream ipAddress, avg(duration) from SampleEvent.win:time(10 sec) group by ipAddress output every 2 seconds");
        statement.addListener(new UpdateListener()
        {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                for (int i = 0; i < newEvents.length; i++)
                {
                    log.info("IPAddress: " + newEvents[i].get("ipAddress") +
                         " Avg Duration: " + newEvents[i].get("avg(duration)"));
                }
            }
        });

    }
}
