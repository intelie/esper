package com.espertech.esper.client.deploy;

import java.util.Set;
import java.util.List;

public class UndeploymentResult
{
    private final String deploymentId;
    private final List<DeploymentInformationItem> statementInfo;

    public UndeploymentResult(String deploymentId, List<DeploymentInformationItem> statementInfo)
    {
        this.deploymentId = deploymentId;
        this.statementInfo = statementInfo;
    }

    public String getDeploymentId()
    {
        return deploymentId;
    }

    public List<DeploymentInformationItem> getStatementInfo()
    {
        return statementInfo;
    }
}
