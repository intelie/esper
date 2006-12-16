package net.esper.eql.core;

import net.esper.view.ViewFactoryChain;
import net.esper.view.ViewFactory;
import net.esper.view.ViewCapability;

public class ViewResourceDelegateImpl implements ViewResourceDelegate
{
    private ViewFactoryChain[] viewFactories;

    public ViewResourceDelegateImpl(ViewFactoryChain[] viewFactories)
    {
        this.viewFactories = viewFactories;
    }

    public boolean requestCapability(int streamNumber, ViewCapability requestedCabability, ViewResourceCallback resourceCallback)
    {
        ViewFactoryChain factories = viewFactories[streamNumber];

        if (!(requestedCabability.inspect(factories.getViewFactoryChain())))
        {
            return false;
        }

        for (ViewFactory factory : factories.getViewFactoryChain())
        {
            if (factory.canProvideCapability(requestedCabability))
            {
                factory.setProvideCapability(requestedCabability, resourceCallback);
                return true;
            }
        }

        return false;
    }
}
