package net.esper.eql.spec;

public class ActiveObjectSpec
{
    private String subscriberUuid;
    private String methodName;
    private Class[] parameters;

    public ActiveObjectSpec(String subscriberUuid, String methodName, Class[] parameters)
    {
        this.subscriberUuid = subscriberUuid;
        this.methodName = methodName;
        this.parameters = parameters;
    }

    public String getSubscriberUuid() {
        return subscriberUuid;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public Class[] getParameters()
    {
        return parameters;
    }
}
