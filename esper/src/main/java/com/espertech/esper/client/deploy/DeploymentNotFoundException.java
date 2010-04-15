package com.espertech.esper.client.deploy;

/**
 * Inner exception to {@link com.espertech.esper.client.deploy.DeploymentActionException} available on statement level.
 */
public class DeploymentNotFoundException extends DeploymentException {

    public DeploymentNotFoundException(String message)
    {
        super(message);
    }
}
