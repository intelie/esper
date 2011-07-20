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

package com.espertech.esper.client.annotation;

/**
 * Use this annotation to install a statement-specific hook or callback at time of statement creation.
 *
 * See {@link HookType} to the types of hooks that may be installed.
 */
public @interface Hook
{
    /**
     * Returns the simple class name (using imports) or fully-qualified class name of the hook.
     * @return class name
     */
    String hook();

    /**
     * Returns hook type.
     * @return hook type
     */
    HookType type();    
}
