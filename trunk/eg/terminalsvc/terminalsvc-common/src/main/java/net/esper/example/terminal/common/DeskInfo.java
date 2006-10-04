package net.esper.example.terminal.common;

import java.io.Serializable;

public class DeskInfo implements Serializable
{
    private String id;
    private boolean isKiosk;
    private String locationCode;

    public DeskInfo(String id, boolean kiosk, String locationCode)
    {
        this.id = id;
        isKiosk = kiosk;
        this.locationCode = locationCode;
    }

    public String getId()
    {
        return id;
    }

    public boolean isKiosk()
    {
        return isKiosk;
    }

    public String getLocationCode()
    {
        return locationCode;
    }

}
