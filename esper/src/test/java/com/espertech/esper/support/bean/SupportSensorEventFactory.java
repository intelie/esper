package com.espertech.esper.support.bean;

public class SupportSensorEventFactory
{
    public static SupportSensorEvent getInstance()
    {
        return new SupportSensorEvent(0, null, null, 0, 0);
    }
}
