package com.espertech.esper.epl.join.plan;

import com.espertech.esper.type.RelationalOpEnum;

public enum QueryGraphRangeEnum {
    /**
     * Less (<).
     */
    LESS(false),

    /**
     * Less or equal (<=).
     */
    LESS_OR_EQUAL(false),

    /**
     * Greater or equal (>=).
     */
    GREATER_OR_EQUAL(false),

    /**
     * Greater (>).
     */
    GREATER(false),

    /**
     * Range contains neither endpoint, i.e. (a,b)
     */
    RANGE_OPEN(true),

    /**
     * Range contains low and high endpoint, i.e. [a,b]
     */
    RANGE_CLOSED(true),

    /**
     * Range includes low endpoint but not high endpoint, i.e. [a,b)
     */
    RANGE_HALF_OPEN(true),

    /**
     * Range includes high endpoint but not low endpoint, i.e. (a,b]
     */
    RANGE_HALF_CLOSED(true),

    /**
     * Inverted-Range contains neither endpoint, i.e. (a,b)
     */
    NOT_RANGE_OPEN(true),

    /**
     * Inverted-Range contains low and high endpoint, i.e. [a,b]
     */
    NOT_RANGE_CLOSED(true),

    /**
     * Inverted-Range includes low endpoint but not high endpoint, i.e. [a,b)
     */
    NOT_RANGE_HALF_OPEN(true),

    /**
     * Inverted-Range includes high endpoint but not low endpoint, i.e. (a,b]
     */
    NOT_RANGE_HALF_CLOSED(true);

    private boolean range;

    QueryGraphRangeEnum(boolean range) {
        this.range = range;
    }

    public static QueryGraphRangeEnum mapFrom(RelationalOpEnum relationalOpEnum) {
        if (relationalOpEnum == RelationalOpEnum.GE) {
            return GREATER_OR_EQUAL;
        }
        else if (relationalOpEnum == RelationalOpEnum.GT) {
            return GREATER;
        }
        else if (relationalOpEnum == RelationalOpEnum.LT) {
            return LESS;
        }
        else if (relationalOpEnum == RelationalOpEnum.LE) {
            return LESS_OR_EQUAL;
        }
        else {
            throw new IllegalArgumentException("Failed to map code " + relationalOpEnum);
        }
    }

    public boolean isRange() {
        return range;
    }

    public boolean isIncludeStart() {
        if (!this.isRange()) {
            throw new UnsupportedOperationException("Cannot determine endpoint-start included for op " + this);
        }
        return this == QueryGraphRangeEnum.RANGE_HALF_OPEN || this == QueryGraphRangeEnum.RANGE_CLOSED ||
                this == QueryGraphRangeEnum.NOT_RANGE_HALF_OPEN || this == QueryGraphRangeEnum.NOT_RANGE_CLOSED;
    }

    public boolean isIncludeEnd() {
        if (!this.isRange()) {
            throw new UnsupportedOperationException("Cannot determine endpoint-end included for op " + this);
        }
        return this == QueryGraphRangeEnum.RANGE_HALF_CLOSED || this == QueryGraphRangeEnum.RANGE_CLOSED ||
                this == QueryGraphRangeEnum.NOT_RANGE_HALF_CLOSED || this == QueryGraphRangeEnum.NOT_RANGE_CLOSED;
    }

    public static QueryGraphRangeEnum getRangeOp(boolean includeStart, boolean includeEnd, boolean isInverted) {
        if (!isInverted) {
            if (includeStart) {
                if (includeEnd) {
                    return RANGE_CLOSED;
                }
                return RANGE_HALF_OPEN;
            }
            else {
                if (includeEnd) {
                    return RANGE_HALF_CLOSED;
                }
                return RANGE_OPEN;
            }
        }
        else {
            if (includeStart) {
                if (includeEnd) {
                    return NOT_RANGE_CLOSED;
                }
                return NOT_RANGE_HALF_OPEN;
            }
            else {
                if (includeEnd) {
                    return NOT_RANGE_HALF_CLOSED;
                }
                return NOT_RANGE_OPEN;
            }
        }
    }

    public boolean isRangeInverted() {
        return isRange() && (this == NOT_RANGE_HALF_CLOSED || this == NOT_RANGE_HALF_OPEN ||
                this == NOT_RANGE_OPEN || this == NOT_RANGE_CLOSED);
    }
}
