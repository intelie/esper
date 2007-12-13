package net.esper.support.eql.parse;

import net.esper.eql.parse.EQLTreeWalker;
import net.esper.eql.core.EngineImportServiceImpl;
import net.esper.eql.core.EngineImportService;
import net.esper.eql.variable.VariableService;
import net.esper.eql.variable.VariableServiceImpl;
import net.esper.pattern.PatternObjectResolutionServiceImpl;

public class SupportEQLTreeWalkerFactory
{
    public static EQLTreeWalker makeWalker(EngineImportService engineImportService, VariableService variableService)
    {
        return new EQLTreeWalker(engineImportService, variableService);
    }

    public static EQLTreeWalker makeWalker()
    {
        return makeWalker(new EngineImportServiceImpl(), new VariableServiceImpl(0, null, null));
    }
}
