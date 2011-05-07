package com.espertech.esper.core.soda;

import com.espertech.esper.client.soda.*;

import java.util.List;
import java.util.ArrayList;

public interface SODAAnalyzerPatternCollector
{
    public void visit(PatternExpr pattern);
}