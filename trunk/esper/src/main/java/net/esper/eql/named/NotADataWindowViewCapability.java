package net.esper.eql.named;

import net.esper.view.ViewCapability;
import net.esper.view.ViewFactory;
import net.esper.view.DataWindowViewFactory;
import net.esper.view.std.GroupByViewFactory;
import net.esper.view.std.MergeViewFactory;
import net.esper.core.StatementContext;
import net.esper.eql.expression.ExprValidationException;

import java.util.List;

/**
 * Expresses the requirement that all views are derived-value views and now data window views, with the exception of
 * group-by and merge views.
 */
public class NotADataWindowViewCapability implements ViewCapability
{
    public boolean inspect(int streamNumber, List<ViewFactory> viewFactories, StatementContext statementContext)
            throws ExprValidationException
    {
        for (ViewFactory viewFactory : viewFactories)
        {
            if ((viewFactory instanceof GroupByViewFactory) || ((viewFactory instanceof MergeViewFactory)))
            {
                continue;
            }
            if (viewFactory instanceof DataWindowViewFactory)
            {
                throw new ExprValidationException(NamedWindowService.ERROR_MSG_NO_DATAWINDOW_ALLOWED);
            }
        }

        return true;
    }

    public boolean requiresChildViews()
    {
        return false;
    }
}
