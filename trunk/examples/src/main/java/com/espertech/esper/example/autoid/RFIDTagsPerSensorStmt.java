package com.espertech.esper.example.autoid;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.UpdateListener;

public class RFIDTagsPerSensorStmt
{
    private EPStatement statement;

    public RFIDTagsPerSensorStmt(EPAdministrator admin)
    {
        String stmt = "select ID as sensorId, coalesce(sum(countTags), 0) as numTagsPerSensor " +
                      "from AutoIdRFIDExample.win:time(60 sec) " +
                      "where Observation[0].Command = 'READ_PALLET_TAGS_ONLY' " +
                      "group by ID";

        statement = admin.createEQL(stmt);
    }

    public void addListener(UpdateListener listener)
    {
        statement.addListener(listener);
    }
}
