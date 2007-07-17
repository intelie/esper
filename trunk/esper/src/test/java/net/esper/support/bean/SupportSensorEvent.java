package net.esper.support.bean;

public class SupportSensorEvent
{
    private int id;
    private String type;
    private String device;
    private double measurement;
    private double confidence;

    public SupportSensorEvent(int id, String type, String device, double measurement, double confidence)
    {
        this.id = id;
        this.type = type;
        this.device = device;
        this.measurement = measurement;
        this.confidence = confidence;
    }

    public int getId()
    {
        return id;
    }

    public String getType()
    {
        return type;
    }

    public String getDevice()
    {
        return device;
    }

    public double getMeasurement()
    {
        return measurement;
    }

    public double getConfidence()
    {
        return confidence;
    }
}
