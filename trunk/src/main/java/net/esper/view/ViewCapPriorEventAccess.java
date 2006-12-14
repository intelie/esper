package net.esper.view;

import net.esper.view.internal.PriorEventViewFactory;

import java.util.List;

public class ViewCapPriorEventAccess implements ViewCapability
{
    private Integer indexConstant;

    public ViewCapPriorEventAccess(Integer indexConstant)
    {
        this.indexConstant = indexConstant;
    }

    public Integer getIndexConstant()
    {
        return indexConstant;
    }

    public boolean inspect(List<ViewFactory> viewFactories)
    {
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
            factory = new PriorEventViewFactory();
            viewFactories.add(factory);
        }

        return true;
    }
}
