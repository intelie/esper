/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.FragmentEventType;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.PropertyResolutionDescriptor;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.enummethod.dot.*;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.schedule.TimeProvider;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents an Dot-operator expression, for use when "(expression).method(...).method(...)"
 */
public class ExprDotNode extends ExprNode implements ExprNodeInnerNodeProvider
{
    private static final long serialVersionUID = 8105121208330622813L;

    private final List<ExprChainedSpec> chainSpec;
    private final boolean isDuckTyping;
    private final boolean isUDFCache;

    private ExprEvaluator exprEvaluator;
    private boolean isReturnsConstantResult;

    public ExprDotNode(List<ExprChainedSpec> chainSpec, boolean isDuckTyping, boolean isUDFCache)
    {
        this.chainSpec = chainSpec;
        this.isDuckTyping = isDuckTyping;
        this.isUDFCache = isUDFCache;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService, ExprEvaluatorContext exprEvaluatorContext, EventAdapterService eventAdapterService) throws ExprValidationException
    {
        // validate all parameters
        ExprNodeUtility.validate(chainSpec, streamTypeService, methodResolutionService, viewResourceDelegate, timeProvider, variableService, exprEvaluatorContext, eventAdapterService);
        ValidationContext validationContext = new ValidationContext(methodResolutionService, viewResourceDelegate, timeProvider, variableService, exprEvaluatorContext, eventAdapterService);

        // The root node expression may provide the input value:
        //   Such as "window(*).doIt(...)" or "(select * from Window).doIt()" or "prevwindow(sb).doIt(...)", in which case the expression to act on is a child expression
        //
        if (!this.getChildNodes().isEmpty()) {
            // the root expression is the first child node
            ExprNode rootNode = this.getChildNodes().get(0);
            ExprEvaluator rootNodeEvaluator = rootNode.getExprEvaluator();
            Class rootNodeType = rootNodeEvaluator.getType();

            // the root expression may also provide a lambda-function input (Iterator<EventBean>)
            // Determine lambda-type and evaluator if any for root node
            ExprEvaluatorLambda rootLambdaEvaluator = null;
            EventType rootLambdaEventType = null;

            if (rootNodeEvaluator instanceof ExprEvaluatorLambda) {
                rootLambdaEvaluator = (ExprEvaluatorLambda) rootNodeEvaluator;
                rootLambdaEventType = rootLambdaEvaluator.getEventTypeIterator();
                if (rootLambdaEventType == null) {  // not eligible if returning null
                    rootLambdaEvaluator = null;
                }
            }
            else if (rootNode instanceof ExprIdentNode) {
                ExprIdentNode identNode = (ExprIdentNode) rootNode;
                int streamId = identNode.getStreamId();
                EventType streamType = streamTypeService.getEventTypes()[streamId];
                EventPropertyGetter getter = streamType.getGetter(identNode.getResolvedPropertyName());
                FragmentEventType fragmentEventType = streamType.getFragmentType(identNode.getResolvedPropertyName());
                if (getter != null && fragmentEventType != null && fragmentEventType.isIndexed()) {
                    rootLambdaEvaluator = new PropertyExprEvaluatorLambda(identNode.getResolvedPropertyName(), streamId, fragmentEventType.getFragmentType(), getter);
                    rootLambdaEventType = fragmentEventType.getFragmentType();
                }
            }

            UniformPair<ExprDotEval[]> evals = ExprDotNodeUtility.getChainEvaluators(rootNodeType, rootLambdaEventType, chainSpec, validationContext, isDuckTyping, streamTypeService);
            exprEvaluator = new ExprDotEvalRootChild(rootNodeEvaluator, rootLambdaEvaluator, evals.getFirst(), evals.getSecond());
        }
        else {
            // There no root node, in this case the classname or property name is provided as part of the chain.
            // Such as "MyClass.myStaticLib(...)" or "mycollectionproperty.doIt(...)"
            //
            List<ExprChainedSpec> modifiedChain = new ArrayList<ExprChainedSpec>(chainSpec);
            ExprChainedSpec firstItem = modifiedChain.remove(0);

            Pair<PropertyResolutionDescriptor, String> propertyInfoPair = null;
            try {
                if (!streamTypeService.hasPropertyAgnosticType()) {
                    propertyInfoPair = ExprIdentNode.getTypeFromStream(streamTypeService, firstItem.getName());
                }
            }
            catch (ExprValidationPropertyException ex) {
                // not a property
            }

            // If property then treat it as such
            if (propertyInfoPair != null) {
                int streamId = propertyInfoPair.getFirst().getStreamNum();
                EventType streamType = streamTypeService.getEventTypes()[streamId];
                EventPropertyGetter getter = streamType.getGetter(propertyInfoPair.getFirst().getPropertyName());
                FragmentEventType fragmentEventType = streamType.getFragmentType(propertyInfoPair.getFirst().getPropertyName());

                ExprEvaluatorLambda rootLambdaEvaluator = null;
                EventType rootLambdaEventType = null;
                if (getter != null && fragmentEventType != null && fragmentEventType.isIndexed()) {
                    rootLambdaEvaluator = new PropertyExprEvaluatorLambda(propertyInfoPair.getFirst().getPropertyName(), streamId, fragmentEventType.getFragmentType(), getter);
                    rootLambdaEventType = fragmentEventType.getFragmentType();
                }

                ExprEvaluator rootNodeEvaluator = new PropertyExprEvaluatorNonLambda(streamId, getter, propertyInfoPair.getFirst().getPropertyType());
                UniformPair<ExprDotEval[]> evals = ExprDotNodeUtility.getChainEvaluators(propertyInfoPair.getFirst().getPropertyType(), rootLambdaEventType, modifiedChain, validationContext, isDuckTyping, streamTypeService);
                exprEvaluator = new ExprDotEvalRootChild(rootNodeEvaluator, rootLambdaEvaluator, evals.getFirst(), evals.getSecond());
            }
            else {

                // If class then resolve as class
                ExprChainedSpec secondItem = modifiedChain.remove(0);

                // Get the types of the parameters for the first invocation
                Class[] paramTypes = new Class[secondItem.getParameters().size()];
                ExprEvaluator[] childEvals = new ExprEvaluator[secondItem.getParameters().size()];
                int count = 0;

                boolean allConstants = true;
                for(ExprNode childNode : secondItem.getParameters())
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
                boolean isConstantParameters = allConstants && isUDFCache;
                isReturnsConstantResult = isConstantParameters && modifiedChain.isEmpty();

                // Try to resolve the method
                String className = firstItem.getName();
                Method method;
                FastMethod staticMethod;
                try
                {
                    method = methodResolutionService.resolveMethod(firstItem.getName(), secondItem.getName(), paramTypes);
                    FastClass declaringClass = FastClass.create(Thread.currentThread().getContextClassLoader(), method.getDeclaringClass());
                    staticMethod = declaringClass.getMethod(method);
                }
                catch(Exception e)
                {
                    throw new ExprValidationException(e.getMessage());
                }

                // this may return a pair of null if there is no lambda or the result cannot be wrapped for lambda-function use
                ExprDotStaticMethodWrap optionalLambdaWrap = ExprDotStaticMethodWrapFactory.make(method, eventAdapterService, modifiedChain);
                EventType optionalLambdaType = optionalLambdaWrap != null ? optionalLambdaWrap.getEventType() : null;

                UniformPair<ExprDotEval[]> evals = ExprDotNodeUtility.getChainEvaluators(staticMethod.getReturnType(), optionalLambdaType, modifiedChain, validationContext, false, streamTypeService);
                exprEvaluator = new ExprDotEvalStaticMethod(className, staticMethod, childEvals, isConstantParameters, optionalLambdaWrap, evals.getSecond());
            }
        }
    }

