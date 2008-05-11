package com.espertech.esper.example.marketdatafeed;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.UpdateListener;

public class TicksFalloffStatement
{
    private EPStatement statement;

    public TicksFalloffStatement(EPAdministrator admin)
    {
        String stmt = "select feed, avg(cnt) as avgCnt, cnt as feedCnt from TicksPerSecond.win:time(10 sec) " +
                      "group by feed " +
                      "having cnt < avg(cnt) * 0.75 ";

        statement = admin.createEPL(stmt);
    }

    public void addListener(UpdateListener listener)
    {
        statement.addListener(listener);
    }
}
