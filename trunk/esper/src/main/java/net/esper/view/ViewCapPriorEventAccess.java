package net.esper.view;

import net.esper.view.internal.PriorEventViewFactory;

import java.util.List;

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

    public boolean inspect(List<ViewFactory> viewFactories)
    {
        boolean unboundStream = viewFactories.isEmpty();

        // Find the prior event view to see if it has already been added
        PriorEventViewFactory factory = null;
        for (ViewFactory viewFactory : viewFactories)
        {
            if (viewFactory instanceof PriorEventViewFactory)
            {
                factory = (PriorEventViewFactory) viewFactory;
            }
        }

        if (factory == null)
        {
            factory = new PriorEventViewFactory(unboundStream);
            viewFactories.add(factory);
        }

        return true;
    }
}