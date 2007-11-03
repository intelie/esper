package net.esper.eql.spec;

public class CreateWindowDesc
{
    private String windowName;

    public CreateWindowDesc(String windowName)
    {
        this.windowName = windowName;
    }

    public String getWindowName()
    {
        return windowName;
    }
}
