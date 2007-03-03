using System;

namespace net.esper.example.transaction
{
    public class TxnEventC : TxnEventBase
    {
        private String supplierId;

        public TxnEventC(String transactionId, long timestamp, String supplierId)
            : base(transactionId, timestamp)
        {
            this.supplierId = supplierId;
        }

        public String SupplierId
        {
            get { return supplierId; }
        }

        public override String ToString()
        {
            return base.ToString() + " supplierId=" + supplierId;
        }
    }
}