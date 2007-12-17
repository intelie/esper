package net.esper.view;

import net.esper.view.std.GroupByViewFactory;
import net.esper.core.StatementContext;

import java.util.List;

/**
 * Describes that we need random access into a data window by index.
 */
public class ViewCapDataWindowAccess implements ViewCapability
{
    /**
     * Ctor.
     */
    public ViewCapDataWindowAccess()
    {
    }

    public boolean inspect(int streamNumber, List<ViewFactory> viewFactories, StatementContext statementContext)
    {
        // We allow the capability only if
        //  - 1 view
        //  - 2 views and the first view is a group-by (for window-per-group access)
        if (viewFactories.size() == 1)
        {
            return true;
        }
        if (viewFactories.size() == 2)
        {
            if (viewFactories.get(0) instanceof GroupByViewFactory)
            {
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean requiresChildViews()
    {
        return true;
    }    
}
