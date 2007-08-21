package net.esper.client;

import java.io.Serializable;

/**
 * Provides access to engine configuration defaults for modification.
 */
public class ConfigurationEngineDefaults implements Serializable
{
    private Threading threading;
    private ViewResources viewResources;
    private EventMeta eventMeta;
    private Logging logging;

    /**
     * Ctor.
     */
    protected ConfigurationEngineDefaults()
    {
        threading = new Threading();
        viewResources = new ViewResources();
        eventMeta = new EventMeta();
        logging = new Logging();
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
     * Returns view resources defaults.
     * @return view resources defaults
     */
    public ViewResources getViewResources()
    {
        return viewResources;
    }

    /**
     * Returns event representation default settings.
     * @return event representation default settings
     */
    public EventMeta getEventMeta()
    {
        return eventMeta;
    }

    /**
     * Returns logging settings applicable to the engine, other then Log4J settings.
     * @return logging settings
     */
    public Logging getLogging()
    {
        return logging;
    }

    /**
     * Holds threading settings.
     */
    public static class Threading implements Serializable
    {
        private boolean isListenerDispatchPreserveOrder;
        private long listenerDispatchTimeout;
        private boolean isInsertIntoDispatchPreserveOrder;
        private long internalTimerMsecResolution;
        private boolean internalTimerEnabled;

        /**
         * Ctor - sets up defaults.
         */
        protected Threading()
        {
            listenerDispatchTimeout = 1000;
            isListenerDispatchPreserveOrder = true;
            isInsertIntoDispatchPreserveOrder = true;
            internalTimerEnabled = true;
            internalTimerMsecResolution = 100;
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

        /**
         * Sets the use of internal timer.
         * <p>
         * By setting internal timer to true (the default) the engine starts the internal timer thread
         * and relies on internal timer events to supply the time.
         * <p>
         * By setting internal timer to false the engine does not start the internal timer thread
         * and relies on external application-supplied timer events to supply the time.
         * @param internalTimerEnabled is true for internal timer enabled, or false if the application supplies timer events
         */
        public void setInternalTimerEnabled(boolean internalTimerEnabled)
        {
            this.internalTimerEnabled = internalTimerEnabled;
        }

        /**
         * Returns true if internal timer is enabled (the default), or false for internal timer disabled.
         * @return true for internal timer enabled, false for internal timer disabled
         */
        public boolean isInternalTimerEnabled()
        {
            return internalTimerEnabled;
        }

        /**
         * Returns the millisecond resolutuion of the internal timer thread.
         * @return number of msec between timer processing intervals 
         */
        public long getInternalTimerMsecResolution()
        {
            return internalTimerMsecResolution;
        }

        /**
         * Sets the length of the interval (resolution) of the timer thread.
         * @param internalTimerMsecResolution is the millisecond interval length
         */
        public void setInternalTimerMsecResolution(long internalTimerMsecResolution)
        {
            this.internalTimerMsecResolution = internalTimerMsecResolution;
        }
    }

    /**
     * Holds view resources settings.
     */
    public static class ViewResources implements Serializable
    {
        private boolean shareViews;

        /**
         * Ctor - sets up defaults.
         */
        protected ViewResources()
        {
            shareViews = true;
        }

        /**
         * Returns true to indicate the engine shares view resources between statements, or false
         * to indicate the engine does not share view resources between statements.
         * @return indicator whether view resources are shared between statements if
         * statements share same-views and the engine sees opportunity to reuse an existing view.
         */
        public boolean isShareViews()
        {
            return shareViews;
        }

        /**
         * Set the flag to instruct the engine whether to share view resources between
         * statements for not
         * @param shareViews is true to share view resources between statements, or false to not share view
         * resources between statements declaring same-views
         */
        public void setShareViews(boolean shareViews)
        {
            this.shareViews = shareViews;
        }
    }

    /**
     * Event representation metadata.
     */
    public static class EventMeta implements Serializable
    {
        private Configuration.PropertyResolutionStyle classPropertyResolutionStyle;

        /**
         * Ctor.
         */
        public EventMeta()
        {
            this.classPropertyResolutionStyle = Configuration.PropertyResolutionStyle.getDefault();
        }

        /**
         * Returns the property resolution style to use for resolving property names
         * of Java classes.
         * @return style of property resolution
         */
        public Configuration.PropertyResolutionStyle getClassPropertyResolutionStyle()
        {
            return classPropertyResolutionStyle;
        }

        /**
         * Sets the property resolution style to use for resolving property names
         * of Java classes.
         * @param classPropertyResolutionStyle style of property resolution
         */
        public void setClassPropertyResolutionStyle(Configuration.PropertyResolutionStyle classPropertyResolutionStyle)
        {
            this.classPropertyResolutionStyle = classPropertyResolutionStyle;
        }
    }

    /**
     * Holds view logging settings other then the Apache commons or Log4J settings.
     */
    public static class Logging implements Serializable
    {
        private boolean enableExecutionDebug;

        /**
         * Ctor - sets up defaults.
         */
        protected Logging()
        {
            enableExecutionDebug = false;
        }

        /**
         * Returns true if execution path debug logging is enabled.
         * <p>
         * Only if this flag is set to true, in addition to LOG4J settings set to DEBUG, does an engine instance,
         * produce debug out. 
         * @return true if debug logging through Log4j is enabled for any event processing execution paths
         */
        public boolean isEnableExecutionDebug()
        {
            return enableExecutionDebug;
        }

        /**
         * Set the debug flag for debugging the execution path, in which case the engine logs
         * to Log4j in debug-level during execution.
         * @param enableExecutionDebug false to disable debug logging in the execution path, true to enable
         */
        public void setEnableExecutionDebug(boolean enableExecutionDebug)
        {
            this.enableExecutionDebug = enableExecutionDebug;
        }
    }
}
