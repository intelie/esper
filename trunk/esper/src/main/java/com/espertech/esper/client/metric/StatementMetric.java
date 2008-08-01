package com.espertech.esper.client.metric;

public class StatementMetric
{
    private final String engineURI;
    private final long engineTimestamp;
    private final String statementName;
    private final String stmtId;
    private final long totalCPU;
    private final long totalWall;
    private final long numOutputs;
    private final long numOutputRStream;
    private final long numOutputIStream;

    public StatementMetric(String engineURI, long engineTimestamp, String statementName, String stmtId, long totalCPU, long totalWall, long numOutputs, long numOutputRStream, long numOutputIStream)
    {
        this.engineURI = engineURI;
        this.engineTimestamp = engineTimestamp;
        this.statementName = statementName;
        this.stmtId = stmtId;
        this.totalCPU = totalCPU;
        this.totalWall = totalWall;
        this.numOutputs = numOutputs;
        this.numOutputRStream = numOutputRStream;
        this.numOutputIStream = numOutputIStream;
    }

    public String getEngineURI()
    {
        return engineURI;
    }

    public long getEngineTimestamp()
    {
        return engineTimestamp;
    }

    public String getStatementName()
    {
        return statementName;
    }

    public String getStmtId()
    {
        return stmtId;
    }

    public long getTotalCPU()
    {
        return totalCPU;
    }

    public long getTotalWall()
    {
        return totalWall;
    }

    public long getNumOutputs()
    {
        return numOutputs;
    }

    public long getNumOutputRStream()
    {
        return numOutputRStream;
    }

    public long getNumOutputIStream()
    {
        return numOutputIStream;
    }
}
