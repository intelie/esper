/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core;

import com.espertech.esper.client.annotation.NoLock;
import com.espertech.esper.epl.annotation.AnnotationUtil;

import java.lang.annotation.Annotation;

/**
 * Provides statement-level locks.
 */
public class StatementLockFactoryImpl implements StatementLockFactory
{
    private final boolean fairlocks;
    private final boolean disableLocking;

    public StatementLockFactoryImpl(boolean fairlocks, boolean disableLocking) {
        this.fairlocks = fairlocks;
        this.disableLocking = disableLocking;
    }

    public StatementLock getStatementLock(String statementName, String expressionText, Annotation[] annotations)
    {
        boolean foundNoLock = AnnotationUtil.findAnnotation(annotations, NoLock.class) != null;
        if (disableLocking || foundNoLock) {
           return new StatementNoLockImpl(statementName);
        }
        return new StatementRWLockImpl(statementName, fairlocks);
    }
}
