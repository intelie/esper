package net.esper.view;

public class ViewCapabilityRandomAccess implements ViewCapability
{
    private Integer optionalIndexConstant;

    public ViewCapabilityRandomAccess(Integer optionalIndexConstant)
    {
        this.optionalIndexConstant = optionalIndexConstant;
    }
}
