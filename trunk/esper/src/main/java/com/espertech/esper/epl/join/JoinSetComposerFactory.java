/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.spec.OuterJoinDesc;
import com.espertech.esper.epl.spec.SelectClauseStreamSelectorEnum;
import com.espertech.esper.client.EventType;
import com.espertech.esper.view.Viewable;

import java.util.List;

/**
 * Factory for building a {@link JoinSetComposer} implementations from analyzing filter nodes, for
 * fast join tuple result set composition.
 */
public interface JoinSetComposerFactory
{
    /**
     * Builds join tuple composer.
     * @param outerJoinDescList - list of descriptors for outer join criteria
     * @param optionalFilterNode - filter tree for analysis to build indexes for fast access
     * @param streamTypes - types of streams
     * @param streamNames - names of streams
     * @param streamViews - leaf view per stream
     * @param selectStreamSelectorEnum - indicator for rstream or istream-only, for optimization
     * @param isUnidirectional is an array of indicators for each stream set to true for a unidirectional stream in a join
     * @param hasChildViews indicates if child views are declared for a stream
     * @param isNamedWindow indicates whether the join is against named windows
     * @param isUnidirectionalNonDriving indicates the additional streams that are unidirectional
     * @return composer implementation
     * @throws ExprValidationException is thrown to indicate that
     * validation of view use in joins failed.
     */
    public JoinSetComposer makeComposer(List<OuterJoinDesc> outerJoinDescList,
                                                   ExprNode optionalFilterNode,
                                                   EventType[] streamTypes,
                                                   String[] streamNames,
                                                   Viewable[] streamViews,
                                                   SelectClauseStreamSelectorEnum selectStreamSelectorEnum,
                                                   boolean[] isUnidirectional,
                                                   boolean[] isUnidirectionalNonDriving,
                                                   boolean[] hasChildViews,
                                                   boolean[] isNamedWindow)
            throws ExprValidationException;
}
