package net.esper.core;

import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.MethodResolutionServiceImpl;
import net.esper.pattern.PatternContextFactory;
import net.esper.pattern.PatternContextFactoryDefault;
import net.esper.schedule.ScheduleBucket;
import net.esper.util.ManagedLock;
import net.esper.view.StatementStopServiceImpl;

/**
 * Default implementation for making a statement-specific context class.
 */
public class StatementContextFactoryDefault implements StatementContextFactory
{
    public StatementContext makeContext(String statementId,
                                    String statementName,
                                    String expression,
                                    EPServicesContext engineServices)
    {
        // Allocate the statement's schedule bucket which stays constant over it's lifetime.
        // The bucket allows callbacks for the same time to be ordered (within and across statements) and thus deterministic.
        ScheduleBucket scheduleBucket = engineServices.getSchedulingService().allocateBucket();

        // Create a lock for the statement
        ManagedLock statementResourceLock = engineServices.getStatementLockFactory().getStatementLock(statementName, expression);
        EPStatementHandle epStatementHandle = new EPStatementHandle(statementId, statementResourceLock, expression);

        MethodResolutionService methodResolutionService = new MethodResolutionServiceImpl(engineServices.getEngineImportService());

        PatternContextFactory patternContextFactory = new PatternContextFactoryDefault();

        // Create statement context
        return new StatementContext(engineServices.getEngineURI(),
                engineServices.getEngineInstanceId(), statementId, statementName, expression, engineServices.getSchedulingService(),
                scheduleBucket, engineServices.getEventAdapterService(), epStatementHandle,
                engineServices.getViewResolutionService(), engineServices.getExtensionServicesContext(),
                new StatementStopServiceImpl(), methodResolutionService, patternContextFactory, engineServices.getFilterService());
    }
}
