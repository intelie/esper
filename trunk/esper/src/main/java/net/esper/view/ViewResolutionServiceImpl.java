package net.esper.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.client.ConfigurationPlugInView;
import net.esper.client.ConfigurationException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ViewResolutionServiceImpl implements ViewResolutionService
{
    private static final Log log = LogFactory.getLog(ViewResolutionServiceImpl.class);
    private final Map<String, Map<String, Class>> nameToFactoryMap;

    public ViewResolutionServiceImpl(List<ConfigurationPlugInView> configurationPlugInViews) throws ConfigurationException
    {
        nameToFactoryMap = new HashMap<String, Map<String, Class>>();

        if (configurationPlugInViews == null)
        {
            return;
        }

        for (ConfigurationPlugInView entry : configurationPlugInViews)
        {
            Class clazz;
            try
            {
                clazz = Class.forName(entry.getFactoryClassName());
            }
            catch (ClassNotFoundException ex)
            {
                throw new ConfigurationException("View factory class " + entry.getFactoryClassName() + " count not be loaded");
            }

            Map<String, Class> namespaceMap = nameToFactoryMap.get(entry.getNamespace());
            if (namespaceMap == null)
            {
                namespaceMap = new HashMap<String, Class>();
                nameToFactoryMap.put(entry.getNamespace(), namespaceMap);
            }
            namespaceMap.put(entry.getName(), clazz);
        }
    }

    public ViewFactory create(ViewSpec spec) throws ViewProcessingException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".create Creating view factory, spec=" + spec.toString());
        }

        Class viewFactoryClass = null;

        // Pre-configured views override build-in views
        Map<String, Class> namespaceMap = nameToFactoryMap.get(spec.getObjectNamespace());
        if (namespaceMap != null)
        {
            viewFactoryClass = namespaceMap.get(spec.getObjectName());            
        }

        // if not found in the plugin views, try o resolve as a builtin view
        if (viewFactoryClass == null)
        {
            // Determine view class
            ViewEnum viewEnum = ViewEnum.forName(spec.getObjectNamespace(), spec.getObjectName());

            if (viewEnum == null)
            {
                String message = "View name '" + spec.getObjectName() + "' is not a known view name";
                throw new ViewProcessingException(message);
            }

            viewFactoryClass = viewEnum.getFactoryClass();
        }

        ViewFactory viewFactory = null;
        try
        {
            viewFactory = (ViewFactory) viewFactoryClass.newInstance();

            if (log.isDebugEnabled())
            {
                log.debug(".create Successfully instantiated view factory");
            }
        }
        catch (ClassCastException e)
        {
            String message = "Error casting view factory instance to " + ViewFactory.class.getName() + " interface for view '" + spec.getObjectName() + "'";
            throw new ViewProcessingException(message, e);
        }
        catch (IllegalAccessException e)
        {
            String message = "Error invoking view factory constructor for view '" + spec.getObjectName() + "'";
            message += "', no invocation access for Class.newInstance";
            throw new ViewProcessingException(message, e);
        }
        catch (InstantiationException e)
        {
            String message = "Error invoking view factory constructor for view '" + spec.getObjectName() + "'";
            message += "' using Class.newInstance";
            throw new ViewProcessingException(message, e);
        }

        return viewFactory;        
    }
}
