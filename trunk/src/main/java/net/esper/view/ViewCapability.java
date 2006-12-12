package net.esper.view;

import java.util.List;

public interface ViewCapability
{
    public boolean veto(List<ViewFactory> viewFactories);
}
