package net.esper.dispatch;

/**
 * Implementations are executed when the DispatchService receives a dispatch invocation.
 */
public interface Dispatchable
{
    /**
     * Execute dispatch. 
     */
    public void execute();
}
