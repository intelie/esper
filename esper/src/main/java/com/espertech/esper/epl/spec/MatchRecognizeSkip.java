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

import com.espertech.esper.util.MetaDefItem;

import java.io.Serializable;

/**
 * Specification for the skip-part of match_recognize.
 */
public class MatchRecognizeSkip implements MetaDefItem, Serializable
{
    private MatchRecognizeSkipEnum skip;
    private static final long serialVersionUID = 579228626022249216L;

    /**
     * Ctor.
     * @param skip enum
     */
    public MatchRecognizeSkip(MatchRecognizeSkipEnum skip) {
        this.skip = skip;
    }

    /**
     * Skip enum.
     * @return skip value
     */
    public MatchRecognizeSkipEnum getSkip() {
        return skip;
    }

    /**
     * Sets the skip value.
     * @param skip to set
     */
    public void setSkip(MatchRecognizeSkipEnum skip)
    {
        this.skip = skip;
    }
}
