package net.esper.view;

import net.esper.client.EPException;
import net.esper.core.StatementContext;
import net.esper.view.internal.PriorEventViewFactory;

import java.util.List;
import java.util.Arrays;

/**
 * Describes that we need access to prior events (result events published by views),
 * for use by the "prior" expression function.
 */
public class ViewCapPriorEventAccess implements ViewCapability
{
    private Integer indexConstant;

    /**
     * Ctor.
     * @param indexConstant is the index of the prior event, with zero being the current event.
     */
    public ViewCapPriorEventAccess(Integer indexConstant)
    {
        this.indexConstant = indexConstant;
    }

    /**
     * Index or the prior event we are asking for.
     * @return prior event index constant
     */
    public Integer getIndexConstant()
    {
        return indexConstant;
    }

    public boolean inspect(int streamNumber, List<ViewFactory> viewFactories, StatementContext statementContext)
    {
        boolean unboundStream = viewFactories.isEmpty();

        // Find the prior event view to see if it has already been added
        for (ViewFactory viewFactory : viewFactories)
        {
            if (viewFactory instanceof PriorEventViewFactory)
            {
                return true;
            }
        }

        try
        {
            String namespace = ViewEnum.PRIOR_EVENT_VIEW.getNamespace();
            String name = ViewEnum.PRIOR_EVENT_VIEW.getName();
            ViewFactory factory = statementContext.getViewResolutionService().create(namespace, name);
            viewFactories.add(factory);
            
            ViewFactoryContext context = new ViewFactoryContext(statementContext, streamNumber, viewFactories.size() + 1, namespace, name);
            factory.setViewParameters(context, Arrays.asList((Object)(Boolean)unboundStream));
        }
        catch (ViewProcessingException ex)
        {
            String text = "Exception creating prior event view factory";
            throw new EPException(text, ex);
        }
        catch (ViewParameterException ex)
        {
            String text = "Exception creating prior event view factory";
            throw new EPException(text, ex);
        }

        return true;
    }

    public boolean requiresChildViews()
    {
        return false;
    }
}
