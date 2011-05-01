/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.client.annotation.Audit;
import com.espertech.esper.client.annotation.AuditEnum;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.agg.AggregationSupport;
import com.espertech.esper.epl.core.EngineImportException;
import com.espertech.esper.epl.core.EngineImportUndefinedException;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.enummethod.dot.ExprLambdaGoesNode;
import com.espertech.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExprNodeUtil {

    /**
     * Validates the expression node subtree that has this
     * node as root. Some of the nodes of the tree, including the
     * root, might be replaced in the process.
     * @throws com.espertech.esper.epl.expression.ExprValidationException when the validation fails
     * @return the root node of the validated subtree, possibly
     *         different than the root node of the unvalidated subtree
     */
    public static ExprNode getValidatedSubtree(ExprNode exprNode, ExprValidationContext validationContext) throws ExprValidationException
    {
        if (exprNode instanceof ExprLambdaGoesNode) {
            return exprNode;
        }

        return getValidatedSubtreeInternal(exprNode, validationContext, true);
    }

    private static ExprNode getValidatedSubtreeInternal(ExprNode exprNode, ExprValidationContext validationContext, boolean isTopLevel) throws ExprValidationException
    {
        ExprNode result = exprNode;
        if (exprNode instanceof ExprLambdaGoesNode) {
            return exprNode;
        }

        for (int i = 0; i < exprNode.getChildNodes().size(); i++)
        {
            ExprNode childNode = exprNode.getChildNodes().get(i);
            if (childNode instanceof ExprLambdaGoesNode) {
                continue;
            }
            ExprNode childNodeValidated = getValidatedSubtreeInternal(childNode, validationContext, false);
            exprNode.getChildNodes().set(i, childNodeValidated);
        }

        try
        {
            exprNode.validate(validationContext);
        }
        catch(ExprValidationException e)
        {
            if (exprNode instanceof ExprIdentNode)
            {
                ExprIdentNode identNode = (ExprIdentNode) exprNode;
                try
                {
                    result = resolveStaticMethodOrField(identNode, e, validationContext);
                }
                catch(ExprValidationException ex)
                {
                    e = ex;
                    result = resolveAsStreamName(identNode, e, validationContext);
                }
            }
            else if (exprNode instanceof ExprDotNode)
            {
                ExprDotNode staticMethodNode = (ExprDotNode) exprNode;
                result = resolveInstanceMethod(staticMethodNode, e, validationContext);
            }
            else
            {
                throw e;
            }
        }

        // For top-level expressions check if we perform audit
        if (isTopLevel) {
            Audit audit = AuditEnum.EXPRESSION.getAudit(validationContext.getAnnotations());
            if (audit != null) {
                return (ExprNode) ExprNodeProxy.newInstance(validationContext.getStatementName(), result);
            }
        }
        
        return result;
    }

    private static ExprNode resolveInstanceMethod(ExprDotNode staticMethodNode, ExprValidationException existingException, ExprValidationContext validationContext)
            throws ExprValidationException
    {
        String streamName = staticMethodNode.getChainSpec().get(0).getName();

        boolean streamFound = false;
        for (String name : validationContext.getStreamTypeService().getStreamNames())
        {
            if (name != null && name.equals(streamName))
            {
                streamFound = true;
            }
        }

        List<ExprChainedSpec> remainingChain = new ArrayList<ExprChainedSpec>(staticMethodNode.getChainSpec());
        remainingChain.remove(0);

        if (remainingChain.isEmpty()) {
            throw existingException;
        }

        ExprStreamInstanceMethodNode exprStream = new ExprStreamInstanceMethodNode(streamName, remainingChain);
        try
        {
            exprStream.validate(validationContext);
        }
        catch (ExprValidationException ex)
        {
            if (streamFound)
            {
                throw ex;
            }
            throw existingException;
        }

        return exprStream;
    }

    private static ExprNode resolveAsStreamName(ExprIdentNode identNode, ExprValidationException existingException, ExprValidationContext validationContext)
            throws ExprValidationException
    {
        ExprStreamUnderlyingNode exprStream = new ExprStreamUnderlyingNodeImpl(identNode.getUnresolvedPropertyName(), false);

        try
        {
            exprStream.validate(validationContext);
        }
        catch (ExprValidationException ex)
        {
            throw existingException;
        }

        return exprStream;
    }

    // Since static method calls such as "Class.method('a')" and mapped properties "Stream.property('key')"
    // look the same, however as the validation could not resolve "Stream.property('key')" before calling this method,
    // this method tries to resolve the mapped property as a static method.
    // Assumes that this is an ExprIdentNode.
    private static ExprNode resolveStaticMethodOrField(ExprIdentNode identNode, ExprValidationException propertyException, ExprValidationContext validationContext)
    throws ExprValidationException
    {
        // Reconstruct the original string
        StringBuffer mappedProperty = new StringBuffer(identNode.getUnresolvedPropertyName());
        if(identNode.getStreamOrPropertyName() != null)
        {
            mappedProperty.insert(0, identNode.getStreamOrPropertyName() + '.');
        }

        // Parse the mapped property format into a class name, method and single string parameter
        MappedPropertyParseResult parse = parseMappedProperty(mappedProperty.toString());
        if (parse == null)
        {
            ExprConstantNode constNode = resolveIdentAsEnumConst(mappedProperty.toString(), validationContext.getMethodResolutionService());
            if (constNode == null)
            {
                throw propertyException;
            }
            else
            {
                return constNode;
            }
        }

        // If there is a class name, assume a static method is possible.
        if (parse.getClassName() != null)
        {
            List<ExprNode> parameters = Collections.singletonList((ExprNode) new ExprConstantNodeImpl(parse.getArgString()));
            List<ExprChainedSpec> chain = new ArrayList<ExprChainedSpec>();
            chain.add(new ExprChainedSpec(parse.getClassName(), Collections.<ExprNode>emptyList(), false));
            chain.add(new ExprChainedSpec(parse.getMethodName(), parameters, false));
            ExprNode result = new ExprDotNode(chain, validationContext.getMethodResolutionService().isDuckType(), validationContext.getMethodResolutionService().isUdfCache());

            // Validate
            try
            {
                result.validate(validationContext);
            }
            catch(ExprValidationException e)
            {
                throw new ExprValidationException("Failed to resolve enumeration method, date-time method or mapped property '" + mappedProperty + "': " + e.getMessage());
            }

            return result;
        }

        // There is no class name, try a single-row function
        String functionName = parse.getMethodName();
        try
        {
            Pair<Class, String> classMethodPair = validationContext.getMethodResolutionService().resolveSingleRow(functionName);
            List<ExprNode> params = Collections.singletonList((ExprNode) new ExprConstantNodeImpl(parse.getArgString()));
            List<ExprChainedSpec> chain = Collections.singletonList(new ExprChainedSpec(classMethodPair.getSecond(), params, false));
            ExprNode result = new ExprPlugInSingleRowNode(functionName, classMethodPair.getFirst(), chain, false);

            // Validate
            try
            {
                result.validate(validationContext);
            }
            catch (RuntimeException e)
            {
                throw new ExprValidationException("Plug-in aggregation function '" + parse.getMethodName() + "' failed validation: " + e.getMessage());
            }

            return result;
        }
        catch (EngineImportUndefinedException e)
        {
            // Not an single-row function
        }
        catch (EngineImportException e)
        {
            throw new IllegalStateException("Error resolving single-row function: " + e.getMessage(), e);
        }

        // There is no class name, try an aggregation function
        try
        {
            AggregationSupport aggregation = validationContext.getMethodResolutionService().resolveAggregation(parse.getMethodName());
            ExprNode result = new ExprPlugInAggFunctionNode(false, aggregation, parse.getMethodName());
            result.addChildNode(new ExprConstantNodeImpl(parse.getArgString()));

            // Validate
            try
            {
                result.validate(validationContext);
            }
            catch (RuntimeException e)
            {
                throw new ExprValidationException("Plug-in aggregation function '" + parse.getMethodName() + "' failed validation: " + e.getMessage());
            }

            return result;
        }
        catch (EngineImportUndefinedException e)
        {
            // Not an aggregation function
        }
        catch (EngineImportException e)
        {
            throw new IllegalStateException("Error resolving aggregation: " + e.getMessage(), e);
        }

        // absolutly cannot be resolved
        throw propertyException;
    }

    private static ExprConstantNode resolveIdentAsEnumConst(String constant, MethodResolutionService methodResolutionService)
            throws ExprValidationException
    {
        Object enumValue = JavaClassHelper.resolveIdentAsEnumConst(constant, methodResolutionService, null);
        if (enumValue != null)
        {
            return new ExprConstantNodeImpl(enumValue);
        }
        return null;
    }

    /**
     * Parse the mapped property into classname, method and string argument.
     * Mind this has been parsed already and is a valid mapped property.
     * @param property is the string property to be passed as a static method invocation
     * @return descriptor object
     */
    protected static MappedPropertyParseResult parseMappedProperty(String property)
    {
        // get argument
        int indexFirstDoubleQuote = property.indexOf("\"");
        int indexFirstSingleQuote = property.indexOf("'");
        int startArg;
        if ((indexFirstSingleQuote == -1) && (indexFirstDoubleQuote == -1))
        {
            return null;
        }
        if ((indexFirstSingleQuote != -1) && (indexFirstDoubleQuote != -1))
        {
            if (indexFirstSingleQuote < indexFirstDoubleQuote)
            {
                startArg = indexFirstSingleQuote;
            }
            else
            {
                startArg = indexFirstDoubleQuote;
            }
        }
        else if (indexFirstSingleQuote != -1)
        {
            startArg = indexFirstSingleQuote;
        }
        else
        {
            startArg = indexFirstDoubleQuote;
        }

        int indexLastDoubleQuote = property.lastIndexOf("\"");
        int indexLastSingleQuote = property.lastIndexOf("'");
        int endArg;
        if ((indexLastSingleQuote == -1) && (indexLastDoubleQuote == -1))
        {
            return null;
        }
        if ((indexLastSingleQuote != -1) && (indexLastDoubleQuote != -1))
        {
            if (indexLastSingleQuote > indexLastDoubleQuote)
            {
                endArg = indexLastSingleQuote;
            }
            else
            {
                endArg = indexLastDoubleQuote;
            }
        }
        else if (indexLastSingleQuote != -1)
        {
            endArg = indexLastSingleQuote;
        }
        else
        {
            endArg = indexLastDoubleQuote;
        }
        String argument = property.substring(startArg + 1, endArg);

        // get method
        String splitDots[] = property.split("[\\.]");
        if (splitDots.length == 0)
        {
            return null;
        }

        // find which element represents the method, its the element with the parenthesis
        int indexMethod = -1;
        for (int i = 0; i < splitDots.length; i++)
        {
            if (splitDots[i].contains("("))
            {
                indexMethod = i;
                break;
            }
        }
        if (indexMethod == -1)
        {
            return null;
        }

        String method = splitDots[indexMethod];
        int indexParan = method.indexOf("(");
        method = method.substring(0, indexParan);
        if (method.length() == 0)
        {
            return null;
        }

        if (splitDots.length == 1)
        {
            // no class name
            return new MappedPropertyParseResult(null, method, argument);
        }


        // get class
        StringBuffer clazz = new StringBuffer();
        for (int i = 0; i < indexMethod; i++)
        {
            if (i > 0)
            {
                clazz.append('.');
            }
            clazz.append(splitDots[i]);
        }

        return new MappedPropertyParseResult(clazz.toString(), method, argument);
    }

    /**
     * Encapsulates the parse result parsing a mapped property as a class and method name with args.
     */
    protected static class MappedPropertyParseResult
    {
        private String className;
        private String methodName;
        private String argString;

        /**
         * Returns class name.
         * @return name of class
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
         * Returns the method argument.
         * @return arg
         */
        public String getArgString()
        {
            return argString;
        }

        /**
         * Returns the parse result of the mapped property.
         * @param className is the class name, or null if there isn't one
         * @param methodName is the method name
         * @param argString is the argument
         */
        public MappedPropertyParseResult(String className, String methodName, String argString)
        {
            this.className = className;
            this.methodName = methodName;
            this.argString = argString;
        }
    }

    public static void acceptChain(ExprNodeVisitor visitor, List<ExprChainedSpec> chainSpec) {
        for (ExprChainedSpec chain : chainSpec) {
            for (ExprNode param : chain.getParameters()) {
                param.accept(visitor);
            }
        }
    }

    public static void acceptChain(ExprNodeVisitorWithParent visitor, List<ExprChainedSpec> chainSpec) {
        for (ExprChainedSpec chain : chainSpec) {
            for (ExprNode param : chain.getParameters()) {
                param.accept(visitor);
            }
        }
    }

    public static void acceptChain(ExprNodeVisitorWithParent visitor, List<ExprChainedSpec> chainSpec, ExprNode parent) {
        for (ExprChainedSpec chain : chainSpec) {
            for (ExprNode param : chain.getParameters()) {
                param.acceptChildnodes(visitor, parent);
            }
        }
    }

    public static final void replaceChildNode(ExprNode parentNode, ExprNode nodeToReplace, ExprNode newNode) {
        int index = parentNode.getChildNodes().indexOf(nodeToReplace);
        if (index == -1) {
            parentNode.replaceUnlistedChildNode(nodeToReplace, newNode);
        }
        else {
            parentNode.getChildNodes().set(index, newNode);
        }
    }

    public static void replaceChainChildNode(ExprNode nodeToReplace, ExprNode newNode, List<ExprChainedSpec> chainSpec) {
        for (ExprChainedSpec chained : chainSpec) {
            int index = chained.getParameters().indexOf(nodeToReplace);
            if (index != -1) {
                chained.getParameters().set(index, newNode);
            }
        }
    }

    private static final Log log = LogFactory.getLog(ExprNodeUtil.class);
}
