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

package com.espertech.esper.epl.core;

import com.espertech.esper.epl.view.OutputCallback;
import com.espertech.esper.epl.view.OutputCondition;

public class PerGroupOutputState implements OutputCallback
{
    private boolean hold;
    private OutputCondition outputCondition;

    public boolean isHold()
    {
        return hold;
    }

    public void setHold(boolean hold)
    {
        this.hold = hold;
    }

    public OutputCondition getOutputCondition()
    {
        return outputCondition;
    }

    public void setOutputCondition(OutputCondition outputCondition)
    {
        this.outputCondition = outputCondition;
    }

    public void continueOutputProcessing(boolean doOutput, boolean forceUpdate)
    {
        hold = false;
    }
}
