package com.espertech.esper.client.hook;

/**
 * Factory for {@link VirtualDataWindow}.
 * <p>
 * Register an implementation of this interface with the engine before use:
 * configuration.addPlugInVirtualDataWindow("test", "vdw", SupportVirtualDWFactory.class.getName());
 */
public interface VirtualDataWindowFactory {
    /**
     * Return a virtual data window to handle the specific event type, named window or paramaters
     * as provided in the context.
     * @param context provides contextual information such as event type, named window name and parameters.
     * @return virtual data window
     */
    public VirtualDataWindow create(VirtualDataWindowContext context);
}
