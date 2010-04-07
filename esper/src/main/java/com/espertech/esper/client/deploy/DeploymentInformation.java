package com.espertech.esper.client.deploy;

import java.util.Calendar;
import java.util.Set;

public class DeploymentInformation
{
    private String deploymentId;
    private String moduleName;
    private String moduleURL;
    private Set<String> moduleUses;
    private Calendar deployedDate;
    private String[] statementNames;

    public DeploymentInformation(String deploymentId, String moduleName, String moduleURL, Set<String> moduleUses, Calendar deployedDate, String[] statementNames)
    {
        this.deploymentId = deploymentId;
        this.moduleName = moduleName;
        this.moduleURL = moduleURL;
        this.moduleUses = moduleUses;
        this.deployedDate = deployedDate;
        this.statementNames = statementNames;
    }

    public String getDeploymentId()
    {
        return deploymentId;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public String getModuleURL()
    {
        return moduleURL;
    }

    public Set<String> getModuleUses()
    {
        return moduleUses;
    }

    public Calendar getDeployedDate()
    {
        return deployedDate;
    }

    public String[] getStatementNames()
    {
        return statementNames;
    }
}
