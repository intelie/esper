package net.esper.example.marketdatafeed;

import net.esper.client.EPStatement;
import net.esper.client.EPAdministrator;
import net.esper.client.UpdateListener;

public class TicksFalloffStatement
{
    private EPStatement statement;

    public TicksFalloffStatement(EPAdministrator admin)
    {
        String stmt = "select feed, avg(cnt) as avgCnt, cnt as feedCnt from TicksPerSecond.win:time(10 sec) " +
                      "group by feed " +
                      "having cnt < avg(cnt) * 0.75 ";

        statement = admin.createEQL(stmt);
    }

    public void addListener(UpdateListener listener)
    {
        statement.addListener(listener);
    }
}
