using System;

using net.esper.client;

namespace net.esper.example.marketdatafeed
{
    public class TicksPerSecondStatement
    {
        private EPStatement statement;

        public TicksPerSecondStatement(EPAdministrator admin)
        {
            String stmt = "insert into TicksPerSecond " +
                          "select Feed, count(*) as cnt from MarketDataEvent.win:time_batch(1 sec) group by Feed";

            statement = admin.CreateEQL(stmt);
        }

        public void AddListener(UpdateListener listener)
        {
            statement.AddListener(listener);
        }
    }
}