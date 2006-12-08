package net.esper.eql.core;

public interface ViewFactoryDelegate
{
    public boolean requestCapability(int streamNumber, Class requestedCabability, ViewFactoryCallback factoryCallback);
}
