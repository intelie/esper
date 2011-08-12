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

package com.espertech.esper.client.util;

/**
 * JSON rendering options.
 */
public class JSONRenderingOptions
{
    private boolean preventLooping;

    /**
     * Ctor.
     */
    public JSONRenderingOptions()
    {
        preventLooping = true;
    }

    /**
     * Indicator whether to prevent looping, by default set to true.
     * Set to false to allow looping in the case where nested properties may refer to themselves, for example.
     * <p>
     * The algorithm to control looping considers the combination of event type and property name for each
     * level of nested property. 
     * @return indicator whether the rendering algorithm prevents looping behavior
     */
    public boolean isPreventLooping()
    {
        return preventLooping;
    }

    /**
     * Indicator whether to prevent looping, by default set to true.
     * Set to false to allow looping in the case where nested properties may refer to themselves, for example.
     * <p>
     * The algorithm to control looping considers the combination of event type and property name for each
     * level of nested property.
     * @param preventLooping indicator whether the rendering algorithm prevents looping behavior
     * @return options object
     */
    public JSONRenderingOptions setPreventLooping(boolean preventLooping)
    {
        this.preventLooping = preventLooping;
        return this;
    }
}
