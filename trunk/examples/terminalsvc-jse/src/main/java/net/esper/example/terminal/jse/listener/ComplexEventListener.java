package net.esper.example.terminal.jse.listener;

/**
 * A generic interface that decorelates EPA from complex event processing
 * <p/>
 * In the JEE implementation this helps encapsulating JMS logic.
 */
public interface ComplexEventListener {

    public void onComplexEvent(String event);
}
