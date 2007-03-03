package net.esper.adapter;

import net.esper.client.*;
import net.esper.core.*;
import org.apache.commons.logging.*;
import org.springframework.beans.*;
import org.springframework.context.support.*;
import org.w3c.dom.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created for ESPER.
 */
public class SpringContextLoader implements AdapterLoader
{
  private final Log log = LogFactory.getLog(this.getClass());
  private AbstractXmlApplicationContext adapterSpringContext;
  private Map<String, Adapter> adapterMap = new HashMap<String, Adapter>();

  public SpringContextLoader()
  {
  }

  public void init(Configuration config, Element configElement)
    throws EPException
  {
    NodeList nodes =
      configElement.getElementsByTagName("classpath-app-context");
    if (nodes.getLength() == 0)
    {
      nodes = configElement.getElementsByTagName("file-app-context");
    }
    if (nodes.getLength() != 1)
    {
      return;
    }
    Node classPathNode = nodes.item(0);
    boolean loadFromClassPath = (classPathNode.getNodeName().equals(
      "classpath-app-context")) ?
      true :
      false;
    String resource =
      classPathNode.getAttributes().getNamedItem("name").getTextContent();
    configure(config, resource, loadFromClassPath);
  }


  public void configure(Configuration config, String resource,
    boolean fromClassPath)
  {
    if ((config == null) || (resource == null))
    {
      return;
    }
    log.debug(".Configuring from resource: " + resource);
    adapterSpringContext =
      createSpringApplicationContext(resource, fromClassPath);
    String[] beanNames = adapterSpringContext.getBeanDefinitionNames();
    for (String beanName : beanNames)
    {
      Object o = getBean(beanName);
      if (o instanceof Adapter)
      {
        adapterMap.put(beanName, (Adapter)o);
      }
    }
  }

  public void configure(String resource, boolean fromClassPath)
  {
    log.debug(".Configuring from resource: " + resource);
    adapterSpringContext =
      createSpringApplicationContext(resource, fromClassPath);
    String[] beanNames = adapterSpringContext.getBeanDefinitionNames();
    for (String beanName : beanNames)
    {
      Object o = getBean(beanName);
      if (o instanceof Adapter)
      {
        adapterMap.put(beanName, (Adapter)o);
      }
    }
  }

  public void setEPServiceProvider(EPServiceProviderSPI epService)
  {
    Collection<Adapter> c = adapterMap.values();
    for (Iterator<Adapter> itAdapter = c.iterator(); itAdapter.hasNext();)
    {
      Adapter adapter = itAdapter.next();
      if (adapter instanceof OutputAdapter)
      {
        ((OutputAdapter)adapter).setEPServiceProvider(epService);
        adapter.start();
      }
    }
  }

  public Adapter getAdapter(String adapterName)
  {
    if (adapterMap == null)
    {
      return null;
    }
    return adapterMap.get(adapterName);
  }

  public void close()
  {
    adapterSpringContext.close();
  }

  private AbstractXmlApplicationContext createSpringApplicationContext(
    String configuration, boolean fromClassPath) throws BeansException
  {
    if (fromClassPath)
    {
      log.debug("classpath configuration");
      return new ClassPathXmlApplicationContext(configuration);
    }
    if (new File(configuration).exists())
    {
      log.debug("File configuration");
      return new FileSystemXmlApplicationContext(configuration);
    }
    else
    {
      throw new EPException("Spring configuration file not found.");
    }
  }

  private Object getBean(String name)
  {
    //assertNotNull("Could not find object in Spring for key: " + name);
    return adapterSpringContext.getBean(name);
  }

  private URL getResource(Class cl, String resource)
  {
    URL url = null;
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if (classLoader != null)
    {
      log.debug("classloader");
      url = classLoader.getResource(resource);
    }
    if (url == null)
    {
      log.debug(" Class for getResource" + cl.getName());
      url = cl.getResource(resource);
    }
    if (url == null)
    {
      log.debug(" Class for getClassLoader.getResource" + cl.getName());
      url = cl.getClassLoader().getResource(resource);
    }
    if (url == null)
    {
      log.debug("not found");
      throw new EPException(resource + " not found");
    }
    log.debug("url " + url.toString());
    return url;
  }

}
