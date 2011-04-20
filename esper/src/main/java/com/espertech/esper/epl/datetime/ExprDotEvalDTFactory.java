package com.espertech.esper.epl.datetime;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.datetime.calop.CalendarOp;
import com.espertech.esper.epl.datetime.calop.CalendarOpFactory;
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

    public static Pair<ExprDotEval, ExprDotEvalTypeInfo> validateMake(Deque<ExprChainedSpec> chainSpecStack, DatetimeMethodEnum dtMethod, String dtMethodName, ExprDotEvalTypeInfo inputType, List<ExprNode> parameters)
            throws ExprValidationException
    {
        // verify input
        String message = "Date-time enumeration method '" + dtMethodName + "' requires a scalar input value of type Calendar, Date or long";
        if (!inputType.isScalar() || inputType.getScalar() == null) {
            throw new ExprValidationException(message);
        }
        if ((!JavaClassHelper.isSubclassOrImplementsInterface(inputType.getScalar(), Calendar.class)) &&
            (!JavaClassHelper.isSubclassOrImplementsInterface(inputType.getScalar(), Date.class)) &&
            (JavaClassHelper.getBoxedType(inputType.getScalar()) != Long.class)) {
            throw new ExprValidationException(message + " but received " + JavaClassHelper.getClassNameFullyQualPretty(inputType.getScalar()));
        }

        List<CalendarOp> calendarOps = new ArrayList<CalendarOp>();
        ReformatOp reformatOp = null;
        DatetimeMethodEnum currentMethod = dtMethod;
        List<ExprNode> currentParameters = parameters;
        String currentMethodName = dtMethodName;

        // drain all calendar ops
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
            else {
                reformatOp = ((ReformatOpFactory) opFactory).getOp(currentMethod, currentMethodName, currentParameters.isEmpty() ? null : currentParameters.get(0));
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
        }

        ExprDotEval dotEval;
        ExprDotEvalTypeInfo returnType;

        if (!calendarOps.isEmpty() && reformatOp == null) {
            dotEval = new ExprDotEvalDTCalendarOps(inputType.getScalar(), calendarOps);
            returnType = dotEval.getTypeInfo();
        }
        else {
            if (calendarOps.isEmpty()) {
                dotEval = new ExprDotEvalDTReformatOnly(dtMethodName, reformatOp);
                returnType = dotEval.getTypeInfo();
            }
            else {
                dotEval = new ExprDotEvalDTCalOpsReformat(dtMethodName, reformatOp, calendarOps);
                returnType = dotEval.getTypeInfo();
            }
        }
        return new Pair<ExprDotEval, ExprDotEvalTypeInfo>(dotEval, returnType);
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
