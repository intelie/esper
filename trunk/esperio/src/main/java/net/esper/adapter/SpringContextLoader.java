package net.esper.adapter;

import net.esper.client.EPException;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProviderManager;
import net.esper.core.EPServiceProviderSPI;
import net.esper.adapter.jms.JMSAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.w3c.dom.Node;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created for ESPER.
 */
public class SpringContextLoader
{
  private final Log log = LogFactory.getLog(this.getClass());
  private AbstractXmlApplicationContext adapterSpringContext;
  private Map<String, Adapter> adapterMap = new HashMap<String, Adapter>();

  public SpringContextLoader()
  {

  }

  public SpringContextLoader(Node configRootNode) throws EPException
  {
    if (!configRootNode.getNodeName().equals("spring-adapter")) return;
    String resource = configRootNode.getAttributes().getNamedItem("SpringApplicationContext").getTextContent();
  }

  public SpringContextLoader(Configuration config, Node configRootNode) throws EPException
  {
    if (!configRootNode.getNodeName().equals("spring-adapter")) return;
    String resource = configRootNode.getAttributes().getNamedItem("SpringApplicationContext").getTextContent();
    configure(config, resource);
  }

  public void configure(Configuration config, String resource)
  {
    log.debug("configuring from resource: " + resource);
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
    Collection<Adapter> c = adapterMap.values();
    for (Iterator<Adapter> itAdapter = c.iterator(); itAdapter.hasNext();)
    {
      Adapter adapter = itAdapter.next();
      if (adapter instanceof OutputAdapter)
      {
        ((OutputAdapter) adapter).setEPServiceProvider(EPServiceProviderManager.getProvider("OutputAdapter", config));
      }
      adapter.start();
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
