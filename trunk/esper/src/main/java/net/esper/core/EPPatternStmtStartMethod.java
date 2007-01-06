package net.esper.core;

import net.esper.pattern.PatternStarter;
import net.esper.pattern.PatternStopCallback;
import net.esper.pattern.PatternContext;
import net.esper.pattern.PatternMatchCallback;
import net.esper.schedule.ScheduleBucket;
import net.esper.util.ManagedLock;

/**
 * Method for starting a pattern statement.
 */
public class EPPatternStmtStartMethod
{
    private final EPServicesContext services;
    private final PatternStarter patternStarter;
    private final ScheduleBucket scheduleBucket;
    private final EPStatementHandle epStatementHandle;

    /**
     * Ctor.
     * @param services - services for starting
     * @param patternStarter - pattern start handle
     * @param epStatementHandle is the statements-own handle for use in registering callbacks with services
     */
    public EPPatternStmtStartMethod(EPServicesContext services, PatternStarter patternStarter, EPStatementHandle epStatementHandle)
    {
        this.services = services;
        this.patternStarter = patternStarter;
        this.epStatementHandle = epStatementHandle;

        // Allocate the statement's schedule bucket which stays constant over it's lifetime.
        // The bucket allows callbacks for the same time to be ordered (within and across statements) and thus deterministic.
        scheduleBucket = services.getSchedulingService().allocateBucket();
    }

    /**
     * Start pattern.
     * @param matchCallback - callback by pattern when matches are found
     * @return stop method used for stopping pattern
     */
    public EPStatementStopMethod start(PatternMatchCallback matchCallback)
    {
        PatternContext context = new PatternContext(services.getFilterService(), services.getSchedulingService(), scheduleBucket, services.getEventAdapterService(), epStatementHandle);
        final PatternStopCallback stopCallback = patternStarter.start(matchCallback, context);

        EPStatementStopMethod stopMethod = new EPStatementStopMethod()
        {
            public void stop()
            {
                stopCallback.stop();
            }
        };

        return stopMethod;
    }

}
