/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.expression;

import net.esper.event.EventBean;
import net.esper.event.PropertyAccessException;
import net.esper.event.EventPropertyGetter;
import net.esper.collection.Pair;
import net.esper.eql.core.*;
import net.esper.eql.variable.VariableService;
import net.esper.schedule.TimeProvider;

/**
 * Represents an stream property identifier in a filter expressiun tree.
 */
public class ExprIdentNode extends ExprNode
{
    // select myprop from...        is a simple property, no stream supplied
    // select s0.myprop from...     is a simple property with a stream supplied, or a nested property (cannot tell until resolved)
    // select indexed[1] from ...   is a indexed property

    private final String unresolvedPropertyName;
    private final String streamOrPropertyName;

    private String resolvedStreamName;
    private String resolvedPropertyName;
    private EventPropertyGetter propertyGetter;
    private int streamNum = -1;
    private Class propertyType;

    /**
     * Ctor.
     * @param unresolvedPropertyName is the event property name in unresolved form, ie. unvalidated against streams
     */
    public ExprIdentNode(String unresolvedPropertyName)
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
    public ExprIdentNode(String unresolvedPropertyName, String streamOrPropertyName)
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

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        Pair<PropertyResolutionDescriptor, String> propertyInfoPair = getTypeFromStream(streamTypeService, unresolvedPropertyName, streamOrPropertyName);
        resolvedStreamName = propertyInfoPair.getSecond();
        streamNum = propertyInfoPair.getFirst().getStreamNum();
        propertyType = propertyInfoPair.getFirst().getPropertyType();
        resolvedPropertyName = propertyInfoPair.getFirst().getPropertyName();
        propertyGetter = propertyInfoPair.getFirst().getStreamEventType().getGetter(resolvedPropertyName);
    }

    public Class getType() throws ExprValidationException
    {
        if (resolvedPropertyName == null)
        {
            throw new IllegalStateException("Identifier node has not been validated");
        }
        return propertyType;
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
        if (streamNum == -1)
        {
            throw new IllegalStateException("Identifier node has not been validated");
        }
        return streamNum;
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
     * Determine stream id and property type given an unresolved property name and
     * a stream name that may also be part of the property name.
     * <p>
     * For example: select s0.p1 from...    p1 is the property name, s0 the stream name, however this could also be a nested property
     * @param streamTypeService - service for type infos
     * @param unresolvedPropertyName - property name
     * @param streamOrPropertyName - stream name, this can also be the first part of the property name
     * @return pair of stream number and property type
     * @throws ExprValidationException if no such property exists
     */
    protected static Pair<PropertyResolutionDescriptor, String> getTypeFromStream(StreamTypeService streamTypeService, String unresolvedPropertyName, String streamOrPropertyName)
        throws ExprValidationException
    {
        PropertyResolutionDescriptor propertyInfo = null;

        // no stream/property name supplied
        if (streamOrPropertyName == null)
        {
            try
            {
                propertyInfo = streamTypeService.resolveByPropertyName(unresolvedPropertyName);
            }
            catch (StreamTypesException ex)
            {
                throw new ExprValidationException(ex.getMessage());
            }
            catch (PropertyAccessException ex)
            {
                throw new ExprValidationException(ex.getMessage());
            }

            // resolves without a stream name, return descriptor and null stream name
            return new Pair<PropertyResolutionDescriptor, String>(propertyInfo, propertyInfo.getStreamName());
        }

        // try to resolve the property name and stream name as it is (ie. stream name as a stream name)
        try
        {
            propertyInfo = streamTypeService.resolveByStreamAndPropName(streamOrPropertyName, unresolvedPropertyName);
            // resolves with a stream name, return descriptor and stream name
            return new Pair<PropertyResolutionDescriptor, String>(propertyInfo, streamOrPropertyName);
        }
        catch (StreamTypesException ex)
        {
            // unhandled
        }

        // try to resolve the property name to a nested property 's0.p0'
        String propertyNameCandidate = streamOrPropertyName + '.' + unresolvedPropertyName;
        try
        {
            propertyInfo = streamTypeService.resolveByPropertyName(propertyNameCandidate);
            // resolves without a stream name, return null for stream name
            return new Pair<PropertyResolutionDescriptor, String>(propertyInfo, null);
        }
        catch (StreamTypesException ex)
        {
            // unhandled
        }

        // fail to resolve
        throw new ExprValidationException("Failed to resolve property '" + propertyNameCandidate + "' to a stream or nested property in a stream");
    }

    public String toString()
    {
        return "unresolvedPropertyName=" + unresolvedPropertyName +
                " streamOrPropertyName=" + streamOrPropertyName +
                " resolvedPropertyName=" + resolvedPropertyName +
                " propertyInfo.pos=" + streamNum +
                " propertyInfo.type=" + propertyType;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        EventBean event = eventsPerStream[streamNum];
        if (event == null)
        {
            return null;
        }
        return propertyGetter.get(event);
    }

    /**
     * Returns true if the property exists, or false if not.
     * @param eventsPerStream each stream's events
     * @param isNewData if the stream represents insert or remove stream
     * @return true if the property exists, false if not
     */
    public boolean evaluatePropertyExists(EventBean[] eventsPerStream, boolean isNewData)
    {
        EventBean event = eventsPerStream[streamNum];
        if (event == null)
        {
            return false;
        }
        return propertyGetter.isExistsProperty(event);
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        if (streamOrPropertyName != null)
        {
            buffer.append(streamOrPropertyName).append('.');
        }
        buffer.append(unresolvedPropertyName);

        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprIdentNode))
        {
            return false;
        }

        ExprIdentNode other = (ExprIdentNode) node;

        if (this.streamNum == -1)
        {
            throw new IllegalStateException("ExprIdentNode has not been validated");
        }

        if ((other.streamNum != this.streamNum) ||
            (!(other.resolvedPropertyName.equals(this.resolvedPropertyName))))
        {
            return false;
        }


        return true;
    }
}
