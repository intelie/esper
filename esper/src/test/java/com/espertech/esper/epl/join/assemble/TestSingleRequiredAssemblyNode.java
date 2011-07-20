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

package com.espertech.esper.epl.join.assemble;

import com.espertech.esper.support.epl.join.SupportJoinProcNode;

import junit.framework.TestCase;

public class TestSingleRequiredAssemblyNode extends TestCase
{
    private SupportJoinProcNode parentNode;
    private BranchRequiredAssemblyNode reqNode;

    public void setUp()
    {
        reqNode = new BranchRequiredAssemblyNode(1, 3);
        parentNode = new SupportJoinProcNode(-1, 3);
        parentNode.addChild(reqNode);
    }

    public void testProcess()
    {
        // the node does nothing when asked to process as it doesn't originate events
    }

    public void testChildResult()
    {
        TestSingleOptionalAssemblyNode.testChildResult(reqNode, parentNode);
    }
}
