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
    private DeploymentInformationItem[] items;

    public DeploymentInformation(String deploymentId, String moduleName, String moduleURL, Set<String> moduleUses, Calendar deployedDate, DeploymentInformationItem[] items)
    {
        this.deploymentId = deploymentId;
        this.moduleName = moduleName;
        this.moduleURL = moduleURL;
        this.moduleUses = moduleUses;
        this.deployedDate = deployedDate;
        this.items = items;
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

    public DeploymentInformationItem[] getItems() {
        return items;
    }

    public String toString() {
        return "id '" + deploymentId + "' " +
               " deployed on " + deployedDate.getTime().toString(); 
    }
}
