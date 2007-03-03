using System;

namespace net.esper.example.transaction
{
    public class TxnEventB : TxnEventBase
    {
        public TxnEventB(String transactionId, long timestamp)
            : base(transactionId, timestamp)
        {
        }
    }
}