using net.esper.client;

namespace net.esper.example.atm
{
	/// <summary>
	/// Demonstrates a simple join between fraud warning and withdrawal event streams.
	/// See the unit test with the same name for any example events generated to test
	/// this example.
	/// </summary>
	public class FraudMonitor
	{
	    private EPStatement joinView;
	
	    public FraudMonitor(EPServiceProvider epService, UpdateListener updateListener)
	    {
	        string joinStatement = "select fraud.accountNumber as accountNumber, fraud.warning as warning, withdraw.amount as amount, " +
	                               "max(fraud.timestamp, withdraw.timestamp) as timestamp, 'withdrawlFraudWarn' as descr from " +
	                                    "FraudWarning.win:time(30 min) as fraud," +
	                                    "Withdrawal.win:time(30 sec) as withdraw" +
	                " where fraud.accountNumber = withdraw.accountNumber";
	
	        joinView = epService.EPAdministrator.CreateEQL(joinStatement);
	        joinView.AddListener(updateListener);
	    }
	}
}
