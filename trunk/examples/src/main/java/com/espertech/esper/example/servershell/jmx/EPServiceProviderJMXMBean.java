package com.espertech.esper.example.servershell.jmx;

import com.espertech.esper.client.UpdateListener;

public interface EPServiceProviderJMXMBean
{
    public void createEPL(String expression, String statementName);
    public void createEPL(String expression, String statementName, UpdateListener listener);
    public void destroy(String statementName);
}
