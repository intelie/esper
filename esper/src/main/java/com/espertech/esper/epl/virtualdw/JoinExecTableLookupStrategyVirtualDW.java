/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.virtualdw;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindowLookup;
import com.espertech.esper.client.hook.VirtualDataWindowKeyRange;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.join.exec.base.JoinExecTableLookupStrategy;
import com.espertech.esper.epl.join.plan.*;
import com.espertech.esper.epl.join.rep.Cursor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

public class JoinExecTableLookupStrategyVirtualDW implements JoinExecTableLookupStrategy {
    private static final Log log = LogFactory.getLog(JoinExecTableLookupStrategyVirtualDW.class);

    private final String namedWindowName;
    private final VirtualDataWindowLookup externalIndex;
    private final ExternalEvaluator[] evaluators;
    private final EventBean[] eventsPerStream;
    private final int lookupStream;

    public JoinExecTableLookupStrategyVirtualDW(String namedWindowName, VirtualDataWindowLookup externalIndex, TableLookupKeyDesc keyDescriptor, int lookupStream) {
        this.namedWindowName = namedWindowName;
        this.externalIndex = externalIndex;
        this.evaluators = new ExternalEvaluator[keyDescriptor.getHashes().size() + keyDescriptor.getRanges().size()];
        this.eventsPerStream = new EventBean[lookupStream + 1];
        this.lookupStream = lookupStream;

        int count = 0;
        for (QueryGraphValueEntryHashKeyed hashKey : keyDescriptor.getHashes()) {
            ExprEvaluator evaluator = hashKey.getKeyExpr().getExprEvaluator();
            evaluators[count] = new ExternalEvaluatorHashRelOp(evaluator);
            count++;
        }
        for (QueryGraphValueEntryRange rangeKey : keyDescriptor.getRanges()) {
            if (rangeKey.getType().isRange()) {
                QueryGraphValueEntryRangeIn range = (QueryGraphValueEntryRangeIn) rangeKey;
                ExprEvaluator evaluatorStart = range.getExprStart().getExprEvaluator();
                ExprEvaluator evaluatorEnd = range.getExprEnd().getExprEvaluator();
                evaluators[count] = new ExternalEvaluatorBtreeRange(evaluatorStart, evaluatorEnd);
            }
            else {
                QueryGraphValueEntryRangeRelOp relOp = (QueryGraphValueEntryRangeRelOp) rangeKey;
                ExprEvaluator evaluator = relOp.getExpression().getExprEvaluator();
                evaluators[count] = new ExternalEvaluatorHashRelOp(evaluator);
            }
            count++;
        }
    }

    public Set<EventBean> lookup(EventBean event, Cursor cursor, ExprEvaluatorContext context) {

        eventsPerStream[lookupStream] = event;

        Object[] keys = new Object[evaluators.length];
        for (int i = 0; i < evaluators.length; i++) {
            keys[i] = evaluators[i].evaluate(eventsPerStream, context);
        }

        Set<EventBean> events = null;
        try {
            events = externalIndex.lookup(keys, eventsPerStream);
        }
        catch (RuntimeException ex) {
            log.warn("Exception encountered invoking virtual data window external index for window '" + namedWindowName + "': " + ex.getMessage(), ex);
        }
        return events;
    }

    public String toQueryPlan() {
        return this.getClass().getSimpleName() + " external index " + externalIndex;
    }

    private interface ExternalEvaluator
    {
        public Object evaluate(EventBean[] events, ExprEvaluatorContext context);
    }

    private static class ExternalEvaluatorHashRelOp implements ExternalEvaluator {

        private final ExprEvaluator hashKeysEval;

        private ExternalEvaluatorHashRelOp(ExprEvaluator hashKeysEval) {
            this.hashKeysEval = hashKeysEval;
        }

        public Object evaluate(EventBean[] events, ExprEvaluatorContext context) {
            return hashKeysEval.evaluate(events, true, context);
        }
    }

    private static class ExternalEvaluatorBtreeRange implements ExternalEvaluator {

        private final ExprEvaluator startEval;
        private final ExprEvaluator endEval;

        private ExternalEvaluatorBtreeRange(ExprEvaluator startEval, ExprEvaluator endEval) {
            this.startEval = startEval;
            this.endEval = endEval;
        }

        public Object evaluate(EventBean[] events, ExprEvaluatorContext context) {
            Object start = startEval.evaluate(events, true, context);
            Object end = endEval.evaluate(events, true, context);
            return new VirtualDataWindowKeyRange(start, end);
        }
    }
}
