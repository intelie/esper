package com.espertech.esperio;

import com.espertech.esper.client.EPException;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.plugin.PluginLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Loader for Spring-configured input and output adapters.
 */
public class SpringContextLoader implements PluginLoader
{
    private final Log log = LogFactory.getLog(this.getClass());
    private AbstractXmlApplicationContext adapterSpringContext;
    private Map<String, Adapter> adapterMap = new HashMap<String, Adapter>();

    /**
     * Default Ctor needed for reflection instantiation.
     */
    public SpringContextLoader()
    {
    }

    public void destroy()
    {
        for (Adapter adapter : adapterMap.values())
        {
            adapterSpringContext.destroy();
            if (adapter.getState() == AdapterState.STARTED)
            {
                adapter.stop();
            }
            if ((adapter.getState() == AdapterState.OPENED) || (adapter.getState() == AdapterState.PAUSED))
            {
                adapter.destroy();
            }
        }
    }

    public void init(String name, Properties properties, EPServiceProviderSPI epService)
    {
        boolean fromClassPath = true;
        String resource = properties.getProperty(SpringContext.CLASSPATH_CONTEXT);
        if (resource == null)
        {
            fromClassPath = false;
            resource = properties.getProperty(SpringContext.FILE_APP_CONTEXT);
        }
        if (resource == null)
        {
            throw new IllegalArgumentException("Required property not found: " + SpringContext.CLASSPATH_CONTEXT + " or " + SpringContext.FILE_APP_CONTEXT);
        }

        // Load adapters
        log.debug(".Configuring from resource: " + resource);
        adapterSpringContext = createSpringApplicationContext(resource, fromClassPath);
        String[] beanNames = adapterSpringContext.getBeanDefinitionNames();
        for (String beanName : beanNames)
        {
            Object o = adapterSpringContext.getBean(beanName);
            if (o instanceof Adapter)
            {
                adapterMap.put(beanName, (Adapter) o);
            }
        }

        // Initialize adapters
        Collection<Adapter> adapters = adapterMap.values();
        for (Adapter adapter : adapters)
        {
            if (adapter instanceof AdapterSPI)
            {
                AdapterSPI spi = (AdapterSPI) adapter;
                spi.setEPServiceProvider(epService);
            }
            adapter.start();
        }
    }

    private AbstractXmlApplicationContext createSpringApplicationContext(String configuration, boolean fromClassPath) throws BeansException
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
}
