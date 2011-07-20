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

public class DotMethodFPParam {

    private final int lambdaParamNum; // 0 means not a lambda expression expected, 1 means "x=>", 2 means "(x,y)=>"
    private final String description;
    private final DotMethodFPParamTypeEnum type;
    private final Class specificType;

    public DotMethodFPParam(int lambdaParamNum, String description, DotMethodFPParamTypeEnum type) {
        this.lambdaParamNum = lambdaParamNum;
        this.description = description;
        this.type = type;
        this.specificType = null;
        if (type == DotMethodFPParamTypeEnum.SPECIFIC) {
            throw new IllegalArgumentException("Invalid ctor for specific-type parameter");
        }
    }

    public DotMethodFPParam(String description, DotMethodFPParamTypeEnum type, Class specificType) {
        this.description = description;
        this.type = type;
        this.specificType = specificType;
        this.lambdaParamNum = 0;
    }

    public int getLambdaParamNum() {
        return lambdaParamNum;
    }

    public String getDescription() {
        return description;
    }

    public DotMethodFPParamTypeEnum getType() {
        return type;
    }

    public Class getSpecificType() {
        return specificType;
    }
}
