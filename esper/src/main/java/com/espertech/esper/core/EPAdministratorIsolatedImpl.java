/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPServiceIsolationException;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.epl.spec.SelectClauseStreamSelectorEnum;
import com.espertech.esper.epl.spec.StatementSpecRaw;
import com.espertech.esper.filter.FilterSet;
import com.espertech.esper.schedule.ScheduleSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation for the admin interface.
 */
public class EPAdministratorIsolatedImpl implements EPAdministratorIsolatedSPI
{
    private static Log log = LogFactory.getLog(EPAdministratorIsolatedImpl.class);

    private final String name;
    private final EPIsolationUnitServices services;
    private final EPServicesContext unisolatedServices;
    private final EPRuntimeIsolatedImpl isolatedRuntime;
    private final Set<String> statementNames = Collections.synchronizedSet(new HashSet<String>());

    /**
     * Ctor.
     * @param name name of the isolated service
     * @param services isolated services
     * @param unisolatedServices engine services
     * @param isolatedRuntime the runtime for this isolated service
     */
    public EPAdministratorIsolatedImpl(String name, EPIsolationUnitServices services, EPServicesContext unisolatedServices, EPRuntimeIsolatedImpl isolatedRuntime)
    {
        this.name = name;
        this.services = services;
        this.unisolatedServices = unisolatedServices;
        this.isolatedRuntime = isolatedRuntime;
    }

    public EPStatement createEPL(String eplStatement, String statementName, Object userObject) throws EPException
    {
        SelectClauseStreamSelectorEnum defaultStreamSelector = SelectClauseStreamSelectorEnum.mapFromSODA(unisolatedServices.getConfigSnapshot().getEngineDefaults().getStreamSelection().getDefaultStreamSelector());
        StatementSpecRaw statementSpec = EPAdministratorImpl.compileEPL(eplStatement, statementName, unisolatedServices, defaultStreamSelector);
        EPStatement statement = unisolatedServices.getStatementLifecycleSvc().createAndStart(statementSpec, eplStatement, false, statementName, userObject, services);
        EPStatementSPI stmtSpi = (EPStatementSPI) statement;
        stmtSpi.getStatementContext().setInternalEventEngineRouteDest(isolatedRuntime);
        stmtSpi.setServiceIsolated(name);
        statementNames.add(name);
        return statement;
    }

    public String[] getStatementNames()
    {
        return statementNames.toArray(new String[statementNames.size()]);
    }

    public void addStatement(String name)
    {
        statementNames.add(name);   // for recovery
    }

    public void addStatement(EPStatement stmt) {

        addStatement(new EPStatement[] {stmt});
    }

    public void addStatement(EPStatement stmt[]) {

        unisolatedServices.getEventProcessingRWLock().acquireWriteLock();

        try
        {
            long fromTime = unisolatedServices.getSchedulingService().getTime();
            long toTime = services.getSchedulingService().getTime();
            long delta = toTime - fromTime;

            // perform checking
            Set<String> statementIds = new HashSet<String>();
            for (EPStatement aStmt : stmt)
            {
                if (aStmt == null)
                {
                    throw new EPServiceIsolationException("Illegal argument, a null value was provided in the statement list");
                }
                EPStatementSPI stmtSpi = (EPStatementSPI) aStmt;
                statementIds.add(stmtSpi.getStatementId());

                if (aStmt.getServiceIsolated() != null)
                {
                    throw new EPServiceIsolationException("Statement named '" + aStmt.getName() + "' already in service isolation under '" + stmtSpi.getServiceIsolated() + "'");
                }
            }

            // start txn
            unisolatedServices.getStatementIsolationService().beginIsolatingStatements(name, services.getUnitId(), stmt);

            FilterSet filters = unisolatedServices.getFilterService().take(statementIds);
            ScheduleSet schedules = unisolatedServices.getSchedulingService().take(statementIds);

            services.getFilterService().apply(filters);
            services.getSchedulingService().apply(schedules);

            for (EPStatement aStmt : stmt)
            {
                EPStatementSPI stmtSpi = (EPStatementSPI) aStmt;
                stmtSpi.getStatementContext().setFilterService(services.getFilterService());
                stmtSpi.getStatementContext().setSchedulingService(services.getSchedulingService());
                stmtSpi.getStatementContext().setInternalEventEngineRouteDest(isolatedRuntime);
                stmtSpi.getStatementContext().getScheduleAdjustmentService().adjust(delta);
                statementNames.add(stmtSpi.getName());
                stmtSpi.setServiceIsolated(name);
            }

            // commit txn
            unisolatedServices.getStatementIsolationService().commitIsolatingStatements(name, services.getUnitId(), stmt);
        }
        catch (EPServiceIsolationException ex)
        {
            throw ex;
        }
        catch (RuntimeException ex)
        {
            unisolatedServices.getStatementIsolationService().rollbackIsolatingStatements(name, services.getUnitId(), stmt);

            String message = "Unexpected exception taking statements: " + ex.getMessage();
            log.error(message, ex);
            throw new EPException(message, ex);
        }
        finally
        {
            unisolatedServices.getEventProcessingRWLock().releaseWriteLock();
        }
    }

