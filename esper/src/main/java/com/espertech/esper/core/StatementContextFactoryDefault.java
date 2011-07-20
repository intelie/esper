/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core;

import com.espertech.esper.client.EPStatementException;
import com.espertech.esper.client.annotation.Audit;
import com.espertech.esper.client.annotation.AuditEnum;
import com.espertech.esper.client.annotation.Drop;
import com.espertech.esper.client.annotation.Priority;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.MethodResolutionServiceImpl;
import com.espertech.esper.epl.join.base.JoinSetComposerFactoryImpl;
import com.espertech.esper.epl.metric.StatementMetricHandle;
import com.espertech.esper.epl.spec.*;
import com.espertech.esper.filter.FilterServiceSPI;
import com.espertech.esper.pattern.*;
import com.espertech.esper.schedule.ScheduleBucket;
import com.espertech.esper.schedule.SchedulingServiceSPI;
import com.espertech.esper.view.StatementStopServiceImpl;
import com.espertech.esper.view.ViewEnumHelper;
import com.espertech.esper.view.ViewResolutionService;
import com.espertech.esper.view.ViewResolutionServiceImpl;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Default implementation for making a statement-specific context class.
 */
public class StatementContextFactoryDefault implements StatementContextFactory
{
    private final PluggableObjectRegistryImpl viewRegistry;
    private final PluggableObjectCollection patternObjectClasses;
    private final Class systemVirtualDWViewFactory;

    /**
     * Ctor.
     * @param viewPlugIns is the view plug-in object descriptions
     * @param plugInPatternObj is the pattern plug-in object descriptions
     */
    public StatementContextFactoryDefault(PluggableObjectCollection viewPlugIns, PluggableObjectCollection plugInPatternObj, Class systemVirtualDWViewFactory)
    {
        viewRegistry = new PluggableObjectRegistryImpl(new PluggableObjectCollection[] {ViewEnumHelper.getBuiltinViews(), viewPlugIns});

        this.systemVirtualDWViewFactory = systemVirtualDWViewFactory;

        patternObjectClasses = new PluggableObjectCollection();
        patternObjectClasses.addObjects(plugInPatternObj);
        patternObjectClasses.addObjects(PatternObjectHelper.getBuiltinPatternObjects());
    }

