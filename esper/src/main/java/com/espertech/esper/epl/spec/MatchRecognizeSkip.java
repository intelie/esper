package com.espertech.esper.epl.spec;

import com.espertech.esper.util.MetaDefItem;

import java.io.Serializable;

public class MatchRecognizeSkip implements MetaDefItem, Serializable
{
    private MatchRecognizeSkipEnum skip;
    private static final long serialVersionUID = 579228626022249216L;

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
