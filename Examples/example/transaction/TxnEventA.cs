using System;

namespace net.esper.example.transaction
{
    public class TxnEventA : TxnEventBase
    {
        private String customerId;

        public TxnEventA(String transactionId, long timestamp, String customerId)
            : base(transactionId, timestamp)
        {
            this.customerId = customerId;
        }

        public String getCustomerId()
        {
            return customerId;
        }

        public override String ToString()
        {
            return base.ToString() + " customerId=" + customerId;
        }
    }
}