/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.client.ConfigurationPlugInSingleRowFunction;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalTypeInfo;
import com.espertech.esper.epl.enummethod.dot.ExprDotStaticMethodWrap;
import com.espertech.esper.epl.enummethod.dot.ExprDotStaticMethodWrapFactory;
import com.espertech.esper.epl.enummethod.dot.ExprLambdaGoesNode;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents an invocation of a plug-in single-row function  in the expression tree.
 */
public class ExprPlugInSingleRowNode extends ExprNodeBase implements ExprNodeInnerNodeProvider
{
    private static final long serialVersionUID = 2485214890449563098L;
    private static final Log log = LogFactory.getLog(ExprPlugInSingleRowNode.class);

    private final String functionName;
    private final Class clazz;
    private final List<ExprChainedSpec> chainSpec;
    private final ConfigurationPlugInSingleRowFunction.ValueCache valueCache;

    private transient boolean isReturnsConstantResult;
    private transient ExprEvaluator evaluator;

    /**
	 * Ctor.
	 * @param chainSpec - the class and name of the method that this node will invoke plus parameters
	 */
	public ExprPlugInSingleRowNode(String functionName, Class clazz, List<ExprChainedSpec> chainSpec, ConfigurationPlugInSingleRowFunction.ValueCache valueCache)
	{
        this.functionName = functionName;
        this.clazz = clazz;
		this.chainSpec = chainSpec;
        this.valueCache = valueCache;
    }

    public ExprEvaluator getExprEvaluator() {
        return evaluator;
    }

    public List<ExprChainedSpec> getChainSpec()
    {
        return chainSpec;
    }

    @Override
    public boolean isConstantResult()
    {
        return isReturnsConstantResult;
    }

    public String getFunctionName()
    {
        return functionName;
    }

    public Class getClazz()
    {
        return clazz;
    }

    public String toExpressionString()
	{
        StringBuilder buffer = new StringBuilder();
		buffer.append(functionName);
        ExprNodeUtility.toExpressionString(chainSpec, buffer, true);
		return buffer.toString();
	}

	public boolean equalsNode(ExprNode node)
	{
		if(!(node instanceof ExprPlugInSingleRowNode))
		{
			return false;
		}

        ExprPlugInSingleRowNode other = (ExprPlugInSingleRowNode) node;
        if (other.chainSpec.size() != this.chainSpec.size()) {
            return false;
        }
        for (int i = 0; i < chainSpec.size(); i++) {
            if (!(this.chainSpec.get(i).equals(other.chainSpec.get(i)))) {
                return false;
            }
        }
        return other.clazz == this.clazz && other.functionName.endsWith(this.functionName);
	}

	public void validate(ExprValidationContext validationContext) throws ExprValidationException
	{
        ExprNodeUtility.validate(chainSpec, validationContext);

        // get first chain item
        List<ExprChainedSpec> chainList = new ArrayList<ExprChainedSpec>(chainSpec);
        ExprChainedSpec firstItem = chainList.remove(0);

		// Get the types of the parameters for the first invocation
		Class[] paramTypes = new Class[firstItem.getParameters().size()];
        ExprEvaluator[] childEvals = new ExprEvaluator[firstItem.getParameters().size()];
		int count = 0;

        boolean allConstants = true;
        for(ExprNode childNode : firstItem.getParameters())
		{
            if (childNode instanceof ExprLambdaGoesNode) {
                throw new ExprValidationException("Unexpected lambda-expression encountered as parameter to UDF or static method");
            }
            ExprEvaluator eval = childNode.getExprEvaluator();
            childEvals[count] = eval;
			paramTypes[count] = eval.getType();
            count++;
            if (!(childNode.isConstantResult()))
            {
                allConstants = false;
            }
        }

        if (valueCache == ConfigurationPlugInSingleRowFunction.ValueCache.DISABLED) {
            isReturnsConstantResult = false;
        }
        else if (valueCache == ConfigurationPlugInSingleRowFunction.ValueCache.CONFIGURED) {
            isReturnsConstantResult = validationContext.getMethodResolutionService().isUdfCache() && allConstants && chainList.isEmpty();
        }
        else if (valueCache == ConfigurationPlugInSingleRowFunction.ValueCache.ENABLED) {
            isReturnsConstantResult = allConstants && chainList.isEmpty();
        }
        else {
            throw new IllegalStateException("Invalid value cache code " + valueCache);
        }

        // Try to resolve the method
        final FastMethod staticMethod;
        Method method;
		try
		{
			method = validationContext.getMethodResolutionService().resolveMethod(clazz.getName(), firstItem.getName(), paramTypes);
			FastClass declaringClass = FastClass.create(Thread.currentThread().getContextClassLoader(), method.getDeclaringClass());
			staticMethod = declaringClass.getMethod(method);
		}
		catch(Exception e)
		{
			throw new ExprValidationException(e.getMessage());
		}

        // this may return a pair of null if there is no lambda or the result cannot be wrapped for lambda-function use
        ExprDotStaticMethodWrap optionalLambdaWrap = ExprDotStaticMethodWrapFactory.make(method, validationContext.getEventAdapterService(), chainList);
        ExprDotEvalTypeInfo typeInfo = optionalLambdaWrap != null ? optionalLambdaWrap.getTypeInfo() : ExprDotEvalTypeInfo.scalarOrUnderlying(method.getReturnType());

        ExprDotEval[] eval = ExprDotNodeUtility.getChainEvaluators(typeInfo, chainList, validationContext, false, new ExprDotNodeFilterAnalyzerInputStatic()).getChainWithUnpack();
        evaluator = new ExprDotEvalStaticMethod(validationContext.getStatementName(), clazz.getName(), staticMethod, childEvals, allConstants, optionalLambdaWrap, eval);

        // If caching the result, evaluate now and return the result.
        if (isReturnsConstantResult) {
            final Object result = evaluator.evaluate(null, true, null);
            evaluator = new ExprEvaluator() {
                public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
                    return result;
                }

                public Class getType() {
                    return staticMethod.getReturnType();
                }

                public Map<String, Object> getEventType() throws ExprValidationException {
                    return null;
                }
            };
        }
	}

    @Override
    public void accept(ExprNodeVisitor visitor) {
        super.accept(visitor);
        ExprNodeUtility.acceptChain(visitor, chainSpec);
    }

    @Override
    public void accept(ExprNodeVisitorWithParent visitor) {
        super.accept(visitor);
        ExprNodeUtility.acceptChain(visitor, chainSpec);
    }

    @Override
    public void acceptChildnodes(ExprNodeVisitorWithParent visitor, ExprNode parent) {
        super.acceptChildnodes(visitor, parent);
        ExprNodeUtility.acceptChain(visitor, chainSpec, this);
    }

    @Override
    public void replaceUnlistedChildNode(ExprNode nodeToReplace, ExprNode newNode) {
        ExprNodeUtility.replaceChainChildNode(nodeToReplace, newNode, chainSpec);
    }

    public List<ExprNode> getAdditionalNodes() {
        return ExprNodeUtility.collectChainParameters(chainSpec);
    }
}
