package com.espertech.esper.example.marketdatafeed;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.UpdateListener;

public class TicksPerSecondStatement
{
    private EPStatement statement;

    public TicksPerSecondStatement(EPAdministrator admin)
    {
        String stmt = "insert into TicksPerSecond " +
                      "select feed, count(*) as cnt from MarketDataEvent.win:time_batch(1 sec) group by feed";

        statement = admin.createEPL(stmt);
    }

    public void addListener(UpdateListener listener)
    {
        statement.addListener(listener);
    }
}

