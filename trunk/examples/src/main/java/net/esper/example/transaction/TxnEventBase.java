package net.esper.example.transaction;

public class TxnEventBase
{
    private String transactionId;
    private long timestamp;

    public TxnEventBase(String transactionId, long timestamp)
    {
        this.transactionId = transactionId;
        this.timestamp = timestamp;
    }

    public String getTransactionId()
    {
        return transactionId;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }

    public String toString()
    {
        return "transactionId=" + transactionId +
               " timestamp=" + timestamp;
    }
}
