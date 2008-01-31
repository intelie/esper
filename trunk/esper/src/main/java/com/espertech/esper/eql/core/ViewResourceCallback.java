/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.eql.core;

/**
 * Callback for use by expression nodes to receive view resources.
 */
public interface ViewResourceCallback
{
    /**
     * Supplies view resource.
     * @param resource supplied
     */
    public void setViewResource(Object resource);
}
