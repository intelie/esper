package net.esper.adapter;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.client.UpdateListener;
import net.esper.adapter.OutputAdapterService;
import net.esper.adapter.jms.JMSAdapter;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Dec 8, 2006
 * Time: 8:45:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class OutputAdapterServiceImpl implements OutputAdapterService
{
    private AbstractXmlApplicationContext adapterContext;
    private ClassPathXmlApplicationContext appContext;
    private Map<String, JMSAdapter> adapterMap = new HashMap<String, JMSAdapter>();
    private Map<String, String> adapterStreamMap = new HashMap<String, String>();
    private static final Log log = LogFactory.getLog(OutputAdapterService.class);

    public OutputAdapterServiceImpl(String configuration)
    {
        //appContext = new ClassPathXmlApplicationContext(configuration);
        adapterContext = createSpringApplicationContext(configuration);
        String[] beanNames =  adapterContext.getBeanDefinitionNames();
        for (String beanName: beanNames)
        {
            Object o = getBean(beanName);
            if (o instanceof JMSAdapter)
            {
               JMSAdapter adapter = (JMSAdapter) o;
               adapterMap.put(beanName, adapter);
               adapterStreamMap.put(beanName, adapter.getOutputStreamAlias());
            }
        }
    }

    private AbstractXmlApplicationContext createSpringApplicationContext(String configuration)
    {
        return new org.springframework.context.support.ClassPathXmlApplicationContext(configuration);
    }

    /* private AbstractXmlApplicationContext createXbeanApplicationContext(String configuration)
    {
        return new org.apache.xbean.spring.context.ClassPathXmlApplicationContext(configuration);
    }*/


    public JMSAdapter createJMSAdapter(String adapterName)
    {
        JMSAdapter adapter;
        if (adapterName == null)
        {
            return null;
        }

        adapter = adapterMap.get(adapterName);
        if (adapter != null)
        {
            return adapter;
        }

        adapter = (JMSAdapter) getBean(adapterName);
        log.debug("Adding adapter " + adapterName);
        adapterMap.put(adapterName, adapter);
        adapterStreamMap.put(adapterName, adapter.getOutputStreamAlias());
        return adapter;
    }

    public JMSAdapter getJMSAdapter(String outputStreamAlias, String adapterName)
    {
        if (adapterMap == null)
        {
            return null;
        }
        return adapterMap.get(adapterName);
    }

    public List<UpdateListener> getMatchingOutputAdapter(String outputStreamAlias)
    {
        List<UpdateListener> list = new ArrayList<UpdateListener>();

        if ((outputStreamAlias == null) || (adapterStreamMap == null))
        {
            return null;
        }

        Set<String> adapterNameSet = adapterStreamMap.keySet();
        Iterator itAdapterNames = adapterNameSet.iterator();
        Collection<String> streamNames = adapterStreamMap.values();
        Iterator itStream = streamNames.iterator();
        while (itStream.hasNext())
        {
            String streamAlias = (String) itStream.next();
            String adapterName =  (String) itAdapterNames.next();
            if (outputStreamAlias.equalsIgnoreCase(streamAlias))
            {
                JMSAdapter adapter = adapterMap.get(adapterName);
                if (adapter.getRole().equalsIgnoreCase("Sender"))
                {
                    list.add(adapter);
                }
            }

        }
        return list;
    }

    private Object getBean(String name)
    {
        //assertNotNull("Could not find object in Spring for key: " + name);
        return adapterContext.getBean(name);
        //return appContext.getBean(name);
    }

}
