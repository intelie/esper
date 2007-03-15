package net.esper.adapter;

import net.esper.core.EPServiceProviderSPI;

import java.util.Properties;

public interface AdapterLoader
{
    public void init(String name, Properties properties, EPServiceProviderSPI epService);    
}
