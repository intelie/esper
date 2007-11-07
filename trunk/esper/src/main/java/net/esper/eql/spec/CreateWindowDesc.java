package net.esper.eql.spec;

import java.util.List;

public class CreateWindowDesc
{
    private String windowName;
    private List<ViewSpec> viewSpecs;

    public CreateWindowDesc(String windowName, List<ViewSpec> viewSpecs)
    {
        this.windowName = windowName;
        this.viewSpecs = viewSpecs;
    }

    public String getWindowName()
    {
        return windowName;
    }

    public List<ViewSpec> getViewSpecs()
    {
        return viewSpecs;
    }
}
