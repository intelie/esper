package com.espertech.esper.example.rfid;

public class LocationReport
{
    private String assetId;
    private int zone;

    public LocationReport(String assetId, int zone)
    {
        this.assetId = assetId;
        this.zone = zone;
    }

    public String getAssetId()
    {
        return assetId;
    }

    public int getZone()
    {
        return zone;
    }
}
