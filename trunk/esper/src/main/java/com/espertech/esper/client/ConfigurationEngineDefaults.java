/**************************************************************************************
 * Copyright (C) 2007 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client;

import com.espertech.esper.client.soda.StreamSelector;

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
    private Variables variables;
    private StreamSelection streamSelection;
    private TimeSource timeSource;

    /**
     * Ctor.
     */
    protected ConfigurationEngineDefaults()
    {
        threading = new Threading();
        viewResources = new ViewResources();
        eventMeta = new EventMeta();
        logging = new Logging();
        variables = new Variables();
        streamSelection = new StreamSelection();
        timeSource = new TimeSource();
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
     * Returns engine defaults applicable to variables.
     * @return variable engine defaults
     */
    public Variables getVariables()
    {
        return variables;
    }

    /**
     * Returns engine defaults applicable to streams (insert and remove, insert only or remove only) selected for a statement.
     * @return stream selection defaults
     */
    public StreamSelection getStreamSelection()
    {
        return streamSelection;
    }

    public TimeSource getTimeSource()
    {
        return timeSource;
    }

    /**
     * Holds threading settings.
     */
    public static class Threading implements Serializable
    {
        private boolean isListenerDispatchPreserveOrder;
        private long listenerDispatchTimeout;
        private Locking listenerDispatchLocking;

        private boolean isInsertIntoDispatchPreserveOrder;
        private long insertIntoDispatchTimeout;
        private Locking insertIntoDispatchLocking;

        private long internalTimerMsecResolution;
        private boolean internalTimerEnabled;

        /**
         * Ctor - sets up defaults.
         */
        protected Threading()
        {
            listenerDispatchTimeout = 1000;
            isListenerDispatchPreserveOrder = true;
            listenerDispatchLocking = Locking.SPIN;

            insertIntoDispatchTimeout = 100;
            isInsertIntoDispatchPreserveOrder = true;
            insertIntoDispatchLocking = Locking.SPIN;

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

        /**
         * Returns the number of milliseconds that a thread may maximually be blocking
         * to deliver statement results from a producing statement that employs insert-into
         * to a consuming statement.
         * @return millisecond timeout for order-of-delivery blocking between statements
         */
        public long getInsertIntoDispatchTimeout()
        {
            return insertIntoDispatchTimeout;
        }

        /**
         * Sets the blocking strategy to use when multiple threads deliver results for
         * a single statement to listeners, and the guarantee of order of delivery must be maintained.
         * @param listenerDispatchLocking is the blocking technique
         */
        public void setListenerDispatchLocking(Locking listenerDispatchLocking)
        {
            this.listenerDispatchLocking = listenerDispatchLocking;
        }

        /**
         * Sets the number of milliseconds that a thread may maximually be blocking
         * to deliver statement results from a producing statement that employs insert-into
         * to a consuming statement.
         * @param msecTimeout timeout for order-of-delivery blocking between statements
         */
        public void setInsertIntoDispatchTimeout(long msecTimeout)
        {
            this.insertIntoDispatchTimeout = msecTimeout;
        }

        /**
         * Sets the blocking strategy to use when multiple threads deliver results for
         * a single statement to consuming statements of an insert-into, and the guarantee of order of delivery must be maintained.
         * @param insertIntoDispatchLocking is the blocking technique
         */
        public void setInsertIntoDispatchLocking(Locking insertIntoDispatchLocking)
        {
            this.insertIntoDispatchLocking = insertIntoDispatchLocking;
        }

        /**
         * Returns the blocking strategy to use when multiple threads deliver results for
         * a single statement to listeners, and the guarantee of order of delivery must be maintained.
         * @return is the blocking technique
         */
        public Locking getListenerDispatchLocking()
        {
            return listenerDispatchLocking;
        }

        /**
         * Returns the blocking strategy to use when multiple threads deliver results for
         * a single statement to consuming statements of an insert-into, and the guarantee of order of delivery must be maintained.
         * @return is the blocking technique
         */
        public Locking getInsertIntoDispatchLocking()
        {
            return insertIntoDispatchLocking;
        }

        /**
         * Enumeration of blocking techniques.
         */
        public enum Locking
        {
            /**
             * Spin lock blocking is good for locks held very shortly or generally uncontended locks and
             * is therefore the default.
             */
            SPIN,

            /**
             * Blocking that suspends a thread and notifies a thread to wake up can be
             * more expensive then spin locks.
             */
            SUSPEND
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

    /**
     * Holds variables settings.
     */
    public static class Variables implements Serializable
    {
        private long msecVersionRelease;

        /**
         * Ctor - sets up defaults.
         */
        protected Variables()
        {
            msecVersionRelease = 15000;
        }

        /**
         * Returns the number of milliseconds that a version of a variables is held stable for
         * use by very long-running atomic statement execution.
         * <p>
         * A slow-executing statement such as an SQL join may use variables that, at the time
         * the statement starts to execute, have certain values. The engine guarantees that during
         * statement execution the value of the variables stays the same as long as the statement
         * does not take longer then the given number of milliseconds to execute. If the statement does take longer
         * to execute then the variables release time, the current variables value applies instead.
         * @return millisecond time interval that a variables version is guaranteed to be stable
         * in the context of an atomic statement execution
         */
        public long getMsecVersionRelease()
        {
            return msecVersionRelease;
        }

        /**
         * Sets the number of milliseconds that a version of a variables is held stable for
         * use by very long-running atomic statement execution.
         * @param msecVersionRelease millisecond time interval that a variables version is guaranteed to be stable
         * in the context of an atomic statement execution
         */
        public void setMsecVersionRelease(long msecVersionRelease)
        {
            this.msecVersionRelease = msecVersionRelease;
        }
    }

    /**
     * Holds default settings for stream selection in the select-clause.
     */
    public static class StreamSelection implements Serializable
    {
        private StreamSelector defaultStreamSelector;

        /**
         * Ctor - sets up defaults.
         */
        protected StreamSelection()
        {
            defaultStreamSelector = StreamSelector.ISTREAM_ONLY;
        }

        /**
         * Returns the default stream selector.
         * <p>
         * Statements that select data from streams and that do not use one of the explicit stream
         * selection keywords (istream/rstream/irstream), by default,
         * generate selection results for the insert stream only, and not for the remove stream.
         * <p>
         * This setting can be used to change the default behavior: Use the RSTREAM_ISTREAM_BOTH
         * value to have your statements generate both insert and remove stream results
         * without the use of the "irstream" keyword in the select clause. 
         * @return default stream selector, which is ISTREAM_ONLY unless changed
         */
        public StreamSelector getDefaultStreamSelector()
        {
            return defaultStreamSelector;
        }

        /**
         * Sets the default stream selector.
         * <p>
         * Statements that select data from streams and that do not use one of the explicit stream
         * selection keywords (istream/rstream/irstream), by default,
         * generate selection results for the insert stream only, and not for the remove stream.
         * <p>
         * This setting can be used to change the default behavior: Use the RSTREAM_ISTREAM_BOTH
         * value to have your statements generate both insert and remove stream results
         * without the use of the "irstream" keyword in the select clause.
         * @param defaultStreamSelector default stream selector
         */
        public void setDefaultStreamSelector(StreamSelector defaultStreamSelector)
        {
            this.defaultStreamSelector = defaultStreamSelector;
        }
    }

    public class TimeSource implements Serializable
    {
        private TimeSourceType timeSourceType;

        public TimeSource()
        {
            timeSourceType = TimeSourceType.MILLI;
        }

        public TimeSourceType getTimeSourceType()
        {
            return timeSourceType;
        }

        public void setTimeSourceType(TimeSourceType timeSourceType)
        {
            this.timeSourceType = timeSourceType;
        }
    }

    public enum TimeSourceType
    {
        MILLI,
        NANO;
    }
}
