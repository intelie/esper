package com.espertech.esper.core;

public class StatementFilterVersion {

    private volatile long stmtFilterVersion;

    public StatementFilterVersion() {
        stmtFilterVersion = Long.MAX_VALUE;
    }

    public void setStmtFilterVersion(long stmtFilterVersion) {
        this.stmtFilterVersion = stmtFilterVersion;
    }

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
