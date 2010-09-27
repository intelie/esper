/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.terminal.jse.listener;

/**
 * A generic interface that decorelates EPA from complex event processing
 * <p/>
 * In the JEE implementation this helps encapsulating JMS logic.
 */
public interface ComplexEventListener {

    public void onComplexEvent(String event);
}
