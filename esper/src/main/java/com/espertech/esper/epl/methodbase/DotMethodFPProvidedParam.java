package com.espertech.esper.epl.methodbase;

import com.espertech.esper.epl.expression.ExprNode;

public class DotMethodFPProvidedParam {

    private int lambdaParamNum; // 0 means not a lambda expression expected, 1 means "x=>", 2 means "(x,y)=>"
    private Class returnType;
    private ExprNode expression;

    public DotMethodFPProvidedParam(int lambdaParamNum, Class returnType, ExprNode expression) {
        this.lambdaParamNum = lambdaParamNum;
        this.returnType = returnType;
        this.expression = expression;
    }

    public int getLambdaParamNum() {
        return lambdaParamNum;
    }

    public Class getReturnType() {
        return returnType;
    }

    public ExprNode getExpression() {
        return expression;
    }
}
