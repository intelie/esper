package net.esper.client;

/**
 * Provides access to engine configuration defaults for modification.
 */
public class ConfigurationEngineDefaults
{
    private Threading threading;

    /**
     * Ctor.
     */
    protected ConfigurationEngineDefaults()
    {
        threading = new Threading();
    }

    /**
     * Returns threading settings.
     * @return threading settings object
     */
    public Threading getThreading()
    {
        return threading;
    }

    /**
     * Holds threading settings.
     */
    public class Threading
    {
        private boolean isListenerDispatchPreserveOrder;
        private long listenerDispatchTimeout;
        private boolean isInsertIntoDispatchPreserveOrder;

        /**
         * Ctor - sets up defaults.
         */
        protected Threading()
        {
            listenerDispatchTimeout = 1000;
            isListenerDispatchPreserveOrder = true;
            isInsertIntoDispatchPreserveOrder = true;
        }

        /**
         * In multithreaded environments, this setting controls whether dispatches to listeners preserve
         * the ordering in which the statement processes events.
         * @param value is true to preserve ordering, or false if not
         */
        public void setListenerDispatchPreserveOrder(boolean value)
        {
            isListenerDispatchPreserveOrder = value;
        }

        /**
         * In multithreaded environments, this setting controls when dispatches to listeners preserve
         * the ordering the timeout to complete any outstanding dispatches.
         * @param value is the timeout in milliseconds that the engine may spend
         * waiting for a listener dispatch to complete before dispatching further
         * results for the same statement to listeners for that statement
         */
        public void setListenerDispatchTimeout(long value)
        {
            listenerDispatchTimeout = value;
        }

        /**
         * In multithreaded environments, this setting controls whether insert-into streams preserve
         * the order of events inserted into them by one or more statements
         * such that statements that consume other statement's events behave deterministic
         * @param value is true to indicate to preserve order, or false to not preserve order
         */
        public void setInsertIntoDispatchPreserveOrder(boolean value)
        {
            isInsertIntoDispatchPreserveOrder = value;
        }

        /**
         * Returns true to indicate preserve order for dispatch to listeners,
         * or false to indicate not to preserve order
         * @return true or false
         */
        public boolean isListenerDispatchPreserveOrder()
        {
            return isListenerDispatchPreserveOrder;
        }

        /**
         * Returns the timeout in millisecond to wait for listener code to complete
         * before dispatching the next result, if dispatch order is preserved
         * @return listener dispatch timeout 
         */
        public long getListenerDispatchTimeout()
        {
            return listenerDispatchTimeout;
        }

        /**
         * Returns true to indicate preserve order for inter-statement insert-into,
         * or false to indicate not to preserve order
         * @return true or false
         */
        public boolean isInsertIntoDispatchPreserveOrder()
        {
            return isInsertIntoDispatchPreserveOrder;
        }
    }
}
