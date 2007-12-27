package net.esper.example.servershell.jmx;

import net.esper.client.UpdateListener;

public interface EPServiceProviderJMXMBean
{
    public void createEQL(String expression, String statementName);
    public void createEQL(String expression, String statementName, UpdateListener listener);
    public void destroy(String statementName);
}
