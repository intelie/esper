package net.esper.eql.core;

import net.esper.view.ViewCapability;

public interface ViewFactoryDelegate
{
    public boolean requestCapability(int streamNumber, ViewCapability requestedCabability, ViewFactoryCallback factoryCallback);
}
