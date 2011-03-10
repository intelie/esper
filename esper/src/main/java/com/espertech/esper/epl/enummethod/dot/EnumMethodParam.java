package com.espertech.esper.epl.enummethod.dot;

public class EnumMethodParam {

    private int lambdaParamNum; // 0 means not a lambda expression expected, 1 means "x=>", 2 means "(x,y)=>"
    private String description;
    private EnumMethodEnumParamType type;

    public EnumMethodParam(int lambdaParamNum, String description, EnumMethodEnumParamType type) {
        this.lambdaParamNum = lambdaParamNum;
        this.description = description;
        this.type = type;
    }

    public int getLambdaParamNum() {
        return lambdaParamNum;
    }

    public String getDescription() {
        return description;
    }

    public EnumMethodEnumParamType getType() {
        return type;
    }
}
