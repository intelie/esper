package com.espertech.esper.type;

import java.io.Serializable;

public interface StringPatternSet extends Serializable
{
    public Boolean isInclude(String stringToMatch);
    public Boolean isExclude(String stringToMatch);
}
