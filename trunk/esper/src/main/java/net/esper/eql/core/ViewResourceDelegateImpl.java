package net.esper.eql.core;

import net.esper.view.ViewFactoryChain;
import net.esper.view.ViewFactory;
import net.esper.view.ViewCapability;

/**
 * Coordinates between view factories and requested resource (by expressions) the
 * availability of view resources to expressions. 
 */
public class ViewResourceDelegateImpl implements ViewResourceDelegate
{
    private ViewFactoryChain[] viewFactories;

    /**
     * Ctor.
     * @param viewFactories array of view factory chains, one for each stream
     */
    public ViewResourceDelegateImpl(ViewFactoryChain[] viewFactories)
    {
        this.viewFactories = viewFactories;
    }

    public boolean requestCapability(int streamNumber, ViewCapability requestedCabability, ViewResourceCallback resourceCallback)
    {
        ViewFactoryChain factories = viewFactories[streamNumber];

        // first we give the capability implementation a chance to inspect the view factory chain
        // it can deny by returning false
        if (!(requestedCabability.inspect(factories.getViewFactoryChain())))
        {
            return false;
        }

        // then ask each view in turn to support the capability
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
