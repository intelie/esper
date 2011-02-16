package com.espertech.esper.epl.named;

import java.util.Arrays;
import java.util.List;

public class IndexMultiKey {

    private IndexedPropDesc[] keyProps;
    private IndexedPropDesc[] rangeProps;

    public IndexMultiKey(List<IndexedPropDesc> keyProps, List<IndexedPropDesc> rangeProps) {
        this.keyProps = keyProps.toArray(new IndexedPropDesc[keyProps.size()]);
        this.rangeProps = rangeProps.toArray(new IndexedPropDesc[rangeProps.size()]);
    }

    public IndexedPropDesc[] getKeyProps() {
        return keyProps;
    }

    public IndexedPropDesc[] getRangeProps() {
        return rangeProps;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexMultiKey that = (IndexMultiKey) o;

        if (!Arrays.equals(keyProps, that.keyProps)) return false;
        if (!Arrays.equals(rangeProps, that.rangeProps)) return false;

        return true;
    }

    public int hashCode() {
        int result = Arrays.hashCode(keyProps);
        result = 31 * result + Arrays.hashCode(rangeProps);
        return result;
    }
}
