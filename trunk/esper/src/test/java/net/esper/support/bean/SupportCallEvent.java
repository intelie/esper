package net.esper.support.bean;

public class SupportCallEvent
{
    private long callId;
    private String source;
    private String dest;
    private long startTime;
    private long endTime;

    public SupportCallEvent(long callId, String source, String destination, long startTime, long endTime)
    {
        this.callId = callId;
        this.source = source;
        this.dest = destination;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getCallId()
    {
        return callId;
    }

    public String getSource()
    {
        return source;
    }

    public String getDest()
    {
        return dest;
    }

    public long getStartTime()
    {
        return startTime;
    }

    public long getEndTime()
    {
        return endTime;
    }
}
