package com.espertech.esper.client.deploy;

/**
 * Options for use in deployment of a module to control the behavior of the deploy operation.
 */
public class DeploymentOptions {

    private boolean compile = true;
    private boolean compileOnly = false;
    private boolean rollbackOnFail = true;
    private boolean failFast = true;
    private String isolatedServiceProvider = null;
    private boolean validateOnly = false;

    /**
     * Returns true (the default) to indicate that the deploy operation first performs a compile step for
     * each statement before attempting to start a statement.
     * @return true for compile before start, false for start-only
     */
    public boolean isCompile() {
        return compile;
    }

    /**
     * Set this indicator to true (the default) to indicate that the deploy operation first performs a compile step for
     * each statement before attempting to start a statement.
     * @param compile true for compile before start, false for start-only
     */
    public void setCompile(boolean compile) {
        this.compile = compile;
    }

    /**
     * Returns true (the default) to indicate that the first statement to fail starting will
     * fail the complete module deployment, or set to false to indicate that the operation should attempt
     * to start all statements regardless of any failures.
     * @return indicator
     */
    public boolean isFailFast()
    {
        return failFast;
    }

    /**
     * Set to true (the default) to indicate that the first statement to fail starting will
     * fail the complete module deployment, or set to false to indicate that the operation should attempt
     * to start all statements regardless of any failures.
     * @param failFast indicator
     */
    public void setFailFast(boolean failFast)
    {
        this.failFast = failFast;
    }

    /**
     * Returns true (the default) to indicate that the engine destroys any started statement when
     * a subsequent statement fails to start, or false if the engine should leave any started statement
     * as-is even when exceptions occur for one or more statements.
     * @return indicator
     */
    public boolean isRollbackOnFail()
    {
        return rollbackOnFail;
    }

    /**
     * Set this indicator to true (the default) to indicate that the engine destroys any started statement when
     * a subsequent statement fails to start, or false if the engine should leave any started statement
     * as-is even when exceptions occur for one or more statements.
     * @param rollbackOnFail indicator
     */
    public void setRollbackOnFail(boolean rollbackOnFail)
    {
        this.rollbackOnFail = rollbackOnFail;
    }

    /**
     * Returns true to indicate to compile only and not start any statements, or false (the default) to
     * indicate that statements are started as part of the deploy.
     * @return indicator
     */
    public boolean isCompileOnly()
    {
        return compileOnly;
    }

    /**
     * Set this indicator to true to indicate to compile only and not start any statements, or false (the default) to
     * indicate that statements are started as part of the deploy.
     * @param compileOnly indicator
     */
    public void setCompileOnly(boolean compileOnly)
    {
        this.compileOnly = compileOnly;
    }

    public String getIsolatedServiceProvider()
    {
        return isolatedServiceProvider;
    }

    public void setIsolatedServiceProvider(String isolatedServiceProvider)
    {
        this.isolatedServiceProvider = isolatedServiceProvider;
    }

    public boolean isValidateOnly()
    {
        return validateOnly;
    }

    public void setValidateOnly(boolean validateOnly)
    {
        this.validateOnly = validateOnly;
    }
}
