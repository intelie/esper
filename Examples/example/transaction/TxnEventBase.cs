using System;

namespace net.esper.example.transaction
{
    public class TxnEventBase
    {
        private String transactionId;
        private long timestamp;

        public TxnEventBase(String transactionId, long timestamp)
        {
            this.transactionId = transactionId;
            this.timestamp = timestamp;
        }

        public String TransactionId
        {
            get { return transactionId; }
            set { transactionId = value; }
        }

        public long Timestamp
        {
            get { return timestamp; }
        }

        public override String ToString()
        {
            return "transactionId=" + transactionId +
                   " timestamp=" + timestamp;
        }
    }
}