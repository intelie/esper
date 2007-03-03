using System;

namespace net.esper.example.qos_sla.eventbean
{
    public class OperationMeasurement
    {
        private String operationName;
        private String customerId;
        private long latency;
        private bool success;

        public OperationMeasurement(
            String operationName,
            String customerId, 
            long latency,
            bool success)
        {
            this.operationName = operationName;
            this.customerId = customerId;
            this.latency = latency;
            this.success = success;
        }

        public String OperationName
        {
            get { return operationName; }
        }

        public String CustomerId
        {
            get { return customerId; }
        }

        public long Latency
        {
            get { return latency; }
        }

        public bool IsSuccess
        {
            get { return success; }
        }
    }
}