package net.esper.view;

import java.util.List;

public class ViewCapDataWindowAccess implements ViewCapability
{
    private Integer optionalIndexConstant;

    public ViewCapDataWindowAccess(Integer optionalIndexConstant)
    {
        this.optionalIndexConstant = optionalIndexConstant;
    }

    public boolean inspect(List<ViewFactory> viewFactories)
    {
        if (viewFactories.size() > 1)
        {
            return true;
        }
        return false;
    }
}
