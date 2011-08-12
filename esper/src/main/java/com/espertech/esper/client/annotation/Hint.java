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
 * Annotation for providing a statement execution hint.
 * <p>
 * Hints are providing instructions that can change latency, throughput or memory requirements of a statement. 
 */
public @interface Hint {

    /**
     * Hint keyword(s), comma-separated.
     * @return keywords
     */
    String value();    
}
