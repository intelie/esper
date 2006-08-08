package net.esper.example.autoid;

import net.esper.client.EPStatement;
import net.esper.client.EPAdministrator;
import net.esper.client.UpdateListener;

public class RFIDTagsPerSensorStmt
{
    private EPStatement statement;

    public RFIDTagsPerSensorStmt(EPAdministrator admin)
    {
        String stmt = "select ID as sensorId, sum(countTags) as numTagsPerSensor " +
                      "from AutoIdRFIDExample.win:time(60) " +
                      "where Observation[0].Command = 'READ_PALLET_TAGS_ONLY' " +
                      "group by ID";

        statement = admin.createEQL(stmt);
    }

    public void addListener(UpdateListener listener)
    {
        statement.addListener(listener);
    }
}
