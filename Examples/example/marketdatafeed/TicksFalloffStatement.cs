using System;

using net.esper.client;

namespace net.esper.example.marketdatafeed
{
    public class TicksFalloffStatement
    {
        private EPStatement statement;

        public TicksFalloffStatement(EPAdministrator admin)
        {
            String stmt = "select Feed, avg(cnt) as avgCnt, cnt as feedCnt from TicksPerSecond.win:time(10 sec) " +
                          "group by Feed " +
                          "having cnt < avg(cnt) * 0.75 ";

            statement = admin.CreateEQL(stmt);
        }

        public void AddListener(UpdateListener listener)
        {
            statement.AddListener(listener);
        }
    }
}
