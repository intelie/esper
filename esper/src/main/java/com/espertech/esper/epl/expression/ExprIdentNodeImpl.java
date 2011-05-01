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
import com.espertech.esper.client.annotation.Audit;
import com.espertech.esper.client.annotation.AuditEnum;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.core.PropertyResolutionDescriptor;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.parse.ASTFilterSpecHelper;

import java.util.Map;

/**
 * Represents an stream property identifier in a filter expressiun tree.
 */
public class ExprIdentNodeImpl extends ExprNodeBase implements ExprIdentNode
{
    private static final long serialVersionUID = 5882493771230745244L;

    // select myprop from...        is a simple property, no stream supplied
    // select s0.myprop from...     is a simple property with a stream supplied, or a nested property (cannot tell until resolved)
    // select indexed[1] from ...   is a indexed property

    private final String unresolvedPropertyName;
    private String streamOrPropertyName;

    private String resolvedStreamName;
    private String resolvedPropertyName;
    private transient ExprIdentNodeEvaluator evaluator;

    /**
     * Ctor.
     * @param unresolvedPropertyName is the event property name in unresolved form, ie. unvalidated against streams
     */
    public ExprIdentNodeImpl(String unresolvedPropertyName)
    {
        if (unresolvedPropertyName == null)
        {
            throw new IllegalArgumentException("Property name is null");
        }
        this.unresolvedPropertyName = unresolvedPropertyName;
        this.streamOrPropertyName = null;
    }

    /**
     * Ctor.
     * @param unresolvedPropertyName is the event property name in unresolved form, ie. unvalidated against streams
     * @param streamOrPropertyName is the stream name, or if not a valid stream name a possible nested property name
     * in one of the streams.
     */
    public ExprIdentNodeImpl(String unresolvedPropertyName, String streamOrPropertyName)
    {
        if (unresolvedPropertyName == null)
        {
            throw new IllegalArgumentException("Property name is null");
        }
        if (streamOrPropertyName == null)
        {
            throw new IllegalArgumentException("Stream (or property name) name is null");
        }
        this.unresolvedPropertyName = unresolvedPropertyName;
        this.streamOrPropertyName = streamOrPropertyName;
    }

    public ExprIdentNodeImpl(EventType eventType, String propertyName, int streamNumber) {
        unresolvedPropertyName = propertyName;
        resolvedPropertyName = propertyName;
        EventPropertyGetter propertyGetter = eventType.getGetter(propertyName);
        if (propertyGetter == null) {
            throw new IllegalArgumentException("Ident-node constructor could not locate property " + propertyName);
        }
        Class propertyType = eventType.getPropertyType(propertyName);
        evaluator = new ExprIdentNodeEvaluator(streamNumber, propertyGetter, propertyType);
    }

    public ExprEvaluator getExprEvaluator()
    {
        return evaluator;
    }

    public Map<String, Object> getEventType() {
        return null;
    }

    /**
     * For unit testing, returns unresolved property name.
     * @return property name
     */
    public String getUnresolvedPropertyName()
    {
        return unresolvedPropertyName;
    }

    /**
     * For unit testing, returns stream or property name candidate.
     * @return stream name, or property name of a nested property of one of the streams
     */
    public String getStreamOrPropertyName()
    {
        return streamOrPropertyName;
    }

    /**
     * Set name.
     * @param streamOrPropertyName to use
     */
    public void setStreamOrPropertyName(String streamOrPropertyName) {
        this.streamOrPropertyName = streamOrPropertyName;
    }

    /**
     * Returns the unresolved property name in it's complete form, including
     * the stream name if there is one.
     * @return property name
     */
    public String getFullUnresolvedName()
    {
        if (streamOrPropertyName == null)
        {
            return unresolvedPropertyName;
        }
        else
        {
            return streamOrPropertyName + "." + unresolvedPropertyName;
        }
    }

