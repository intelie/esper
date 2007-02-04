package net.esper.adapter;

import net.esper.client.Configuration;
import net.esper.client.EPException;
import net.esper.core.EPServiceProviderSPI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.w3c.dom.*;
import org.xml.sax.*;

import javax.xml.parsers.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.reflect.*;

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

  public void init(Configuration config, Element configElement) throws EPException
  {
    NodeList nodes = configElement.getElementsByTagName("classpath-app-context");
    if (nodes.getLength() != 1) return;
    Node classPathNode = nodes.item(0);
    if (!classPathNode.getNodeName().equals("classpath-app-context")) return;
    String resource = classPathNode.getAttributes().getNamedItem("name").getTextContent();
    configure(config, resource);
  }


  public void configure(Configuration config, String resource)
  {
    if ((config == null) || (resource == null)) return;
    log.debug(".Configuring from resource: " + resource);
    adapterSpringContext = createSpringApplicationContext(resource);
    String[] beanNames = adapterSpringContext.getBeanDefinitionNames();
    for (String beanName : beanNames)
    {
      Object o = getBean(beanName);
      if (o instanceof Adapter)
      {
        adapterMap.put(beanName, (Adapter) o);
      }
    }
  }

  public void configure(String resource)
  {
    log.debug(".Configuring from resource: " + resource);
    adapterSpringContext = createSpringApplicationContext(resource);
    String[] beanNames = adapterSpringContext.getBeanDefinitionNames();
    for (String beanName : beanNames)
    {
      Object o = getBean(beanName);
      if (o instanceof Adapter)
      {
        adapterMap.put(beanName, (Adapter) o);
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
        ((OutputAdapter) adapter).setEPServiceProvider(epService);
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

  private AbstractXmlApplicationContext createSpringApplicationContext(String configuration) throws BeansException
  {
    if (new File(configuration).exists())
    {
      log.debug("File configuration");
      return new FileSystemXmlApplicationContext(configuration);
    }
    else
    {
      log.debug("classpath configuration");
      return new ClassPathXmlApplicationContext(configuration);
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
