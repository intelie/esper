package com.espertech.esper.core;

/**
 * Records minimal statement filter version required for processing.
 */
public class StatementFilterVersion {

    private volatile long stmtFilterVersion;

    /**
     * Ctor.
     */
    public StatementFilterVersion() {
        stmtFilterVersion = Long.MIN_VALUE;
    }

    /**
     * Set filter version.
     * @param stmtFilterVersion to set
     */
    public void setStmtFilterVersion(long stmtFilterVersion) {
        this.stmtFilterVersion = stmtFilterVersion;
    }

    /**
     * Check current filter.
     * @param filterVersion to check
     * @return false if not current
     */
    public boolean isCurrentFilter(long filterVersion)
    {
        if (filterVersion < stmtFilterVersion) {
            // catch-up in case of roll
            if (filterVersion + 100000 < stmtFilterVersion) {
                stmtFilterVersion = filterVersion;
            }
            return false;
        }
        return true;
    }
}
