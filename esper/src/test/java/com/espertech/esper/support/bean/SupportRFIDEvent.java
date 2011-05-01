package com.espertech.esper.support.bean;

import java.io.Serializable;

public class SupportRFIDEvent implements Serializable
{
    private String locationReportId;
    private String mac;
    private String zoneID;

    public SupportRFIDEvent(String mac, String zoneID)
    {
        this(null, mac, zoneID);
    }

    public SupportRFIDEvent(String locationReportId, String mac, String zoneID)
    {
        this.locationReportId = locationReportId;
        this.mac = mac;
        this.zoneID = zoneID;
    }

    public String getLocationReportId()
    {
        return locationReportId;
    }

    public String getMac()
    {
        return mac;
    }

    public String getZoneID()
    {
        return zoneID;
    }
}
