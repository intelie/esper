package com.espertech.esper.epl.spec;

import com.espertech.esper.util.MetaDefItem;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.named.NamedWindowService;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.pattern.PatternObjectResolutionService;
import com.espertech.esper.schedule.TimeProvider;

import java.util.List;

/**
 * Specification object for historical data poll via database SQL statement.
 */
public class MethodStreamSpec extends StreamSpecBase implements StreamSpecRaw, StreamSpecCompiled, MetaDefItem
{
    private String ident;
    private String className;
    private String methodName;
    private List<ExprNode> expressions;

    /**
     * Ctor.
     * @param optionalStreamName is the stream name or null if none defined
     * @param viewSpecs is an list of view specifications
     * @param ident the prefix in the clause
     * @param className the class name
     * @param methodName the method name
     * @param expressions the parameter expressions
     */
    public MethodStreamSpec(String optionalStreamName, List<ViewSpec> viewSpecs, String ident, String className, String methodName, List<ExprNode> expressions)
    {
        super(optionalStreamName, viewSpecs, false);
        this.ident = ident;
        this.className = className;
        this.methodName = methodName;
        this.expressions = expressions;
    }

    /**
     * Returns the prefix (method) for the method invocation syntax.
     * @return identifier
     */
    public String getIdent()
    {
        return ident;
    }

    /**
     * Returns the class name.
     * @return class name
     */
    public String getClassName()
    {
        return className;
    }

    /**
     * Returns the method name.
     * @return method name
     */
    public String getMethodName()
    {
        return methodName;
    }

    /**
     * Returns the parameter expressions.
     * @return parameter expressions
     */
    public List<ExprNode> getExpressions()
    {
        return expressions;
    }

    public StreamSpecCompiled compile(EventAdapterService eventAdapterService, MethodResolutionService methodResolutionService, PatternObjectResolutionService patternObjectResolutionService, TimeProvider timeProvider, NamedWindowService namedWindowService, VariableService variableService) throws ExprValidationException
    {
        if (!ident.equals("method"))
        {
            throw new ExprValidationException("Expecting keyword 'method', found '" + ident + "'");
        }
        if (methodName == null)
        {
            throw new ExprValidationException("No method name specified for method-based join");
        }
        return this;
    }
}
