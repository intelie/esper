/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventPropertyDescriptor;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.FragmentEventType;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.epl.core.PropertyResolutionDescriptor;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.*;
import com.espertech.esper.util.JavaClassHelper;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Represents an Dot-operator expression, for use when "(expression).method(...).method(...)"
 */
public class ExprDotNode extends ExprNodeBase implements ExprNodeInnerNodeProvider
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

    public void validate(ExprValidationContext validationContext) throws ExprValidationException
    {
        // validate all parameters
        ExprNodeUtility.validate(chainSpec, validationContext);

        // determine if there are enumeration method expressions in the chain
        boolean hasEnumerationMethod = false;
        for (ExprChainedSpec chain : chainSpec) {
            if (EnumMethodEnum.isEnumerationMethod(chain.getName())) {
                hasEnumerationMethod = true;
                break;
            }
        }

        // The root node expression may provide the input value:
        //   Such as "window(*).doIt(...)" or "(select * from Window).doIt()" or "prevwindow(sb).doIt(...)", in which case the expression to act on is a child expression
        //
        StreamTypeService streamTypeService = validationContext.getStreamTypeService();
        if (!this.getChildNodes().isEmpty()) {
            // the root expression is the first child node
            ExprNode rootNode = this.getChildNodes().get(0);
            ExprEvaluator rootNodeEvaluator = rootNode.getExprEvaluator();

            // the root expression may also provide a lambda-function input (Iterator<EventBean>)
            // Determine collection-type and evaluator if any for root node
            Pair<ExprEvaluatorEnumeration, ExprDotEvalTypeInfo> enumSrc = getEnumerationSource(rootNode, validationContext.getStreamTypeService(), hasEnumerationMethod);

            ExprDotEvalTypeInfo typeInfo;
            if (enumSrc.getSecond() == null) {
                typeInfo = ExprDotEvalTypeInfo.scalarOrUnderlying(rootNodeEvaluator.getType());    // not a collection type, treat as scalar
            }
            else {
                typeInfo = enumSrc.getSecond();
            }

            UniformPair<ExprDotEval[]> evals = ExprDotNodeUtility.getChainEvaluators(typeInfo, chainSpec, validationContext, isDuckTyping);
            exprEvaluator = new ExprDotEvalRootChild(rootNodeEvaluator, enumSrc.getFirst(), enumSrc.getSecond(), evals.getFirst(), evals.getSecond());
        }
        else {
            // There no root node, in this case the classname or property name is provided as part of the chain.
            // Such as "MyClass.myStaticLib(...)" or "mycollectionproperty.doIt(...)"
            //
            List<ExprChainedSpec> modifiedChain = new ArrayList<ExprChainedSpec>(chainSpec);
            ExprChainedSpec firstItem = modifiedChain.remove(0);

            Pair<PropertyResolutionDescriptor, String> propertyInfoPair = null;
            try {
                if (!streamTypeService.hasPropertyAgnosticType() && (firstItem.isProperty() || firstItem.getParameters().isEmpty())) {
                    propertyInfoPair = ExprIdentNodeImpl.getTypeFromStream(streamTypeService, firstItem.getName());
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

                Pair<ExprEvaluatorEnumeration, ExprDotEvalTypeInfo> propertyEval = getPropertyEnumerationSource(propertyInfoPair.getFirst().getPropertyName(), streamId, streamType, hasEnumerationMethod);

                ExprEvaluator rootNodeEvaluator = new PropertyExprEvaluatorNonLambda(streamId, getter, propertyInfoPair.getFirst().getPropertyType());
                UniformPair<ExprDotEval[]> evals = ExprDotNodeUtility.getChainEvaluators(propertyEval.getSecond(), modifiedChain, validationContext, isDuckTyping);
                exprEvaluator = new ExprDotEvalRootChild(rootNodeEvaluator, propertyEval.getFirst(), propertyEval.getSecond(), evals.getFirst(), evals.getSecond());
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
                        throw new ExprValidationException("Unexpected lambda-expression encountered as parameter to UDF or static method '" + secondItem.getName() + "' and failed to resolve '" + firstItem.getName() + "' as a property");
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

                // Resolve the method - last choice
                String className = firstItem.getName();
                Method method;
                FastMethod staticMethod;
                try
                {
                    method = validationContext.getMethodResolutionService().resolveMethod(firstItem.getName(), secondItem.getName(), paramTypes);
                    FastClass declaringClass = FastClass.create(Thread.currentThread().getContextClassLoader(), method.getDeclaringClass());
                    staticMethod = declaringClass.getMethod(method);
                }
                catch(Exception e)
                {
                    String message = "Failed to resolve '" + firstItem.getName() + "' to a property or class name: " + e.getMessage();
                    throw new ExprValidationException(message, e);
                }

                // this may return a pair of null if there is no lambda or the result cannot be wrapped for lambda-function use
                ExprDotStaticMethodWrap optionalLambdaWrap = ExprDotStaticMethodWrapFactory.make(method, validationContext.getEventAdapterService(), modifiedChain);
                ExprDotEvalTypeInfo typeInfo = optionalLambdaWrap != null ? optionalLambdaWrap.getTypeInfo() : ExprDotEvalTypeInfo.scalarOrUnderlying(method.getReturnType());

                UniformPair<ExprDotEval[]> evals = ExprDotNodeUtility.getChainEvaluators(typeInfo, modifiedChain, validationContext, false);
                exprEvaluator = new ExprDotEvalStaticMethod(className, staticMethod, childEvals, isConstantParameters, optionalLambdaWrap, evals.getSecond());
            }
        }
    }

    public void accept(ExprNodeVisitor visitor) {
        super.accept(visitor);
        ExprNodeUtil.acceptChain(visitor, chainSpec);
    }

    public void accept(ExprNodeVisitorWithParent visitor) {
        super.accept(visitor);
        ExprNodeUtil.acceptChain(visitor, chainSpec);
    }

    public void acceptChildnodes(ExprNodeVisitorWithParent visitor, ExprNode parent) {
        super.acceptChildnodes(visitor, parent);
        ExprNodeUtil.acceptChain(visitor, chainSpec, this);
    }

    public void replaceUnlistedChildNode(ExprNode nodeToReplace, ExprNode newNode) {
        ExprNodeUtil.replaceChainChildNode(nodeToReplace, newNode, chainSpec);
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

    public static Pair<ExprEvaluatorEnumeration, ExprDotEvalTypeInfo> getEnumerationSource(ExprNode inputExpression, StreamTypeService streamTypeService, boolean hasEnumerationMethod) throws ExprValidationException {
        ExprEvaluator rootNodeEvaluator = inputExpression.getExprEvaluator();
        ExprEvaluatorEnumeration rootLambdaEvaluator = null;
        ExprDotEvalTypeInfo info = null;

        if (rootNodeEvaluator instanceof ExprEvaluatorEnumeration) {
            rootLambdaEvaluator = (ExprEvaluatorEnumeration) rootNodeEvaluator;

            if (rootLambdaEvaluator.getEventTypeCollection() != null) {
                info = ExprDotEvalTypeInfo.eventColl(rootLambdaEvaluator.getEventTypeCollection());
            }
            else if (rootLambdaEvaluator.getComponentTypeCollection() != null) {
                info = ExprDotEvalTypeInfo.componentColl(rootLambdaEvaluator.getComponentTypeCollection());
            }
        }
        else if (inputExpression instanceof ExprIdentNode) {
            ExprIdentNode identNode = (ExprIdentNode) inputExpression;
            int streamId = identNode.getStreamId();
            EventType streamType = streamTypeService.getEventTypes()[streamId];
            return getPropertyEnumerationSource(identNode.getResolvedPropertyName(), streamId, streamType, hasEnumerationMethod);
        }
        return new Pair<ExprEvaluatorEnumeration, ExprDotEvalTypeInfo>(rootLambdaEvaluator, info);
    }

    public static Pair<ExprEvaluatorEnumeration, ExprDotEvalTypeInfo> getPropertyEnumerationSource(String propertyName, int streamId, EventType streamType, boolean hasEnumerationMethod) {


        EventPropertyGetter getter = streamType.getGetter(propertyName);
        FragmentEventType fragmentEventType = streamType.getFragmentType(propertyName);
        Class propertyType = streamType.getPropertyType(propertyName);
        ExprDotEvalTypeInfo typeInfo = ExprDotEvalTypeInfo.scalarOrUnderlying(propertyType);  // assume scalar for now

        // no enumeration methods, no need to expose as an enumeration
        if (!hasEnumerationMethod) {
            return new Pair<ExprEvaluatorEnumeration, ExprDotEvalTypeInfo>(null, typeInfo);
        }

        ExprEvaluatorEnumeration enumEvaluator = null;
        if (getter != null && fragmentEventType != null && fragmentEventType.isIndexed()) {
            enumEvaluator = new PropertyExprEvaluatorEventCollection(propertyName, streamId, fragmentEventType.getFragmentType(), getter);
            typeInfo = ExprDotEvalTypeInfo.eventColl(fragmentEventType.getFragmentType());
        }
        else {
            EventPropertyDescriptor desc = streamType.getPropertyDescriptor(propertyName);
            if (desc != null && desc.isIndexed() && !desc.isRequiresIndex() && desc.getPropertyComponentType() != null) {
                if (JavaClassHelper.isImplementsInterface(propertyType, Collection.class)) {
                    enumEvaluator = new PropertyExprEvaluatorScalarCollection(propertyName, streamId, getter, desc.getPropertyComponentType());
                }
                else if (JavaClassHelper.isImplementsInterface(propertyType, Iterable.class)) {
                    enumEvaluator = new PropertyExprEvaluatorScalarIterable(propertyName, streamId, getter, desc.getPropertyComponentType());
                }
                else if (propertyType.isArray()) {
                    enumEvaluator = new PropertyExprEvaluatorScalarArray(propertyName, streamId, getter, desc.getPropertyComponentType());
                }
                else {
                    throw new IllegalStateException("Property indicated indexed-type but failed to find proper collection adapter for use with enumeration methods");
                }
                typeInfo = ExprDotEvalTypeInfo.componentColl(desc.getPropertyComponentType());
            }
        }
        return new Pair<ExprEvaluatorEnumeration, ExprDotEvalTypeInfo>(enumEvaluator, typeInfo);
    }
}

