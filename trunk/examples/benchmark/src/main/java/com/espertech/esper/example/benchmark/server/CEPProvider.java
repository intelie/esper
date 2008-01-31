/**************************************************************************************
 * Copyright (C) 2007 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.benchmark.server;

import com.espertech.esper.client.*;
import com.espertech.esper.event.EventBean;

import com.espertech.esper.example.benchmark.MarketData;

/**
 * A factory and interface to wrap ESP/CEP engine dependency in a single space
 *
 * @author Alexandre Vasseur http://avasseur.blogspot.com
 */
public class CEPProvider {

    public static interface ICEPProvider {

        public void init(int sleepListenerMillis);

        public void registerStatement(String statement, String statementID);

        public void sendEvent(Object event);
    }

    public static ICEPProvider getCEPProvider() {
        String className = System.getProperty("esper.benchmark.provider", EsperCEPProvider.class.getName());
        try {
            Class klass = Class.forName(className);
            return (ICEPProvider) klass.newInstance();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t);
        }
    }

    public static class EsperCEPProvider implements ICEPProvider {

        private EPAdministrator epAdministrator;

        private EPRuntime epRuntime;

        private UpdateListener updateListener;

        public EsperCEPProvider() {
        }

        public void init(final int sleepListenerMillis) {
            Configuration configuration = new Configuration();
            configuration.addEventTypeAlias("Market", MarketData.class);
            EPServiceProvider epService = EPServiceProviderManager.getProvider("benchmark", configuration);
            epAdministrator = epService.getEPAdministrator();
            updateListener = new UpdateListener() {
                public void update(EventBean[] newEvents, EventBean[] oldEvents) {
                    if (newEvents != null) {
                        if (sleepListenerMillis > 0) {
                            try {
                                Thread.sleep(sleepListenerMillis);
                            } catch (InterruptedException ie) {
                                ;
                            }
                        }
                    }
                }
            };
            epRuntime = epService.getEPRuntime();
        }

        public void registerStatement(String statement, String statementID) {
            EPStatement stmt = epAdministrator.createEQL(statement, statementID);
            stmt.addListener(updateListener);
        }

        public void sendEvent(Object event) {
            epRuntime.sendEvent(event);
        }
    }
}
