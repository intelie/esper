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

package com.espertech.esper.regression.rowrecog;

public class SupportTestCaseItem {
    private String testdata;
    private String[] expected;

    public SupportTestCaseItem(String testdata, String[] expected) {
        this.testdata = testdata;
        this.expected = expected;
    }

    public String getTestdata() {
        return testdata;
    }

    public String[] getExpected() {
        return expected;
    }
}
