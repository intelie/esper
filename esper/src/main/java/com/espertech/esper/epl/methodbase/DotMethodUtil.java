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

package com.espertech.esper.epl.methodbase;

import com.espertech.esper.epl.enummethod.dot.ExprLambdaGoesNode;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprStreamUnderlyingNode;
import com.espertech.esper.epl.expression.ExprTimePeriod;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.util.JavaClassHelper;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class DotMethodUtil {

    public static DotMethodFPProvided getProvidedFootprint(List<ExprNode> parameters) {
        List<DotMethodFPProvidedParam> params = new ArrayList<DotMethodFPProvidedParam>();
        for (ExprNode node : parameters) {
            if (!(node instanceof ExprLambdaGoesNode)) {
                params.add(new DotMethodFPProvidedParam(0, node.getExprEvaluator().getType(), node));
                continue;
            }
            ExprLambdaGoesNode goesNode = (ExprLambdaGoesNode) node;
            params.add(new DotMethodFPProvidedParam(goesNode.getGoesToNames().size(), null, goesNode));
        }
        return new DotMethodFPProvided(params.toArray(new DotMethodFPProvidedParam[params.size()]));
    }

    public static DotMethodFP validateParameters(DotMethodFP[] footprints, DotMethodTypeEnum methodType, String methodUsedName, DotMethodFPProvided providedFootprint)
        throws ExprValidationException
    {
        boolean isLambdaApplies = DotMethodTypeEnum.ENUM == methodType;
        DotMethodFP found = null;
        DotMethodFP bestMatch = null;
        for (DotMethodFP footprint : footprints) {

            DotMethodFPParam[] requiredParams = footprint.getParams();
            if (requiredParams.length != providedFootprint.getParams().length) {
                continue;
            }

            if (bestMatch == null) {    // take first if number of parameters matches
                bestMatch = footprint;
            }

            boolean paramMatch = true;
            int count = 0;
            for (DotMethodFPParam requiredParam : requiredParams) {

                DotMethodFPProvidedParam providedParam = providedFootprint.getParams()[count++];
                if (requiredParam.getLambdaParamNum() != providedParam.getLambdaParamNum()) {
                    paramMatch = false;
                }
            }

            if (paramMatch) {
                found = footprint;
                break;
            }
        }

        if (found != null) {
            validateSpecificTypes(methodUsedName, methodType, found.getParams(), providedFootprint.getParams());
            return found;
        }

        String message = "Parameters mismatch for " + methodType.getTypeName() + " method '" + methodUsedName + "', the method ";
        if (bestMatch != null) {
            StringWriter buf = new StringWriter();
            buf.append(bestMatch.toStringFootprint(isLambdaApplies));
            buf.append(", but receives ");
            buf.append(DotMethodFP.toStringProvided(providedFootprint, isLambdaApplies));
            throw new ExprValidationException(message + "requires " + buf.toString());
        }

        if (footprints.length == 1) {
            throw new ExprValidationException(message + "requires " + footprints[0].toStringFootprint(isLambdaApplies));
        }
        else {
            StringWriter buf = new StringWriter();
            String delimiter = "";
            for (DotMethodFP footprint : footprints) {
                buf.append(delimiter);
                buf.append(footprint.toStringFootprint(isLambdaApplies));
                delimiter = ", or ";
            }
            throw new ExprValidationException(message + "has multiple footprints accepting " + buf +
                    ", but receives " + DotMethodFP.toStringProvided(providedFootprint, isLambdaApplies));
        }
    }

    private static void validateSpecificTypes(String methodUsedName, DotMethodTypeEnum type, DotMethodFPParam[] foundParams, DotMethodFPProvidedParam[] params)
        throws ExprValidationException
    {
        for (int i = 0; i < foundParams.length; i++) {
            DotMethodFPParam found = foundParams[i];
            DotMethodFPProvidedParam provided = params[i];

            // Lambda-type expressions not validated here
            if (found.getLambdaParamNum() > 0) {
                continue;
            }
            validateSpecificType(methodUsedName, type, found.getType(), found.getSpecificType(), provided.getReturnType(), i, provided.getExpression());
        }
    }

    public static void validateSpecificType(String methodUsedName, DotMethodTypeEnum type, DotMethodFPParamTypeEnum expectedTypeEnum, Class expectedTypeClass, Class providedType, int parameterNum, ExprNode parameterExpression)
            throws ExprValidationException
    {
        String message = "Error validating " + type.getTypeName() + " method '" + methodUsedName + "', ";
        if (expectedTypeEnum == DotMethodFPParamTypeEnum.BOOLEAN && (!JavaClassHelper.isBoolean(providedType))) {
            throw new ExprValidationException(message + "expected a boolean-type result for expression parameter " + parameterNum + " but received " + JavaClassHelper.getClassNameFullyQualPretty(providedType));
        }
        if (expectedTypeEnum == DotMethodFPParamTypeEnum.NUMERIC && (!JavaClassHelper.isNumeric(providedType))) {
            throw new ExprValidationException(message + "expected a number-type result for expression parameter " + parameterNum + " but received " + JavaClassHelper.getClassNameFullyQualPretty(providedType));
        }
        if (expectedTypeEnum == DotMethodFPParamTypeEnum.SPECIFIC) {
            Class boxedExpectedType = JavaClassHelper.getBoxedType(expectedTypeClass);
            Class boxedProvidedType = JavaClassHelper.getBoxedType(providedType);
            if (!JavaClassHelper.isSubclassOrImplementsInterface(boxedProvidedType, boxedExpectedType)) {
                throw new ExprValidationException(message + "expected a " + boxedExpectedType.getSimpleName() + "-type result for expression parameter " + parameterNum + " but received " + JavaClassHelper.getClassNameFullyQualPretty(providedType));
            }
        }
        if (expectedTypeEnum == DotMethodFPParamTypeEnum.TIME_PERIOD_OR_SEC) {
            if (parameterExpression instanceof ExprTimePeriod || parameterExpression instanceof ExprStreamUnderlyingNode) {
                return;
            }
            if (!(JavaClassHelper.isNumeric(providedType))) {
                throw new ExprValidationException(message + "expected a time-period expression or a numeric-type result for expression parameter " + parameterNum + " but received " + JavaClassHelper.getClassNameFullyQualPretty(providedType));
            }
        }
        if (expectedTypeEnum == DotMethodFPParamTypeEnum.DATETIME) {
            if (!(JavaClassHelper.isDatetimeClass(providedType))) {
                throw new ExprValidationException(message + "expected a long-typed, Date-typed or Calendar-typed result for expression parameter " + parameterNum + " but received " + JavaClassHelper.getClassNameFullyQualPretty(providedType));
            }
        }
    }
}
