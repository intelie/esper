package net.esper.view;

import java.util.List;

/**
 * Describes that we need random access into a data window by index.
 */
public class ViewCapDataWindowAccess implements ViewCapability
{
    private Integer optionalIndexConstant;

    /**
     * Ctor.
     * @param optionalIndexConstant is the index, or null if expression-supplied index and not constant
     */
    public ViewCapDataWindowAccess(Integer optionalIndexConstant)
    {
        this.optionalIndexConstant = optionalIndexConstant;
    }

    public boolean inspect(List<ViewFactory> viewFactories)
    {
        if (viewFactories.size() > 1)
        {
            return false;
        }
        return true;
    }
}
