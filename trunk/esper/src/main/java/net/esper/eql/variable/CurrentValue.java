package net.esper.eql.variable;

public class CurrentValue<T>
{
    private VersionedValue<T> currentVersion;
    private VersionedValue<T> priorVersion;

    public CurrentValue(VersionedValue<T> currentVersion, VersionedValue<T> priorVersion)
    {
        this.currentVersion = currentVersion;
        this.priorVersion = priorVersion;
    }

    public VersionedValue<T> getCurrentVersion()
    {
        return currentVersion;
    }

    public VersionedValue<T> getPriorVersion()
    {
        return priorVersion;
    }
}
