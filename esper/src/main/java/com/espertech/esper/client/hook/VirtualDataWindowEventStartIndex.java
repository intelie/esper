package com.espertech.esper.client.hook;

import java.util.List;

public class VirtualDataWindowEventStartIndex extends VirtualDataWindowEvent {

    private final String namedWindowName;
    private final String indexName;
    private final List<VDWCreateIndexField> fields;

    public VirtualDataWindowEventStartIndex(String namedWindowName, String indexName, List<VDWCreateIndexField> fields) {
        this.namedWindowName = namedWindowName;
        this.indexName = indexName;
        this.fields = fields;
    }

    public String getIndexName() {
        return indexName;
    }

    public List<VDWCreateIndexField> getFields() {
        return fields;
    }

    public String getNamedWindowName() {
        return namedWindowName;
    }

    public static class VDWCreateIndexField {
        private String name;
        private boolean hash;

        public VDWCreateIndexField(String name, boolean hash) {
            this.name = name;
            this.hash = hash;
        }

        public String getName() {
            return name;
        }

        public boolean isHash() {
            return hash;
        }
    }
}
