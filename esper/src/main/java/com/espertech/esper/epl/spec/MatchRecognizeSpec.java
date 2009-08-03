package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.rowregex.RowRegexExprNode;

import java.util.List;
import java.util.ArrayList;

public class MatchRecognizeSpec {

    private List<ExprNode> partitionByExpressions;
    private List<MatchRecognizeMeasureItem> measures;
    private RowRegexExprNode pattern;
    private List<MatchRecognizeDefineItem> defines;
    private boolean isAllMatches;
    private MatchRecognizeSkip skip;
    private MatchRecognizeInterval interval;

    public MatchRecognizeSpec() {
        partitionByExpressions = new ArrayList<ExprNode>();
        measures = new ArrayList<MatchRecognizeMeasureItem>();
        defines = new ArrayList<MatchRecognizeDefineItem>();
        skip = new MatchRecognizeSkip(MatchRecognizeSkipEnum.PAST_LAST_ROW);
    }

    public MatchRecognizeInterval getInterval()
    {
        return interval;
    }

    public void setInterval(MatchRecognizeInterval interval)
    {
        this.interval = interval;
    }

    public boolean isAllMatches() {
        return isAllMatches;
    }

    public void setAllMatches(boolean allMatches) {
        isAllMatches = allMatches;
    }

    public List<ExprNode> getPartitionByExpressions() {
        return partitionByExpressions;
    }

    public void setPartitionByExpressions(List<ExprNode> partitionByExpressions) {
        this.partitionByExpressions = partitionByExpressions;
    }

    public void addMeasureItem(MatchRecognizeMeasureItem item)
    {
        measures.add(item);   
    }

    public List<MatchRecognizeDefineItem> getDefines() {
        return defines;
    }

    public void setPattern(RowRegexExprNode pattern) {
        this.pattern = pattern;
    }

    public void addDefine(MatchRecognizeDefineItem define) {
        this.defines.add(define);
    }

    public List<MatchRecognizeMeasureItem> getMeasures() {
        return measures;
    }

    public RowRegexExprNode getPattern() {
        return pattern;
    }

    public MatchRecognizeSkip getSkip() {
        return skip;
    }

    public void setSkip(MatchRecognizeSkip skip) {
        this.skip = skip;
    }
}
