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

package com.espertech.esper.epl.expression;

import com.espertech.esper.util.JavaClassHelper;

import java.lang.reflect.Method;

public class ExprNodeProxy implements java.lang.reflect.InvocationHandler {

    private static Method target = JavaClassHelper.getMethodByName(ExprNode.class, "getExprEvaluator");

    private String statementName;
    private ExprNode exprNode;

    public static Object newInstance(String statementName, ExprNode exprNode) {
        return java.lang.reflect.Proxy.newProxyInstance(
                exprNode.getClass().getClassLoader(),
                JavaClassHelper.getSuperInterfaces(exprNode.getClass()),
                new ExprNodeProxy(statementName, exprNode));
    }

    public ExprNodeProxy(String statementName, ExprNode exprNode) {
        this.statementName = statementName;
        this.exprNode = exprNode;
    }

    public Object invoke(Object proxy, Method m, Object[] args)
            throws Throwable {

        if (!m.equals(target)) {
            return m.invoke(exprNode, args);
        }

        String expressionToString = "undefined";
        try {
            expressionToString = exprNode.toExpressionString();
        }
        catch (RuntimeException ex) {
            // no action
        }

        ExprEvaluator evaluator = (ExprEvaluator) m.invoke(exprNode, args);
        return ExprEvaluatorProxy.newInstance(statementName, expressionToString, evaluator);
    }
}

