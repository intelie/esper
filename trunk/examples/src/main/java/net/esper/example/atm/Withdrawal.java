package net.esper.example.atm;

public class Withdrawal
{
    private long accountNumber;
    private int amount;
    private long timestamp;

    public Withdrawal(long accountNumber, int amount)
    {
        this.accountNumber = accountNumber;
        this.amount = amount;
        timestamp = System.currentTimeMillis();
    }

    public long getAccountNumber()
    {
        return accountNumber;
    }

    public int getAmount()
    {
        return amount;
    }

    public long getTimestamp()
    {
        return timestamp;
    }
}
