package com.espertech.esper.client.metric;

public class ViewMetric
{
    private final String engineURI;
    private final long engineTimestamp;
    private final String stmtIdPrincipal;
    private final int stmtViewNumber;
    private final String viewNamespace;
    private final String viewName;
    private final long numEvents;

    public ViewMetric(String engineURI, long engineTimestamp, String stmtIdPrincipal, int viewId, String viewNamespace, String viewName, long numEvents)
    {
        this.engineURI = engineURI;
        this.engineTimestamp = engineTimestamp;
        this.stmtIdPrincipal = stmtIdPrincipal;
        this.stmtViewNumber = viewId;
        this.viewNamespace = viewNamespace;
        this.viewName = viewName;
        this.numEvents = numEvents;
    }

    public String getEngineURI()
    {
        return engineURI;
    }

    public long getEngineTimestamp()
    {
        return engineTimestamp;
    }

    public String getStmtIdPrincipal()
    {
        return stmtIdPrincipal;
    }

    public int getStmtViewNumber()
    {
        return stmtViewNumber;
    }

    public String getViewNamespace()
    {
        return viewNamespace;
    }

    public String getViewName()
    {
        return viewName;
    }

    public long getNumEvents()
    {
        return numEvents;
    }
}
