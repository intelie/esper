package com.espertech.esper.client.deploy;

import com.espertech.esper.client.EPStatement;

import java.util.List;

public class DeploymentResult
{
    private final String deploymentId;
    private final List<EPStatement> statements;

    public DeploymentResult(String deploymentId, List<EPStatement> statements)
    {
        this.deploymentId = deploymentId;
        this.statements = statements;
    }

    public String getDeploymentId()
    {
        return deploymentId;
    }

    public List<EPStatement> getStatements()
    {
        return statements;
    }
}
