package com.espertech.esper.support.bean.lrreport;

import java.util.ArrayList;
import java.util.List;

public class ZoneFactory {

    public static Iterable<Zone> getZones() {
        List<Zone> zones = new ArrayList<Zone>();
        zones.add(new Zone("Z1", new Rectangle(0, 0, 20, 20)));
        return zones;
    }
}
