package com.espertech.esper.epl.methodbase;

public class DotMethodFPProvidedParam {

    private int lambdaParamNum; // 0 means not a lambda expression expected, 1 means "x=>", 2 means "(x,y)=>"
    private Class returnType;

    public DotMethodFPProvidedParam(int lambdaParamNum, Class returnType) {
        this.lambdaParamNum = lambdaParamNum;
        this.returnType = returnType;
    }

    public int getLambdaParamNum() {
        return lambdaParamNum;
    }

    public Class getReturnType() {
        return returnType;
    }
}
