package com.espertech.esper.epl.enummethod.dot;

import java.io.StringWriter;

public class EnumMethodFootprint {

    private final EnumMethodInputEnum input;
    private final EnumMethodParam[] params;

    public EnumMethodFootprint(EnumMethodInputEnum input, EnumMethodParam... params) {
        this.input = input;
        this.params = params;
    }

    public EnumMethodInputEnum getInput() {
        return input;
    }

    public EnumMethodParam[] getParams() {
        return params;
    }

    public String toStringFootprint() {
        if (params.length == 0) {
            return "no parameters";
        }
        StringWriter buf = new StringWriter();
        String delimiter = "";
        for (EnumMethodParam param : params) {
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

    public static String toStringProvided(EnumMethodFootprintProvided provided) {
        if (provided.getParams().length == 0) {
            return "no parameters";
        }
        StringWriter buf = new StringWriter();
        String delimiter = "";
        for (EnumMethodParamProvided param : provided.getParams()) {
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
