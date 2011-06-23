package com.espertech.esper.client.deploy;

import com.espertech.esper.client.EPStatement;

import java.util.List;

/**
 * Result of a deployment operation carries a deployment id for use in undeploy and statement-level information.
 */
public class DeploymentResult
{
    private final String deploymentId;
    private final List<EPStatement> statements;
    private final List<String> imports;

    /**
     * Ctor.
     * @param deploymentId deployment id
     * @param statements statements deployed and started
     */
    public DeploymentResult(String deploymentId, List<EPStatement> statements, List<String> imports)
    {
        this.deploymentId = deploymentId;
        this.statements = statements;
        this.imports = imports;
    }

    /**
     * Returns the deployment id.
     * @return id
     */
    public String getDeploymentId()
    {
        return deploymentId;
    }

    /**
     * Returns the statements.
     * @return statements
     */
    public List<EPStatement> getStatements()
    {
        return statements;
    }

    public List<String> getImports() {
        return imports;
    }
}
