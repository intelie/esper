package net.esper.view;

import net.esper.client.ConfigurationException;
import net.esper.client.ConfigurationPlugInView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Resolves view namespace and name to view factory class, using configuration. 
 */
public class ViewResolutionServiceImpl implements ViewResolutionService
{
    private static final Log log = LogFactory.getLog(ViewResolutionServiceImpl.class);
    private final Map<String, Map<String, Class>> nameToFactoryMap;

    /**
     * Ctor.
     * @param configurationPlugInViews is the configured plug-in views
     * @throws ConfigurationException when plug-in views cannot be solved
     */
    public ViewResolutionServiceImpl(List<ConfigurationPlugInView> configurationPlugInViews) throws ConfigurationException
    {
        nameToFactoryMap = new HashMap<String, Map<String, Class>>();

        if (configurationPlugInViews == null)
        {
            return;
        }

        for (ConfigurationPlugInView entry : configurationPlugInViews)
        {
            if (entry.getFactoryClassName() == null)
            {
                throw new ConfigurationException("Factory class name has not been supplied for object '" + entry.getName() + "'");
            }
            if (entry.getNamespace() == null)
            {
                throw new ConfigurationException("Namespace name has not been supplied for object '" + entry.getName() + "'");
            }
            if (entry.getName() == null)
            {
                throw new ConfigurationException("Name has not been supplied for object in namespace '" + entry.getNamespace() + "'");
            }

            Class clazz;
            try
            {
                clazz = Class.forName(entry.getFactoryClassName());
            }
            catch (ClassNotFoundException ex)
            {
                throw new ConfigurationException("View factory class " + entry.getFactoryClassName() + " could not be loaded");
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

    public ViewFactory create(String nameSpace, String name) throws ViewProcessingException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".create Creating view factory, namespace=" + nameSpace + " name=" + name);
        }

        Class viewFactoryClass = null;

        // Pre-configured views override build-in views
        Map<String, Class> namespaceMap = nameToFactoryMap.get(nameSpace);
        if (namespaceMap != null)
        {
            viewFactoryClass = namespaceMap.get(name);
        }

        // if not found in the plugin views, try o resolve as a builtin view
        if (viewFactoryClass == null)
        {
            // Determine view class
            ViewEnum viewEnum = ViewEnum.forName(nameSpace, name);

            if (viewEnum == null)
            {
                String message = "View name '" + nameSpace + ":" + name + "' is not a known view name";
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
                log.debug(".create Successfully instantiated view");
            }
        }
        catch (ClassCastException e)
        {
            String message = "Error casting view factory instance to " + ViewFactory.class.getName() + " interface for view '" + nameSpace + "'";
            throw new ViewProcessingException(message, e);
        }
        catch (IllegalAccessException e)
        {
            String message = "Error invoking view factory constructor for view '" + name;
            message += "', no invocation access for Class.newInstance";
            throw new ViewProcessingException(message, e);
        }
        catch (InstantiationException e)
        {
            String message = "Error invoking view factory constructor for view '" + name;
            message += "' using Class.newInstance";
            throw new ViewProcessingException(message, e);
        }

        return viewFactory;        
    }
}
