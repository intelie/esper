package com.espertech.esper.support.bean.lrreport;

public class Item {

    private String assetId;
    private Location location;
    private String type;
    private String assetIdPassenger;

    public Item(String assetId, Location location) {
        this.assetId = assetId;
        this.location = location;
    }

    public Item(String assetId, Location location, String type, String assetIdPassenger) {
        this.assetId = assetId;
        this.location = location;
        this.type = type;
        this.assetIdPassenger = assetIdPassenger;
    }

    public String getType() {
        return type;
    }

    public String getAssetIdPassenger() {
        return assetIdPassenger;
    }

    public String getAssetId() {
        return assetId;
    }

    public Location getLocation() {
        return location;
    }
}
