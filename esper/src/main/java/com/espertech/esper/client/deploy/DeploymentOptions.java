package com.espertech.esper.client.deploy;

public class DeploymentOptions {

    private boolean compile = true;
    private boolean compileOnly = false;
    private boolean rollbackOnFail = true;
    private boolean failFast = true;

    public boolean isCompile() {
        return compile;
    }

    public void setCompile(boolean compile) {
        this.compile = compile;
    }

    public boolean isFailFast()
    {
        return failFast;
    }

    public void setFailFast(boolean failFast)
    {
        this.failFast = failFast;
    }

    public boolean isRollbackOnFail()
    {
        return rollbackOnFail;
    }

    public void setRollbackOnFail(boolean rollbackOnFail)
    {
        this.rollbackOnFail = rollbackOnFail;
    }

    public boolean isCompileOnly()
    {
        return compileOnly;
    }

    public void setCompileOnly(boolean compileOnly)
    {
        this.compileOnly = compileOnly;
    }
}
