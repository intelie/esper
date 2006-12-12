package net.esper.view;

import java.util.List;

public class ViewCapabilityRandomAccess implements ViewCapability
{
    private Integer optionalIndexConstant;

    public ViewCapabilityRandomAccess(Integer optionalIndexConstant)
    {
        this.optionalIndexConstant = optionalIndexConstant;
    }

    public boolean veto(List<ViewFactory> viewFactories)
    {
        if (viewFactories.size() > 1)
        {
            return true;
        }
        return false;
    }
}
