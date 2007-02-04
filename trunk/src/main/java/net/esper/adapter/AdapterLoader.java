package net.esper.adapter;

import net.esper.client.*;
import net.esper.core.*;

import java.util.*;

import org.w3c.dom.*;

/**
 * Created for ESPER.
 */
public interface AdapterLoader
{
  public void init(Configuration config, Element configElement);
  public void setEPServiceProvider(EPServiceProviderSPI epService);
}
