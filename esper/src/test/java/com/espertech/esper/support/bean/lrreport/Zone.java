package com.espertech.esper.support.bean.lrreport;

public class Zone {

    private final String name;
    private final Rectangle rectangle;

    public Zone(String name, Rectangle rectangle) {
        this.name = name;
        this.rectangle = rectangle;
    }

    public String getName() {
        return name;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public static String[] getZoneNames() {
        return new String[] {"Z1", "Z2"};
    }
}
