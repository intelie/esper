package net.esper.example.transaction;

public class TxnEventC extends TxnEventBase
{
    private String supplierId;

    public TxnEventC(String transactionId, long timestamp, String supplierId)
    {
        super(transactionId, timestamp);
        this.supplierId = supplierId;
    }

    public String getSupplierId()
    {
        return supplierId;
    }

    public String toString()
    {
        return super.toString() + " supplierId=" + supplierId;
    }

}
