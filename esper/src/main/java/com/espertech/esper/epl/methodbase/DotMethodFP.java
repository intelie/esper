package com.espertech.esper.epl.methodbase;

import java.io.StringWriter;

public class DotMethodFP {

    private final DotMethodFPInputEnum input;
    private final DotMethodFPParam[] params;

    public DotMethodFP(DotMethodFPInputEnum input, DotMethodFPParam... params) {
        this.input = input;
        this.params = params;
    }

    public DotMethodFPInputEnum getInput() {
        return input;
    }

    public DotMethodFPParam[] getParams() {
        return params;
    }

    public String toStringFootprint() {
        if (params.length == 0) {
            return "no parameters";
        }
        StringWriter buf = new StringWriter();
        String delimiter = "";
        for (DotMethodFPParam param : params) {
            buf.append(delimiter);

            if (param.getLambdaParamNum() == 0) {
                buf.append("an (non-lambda)");
            }
            else if (param.getLambdaParamNum() == 1) {
                buf.append("a lambda");
            }
            else {
                buf.append("a " + param.getLambdaParamNum() + "-parameter lambda");
            }
            buf.append(" expression");
            buf.append(" providing ");
            buf.append(param.getDescription());
            delimiter = " and ";
        }
        return buf.toString();
    }

    public static String toStringProvided(DotMethodFPProvided provided) {
        if (provided.getParams().length == 0) {
            return "no parameters";
        }
        StringWriter buf = new StringWriter();
        String delimiter = "";
        for (DotMethodFPProvidedParam param : provided.getParams()) {
            buf.append(delimiter);

            if (param.getLambdaParamNum() == 0) {
                buf.append("an (non-lambda)");
            }
            else if (param.getLambdaParamNum() == 1) {
                buf.append("a lambda");
            }
            else {
                buf.append("a " + param.getLambdaParamNum() + "-parameter lambda");
            }
            buf.append(" expression");
            delimiter = " and ";
        }
        return buf.toString();
    }

}
