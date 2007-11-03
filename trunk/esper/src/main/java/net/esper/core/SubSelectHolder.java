package net.esper.core;

import net.esper.view.Viewable;
import net.esper.view.ViewFactoryChain;

/**
 * Entry holding lookup resource references for use by {@link SubSelectStreamCollection}.
 */
public class SubSelectHolder
{
    private int streamNumber;
    private Viewable viewable;
    private ViewFactoryChain viewFactoryChain;

    /**
     * Ctor.
     * @param streamNumber is the lookup stream number
     * @param viewable is the root viewable
     * @param viewFactoryChain is the view chain
     */
    public SubSelectHolder(int streamNumber, Viewable viewable, ViewFactoryChain viewFactoryChain)
    {
        this.streamNumber = streamNumber;
        this.viewable = viewable;
        this.viewFactoryChain = viewFactoryChain;
    }

    /**
     * Returns lookup stream number.
     * @return stream num
     */
    public int getStreamNumber()
    {
        return streamNumber;
    }

    /**
     * Returns the lookup child viewable.
     * @return child-most viewable
     */
    public Viewable getViewable()
    {
        return viewable;
    }

    /**
     * Returns the lookup view factory chain
     * @return view factory chain
     */
    public ViewFactoryChain getViewFactoryChain()
    {
        return viewFactoryChain;
    }
}
