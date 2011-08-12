/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;

/**
 * Atom representing an expression for use in match-recognize.
 * <p>
 * Event row regular expressions are organized into a tree-like structure with nodes representing sub-expressions.
 */
public class MatchRecognizeRegExAtom extends MatchRecognizeRegEx implements Serializable
{
    private static final long serialVersionUID = -7673341936779816149L;
    
    private String name;
    private MatchRecogizePatternElementType type;

    /**
     * Ctor.
     */
    public MatchRecognizeRegExAtom() {
    }

    /**
     * Ctor.
     * @param name of variable
     * @param type multiplicity
     */
    public MatchRecognizeRegExAtom(String name, MatchRecogizePatternElementType type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Returns variable name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets variable name.
     * @param name variable name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns multiplicity.
     * @return multiplicity
     */
    public MatchRecogizePatternElementType getType() {
        return type;
    }

    /**
     * Sets multiplicity.
     * @param type multiplicity
     */
    public void setType(MatchRecogizePatternElementType type) {
        this.type = type;
    }

    public void writeEPL(StringWriter writer) {
        writer.write(name);
        writer.write(type.getText());
    }
}