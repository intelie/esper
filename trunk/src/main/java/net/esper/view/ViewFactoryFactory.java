package net.esper.view;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * Static factory for creating view instances based on a view specification and a given parent view.
 */
public final class ViewFactoryFactory
{
    /**
     * Instantiates a view factory based on view name stored in the view spec.
     * @param spec contains view name
     * @return instantiated and hooked-up view
     * @throws ViewProcessingException if the view name is wrong, parameters don't match view constructors, or
     * the view refuses to hook up with its parent
     */
    protected static ViewFactory create(ViewSpec spec)
        throws ViewProcessingException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".create Creating view factory, spec=" + spec.toString());
        }

        // Determine view class
        ViewEnum viewEnum = ViewEnum.forName(spec.getObjectNamespace(), spec.getObjectName());

        if (viewEnum == null)
        {
            String message = "View name '" + spec.getObjectName() + "' is not a known view name";
            throw new ViewProcessingException(message);
        }

        Class viewFactoryClass = viewEnum.getFactoryClass();
        ViewFactory viewFactory = null;

        try
        {
            viewFactory = (ViewFactory) viewFactoryClass.newInstance();

            if (log.isDebugEnabled())
            {
                log.debug(".create Successfully instantiated view");
            }
        }
        catch (IllegalAccessException e)
        {
            String message = "Error invoking view factory constructor for view '" + spec.getObjectName();
            message += "', no invocation access for Class.newInstance";
            throw new ViewProcessingException(message, e);
        }
        catch (InstantiationException e)
        {
            String message = "Error invoking view factory constructor for view '" + spec.getObjectName();
            message += "' for Class.newInstance";
            throw new ViewProcessingException(message, e);
        }

        return viewFactory;
    }

    private static final Log log = LogFactory.getLog(ViewFactoryFactory.class);
}
