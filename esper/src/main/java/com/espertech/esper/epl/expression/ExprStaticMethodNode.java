/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.*;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.schedule.TimeProvider;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Represents an invocation of a static library method in the expression tree.
 */
public class ExprStaticMethodNode extends ExprNode
{
    private static final Log log = LogFactory.getLog(ExprStaticMethodNode.class);

	private final String classOrPropertyName;
	private final String methodName;
    private final boolean isUseCache;

    private ExprEvaluator evaluator;
    private boolean isReturnsConstantResult;
    private static final long serialVersionUID = -2237283743896280252L;

    /**
	 * Ctor.
	 * @param classOrPropertyName - the declaring class for the method that this node will invoke
	 * @param methodName - the name of the method that this node will invoke
     * @param isUseCache - configuration whether to use cache
	 */
	public ExprStaticMethodNode(String classOrPropertyName, String methodName, boolean isUseCache)
	{
		if(classOrPropertyName == null)
		{
			throw new NullPointerException("Class name is null");
		}
		if(methodName == null)
		{
			throw new NullPointerException("Method name is null");
		}

		this.classOrPropertyName = classOrPropertyName;
		this.methodName = methodName;
        this.isUseCache = isUseCache;
    }

    public ExprEvaluator getExprEvaluator()
    {
        return evaluator;
    }

    @Override
    public boolean isConstantResult()
    {
        return isReturnsConstantResult;
    }

    /**
     * Returns the class name.
	 * @return the class that declared the static method
	 */
	public String getClassOrPropertyName() {
		return classOrPropertyName;
	}

	/**
     * Returns the method name.
	 * @return the name of the method
	 */
	public String getMethodName() {
		return methodName;
	}

	public String toExpressionString()
	{
        StringBuilder buffer = new StringBuilder();
		buffer.append(classOrPropertyName);
		buffer.append('.');
		buffer.append(methodName);

		buffer.append('(');
		String appendString = "";
		for(ExprNode child : getChildNodes())
		{
			buffer.append(appendString);
			buffer.append(child.toExpressionString());
			appendString = ", ";
		}
		buffer.append(')');

		return buffer.toString();
	}

	public boolean equalsNode(ExprNode node)
	{
		if(!(node instanceof ExprStaticMethodNode))
		{
			return false;
		}
        ExprStaticMethodNode otherNode = (ExprStaticMethodNode) node;
        return classOrPropertyName.equals(otherNode.classOrPropertyName) && methodName.equals(otherNode.methodName);
	}

	public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService, ExprEvaluatorContext exprEvaluatorContext) throws ExprValidationException
	{
        // See if the class name
        PropertyResolutionDescriptor classPropertyResoltion = null;
        try
        {
            classPropertyResoltion = streamTypeService.resolveByPropertyName(classOrPropertyName);
        }
        catch (StreamTypesException e) {
            // expected, may actually be a class name
        }

        if (classPropertyResoltion != null) {
            evaluator = validatePropertyMethod(methodName, classPropertyResoltion);
            return;
        }

		// Get the types of the childNodes
		List<ExprNode> childNodes = this.getChildNodes();
		Class[] paramTypes = new Class[childNodes.size()];
		int count = 0;
        
        boolean allConstants = true;
        ExprEvaluator[] childEvals = new ExprEvaluator[childNodes.size()];
        for(ExprNode childNode : childNodes)
		{
            ExprEvaluator eval = childNode.getExprEvaluator();
            childEvals[count] = eval;
			paramTypes[count] = eval.getType();
            count++;
            if (!(childNode.isConstantResult()))
            {
                allConstants = false;
            }
        }
        boolean isConstantParameters = allConstants && isUseCache;
        isReturnsConstantResult = isConstantParameters; 

        // Try to resolve the method
		try
		{
			Method method = methodResolutionService.resolveMethod(classOrPropertyName, methodName, paramTypes);
			FastClass declaringClass = FastClass.create(Thread.currentThread().getContextClassLoader(), method.getDeclaringClass());
			FastMethod staticMethod = declaringClass.getMethod(method);
            this.evaluator = new ExprStaticMethodEvalInvoke(classOrPropertyName, staticMethod, childEvals, isConstantParameters);
		}
		catch(Exception e)
		{
			throw new ExprValidationException(e.getMessage());
		}
	}

    private ExprEvaluator validatePropertyMethod(String methodName, PropertyResolutionDescriptor classPropertyResoltion)
            throws ExprValidationException
    {
        // There are two built-in methods:
        //   size() - returns array size
        //   get(index) - returns array index
        if (methodName.toLowerCase().equals("size")) {
            if (classPropertyResoltion.getPropertyType().isArray()) {
                return new ExprIdentEvalSize(classPropertyResoltion.getStreamNum(), classPropertyResoltion.getStreamEventType().getGetter(classPropertyResoltion.getPropertyName()));
            }
        }
        // TODO
        throw new ExprValidationException("TODO");
    }
}