    public void accept(ExprNodeVisitor visitor) {
        super.accept(visitor);
        ExprNode.acceptChain(visitor, chainSpec);
    }

    public void accept(ExprNodeVisitorWithParent visitor) {
        super.accept(visitor);
        ExprNode.acceptChain(visitor, chainSpec);
    }

    public void acceptChildnodes(ExprNodeVisitorWithParent visitor, ExprNode parent) {
        super.acceptChildnodes(visitor, parent);
        ExprNode.acceptChain(visitor, chainSpec, this);
    }

    protected void replaceUnlistedChildNode(ExprNode nodeToReplace, ExprNode newNode) {
        ExprNode.replaceChainChildNode(nodeToReplace, newNode, chainSpec);
    }

    public List<ExprChainedSpec> getChainSpec()
    {
        return chainSpec;
    }

    public ExprEvaluator getExprEvaluator()
    {
        return exprEvaluator;
    }

    public boolean isConstantResult()
    {
        return isReturnsConstantResult;
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        if (!this.getChildNodes().isEmpty()) {
            buffer.append('(');
            buffer.append(this.getChildNodes().get(0).toExpressionString());
            buffer.append(")");
        }
        ExprNodeUtility.toExpressionString(chainSpec, buffer, !this.getChildNodes().isEmpty());
		return buffer.toString();
    }

    public Map<String, Object> getEventType() {
        return null;
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprDotNode))
        {
            return false;
        }

        ExprDotNode other = (ExprDotNode) node;
        if (other.chainSpec.size() != this.chainSpec.size()) {
            return false;
        }
        for (int i = 0; i < chainSpec.size(); i++) {
            if (!(this.chainSpec.get(i).equals(other.chainSpec.get(i)))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<ExprNode> getAdditionalNodes() {
        return ExprNodeUtility.collectChainParameters(chainSpec);
    }
}
