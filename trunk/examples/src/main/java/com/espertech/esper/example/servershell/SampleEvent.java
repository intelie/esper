package com.espertech.esper.example.servershell;

public class SampleEvent
{
    private String ipAddress;
    private double duration;

    public SampleEvent(String ipAddress, double duration)
    {
        this.ipAddress = ipAddress;
        this.duration = duration;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public double getDuration()
    {
        return duration;
    }
}
