package net.esper.example.autoid;

import net.esper.client.EPStatement;
import net.esper.client.EPAdministrator;
import net.esper.client.UpdateListener;

public class RFIDCountStatement
{
    private EPStatement statement;

    public RFIDCountStatement(EPAdministrator admin)
    {
        String stmt = "insert into TicksPerSecond " +
                      "select feed, count(*) as cnt from MarketDataEvent.win:time_batch(1) group by feed";

        statement = admin.createEQL(stmt);
    }

    public void addListener(UpdateListener listener)
    {
        statement.addListener(listener);
    }
}
