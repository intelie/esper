using System;

namespace net.esper.example.qos_sla.eventbean
{
    public class LatencyLimit
    {
        private String operationName;
        private String customerId;
        private long latencyThreshold;

        public LatencyLimit(String operationName, String customerId, long latencyThreshold)
        {
            this.operationName = operationName;
            this.customerId = customerId;
            this.latencyThreshold = latencyThreshold;
        }

        public String OperationName
        {
            get { return operationName; }
        }

        public String CustomerId
        {
            get { return customerId; }
        }

        public long LatencyThreshold
        {
            get { return latencyThreshold; }
        }
    }
}