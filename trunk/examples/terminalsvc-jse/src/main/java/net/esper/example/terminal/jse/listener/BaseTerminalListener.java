package net.esper.example.terminal.jse.listener;

import net.esper.client.UpdateListener;

/**
 * The parent class of our EPA - event processing agents
 * <p/>
 * Our EPA will get triggered based on the ESP/CEP queries registered and can
 * pipe complex composite events to the bound ComplexEventListener
 */
public abstract class BaseTerminalListener implements UpdateListener {

    protected ComplexEventListener complexEventListener;

    public BaseTerminalListener(ComplexEventListener complexEventListener) {
        this.complexEventListener = complexEventListener;
    }

}