    public StatementContext makeContext(String statementId,
                                    String statementName,
                                    String expression,
                                    boolean hasVariables,
                                    EPServicesContext engineServices,
                                    Map<String, Object> optAdditionalContext,
                                    OnTriggerDesc optOnTriggerDesc,
                                    CreateWindowDesc optCreateWindowDesc,
                                    boolean isFireAndForget,
                                    Annotation[] annotations,
                                    EPIsolationUnitServices isolationUnitServices,
                                    String optionalCreateNamedWindowName)
    {
        // Allocate the statement's schedule bucket which stays constant over it's lifetime.
        // The bucket allows callbacks for the same time to be ordered (within and across statements) and thus deterministic.
        ScheduleBucket scheduleBucket = engineServices.getSchedulingMgmtService().allocateBucket();

        // Create a lock for the statement
        StatementLock statementResourceLock;

        // For on-delete statements, use the create-named-window statement lock
        if ((optOnTriggerDesc != null) && (optOnTriggerDesc instanceof OnTriggerWindowDesc))
        {
            String windowName = ((OnTriggerWindowDesc) optOnTriggerDesc).getWindowName();
            statementResourceLock = engineServices.getNamedWindowService().getNamedWindowLock(windowName);
            if (statementResourceLock == null)
            {
                throw new EPStatementException("Named window '" + windowName + "' has not been declared", expression);
            }
        }
        // For creating a named window, save the lock for use with on-delete statements
        else if (optCreateWindowDesc != null)
        {
            statementResourceLock = engineServices.getNamedWindowService().getNamedWindowLock(optCreateWindowDesc.getWindowName());
            if (statementResourceLock == null)
            {
                statementResourceLock = engineServices.getStatementLockFactory().getStatementLock(statementName, expression);
                engineServices.getNamedWindowService().addNamedWindowLock(optCreateWindowDesc.getWindowName(), statementResourceLock, statementName);
            }
        }
        else
        {
            statementResourceLock = engineServices.getStatementLockFactory().getStatementLock(statementName, expression);
        }

        StatementMetricHandle stmtMetric = null;
        if (!isFireAndForget)
        {
            stmtMetric = engineServices.getMetricsReportingService().getStatementHandle(statementId, statementName);
        }

        StatementFilterVersion statementFilterVersion = new StatementFilterVersion();

        AnnotationAnalysisResult annotationData = AnnotationAnalysisResult.analyzeAnnotations(annotations);
        EPStatementHandle epStatementHandle = new EPStatementHandle(statementId, statementName, expression, statementResourceLock, expression, hasVariables, stmtMetric, annotationData.getPriority(), annotationData.isPremptive(), statementFilterVersion);

        MethodResolutionService methodResolutionService = new MethodResolutionServiceImpl(engineServices.getEngineImportService(), engineServices.getSchedulingService());

        PatternContextFactory patternContextFactory = new PatternContextFactoryDefault();

        ViewResolutionService viewResolutionService = new ViewResolutionServiceImpl(viewRegistry, optionalCreateNamedWindowName, systemVirtualDWViewFactory);
        PatternObjectResolutionService patternResolutionService = new PatternObjectResolutionServiceImpl(patternObjectClasses);

        SchedulingServiceSPI schedulingService = engineServices.getSchedulingService();
        FilterServiceSPI filterService = engineServices.getFilterService();
        if (isolationUnitServices != null)
        {
            filterService = isolationUnitServices.getFilterService();
            schedulingService = isolationUnitServices.getSchedulingService();
        }

        Audit scheduleAudit = AuditEnum.SCHEDULE.getAudit(annotations);
        if (scheduleAudit != null) {
            schedulingService = new SchedulingServiceAudit(statementName, schedulingService);
        }

        // Create statement context
        return new StatementContext(engineServices.getEngineURI(),
                engineServices.getEngineInstanceId(),
                statementId,
                statementName,
                expression,
                schedulingService,
                scheduleBucket,
                engineServices.getEventAdapterService(),
                epStatementHandle,
                viewResolutionService,
                patternResolutionService,
                null,   // no statement extension context
                new StatementStopServiceImpl(),
                methodResolutionService,
                patternContextFactory,
                filterService,
                new JoinSetComposerFactoryImpl(),
                engineServices.getOutputConditionFactory(),
                engineServices.getNamedWindowService(),
                engineServices.getVariableService(),
                new StatementResultServiceImpl(statementName, engineServices.getStatementLifecycleSvc(), engineServices.getMetricsReportingService(), engineServices.getThreadingService()),
                engineServices.getEngineSettingsService().getPlugInEventTypeResolutionURIs(),
                engineServices.getValueAddEventService(),
                engineServices.getConfigSnapshot(),
                engineServices.getInternalEventEngineRouteDest(),
                engineServices.getMetricsReportingService(),
                engineServices.getViewService(),
                statementFilterVersion,
                annotations,
                engineServices.getExceptionHandlingService(),
                new ExpressionResultCacheService(),
                engineServices.getEventTypeIdGenerator());
    }

    /**
     * Analysis result of analysing annotations for a statement.
     */
    public static class AnnotationAnalysisResult
    {
        private int priority;
        private boolean isPremptive;

        /**
         * Ctor.
         * @param priority priority
         * @param premptive preemptive indicator
         */
        private AnnotationAnalysisResult(int priority, boolean premptive)
        {
            this.priority = priority;
            isPremptive = premptive;
        }

        /**
         * Returns execution priority.
         * @return priority.
         */
        public int getPriority()
        {
            return priority;
        }

        /**
         * Returns preemptive indicator (drop or normal).
         * @return true for drop
         */
        public boolean isPremptive()
        {
            return isPremptive;
        }

        /**
         * Analyze the annotations and return priority and drop settings.
         * @param annotations to analyze
         * @return analysis result
         */
        public static AnnotationAnalysisResult analyzeAnnotations(Annotation[] annotations)
        {
            boolean preemptive = false;
            int priority = 0;
            boolean hasPrioritySetting = false;
            for (Annotation annotation : annotations)
            {
                if (annotation instanceof Priority)
                {
                    priority = ((Priority) annotation).value();
                    hasPrioritySetting = true;
                }
                if (annotation instanceof Drop)
                {
                    preemptive = true;
                }
            }
            if (!hasPrioritySetting && preemptive)
            {
                priority = 1;
            }
            return new AnnotationAnalysisResult(priority, preemptive);
        }
    }
}
