package com.espertech.esper.support.bean.lrreport;

import java.util.List;

public class LocationReport {

    private List<Item> items;

    public LocationReport(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}
