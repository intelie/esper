package net.esper.eql.named;

import net.esper.view.*;
import net.esper.view.std.GroupByViewFactory;
import net.esper.view.std.MergeViewFactory;
import net.esper.view.internal.PriorEventViewFactory;
import net.esper.core.StatementContext;
import net.esper.client.EPException;

import java.util.List;
import java.util.Arrays;

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
