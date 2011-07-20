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

package com.espertech.esper.epl.datetime.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.datetime.calop.CalendarOp;
import com.espertech.esper.epl.datetime.calop.CalendarOpFactory;
import com.espertech.esper.epl.datetime.interval.IntervalOp;
import com.espertech.esper.epl.datetime.interval.IntervalOpFactory;
import com.espertech.esper.epl.datetime.reformatop.ReformatOp;
import com.espertech.esper.epl.datetime.reformatop.ReformatOpFactory;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalTypeInfo;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.methodbase.DotMethodFPProvided;
import com.espertech.esper.epl.methodbase.DotMethodTypeEnum;
import com.espertech.esper.epl.methodbase.DotMethodUtil;
import com.espertech.esper.util.JavaClassHelper;

import java.util.*;

public class ExprDotEvalDTFactory {

    public static ExprDotEvalDTMethodDesc validateMake(EventType[] typesPerStream, Deque<ExprChainedSpec> chainSpecStack, DatetimeMethodEnum dtMethod, String dtMethodName, ExprDotEvalTypeInfo inputType, List<ExprNode> parameters, ExprDotNodeFilterAnalyzerInput inputDesc)
            throws ExprValidationException
    {
        // verify input
        String message = "Date-time enumeration method '" + dtMethodName + "' requires either a Calendar, Date or long value as input or events of an event type that declares a timestamp property";
        if (inputType.getEventType() != null) {
            if (inputType.getEventType().getStartTimestampPropertyName() == null) {
                throw new ExprValidationException(message);
            }
        }
        else {
            if (!inputType.isScalar() || inputType.getScalar() == null) {
                throw new ExprValidationException(message);
            }
            if (!JavaClassHelper.isDatetimeClass(inputType.getScalar())) {
                throw new ExprValidationException(message + " but received " + JavaClassHelper.getClassNameFullyQualPretty(inputType.getScalar()));
            }
        }

        List<CalendarOp> calendarOps = new ArrayList<CalendarOp>();
        ReformatOp reformatOp = null;
        IntervalOp intervalOp = null;
        DatetimeMethodEnum currentMethod = dtMethod;
        List<ExprNode> currentParameters = parameters;
        String currentMethodName = dtMethodName;

        // drain all calendar ops
        ExprDotNodeFilterAnalyzerDTIntervalDesc intervalFilterDesc = null;
        while(true) {

            // handle the first one only if its a calendar op
            ExprEvaluator[] evaluators = getEvaluators(currentParameters);
            OpFactory opFactory = currentMethod.getOpFactory();

            // compile parameter abstract for validation against available footprints
            DotMethodFPProvided footprintProvided = DotMethodUtil.getProvidedFootprint(currentParameters);

            // validate parameters
            DotMethodUtil.validateParameters(currentMethod.getFootprints(), DotMethodTypeEnum.DATETIME, currentMethodName, footprintProvided);

            if (opFactory instanceof CalendarOpFactory) {
                CalendarOp calendarOp = ((CalendarOpFactory) currentMethod.getOpFactory()).getOp(currentMethod, currentMethodName, currentParameters, evaluators);
                calendarOps.add(calendarOp);
            }
            else if (opFactory instanceof ReformatOpFactory) {
                reformatOp = ((ReformatOpFactory) opFactory).getOp(currentMethod, currentMethodName, currentParameters.isEmpty() ? null : currentParameters.get(0));
            }
            else if (opFactory instanceof IntervalOpFactory) {
                intervalOp = ((IntervalOpFactory) opFactory).getOp(typesPerStream, currentMethod, currentMethodName, currentParameters, evaluators);

                // compile filter analyzer information if there are no calendar ops in the chain
                if (calendarOps.isEmpty()) {
                    intervalFilterDesc = intervalOp.getFilterDesc(typesPerStream, currentMethod, currentParameters, inputDesc);
                }
                else {
                    intervalFilterDesc = null;
                }
            }
            else {
                throw new IllegalStateException("Invalid op factory class " + opFactory);
            }

            // see if there is more
            if (chainSpecStack.isEmpty() || !DatetimeMethodEnum.isDateTimeMethod(chainSpecStack.getFirst().getName())) {
                break;
            }

            // pull next
            ExprChainedSpec next = chainSpecStack.removeFirst();
            currentMethod = DatetimeMethodEnum.fromName(next.getName());
            currentParameters = next.getParameters();
            currentMethodName = next.getName();

            if ((reformatOp != null || intervalOp != null)) {
                throw new ExprValidationException("Invalid input for date-time method '" + next.getName() + "'");
            }
        }

        ExprDotEval dotEval;
        ExprDotEvalTypeInfo returnType;

        dotEval = new ExprDotEvalDT(calendarOps, reformatOp, intervalOp, inputType.getScalar(), inputType.getEventType());
        returnType = dotEval.getTypeInfo();
        return new ExprDotEvalDTMethodDesc(dotEval, returnType, intervalFilterDesc);
    }

    private static ExprEvaluator[] getEvaluators(List<ExprNode> parameters) {

        ExprEvaluator[] inputExpr = new ExprEvaluator[parameters.size()];
        for (int i = 0; i < parameters.size(); i++) {

            ExprNode innerExpr = parameters.get(i);
            final ExprEvaluator inner = innerExpr.getExprEvaluator();

            // Time periods get special attention
            if (innerExpr instanceof ExprTimePeriod) {

                final ExprTimePeriod timePeriod = (ExprTimePeriod) innerExpr;
                inputExpr[i] = new ExprEvaluator() {
                    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
                        return timePeriod.evaluateGetTimePeriod(eventsPerStream, isNewData, context);
                    }

                    public Class getType() {
                        return TimePeriod.class;
                    }

                    public Map<String, Object> getEventType() throws ExprValidationException {
                        return null;
                    }
                };
            }
            else {
                inputExpr[i] = inner;
            }
        }
        return inputExpr;
    }
}
