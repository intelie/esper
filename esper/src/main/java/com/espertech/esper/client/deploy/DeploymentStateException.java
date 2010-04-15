package com.espertech.esper.client.deploy;

/**
 * Inner exception to {@link DeploymentActionException} available on statement level.
 */
public class DeploymentStateException extends DeploymentException {

    public DeploymentStateException(String message)
    {
        super(message);
    }
}
