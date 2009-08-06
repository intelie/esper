/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.rowregex;

import com.espertech.esper.rowregex.RegexNFATypeEnum;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RowRegexExprNodeAtom extends RowRegexExprNode
{
    private static final Log log = LogFactory.getLog(RowRegexExprNodeAtom.class);

    private final String tag;
    private final RegexNFATypeEnum type;
    private static final long serialVersionUID = -4844175686289523214L;

    public RowRegexExprNodeAtom(String tag, RegexNFATypeEnum type) {
        this.tag = tag;
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public RegexNFATypeEnum getType() {
        return type;
    }

    public String toExpressionString() {
        return tag + type.getOptionalPostfix();
    }
}
