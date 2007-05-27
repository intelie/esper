package net.esper.support.eql.parse;

import net.esper.eql.parse.EQLTreeWalker;
import net.esper.eql.core.EngineImportServiceImpl;
import net.esper.eql.core.EngineImportService;
import net.esper.pattern.PatternObjectResolutionServiceImpl;

public class SupportEQLTreeWalkerFactory
{
    public static EQLTreeWalker makeWalker(EngineImportService engineImportService)
    {
        return new EQLTreeWalker(engineImportService, new PatternObjectResolutionServiceImpl(null));
    }

    public static EQLTreeWalker makeWalker()
    {
        return makeWalker(new EngineImportServiceImpl());
    }
}
