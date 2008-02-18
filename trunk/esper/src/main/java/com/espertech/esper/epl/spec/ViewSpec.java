/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import java.util.List;

/**
 * Specification for a view object consists of a namespace, name and view object parameters. 
 */
public final class ViewSpec extends ObjectSpec
{
    /**
     * Constructor.
     * @param namespace if the namespace the object is in
     * @param objectName is the name of the object
     * @param objectParameters is a list of values representing the object parameters
     */
    public ViewSpec(String namespace, String objectName, List<Object> objectParameters)
    {
        super(namespace, objectName, objectParameters);
    }
}
