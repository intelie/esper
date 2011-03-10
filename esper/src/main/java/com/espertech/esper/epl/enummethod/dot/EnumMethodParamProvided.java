package com.espertech.esper.epl.enummethod.dot;

public class EnumMethodParamProvided {

    private int lambdaParamNum; // 0 means not a lambda expression expected, 1 means "x=>", 2 means "(x,y)=>"

    public EnumMethodParamProvided(int lambdaParamNum) {
        this.lambdaParamNum = lambdaParamNum;
    }

    public int getLambdaParamNum() {
        return lambdaParamNum;
    }
}
