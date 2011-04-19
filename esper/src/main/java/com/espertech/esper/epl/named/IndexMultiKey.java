package com.espertech.esper.epl.named;

import java.util.Arrays;
import java.util.List;

public class IndexMultiKey {

    private IndexedPropDesc[] hashIndexedProps;
    private IndexedPropDesc[] rangeIndexedProps;

    public IndexMultiKey(List<IndexedPropDesc> hashIndexedProps, List<IndexedPropDesc> rangeIndexedProps) {
        this.hashIndexedProps = hashIndexedProps.toArray(new IndexedPropDesc[hashIndexedProps.size()]);
        this.rangeIndexedProps = rangeIndexedProps.toArray(new IndexedPropDesc[rangeIndexedProps.size()]);
    }

    public IndexedPropDesc[] getHashIndexedProps() {
        return hashIndexedProps;
    }

    public IndexedPropDesc[] getRangeIndexedProps() {
        return rangeIndexedProps;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexMultiKey that = (IndexMultiKey) o;

        if (!Arrays.equals(hashIndexedProps, that.hashIndexedProps)) return false;
        if (!Arrays.equals(rangeIndexedProps, that.rangeIndexedProps)) return false;

        return true;
    }

    public int hashCode() {
        int result = Arrays.hashCode(hashIndexedProps);
        result = 31 * result + Arrays.hashCode(rangeIndexedProps);
        return result;
    }
}
