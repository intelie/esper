package net.esper.core;

import net.esper.pattern.PatternStarter;
import net.esper.pattern.PatternStopCallback;
import net.esper.pattern.PatternContext;
import net.esper.pattern.PatternMatchCallback;

/**
 * Method for starting a pattern statement.
 */
public class EPPatternStmtStartMethod
{
    private final EPServicesContext services;
    private final PatternStarter patternStarter;

    /**
     * Ctor.
     * @param services - services for starting
     * @param patternStarter - pattern start handle
     */
    public EPPatternStmtStartMethod(EPServicesContext services, PatternStarter patternStarter)
    {
        this.services = services;
        this.patternStarter = patternStarter;
    }

    /**
     * Start pattern.
     * @param matchCallback - callback by pattern when matches are found
     * @return stop method used for stopping pattern
     */
    public EPStatementStopMethod start(PatternMatchCallback matchCallback)
    {
        PatternContext context = new PatternContext(services.getFilterService(), services.getSchedulingService());
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
