package com.espertech.esper.client.hook;

public class VirtualDataWindowEventStopIndex extends VirtualDataWindowEvent {

    private final String namedWindowName;
    private final String indexName;

    public VirtualDataWindowEventStopIndex(String namedWindowName, String indexName) {
        this.namedWindowName = namedWindowName;
        this.indexName = indexName;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getNamedWindowName() {
        return namedWindowName;
    }
}
