package net.esper.view;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import java.lang.reflect.InvocationTargetException;

/**
 * Static factory for creating view instances based on a view specification and a given parent view.
 */
public final class ViewFactory
{
    /**
     * Instantiates a view based on view name and parameters stored in the view spec, and attempts to
     * hook it up with a parent view.
     * @param parentView is the parent view to hook the new view into
     * @param spec contains view name and parameters
     * @return instantiated and hooked-up view
     * @throws ViewProcessingException if the view name is wrong, parameters don't match view constructors, or
     * the view refuses to hook up with its parent
     */
    protected static View create(Viewable parentView, ViewSpec spec)
        throws ViewProcessingException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".create Creating view, parentView.class=" + parentView.getClass().getName() +
                    "  spec=" + spec.toString());
        }

        // Determine view class
        ViewEnum viewEnum = ViewEnum.forName(spec.getObjectNamespace(), spec.getObjectName());

        if (viewEnum == null)
        {
            String message = "View name '" + spec.getObjectName() + "' is not a known view name";
            log.fatal(".create " + message);
            throw new ViewProcessingException(message);
        }

        Object[] arguments = spec.getObjectParameters().toArray();

        // If the view requires parameters, the empty argument list would be a problem
        // since all views are also Java beans
        if ((arguments.length == 0) && (viewEnum.isRequiresParameters()))
        {
            String message = "No parameters have been supplied for view " + spec.getObjectName();
            throw new ViewProcessingException(message);
        }

        View view = null;
        try
        {
            view = (View) ConstructorUtils.invokeConstructor(viewEnum.getClazz(), arguments);

            if (log.isDebugEnabled())
            {
                log.debug(".create Successfully instantiated view");
            }
        }
        catch (NoSuchMethodException e)
        {
            String message = "Error invoking constructor for view '" + spec.getObjectName();
            message += "', the view parameter list is not valid for the view";
            log.fatal(".create " + message);
            throw new ViewProcessingException(message, e);
        }
        catch (IllegalAccessException e)
        {
            String message = "Error invoking constructor for view '" + spec.getObjectName();
            message += "', no invocation access";
            log.fatal(".create " + message);
            throw new ViewProcessingException(message, e);
        }
        catch (InvocationTargetException e)
        {
            String message = "Error invoking constructor for view '" + spec.getObjectName();
            message += "', invocation threw exception: " + e.getCause().getMessage();
            log.fatal(".create " + message);
            throw new ViewProcessingException(message, e);
        }
        catch (InstantiationException e)
        {
            String message = "Error invoking constructor for view '" + spec.getObjectName();
            message += "', could not instantiateChain";
            log.fatal(".create " + message);
            throw new ViewProcessingException(message, e);
        }

        // Ask view if it can indeed hook into the parent
        String errorMessage = view.attachesTo(parentView);
        if (errorMessage != null)
        {
            String message = "Cannot attach view, the view '" + spec.getObjectName() +
                    "' is incompatible to its parent view, explanation: " + errorMessage;
            log.fatal(".create " + message);
            throw new ViewProcessingException(message);
        }

        return view;
    }

    private static final Log log = LogFactory.getLog(ViewFactory.class);
}
