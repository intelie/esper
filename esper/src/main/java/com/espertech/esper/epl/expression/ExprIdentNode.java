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
import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.client.annotation.Audit;
import com.espertech.esper.client.annotation.AuditEnum;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.core.PropertyResolutionDescriptor;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.StreamTypesException;
import com.espertech.esper.epl.parse.ASTFilterSpecHelper;
import com.espertech.esper.util.LevenshteinDistance;

import java.util.Map;

/**
 * Represents an stream property identifier in a filter expressiun tree.
 */
public interface ExprIdentNode extends ExprNode
{
    public String getUnresolvedPropertyName();
    public String getFullUnresolvedName();
    public int getStreamId();
    public String getResolvedPropertyNameRoot();
    public String getResolvedPropertyName();
    public String getStreamOrPropertyName();
    public void setStreamOrPropertyName(String streamOrPropertyName);
    public String getResolvedStreamName();
    public ExprIdentNodeEvaluator getExprEvaluatorIdent();
}
