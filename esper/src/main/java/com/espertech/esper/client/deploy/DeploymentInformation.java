package com.espertech.esper.client.deploy;

import java.util.Calendar;
import java.util.Set;

/**
 * Available information about deployment made.
 */
public class DeploymentInformation
{
    private String deploymentId;
    private String moduleName;
    private String moduleURI;
    private String moduleArchiveName;
    private Object moduleUserObject;
    private Set<String> moduleUses;
    private Calendar deployedDate;
    private DeploymentInformationItem[] items;

    /**
     * Ctor.
     * @param deploymentId deployment id
     * @param moduleName module name
     * @param moduleURI module URI
     * @param moduleArchiveName archive name
     * @param moduleUses uses-declarations of module
     * @param deployedDate deployed date
     * @param items module statement-level details
     * @param moduleUserObject optional user object
     */
    public DeploymentInformation(String deploymentId, String moduleName, String moduleURI, String moduleArchiveName, Object moduleUserObject, Set<String> moduleUses, Calendar deployedDate, DeploymentInformationItem[] items)
    {
        this.deploymentId = deploymentId;
        this.moduleName = moduleName;
        this.moduleURI = moduleURI;
        this.moduleArchiveName = moduleArchiveName;
        this.moduleUserObject = moduleUserObject;
        this.moduleUses = moduleUses;
        this.deployedDate = deployedDate;
        this.items = items;
    }

    /**
     * The user object attached to the module if one was provided.
     * @return user object
     */
    public Object getModuleUserObject() {
        return moduleUserObject;
    }

    /**
     * Returns the deployment id.
     * @return deployment id
     */
    public String getDeploymentId()
    {
        return deploymentId;
    }

    /**
     * Returns the module name.
     * @return module name
     */
    public String getModuleName()
    {
        return moduleName;
    }

    /**
     * Returns the module URI.
     * @return uri
     */
    public String getModuleURI()
    {
        return moduleURI;
    }

    /**
     * Returns the module archive name if one was provided.
     * @return archive name
     */
    public String getModuleArchiveName() {
        return moduleArchiveName;
    }

    /**
     * Returns the names of modules that the module depends on.
     * @return uses-dependencies
     */
    public Set<String> getModuleUses()
    {
        return moduleUses;
    }

    /**
     * Returns the deployment date.
     * @return deployment date
     */
    public Calendar getDeployedDate()
    {
        return deployedDate;
    }

    /**
     * Returns deployment statement-level details.
     * @return statement details
     */
    public DeploymentInformationItem[] getItems() {
        return items;
    }

    public String toString() {
        return "id '" + deploymentId + "' " +
               " deployed on " + deployedDate.getTime().toString(); 
    }
}
