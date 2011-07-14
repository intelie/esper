/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.datetime.eval.ExprDotNodeFilterAnalyzerDTIntervalDesc;

public class ExprDotNodeRealizedChain
{
    private final ExprDotEval[] chain;
    private final ExprDotEval[] chainWithUnpack;
    private final ExprDotNodeFilterAnalyzerDTIntervalDesc filterAnalyzerDesc;

    public ExprDotNodeRealizedChain(ExprDotEval[] chain, ExprDotEval[] chainWithUnpack, ExprDotNodeFilterAnalyzerDTIntervalDesc filterAnalyzerDesc) {
        this.chain = chain;
        this.chainWithUnpack = chainWithUnpack;
        this.filterAnalyzerDesc = filterAnalyzerDesc;
    }

    public ExprDotEval[] getChain() {
        return chain;
    }

    public ExprDotEval[] getChainWithUnpack() {
        return chainWithUnpack;
    }

    public ExprDotNodeFilterAnalyzerDTIntervalDesc getFilterAnalyzerDesc() {
        return filterAnalyzerDesc;
    }
}
