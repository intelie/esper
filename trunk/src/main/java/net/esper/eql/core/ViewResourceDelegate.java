package net.esper.eql.core;

import net.esper.view.ViewCapability;

public interface ViewResourceDelegate
{
    public boolean requestCapability(int streamNumber, ViewCapability requestedCabability, ViewResourceCallback resourceCallback);
}
