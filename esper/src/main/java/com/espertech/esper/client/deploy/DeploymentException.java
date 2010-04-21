package com.espertech.esper.client.deploy;

import java.util.List;
import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * Base deployment exception.
 */
public class DeploymentException extends Exception {

    public DeploymentException(String message)
    {
        super(message);
    }

    public DeploymentException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DeploymentException(Throwable cause)
    {
        super(cause);
    }
}