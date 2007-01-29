package net.esper.eql.core;

import net.esper.view.ViewCapability;

/**
 * Service to expression nodes for indicating view resource requirements.
 */
public interface ViewResourceDelegate
{
    /**
     * Request a view resource.
     * @param streamNumber is the stream number to provide the resource
     * @param requestedCabability describes the view capability required
     * @param resourceCallback for the delegate to supply the resource
     * @return true to indicate the resource can be granted
     */
    public boolean requestCapability(int streamNumber, ViewCapability requestedCabability, ViewResourceCallback resourceCallback);
}
