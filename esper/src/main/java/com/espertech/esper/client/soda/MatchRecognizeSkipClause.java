package com.espertech.esper.client.soda;

public enum MatchRecognizeSkipClause {
    /**
     * Skip to current row.
     */
    TO_CURRENT_ROW("to current row"),

    /**
     * Skip to next row.
     */
    TO_NEXT_ROW("to next row"),

    /**
     * Skip past last row.
     */
    PAST_LAST_ROW("past last row");

    private String text;

    MatchRecognizeSkipClause(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
