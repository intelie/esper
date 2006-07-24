package net.esper.example.qos_sla.eventbean;

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

    public String getOperationName()
    {
        return operationName;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public long getLatencyThreshold()
    {
        return latencyThreshold;
    }
}
