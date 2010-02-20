/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import com.espertech.esper.rowregex.RowRegexExprNode;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.StringWriter;

public class MatchRecognizeRegExPlaceholder extends MatchRecognizeRegEx implements Serializable
{
    public void writeEPL(StringWriter writer) {
        if ((this.getChildren() == null) || (this.getChildren().size() == 0)) {
            return;
        }
        this.getChildren().get(0).writeEPL(writer);
    }
}