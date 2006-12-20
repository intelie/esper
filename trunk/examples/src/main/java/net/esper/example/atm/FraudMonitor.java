package net.esper.example.atm;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.UpdateListener;

/**
 * Demonstrates a simple join between fraud warning and withdrawal event streams.
 *
 * See the unit test with the same name for any example events generated to test this example.
 */
public class FraudMonitor
{
    private EPStatement joinView;

    public FraudMonitor(EPServiceProvider epService, UpdateListener updateListener)
    {
        String joinStatement = "select fraud.accountNumber as accountNumber, fraud.warning as warning, withdraw.amount as amount, " +
                               "max(fraud.timestamp, withdraw.timestamp) as timestamp, 'withdrawlFraudWarn' as descr from " +
                                    "FraudWarning.win:time(30 min) as fraud," +
                                    "Withdrawal.win:time(30 sec) as withdraw" +
                " where fraud.accountNumber = withdraw.accountNumber";

        joinView = epService.getEPAdministrator().createEQL(joinStatement);
        joinView.addListener(updateListener);
    }
}