    public void validate(ExprValidationContext validationContext) throws ExprValidationException
    {
        Pair<PropertyResolutionDescriptor, String> propertyInfoPair = ExprIdentNodeUtil.getTypeFromStream(validationContext.getStreamTypeService(), unresolvedPropertyName, streamOrPropertyName);
        resolvedStreamName = propertyInfoPair.getSecond();
        int streamNum = propertyInfoPair.getFirst().getStreamNum();
        Class propertyType = propertyInfoPair.getFirst().getPropertyType();
        resolvedPropertyName = propertyInfoPair.getFirst().getPropertyName();
        EventPropertyGetter propertyGetter = propertyInfoPair.getFirst().getStreamEventType().getGetter(resolvedPropertyName);

        if (propertyGetter == null)
        {
            throw new ExprValidationException("Property getter returned was invalid for property '" + unresolvedPropertyName + "'");
        }

        Audit audit = AuditEnum.PROPERTY.getAudit(validationContext.getAnnotations());
        if (audit != null) {
            evaluator = new ExprIdentNodeEvaluatorLogging(streamNum, propertyGetter, propertyType, resolvedPropertyName, validationContext.getStatementName());
        }
        else {
            evaluator = new ExprIdentNodeEvaluator(streamNum, propertyGetter, propertyType);
        }
    }

    public boolean isConstantResult()
    {
        return false;
    }

    /**
     * Returns stream id supplying the property value.
     * @return stream number
     */
    public int getStreamId()
    {
        if (evaluator == null) {
            throw new IllegalStateException("Identifier expression has not been validated");
        }
        return evaluator.getStreamNum();
    }

    public Class getType() {
        if (evaluator == null) {
            throw new IllegalStateException("Identifier expression has not been validated");
        }
        return evaluator.getType();
    }

    /**
     * Returns stream name as resolved by lookup of property in streams.
     * @return stream name
     */
    public String getResolvedStreamName()
    {
        if (resolvedStreamName == null)
        {
            throw new IllegalStateException("Identifier node has not been validated");
        }
        return resolvedStreamName;
    }

    /**
     * Return property name as resolved by lookup in streams.
     * @return property name
     */
    public String getResolvedPropertyName()
    {
        if (resolvedPropertyName == null)
        {
            throw new IllegalStateException("Identifier node has not been validated");
        }
        return resolvedPropertyName;
    }

    /**
     * Returns the root of the resolved property name, if any.
     * @return root
     */
    public String getResolvedPropertyNameRoot() {
        if (resolvedPropertyName == null)
        {
            throw new IllegalStateException("Identifier node has not been validated");
        }
        if (resolvedPropertyName.indexOf('[') != -1) {
            return resolvedPropertyName.substring(0, resolvedPropertyName.indexOf('['));
        }
        if (resolvedPropertyName.indexOf('(') != -1) {
            return resolvedPropertyName.substring(0, resolvedPropertyName.indexOf('('));
        }
        if (resolvedPropertyName.indexOf('.') != -1) {
            return resolvedPropertyName.substring(0, resolvedPropertyName.indexOf('.'));
        }
        return resolvedPropertyName;
    }

    public String toString()
    {
        return "unresolvedPropertyName=" + unresolvedPropertyName +
                " streamOrPropertyName=" + streamOrPropertyName +
                " resolvedPropertyName=" + resolvedPropertyName;
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        if (streamOrPropertyName != null)
        {
            buffer.append(ASTFilterSpecHelper.unescapeDot(streamOrPropertyName)).append('.');
        }
        buffer.append(ASTFilterSpecHelper.unescapeDot(unresolvedPropertyName));

        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprIdentNodeImpl))
        {
            return false;
        }

        ExprIdentNodeImpl other = (ExprIdentNodeImpl) node;

        if (streamOrPropertyName != null ? !streamOrPropertyName.equals(other.streamOrPropertyName) : other.streamOrPropertyName != null)
            return false;
        if (unresolvedPropertyName != null ? !unresolvedPropertyName.equals(other.unresolvedPropertyName) : other.unresolvedPropertyName != null)
            return false;
        return true;
    }

    public static Pair<PropertyResolutionDescriptor, String> getTypeFromStream(StreamTypeService streamTypeService, String propertyNameNestable)
                    throws ExprValidationPropertyException {
        String streamOrProp = null;
        String prop = propertyNameNestable;
        if (propertyNameNestable.indexOf('.') != -1) {
            prop = propertyNameNestable.substring(propertyNameNestable.indexOf('.') + 1);
            streamOrProp = propertyNameNestable.substring(0, propertyNameNestable.indexOf('.'));
        }
        return ExprIdentNodeUtil.getTypeFromStream(streamTypeService, prop, streamOrProp);
    }

    public ExprIdentNodeEvaluator getExprEvaluatorIdent() {
        return evaluator;
    }
}
