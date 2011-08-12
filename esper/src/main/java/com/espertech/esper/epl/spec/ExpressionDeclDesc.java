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

package com.espertech.esper.epl.spec;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExpressionDeclDesc implements Serializable {

    private static final long serialVersionUID = -8155216999087913248L;

    private List<ExpressionDeclItem> expressions = new ArrayList<ExpressionDeclItem>();

    public List<ExpressionDeclItem> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<ExpressionDeclItem> expressions) {
        this.expressions = expressions;
    }
}
