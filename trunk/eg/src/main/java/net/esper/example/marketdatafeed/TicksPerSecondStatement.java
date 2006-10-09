package net.esper.example.marketdatafeed;

import net.esper.client.EPStatement;
import net.esper.client.EPAdministrator;
import net.esper.client.UpdateListener;

public class TicksPerSecondStatement
{
    private EPStatement statement;

    public TicksPerSecondStatement(EPAdministrator admin)
    {
        String stmt = "insert into TicksPerSecond " +
                      "select feed, count(*) as cnt from MarketDataEvent.win:time_batch(1 sec) group by feed";

        statement = admin.createEQL(stmt);
    }

    public void addListener(UpdateListener listener)
    {
        statement.addListener(listener);
    }
}

