package com.espertech.esper.client.deploy;

import java.util.Set;

public class UndeploymentResult
{
    private final String deploymentId;
    private final Set<String> statementNames;

    public UndeploymentResult(String deploymentId, Set<String> statementNames)
    {
        this.deploymentId = deploymentId;
        this.statementNames = statementNames;
    }

    public String getDeploymentId()
    {
        return deploymentId;
    }

    public Set<String> getStatementNames()
    {
        return statementNames;
    }
}
