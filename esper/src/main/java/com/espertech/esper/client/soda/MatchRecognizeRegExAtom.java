/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * Atom representing an expression for use in match-recognize.
 * <p>
 * Event row regular expressions are organized into a tree-like structure with nodes representing sub-expressions.
 */
public class MatchRecognizeRegExAtom extends MatchRecognizeRegEx implements Serializable
{
    private String name;
    private MatchRecogizePatternElementType type;

    public MatchRecognizeRegExAtom() {
    }

    public MatchRecognizeRegExAtom(String name, MatchRecogizePatternElementType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MatchRecogizePatternElementType getType() {
        return type;
    }

    public void setType(MatchRecogizePatternElementType type) {
        this.type = type;
    }

    public void writeEPL(StringWriter writer) {
        writer.write(name);
        writer.write(type.getText());
    }
}