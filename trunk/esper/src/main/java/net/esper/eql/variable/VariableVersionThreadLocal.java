package net.esper.eql.variable;

public class VariableVersionThreadLocal
{
    private ThreadLocal<VariableVersionThreadEntry> perThreadVersion;

    public VariableVersionThreadLocal()
    {
        perThreadVersion = new ThreadLocal<VariableVersionThreadEntry>()
        {
            protected synchronized VariableVersionThreadEntry initialValue()
            {
                return new VariableVersionThreadEntry(0, null);
            }
        };
    }

    public VariableVersionThreadEntry getCurrentThread()
    {
        VariableVersionThreadEntry entry = perThreadVersion.get();
        if (entry == null)
        {
            entry = new VariableVersionThreadEntry(0, null);
            perThreadVersion.set(entry);
        }
        return entry;        
    }
}
