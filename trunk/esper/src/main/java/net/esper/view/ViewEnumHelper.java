package net.esper.view;

import net.esper.eql.spec.PluggableObjectDesc;
import net.esper.eql.spec.PluggableObjectType;

/**
 * Helper producing a repository of built-in views.
 */
public class ViewEnumHelper
{
    private final static PluggableObjectDesc builtinViews;

    static
    {
        builtinViews = new PluggableObjectDesc();
        for (ViewEnum viewEnum : ViewEnum.values())
        {
            builtinViews.addObject(viewEnum.getNamespace(), viewEnum.getName(), viewEnum.getFactoryClass(), PluggableObjectType.VIEW);    
        }
    }

    public static PluggableObjectDesc getBuiltinViews()
    {
        return builtinViews;
    }
}
