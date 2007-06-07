package net.esper.example.transaction;

public class TxnEventA extends TxnEventBase
{
    private String customerId;

    public TxnEventA(String transactionId, long timestamp, String customerId)
    {
        super(transactionId, timestamp);
        this.customerId = customerId;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public String toString()
    {
        return super.toString() + " customerId=" + customerId;
    }
}
