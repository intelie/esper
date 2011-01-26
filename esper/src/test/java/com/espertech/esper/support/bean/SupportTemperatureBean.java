package com.espertech.esper.support.bean;

public class SupportTemperatureBean
{
    private String geom;

    private SupportTemperatureBean()
    {
        // need a private ctor for testing
    }

    public SupportTemperatureBean(String geom)
    {
        this.geom = geom;
    }

    public String getGeom()
    {
        return geom;
    }

    public void setGeom(String geom)
    {
        this.geom = geom;
    }
}
