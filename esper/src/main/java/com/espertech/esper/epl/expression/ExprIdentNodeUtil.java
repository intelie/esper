/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.core.PropertyResolutionDescriptor;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.StreamTypesException;
import com.espertech.esper.util.LevenshteinDistance;

public class ExprIdentNodeUtil
{
    /**
     * Determine stream id and property type given an unresolved property name and
     * a stream name that may also be part of the property name.
     * <p>
     * For example: select s0.p1 from...    p1 is the property name, s0 the stream name, however this could also be a nested property
     * @param streamTypeService - service for type infos
     * @param unresolvedPropertyName - property name
     * @param streamOrPropertyName - stream name, this can also be the first part of the property name
     * @return pair of stream number and property type
     * @throws ExprValidationPropertyException if no such property exists
     */
    protected static Pair<PropertyResolutionDescriptor, String> getTypeFromStream(StreamTypeService streamTypeService, String unresolvedPropertyName, String streamOrPropertyName)
        throws ExprValidationPropertyException
    {
        PropertyResolutionDescriptor propertyInfo;

        // no stream/property name supplied
        if (streamOrPropertyName == null)
        {
            try
            {
                propertyInfo = streamTypeService.resolveByPropertyName(unresolvedPropertyName);
            }
            catch (StreamTypesException ex)
            {
                String suggestion = getSuggestion(ex);
                if (suggestion != null)
                {
                    throw new ExprValidationPropertyException(ex.getMessage() + suggestion);
                }
                else
                {
                    throw new ExprValidationPropertyException(ex.getMessage());
                }
            }
            catch (PropertyAccessException ex)
            {
                throw new ExprValidationPropertyException(ex.getMessage());
            }

            // resolves without a stream name, return descriptor and null stream name
            return new Pair<PropertyResolutionDescriptor, String>(propertyInfo, propertyInfo.getStreamName());
        }

        // try to resolve the property name and stream name as it is (ie. stream name as a stream name)
        StreamTypesException typeExceptionOne;
        try
        {
            propertyInfo = streamTypeService.resolveByStreamAndPropName(streamOrPropertyName, unresolvedPropertyName);
            // resolves with a stream name, return descriptor and stream name
            return new Pair<PropertyResolutionDescriptor, String>(propertyInfo, streamOrPropertyName);
        }
        catch (StreamTypesException ex)
        {
            typeExceptionOne = ex;
        }

        // try to resolve the property name to a nested property 's0.p0'
        StreamTypesException typeExceptionTwo;
        String propertyNameCandidate = streamOrPropertyName + '.' + unresolvedPropertyName;
        try
        {
            propertyInfo = streamTypeService.resolveByPropertyName(propertyNameCandidate);
            // resolves without a stream name, return null for stream name
            return new Pair<PropertyResolutionDescriptor, String>(propertyInfo, null);
        }
        catch (StreamTypesException ex)
        {
            typeExceptionTwo = ex;
        }

        String suggestionOne = getSuggestion(typeExceptionOne);
        String suggestionTwo = getSuggestion(typeExceptionTwo);
        if (suggestionOne != null)
        {
            throw new ExprValidationPropertyException(typeExceptionOne.getMessage() + suggestionOne);
        }
        if (suggestionTwo != null)
        {
            throw new ExprValidationPropertyException(typeExceptionTwo.getMessage() + suggestionTwo);
        }

        // fail to resolve
        throw new ExprValidationPropertyException("Failed to resolve property '" + propertyNameCandidate + "' to a stream or nested property in a stream");
    }

    private static String getSuggestion(StreamTypesException ex)
    {
        if (ex == null)
        {
            return null;
        }
        if (ex.getOptionalSuggestion() == null)
        {
            return null;
        }
        if (ex.getOptionalSuggestion().getFirst() > LevenshteinDistance.ACCEPTABLE_DISTANCE)
        {
            return null;
        }
        return " (did you mean '" + ex.getOptionalSuggestion().getSecond() + "'?)";
    }
}
