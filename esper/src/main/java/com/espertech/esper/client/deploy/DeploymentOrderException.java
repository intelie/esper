package com.espertech.esper.client.deploy;

/**
 * Exception indicates a problem when determining delpoyment order and uses-dependency checking.
 */
public class DeploymentOrderException extends DeploymentException {

    /**
     * Ctor.
     * @param message error message
     */
    public DeploymentOrderException(String message)
    {
        super(message);
    }
}