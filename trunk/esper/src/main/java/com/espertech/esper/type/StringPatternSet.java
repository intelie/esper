package com.espertech.esper.type;

import java.io.Serializable;

public interface StringPatternSet extends Serializable
{
    public boolean match(String stringToMatch);    
}
