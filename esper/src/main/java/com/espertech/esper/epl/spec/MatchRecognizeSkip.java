package com.espertech.esper.epl.spec;

public class MatchRecognizeSkip {
    private MatchRecognizeSkipEnum skip;

    public MatchRecognizeSkip(MatchRecognizeSkipEnum skip) {
        this.skip = skip;
    }

    public MatchRecognizeSkipEnum getSkip() {
        return skip;
    }

    public void setSkip(MatchRecognizeSkipEnum skip) {
        this.skip = skip;
    }
}
