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