    public void removeStatement(EPStatement stmt)
    {
        removeStatement(new EPStatement[] {stmt});
    }

    public void removeStatement(EPStatement[] stmt) {

        unisolatedServices.getEventProcessingRWLock().acquireWriteLock();

        try
        {
            long fromTime = services.getSchedulingService().getTime();
            long toTime = unisolatedServices.getSchedulingService().getTime();
            long delta = toTime - fromTime;

            Set<String> statementIds = new HashSet<String>();
            for (EPStatement aStmt : stmt)
            {
                if (aStmt == null)
                {
                    throw new EPServiceIsolationException("Illegal argument, a null value was provided in the statement list");
                }

                EPStatementSPI stmtSpi = (EPStatementSPI) aStmt;
                statementIds.add(stmtSpi.getStatementId());

                if (aStmt.getServiceIsolated() == null)
                {
                    throw new EPServiceIsolationException("Statement named '" + aStmt.getName() + "' is not currently in service isolation");
                }
                if (!aStmt.getServiceIsolated().equals(name))
                {
                    throw new EPServiceIsolationException("Statement named '" + aStmt.getName() + "' not in this service isolation but under service isolation '" + aStmt.getName() + "'");
                }
            }

            // start txn
            unisolatedServices.getStatementIsolationService().beginUnisolatingStatements(name, services.getUnitId(), stmt);

            FilterSet filters = services.getFilterService().take(statementIds);
            ScheduleSet schedules = services.getSchedulingService().take(statementIds);

            unisolatedServices.getFilterService().apply(filters);
            unisolatedServices.getSchedulingService().apply(schedules);

            for (EPStatement aStmt : stmt)
            {
                EPStatementSPI stmtSpi = (EPStatementSPI) aStmt;
                stmtSpi.getStatementContext().setFilterService(unisolatedServices.getFilterService());
                stmtSpi.getStatementContext().setSchedulingService(unisolatedServices.getSchedulingService());
                stmtSpi.getStatementContext().setInternalEventEngineRouteDest(unisolatedServices.getInternalEventEngineRouteDest());
                stmtSpi.getStatementContext().getScheduleAdjustmentService().adjust(delta);
                statementNames.remove(stmtSpi.getName());
                stmtSpi.setServiceIsolated(null);
            }

            // commit txn
            unisolatedServices.getStatementIsolationService().commitUnisolatingStatements(name, services.getUnitId(), stmt);
        }
        catch (EPServiceIsolationException ex)
        {
            throw ex;
        }
        catch (RuntimeException ex)
        {
            unisolatedServices.getStatementIsolationService().rollbackUnisolatingStatements(name, services.getUnitId(), stmt);

            String message = "Unexpected exception taking statements: " + ex.getMessage();
            log.error(message, ex);
            throw new EPException(message, ex);
        }
        finally
        {
            unisolatedServices.getEventProcessingRWLock().releaseWriteLock();
        }
    }
}