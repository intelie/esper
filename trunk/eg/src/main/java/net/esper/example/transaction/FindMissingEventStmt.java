package net.esper.example.transaction;

import net.esper.client.EPAdministrator;
import net.esper.client.UpdateListener;
import net.esper.client.EPStatement;

public class FindMissingEventStmt
{
    public static int TIME_WINDOW_TXNC_IN_SEC = 60 * 60;

    private EPStatement statement;

    //
    // We need to detect a transaction that did not make it through all three events.
    // In other words, a transaction with events A or B, but not C.
    // Note that, in this case, what we care about is event C.
    // The lack of events A or B could indicate a failure in the event transport and should be ignored.
    // Although the lack of an event C could also be a transport failure, it merits looking into.
    //
    public FindMissingEventStmt(EPAdministrator admin)
    {
        // The inner table to both A and B is C.
        //
        // The listener will consider old events generated when either A or B leave the window, with
        // a window size for A and B of 30 minutes.
        //
        // The window of C is declared large to ensure the C events don't leave the window before A and B
        // thus generating false alerts, making these obvious via timestamp. Lets keep 1 hour of data for C.
        String stmt = "select * from " +
                        "TxnEventA.win:time(30 min) A " +
                          "full outer join " +
                        "TxnEventC.win:time(1 hour) C on A.transactionId = C.transactionId " +
                          "full outer join " +
                        "TxnEventB.win:time(30 min) B on B.transactionId = C.transactionId " +
                      "where C.transactionId is null";

        statement = admin.createEQL(stmt);
    }

    public void addListener(UpdateListener listener)
    {
        statement.addListener(listener);
    }
}
