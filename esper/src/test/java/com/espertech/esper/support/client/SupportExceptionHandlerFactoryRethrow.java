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

package com.espertech.esper.support.client;

import com.espertech.esper.client.hook.ExceptionHandler;
import com.espertech.esper.client.hook.ExceptionHandlerContext;
import com.espertech.esper.client.hook.ExceptionHandlerFactory;
import com.espertech.esper.client.hook.ExceptionHandlerFactoryContext;

import java.util.ArrayList;
import java.util.List;

public class SupportExceptionHandlerFactoryRethrow implements ExceptionHandlerFactory {

    @Override
    public ExceptionHandler getHandler(ExceptionHandlerFactoryContext context) {
        return new SupportExceptionHandlerRethrow();
    }

    public static class SupportExceptionHandlerRethrow implements ExceptionHandler {
        public void handle(ExceptionHandlerContext context) {
            throw new RuntimeException("Unexpected exception in statement '" + context.getStatementName() +
                    "': " + context.getThrowable().getMessage(), context.getThrowable());
        }
    }
}
