package com.espertech.esper.client.hook;

import com.espertech.esper.client.EventBeanFactory;
import com.espertech.esper.client.EventType;
import com.espertech.esper.core.StatementContext;

/**
 * Context for use with virtual data window factory {@link VirtualDataWindowFactory} provides
 * contextual information about the named window and the type of events held,
 * handle for posting insert and remove streams and factory for event bean instances.
 */
public class VirtualDataWindowContext {

    private final StatementContext statementContext;
    private final EventType eventType;
    private final Object[] parameters;
    private final EventBeanFactory eventFactory;
    private final VirtualDataWindowOutStream outputStream;
    private final String namedWindowName;

    /**
     * Ctor.
     * @param statementContext statement services and statement information such as statement name, statement id, EPL expression
     * @param eventType the event type that the named window is declared to hold.
     * @param parameters the parameters passed when declaring the named window, for example "create window ABC.my:vdw("10.0.0.1")" passes one paramater here.
     * @param eventFactory factory for converting row objects to EventBean instances
     * @param outputStream forward the input and output stream received from the update method here
     * @param namedWindowName the name of the named window
     */
    public VirtualDataWindowContext(StatementContext statementContext, EventType eventType, Object[] parameters, EventBeanFactory eventFactory, VirtualDataWindowOutStream outputStream, String namedWindowName) {
        this.statementContext = statementContext;
        this.eventType = eventType;
        this.parameters = parameters;
        this.eventFactory = eventFactory;
        this.outputStream = outputStream;
        this.namedWindowName = namedWindowName;
    }

    /**
     * Returns the statement context which holds statement information (name, expression, id) and statement-level services.
     * @return statement context
     */
    public StatementContext getStatementContext() {
        return statementContext;
    }

    /**
     * Returns the event type of the events held in the virtual data window as per declaration of the named window.
     * @return event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Returns the parameters passed; for example "create window ABC.my:vdw("10.0.0.1")" passes one paramater here.
     * @return parameters
     */
    public Object[] getParameters() {
        return parameters;
    }

    /**
     * Returns the factory for creating instances of EventBean from rows.
     * @return event bean factory
     */
    public EventBeanFactory getEventFactory() {
        return eventFactory;
    }

    /**
     * Returns a handle for use to send insert and remove stream data to consuming statements.
     * <p>
     * Typically use "context.getOutputStream().update(newData, oldData);" in the update method of the virtual data window.
     * @return handle for posting insert and remove stream
     */
    public VirtualDataWindowOutStream getOutputStream() {
        return outputStream;
    }

    /**
     * Returns the name of the named window used in connection with the virtual data window.
     * @return named window
     */
    public String getNamedWindowName() {
        return namedWindowName;
    }
}
