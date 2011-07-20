/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.core;

import com.espertech.esper.client.EPServiceProviderIsolated;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.filter.FilterServiceProvider;
import com.espertech.esper.filter.FilterServiceSPI;
import com.espertech.esper.schedule.SchedulingServiceImpl;
import com.espertech.esper.schedule.SchedulingServiceSPI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service to maintain currently active isoalted service providers for an engine.
 */
public class StatementIsolationServiceImpl implements StatementIsolationService
{
    private static final Log log = LogFactory.getLog(StatementIsolationServiceImpl.class);

    private final Map<String, EPServiceProviderIsolatedImpl> isolatedProviders;
    private EPServicesContext epServicesContext;
    private volatile int currentUnitId = 0;

    /**
     * Ctor.
     */
    public StatementIsolationServiceImpl()
    {
        isolatedProviders = new ConcurrentHashMap<String, EPServiceProviderIsolatedImpl>();
    }

    /**
     * Set the engine service context.
     * @param epServicesContext services context
     */
    public void setEpServicesContext(EPServicesContext epServicesContext)
    {
        this.epServicesContext = epServicesContext;
    }

    public EPServiceProviderIsolated getIsolationUnit(String name, Integer optionalUnitId)
    {
        EPServiceProviderIsolatedImpl serviceProviderIsolated = isolatedProviders.get(name);
        if (serviceProviderIsolated != null)
        {
            return serviceProviderIsolated;
        }

        FilterServiceSPI filterService = FilterServiceProvider.newService();
        SchedulingServiceSPI scheduleService = new SchedulingServiceImpl(epServicesContext.getTimeSource());
        EPIsolationUnitServices services = new EPIsolationUnitServices(name, currentUnitId, filterService, scheduleService);
        serviceProviderIsolated = new EPServiceProviderIsolatedImpl(name, services, epServicesContext, isolatedProviders);
        isolatedProviders.put(name, serviceProviderIsolated);
        return serviceProviderIsolated;
    }

    public void destroy()
    {
        isolatedProviders.clear();        
    }

    public String[] getIsolationUnitNames()
    {
        Set<String> keyset = isolatedProviders.keySet();
        return keyset.toArray(new String[keyset.size()]);        
    }

    public void beginIsolatingStatements(String name, int unitId, EPStatement[] stmt)
    {
        if (log.isInfoEnabled())
        {
            log.info("Begin isolating statements " + print(stmt) + " unit " + name + " id " + unitId);
        }
    }

    public void commitIsolatingStatements(String name, int unitId, EPStatement[] stmt)
    {
        if (log.isInfoEnabled())
        {
            log.info("Completed isolating statements " + print(stmt) + " unit " + name + " id " + unitId);
        }
    }

    public void rollbackIsolatingStatements(String name, int unitId, EPStatement[] stmt)
    {
        if (log.isInfoEnabled())
        {
            log.info("Failed isolating statements " + print(stmt) + " unit " + name + " id " + unitId);
        }
    }

    public void beginUnisolatingStatements(String name, int unitId, EPStatement[] stmt)
    {
        if (log.isInfoEnabled())
        {
            log.info("Begin un-isolating statements " + print(stmt) + " unit " + name + " id " + unitId);
        }
    }

    public void commitUnisolatingStatements(String name, int unitId, EPStatement[] stmt)
    {
        if (log.isInfoEnabled())
        {
            log.info("Completed un-isolating statements " + print(stmt) + " unit " + name + " id " + unitId);
        }
    }

    public void rollbackUnisolatingStatements(String name, int unitId, EPStatement[] stmt)
    {
        if (log.isInfoEnabled())
        {
            log.info("Failed un-isolating statements " + print(stmt) + " unit " + name + " id " + unitId);
        }
    }

    public void newStatement(String stmtId, String stmtName, EPIsolationUnitServices isolatedServices)
    {
        log.info("New statement '" + stmtName + "' unit " + isolatedServices.getName());
    }

    private String print(EPStatement[] stmts)
    {
        StringBuilder buf = new StringBuilder();
        String delimiter = "";
        for (EPStatement stmt : stmts)
        {
            buf.append(delimiter);
            buf.append(stmt.getName());
            delimiter = ", ";
        }
        return buf.toString();
    }
}