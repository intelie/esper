using net.esper.compat;

namespace net.esper.example.atm
{
	public class FraudWarning
	{
	    private long accountNumber;
	    private string warning;
	    private long timestamp;
	
	    public FraudWarning(long accountNumber, string warning)
	    {
	        this.accountNumber = accountNumber;
	        this.warning = warning;
	        this.timestamp = DateTimeHelper.CurrentTimeMillis;
	    }

	    public long AccountNumber
	    {
	    	get { return accountNumber; }
	    }
	
	    public string Warning
	    {
	    	get { return warning; }
	    }
	
	    public long Timestamp
	    {
	    	get { return timestamp; }
	    }
	}
}
