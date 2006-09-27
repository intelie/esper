package net.esper.example.qos_sla.eventbean;

public class OperationMeasurement
{
    private String operationName;
    private String customerId;
    private long latency;
    private boolean success;

    public OperationMeasurement(String operationName, String customerId, long latency,
                              boolean success)
    {
        this.operationName = operationName;
        this.customerId = customerId;
        this.latency = latency;
        this.success = success;
    }

    public String getOperationName()
    {
        return operationName;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public long getLatency()
    {
        return latency;
    }

    public boolean isSuccess()
    {
        return success;
    }
}
