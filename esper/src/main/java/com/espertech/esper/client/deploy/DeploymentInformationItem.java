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

package com.espertech.esper.client.deploy;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * Statement level information for deployed modules.
 */
public class DeploymentInformationItem implements Serializable
{
    private static final long serialVersionUID = 6877181100706738876L;
    private String statementName;
    private String expression;

    /**
     * Ctor.
     * @param statementName name of statement
     * @param expression EPL text
     */
    public DeploymentInformationItem(String statementName, String expression) {
        this.statementName = statementName;
        this.expression = expression;
    }

    /**
     * Returns statement name.
     * @return name
     */
    public String getStatementName() {
        return statementName;
    }

    /**
     * Returns EPL text.
     * @return expression
     */
    public String getExpression() {
        return expression;
    }

    public String toString() {
        return "name '" + statementName + "' " +
               " expression " + expression;
    }
}