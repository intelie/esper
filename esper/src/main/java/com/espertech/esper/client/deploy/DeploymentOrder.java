package com.espertech.esper.client.deploy;

import java.util.List;

public class DeploymentOrder
{
    private List<Module> ordered;

    public DeploymentOrder(List<Module> ordered)
    {
        this.ordered = ordered;
    }

    public List<Module> getOrdered()
    {
        return ordered;
    }
}
