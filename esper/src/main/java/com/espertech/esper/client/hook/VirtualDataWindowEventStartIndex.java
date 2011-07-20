/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.client.hook;

import java.util.List;

/**
 * Event raised when an index gets created or started via the "create index" syntax.
 */
public class VirtualDataWindowEventStartIndex extends VirtualDataWindowEvent {

    private final String namedWindowName;
    private final String indexName;
    private final List<VDWCreateIndexField> fields;

    /**
     * Ctor.
     * @param namedWindowName named window name
     * @param indexName index name
     * @param fields index fields
     */
    public VirtualDataWindowEventStartIndex(String namedWindowName, String indexName, List<VDWCreateIndexField> fields) {
        this.namedWindowName = namedWindowName;
        this.indexName = indexName;
        this.fields = fields;
    }

    /**
     * Returns the index name.
     * @return index name
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * Returns a list of fields that are part of the index.
     * @return list of index fields
     */
    public List<VDWCreateIndexField> getFields() {
        return fields;
    }

    /**
     * Returns the named window name.
     * @return named window name
     */
    public String getNamedWindowName() {
        return namedWindowName;
    }

    /**
     * Captures virtual data window indexed field informaion.
     */
    public static class VDWCreateIndexField {
        private String name;
        private boolean hash;

        /**
         * Ctor.
         * @param name named window name
         * @param hash true for hash-based index, false for btree index
         */
        public VDWCreateIndexField(String name, boolean hash) {
            this.name = name;
            this.hash = hash;
        }

        /**
         * Name of the indexed field.
         * @return field name
         */
        public String getName() {
            return name;
        }

        /**
         * Indicate whether the index is hash or btree, true for hash.
         * @return index type indicator
         */
        public boolean isHash() {
            return hash;
        }
    }
}
