package net.esper.eql.spec;

public class ActiveObjectBindingSpec
{
    private String boundObjectId;

    public ActiveObjectBindingSpec(String boundObjectId)
    {
        this.boundObjectId = boundObjectId;
    }

    public String getBoundObjectId()
    {
        return boundObjectId;
    }
}
