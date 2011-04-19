package com.espertech.esper.epl.expression;

import com.espertech.esper.util.AuditPath;
import com.espertech.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;

public class ExprEvaluatorProxy implements java.lang.reflect.InvocationHandler {

    private static final Log auditLog = LogFactory.getLog(AuditPath.AUDIT_LOG);
    private static Method target = JavaClassHelper.getMethodByName(ExprEvaluator.class, "evaluate");

    private final String statementName;
    private final String expressionToString;
    private final ExprEvaluator evaluator;

    public static Object newInstance(String statementName, String expressionToString, ExprEvaluator evaluator) {
        return java.lang.reflect.Proxy.newProxyInstance(
                evaluator.getClass().getClassLoader(),
                JavaClassHelper.getSuperInterfaces(evaluator.getClass()),
                new ExprEvaluatorProxy(statementName, expressionToString, evaluator));
    }

    public ExprEvaluatorProxy(String statementName, String expressionToString, ExprEvaluator evaluator) {
        this.statementName = statementName;
        this.expressionToString = expressionToString;
        this.evaluator = evaluator;
    }

    public Object invoke(Object proxy, Method m, Object[] args)
            throws Throwable {

        if (!m.equals(target)) {
            return m.invoke(evaluator, args);
        }

        Object result = m.invoke(evaluator, args);
        if (auditLog.isInfoEnabled()) {
            auditLog.info("Statement " + statementName + " expression " + expressionToString + " result " + result);
        }
        return result;
    }
}

