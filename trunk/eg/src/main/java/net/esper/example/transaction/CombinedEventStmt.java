package net.esper.example.transaction;

import net.esper.client.*;

public class CombinedEventStmt
{
    private EPStatement statement;

    public CombinedEventStmt(EPAdministrator admin)
    {
        // We need to take in events A, B and C and produce a single, combined event
        String stmt = "insert into CombinedEvent(transactionId, customerId, supplierId, latencyAC, latencyBC, latencyAB)" +
                        "select C.transactionId," +
                             "customerId," +
                             "supplierId," +
                             "C.timestamp - A.timestamp," +
                             "C.timestamp - B.timestamp," +
                             "B.timestamp - A.timestamp " +
                        "from EventA.win:time(1800) A," +
                             "EventB.win:time(1800) B," +
                             "EventC.win:time(1800) C " +
                        "where A.transactionId = B.transactionId and B.transactionId = C.transactionId";

        statement = admin.createEQL(stmt);
    }

    public void addListener(UpdateListener listener)
    {
        statement.addListener(listener);
    }
}
