package com.espertech.esper.regression.rowrecog;

import java.util.ArrayList;
import java.util.List;

public class SupportTestCaseHolder {

    private String measures;
    private String pattern;
    private List<SupportTestCaseItem> testcases;

    public SupportTestCaseHolder(String measures, String pattern) {
        this.measures = measures;
        this.pattern = pattern;
        this.testcases = new ArrayList<SupportTestCaseItem>();
    }

    public String getMeasures() {
        return measures;
    }

    public String getPattern() {
        return pattern;
    }

    public List<SupportTestCaseItem> getTestCases() {
        return testcases;
    }

    public SupportTestCaseHolder add(String testdataString, String[] expected)
    {
        testcases.add(new SupportTestCaseItem(testdataString, expected));
        return this;
    }
}
