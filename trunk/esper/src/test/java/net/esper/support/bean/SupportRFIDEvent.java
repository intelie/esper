package net.esper.support.bean;

public class SupportRFIDEvent
{
    private String mac;
    private String zoneID;

    public SupportRFIDEvent(String mac, String zoneID)
    {
        this.mac = mac;
        this.zoneID = zoneID;
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
