package net.esper.core;

import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.MethodResolutionServiceImpl;
import net.esper.eql.join.JoinSetComposerFactoryImpl;
import net.esper.eql.spec.PluggableObjectCollection;
import net.esper.pattern.*;
import net.esper.pattern.PatternObjectHelper;
import net.esper.schedule.ScheduleBucket;
import net.esper.util.ManagedLock;
import net.esper.view.StatementStopServiceImpl;
import net.esper.view.ViewEnumHelper;
import net.esper.view.ViewResolutionService;
import net.esper.view.ViewResolutionServiceImpl;

/**
 * Default implementation for making a statement-specific context class.
 */
public class StatementContextFactoryDefault implements StatementContextFactory
{
    private PluggableObjectCollection viewClasses;
    private PluggableObjectCollection patternObjectClasses;

    /**
     * Ctor.
     * @param viewPlugIns is the view plug-in object descriptions
     * @param plugInPatternObj is the pattern plug-in object descriptions
     */
    public StatementContextFactoryDefault(PluggableObjectCollection viewPlugIns, PluggableObjectCollection plugInPatternObj)
    {
        viewClasses = new PluggableObjectCollection();
        viewClasses.addObjects(viewPlugIns);
        viewClasses.addObjects(ViewEnumHelper.getBuiltinViews());

        patternObjectClasses = new PluggableObjectCollection();
        patternObjectClasses.addObjects(plugInPatternObj);
        patternObjectClasses.addObjects(PatternObjectHelper.getBuiltinPatternObjects());
    }

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

        ViewResolutionService viewResolutionService = new ViewResolutionServiceImpl(viewClasses);
        PatternObjectResolutionService patternResolutionService = new PatternObjectResolutionServiceImpl(patternObjectClasses);

        // Create statement context
        return new StatementContext(engineServices.getEngineURI(),
                engineServices.getEngineInstanceId(),
                statementId,
                statementName,
                expression,
                engineServices.getSchedulingService(),
                scheduleBucket,
                engineServices.getEventAdapterService(),
                epStatementHandle,
                viewResolutionService,
                patternResolutionService,
                engineServices.getExtensionServicesContext(),
                new StatementStopServiceImpl(),
                methodResolutionService,
                patternContextFactory,
                engineServices.getFilterService(),
                new JoinSetComposerFactoryImpl(),
                engineServices.getOutputConditionFactory());
    }
}
