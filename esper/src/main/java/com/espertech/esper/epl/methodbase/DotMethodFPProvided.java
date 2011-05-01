package com.espertech.esper.epl.methodbase;

public class DotMethodFPProvided {

    private final DotMethodFPProvidedParam[] params;

    public DotMethodFPProvided(DotMethodFPProvidedParam[] params) {
        this.params = params;
    }

    public DotMethodFPProvidedParam[] getParams() {
        return params;
    }
}
