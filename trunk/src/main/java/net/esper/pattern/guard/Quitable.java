package net.esper.pattern.guard;

/**
 * Receiver for quit events for use by guards.
 */
public interface Quitable
{
    /**
     * Indicate guard quitted.
     */
    public void guardQuit();
}
