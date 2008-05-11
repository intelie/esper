package com.espertech.esper.epl.named;

import com.espertech.esper.view.*;
import com.espertech.esper.view.std.GroupByViewFactory;
import com.espertech.esper.view.std.MergeViewFactory;
import com.espertech.esper.core.StatementContext;

import java.util.List;

/**
 * View capability requirement that asks views to handle the remove stream posted by parent views, for use with
 * named windows since these allow on-delete removal of events from a window.
 * <p>
 * Based on being asked to provide the capability, a view factory may need to use a view with a
 * different internal collection to provide a remove stream capability that
 * has good performance, but may come at the cost of lower insert performance as a view
 * may need to build reverse indexes to effeciently remove an event.
 */
public class RemoveStreamViewCapability implements ViewCapability
{
    public boolean inspect(int streamNumber, List<ViewFactory> viewFactories, StatementContext statementContext)
    {
        for (ViewFactory viewFactory : viewFactories)
        {
            if ((viewFactory instanceof GroupByViewFactory) || ((viewFactory instanceof MergeViewFactory)))
            {
                continue;
            }
            if (!(viewFactory.canProvideCapability(this)))
            {
                return false;
            }
        }

        return true;
    }

    public boolean requiresChildViews()
    {
        return false;
    }
}
