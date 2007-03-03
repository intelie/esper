using net.esper.compat;

namespace net.esper.example.atm
{
	public class Withdrawal
	{
	    private long accountNumber;
	    private int amount;
	    private long timestamp;
	
	    public Withdrawal(long accountNumber, int amount)
	    {
	        this.accountNumber = accountNumber;
	        this.amount = amount;
	        timestamp = DateTimeHelper.CurrentTimeMillis;;
	    }
	
	    public long AccountNumber
	    {
	    	get { return accountNumber; }
	    }
	
	    public int Amount
	    {
	    	get { return amount; }
	    }
	
	    public long Timestamp
	    {
	    	get { return timestamp; }
	    }
	}
}
