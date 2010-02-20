package com.espertech.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;

public class MatchRecognizeClause implements Serializable {

    private List<Expression> partitionExpressions = new ArrayList<Expression>();
    private List<SelectClauseExpression> measures = new ArrayList<SelectClauseExpression>();
    private boolean all;
    private MatchRecognizeSkipClause skipClause = MatchRecognizeSkipClause.PAST_LAST_ROW;
    private MatchRecognizeRegEx pattern;
    private MatchRecognizeIntervalClause intervalClause;
    private List<MatchRecognizeDefine> defines = new ArrayList<MatchRecognizeDefine>();

    public MatchRecognizeClause() {
    }

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
    public void toEPL(StringWriter writer)
    {
        writer.write(" match_recognize (");

        if (partitionExpressions.size() > 0) {
            String delimiter = "";
            writer.write(" partition by ");
            for (Expression part : partitionExpressions) {
                writer.write(delimiter);
                part.toEPL(writer, ExpressionPrecedenceEnum.MINIMUM);
                delimiter = ", ";
            }
        }

        String delimiter = "";
        writer.write(" measures ");
        for (SelectClauseExpression part : measures) {
            writer.write(delimiter);
            part.toEPLElement(writer);
            delimiter = ", ";
        }

        if (all) {
            writer.write(" all matches");
        }

        if (skipClause != MatchRecognizeSkipClause.PAST_LAST_ROW) {
            writer.write(" after match skip " + skipClause.getText());
        }

        writer.write(" pattern (");
        pattern.writeEPL(writer);
        writer.write(")");

        if ((intervalClause != null) && (intervalClause.getExpression() != null)){
            writer.write(" interval ");
            intervalClause.getExpression().toEPL(writer, ExpressionPrecedenceEnum.MINIMUM);
        }

        delimiter = "";
        writer.write(" define ");
        for (MatchRecognizeDefine def : defines) {
            writer.write(delimiter);
            writer.write(def.getName());
            writer.write(" as ");
            def.getExpression().toEPL(writer, ExpressionPrecedenceEnum.MINIMUM);
            delimiter = ", ";
        }
        writer.write(")");
    }

    public List<Expression> getPartitionExpressions() {
        return partitionExpressions;
    }

    public void setPartitionExpressions(List<Expression> partitionExpressions) {
        this.partitionExpressions = partitionExpressions;
    }

    public List<SelectClauseExpression> getMeasures() {
        return measures;
    }

    public void setMeasures(List<SelectClauseExpression> measures) {
        this.measures = measures;
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public MatchRecognizeSkipClause getSkip() {
        return skipClause;
    }

    public void setSkip(MatchRecognizeSkipClause skipClause) {
        this.skipClause = skipClause;
    }

    public MatchRecognizeSkipClause getSkipClause() {
        return skipClause;
    }

    public void setSkipClause(MatchRecognizeSkipClause skipClause) {
        this.skipClause = skipClause;
    }

    public List<MatchRecognizeDefine> getDefines() {
        return defines;
    }

    public void setDefines(List<MatchRecognizeDefine> defines) {
        this.defines = defines;
    }

    public MatchRecognizeIntervalClause getIntervalClause() {
        return intervalClause;
    }

    public void setIntervalClause(MatchRecognizeIntervalClause intervalClause) {
        this.intervalClause = intervalClause;
    }

    public MatchRecognizeRegEx getPattern() {
        return pattern;
    }

    public void setPattern(MatchRecognizeRegEx pattern) {
        this.pattern = pattern;
    }
}
