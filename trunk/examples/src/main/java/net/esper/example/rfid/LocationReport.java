package net.esper.example.rfid;

public class LocationReport
{
    private String assetId;
    private int locX;
    private int locY;
    private int zone;
    private int[] categories;

    public LocationReport(String assetId, int zone)
    {
        this.assetId = assetId;
        this.zone = zone;
    }

    public String getAssetId()
    {
        return assetId;
    }

    public int getLocX()
    {
        return locX;
    }

    public int getLocY()
    {
        return locY;
    }

    public int getZone()
    {
        return zone;
    }

    public int[] getCategories()
    {
        return categories;
    }
}
