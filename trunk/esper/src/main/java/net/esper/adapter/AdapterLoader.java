package net.esper.adapter;

import net.esper.client.*;
import net.esper.core.*;
import org.w3c.dom.*;

import java.util.Properties;

/**
 * Created for ESPER.
 */
public interface AdapterLoader
{
    public void init(Properties properties, Element configElement);

    public void close();

    public void setEPServiceProvider(EPServiceProviderSPI epService);
}
