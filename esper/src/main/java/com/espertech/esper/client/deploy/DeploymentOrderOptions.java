package com.espertech.esper.client.deploy;

public class DeploymentOrderOptions
{
    private boolean checkCircularDependency = true;
    private boolean checkUses = true;

    public boolean isCheckCircularDependency()
    {
        return checkCircularDependency;
    }

    public void setCheckCircularDependency(boolean checkCircularDependency)
    {
        this.checkCircularDependency = checkCircularDependency;
    }

    public boolean isCheckUses()
    {
        return checkUses;
    }

    public void setCheckUses(boolean checkUses)
    {
        this.checkUses = checkUses;
    }
}