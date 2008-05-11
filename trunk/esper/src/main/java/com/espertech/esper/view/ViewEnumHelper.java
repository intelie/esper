package com.espertech.esper.view;

import com.espertech.esper.epl.spec.PluggableObjectCollection;
import com.espertech.esper.epl.spec.PluggableObjectType;

/**
 * Helper producing a repository of built-in views.
 */
public class ViewEnumHelper
{
    private final static PluggableObjectCollection builtinViews;

    static
    {
        builtinViews = new PluggableObjectCollection();
        for (ViewEnum viewEnum : ViewEnum.values())
        {
            builtinViews.addObject(viewEnum.getNamespace(), viewEnum.getName(), viewEnum.getFactoryClass(), PluggableObjectType.VIEW);    
        }
    }

    /**
     * Returns a collection of plug-in views.
     * @return built-in view definitions
     */
    public static PluggableObjectCollection getBuiltinViews()
    {
        return builtinViews;
    }
}
