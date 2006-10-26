package net.esper.example.atm;

public class FraudWarning
{
    private long accountNumber;
    private String warning;
    private long timestamp;

    public FraudWarning(long accountNumber, String warning)
    {
        this.accountNumber = accountNumber;
        this.warning = warning;
        this.timestamp = System.currentTimeMillis();
    }

    public long getAccountNumber()
    {
        return accountNumber;
    }

    public String getWarning()
    {
        return warning;
    }

    public long getTimestamp()
    {
        return timestamp;
    }
}
