package net.esper.eql.variable;

import java.util.Map;

public class VariableVersionThreadEntry
{
    private Integer version;
    private Map<Integer, Object> uncommitted;

    public VariableVersionThreadEntry(int version, Map<Integer, Object> uncommitted)
    {
        this.version = version;
        this.uncommitted = uncommitted;
    }

    public Integer getVersion()
    {
        return version;
    }

    public void setVersion(Integer version)
    {
        this.version = version;
    }

    public Map<Integer, Object> getUncommitted()
    {
        return uncommitted;
    }

    public void setUncommitted(Map<Integer, Object> uncommitted)
    {
        this.uncommitted = uncommitted;
    }
}
