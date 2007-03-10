package net.esper.core;

import net.esper.filter.FilterHandleCallback;
import net.esper.filter.FilterHandle;
import net.esper.schedule.ScheduleHandle;
import net.esper.schedule.ScheduleHandleCallback;
import net.esper.util.MetaDefItem;

/**
 * Statement resource handle and callback for use with {@link net.esper.filter.FilterService} and
 * {@link net.esper.schedule.SchedulingService}.
 * <p>
 * Links the statement handle identifying a statement and containing the statement resource lock,
 * with the actual callback to invoke for a statement together.
 */
public class EPStatementHandleCallback implements FilterHandle, ScheduleHandle, MetaDefItem
{
    private EPStatementHandle epStatementHandle;
    private FilterHandleCallback filterCallback;
    private ScheduleHandleCallback scheduleCallback;

    /**
     * Ctor.
     * @param epStatementHandle is a statement handle
     * @param callback is a filter callback
     */
    public EPStatementHandleCallback(EPStatementHandle epStatementHandle, FilterHandleCallback callback)
    {
        this.epStatementHandle = epStatementHandle;
        this.filterCallback = callback;
    }

    /**
     * Ctor.
     * @param epStatementHandle is a statement handle
     * @param callback is a schedule callback
     */
    public EPStatementHandleCallback(EPStatementHandle epStatementHandle, ScheduleHandleCallback callback)
    {
        this.epStatementHandle = epStatementHandle;
        this.scheduleCallback = callback;
    }

    /**
     * Returns the statement handle.
     * @return handle containing a statement resource lock
     */
    public EPStatementHandle getEpStatementHandle()
    {
        return epStatementHandle;
    }

    /**
     * Returns the statement filter callback, or null if this is a schedule callback handle.
     * @return filter callback
     */
    public FilterHandleCallback getFilterCallback()
    {
        return filterCallback;
    }

    /**
     * Returns the statement schedule callback, or null if this is a filter callback handle.
     * @return schedule callback
     */
    public ScheduleHandleCallback getScheduleCallback()
    {
        return scheduleCallback;
    }
}
