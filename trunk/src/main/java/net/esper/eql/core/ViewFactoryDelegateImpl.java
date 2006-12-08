package net.esper.eql.core;

import net.esper.view.ViewFactoryChain;
import net.esper.view.ViewFactory;

public class ViewFactoryDelegateImpl implements ViewFactoryDelegate
{
    private ViewFactoryChain[] viewFactories;

    public ViewFactoryDelegateImpl(ViewFactoryChain[] viewFactories)
    {
        this.viewFactories = viewFactories;
    }

    public boolean requestCapability(int streamNumber, Class requestedCabability, ViewFactoryCallback factoryCallback)
    {
        ViewFactoryChain factories = viewFactories[streamNumber];

        for (ViewFactory factory : factories.getViewFactoryChain())
        {
            if (factory.canProvideCapability(requestedCabability))
            {
                factory.setProvideCapability(requestedCabability, factoryCallback);
                return true;
            }
        }

        return false;
    }
}