package net.esper.support.eql.parse;

import net.esper.eql.parse.EQLTreeWalker;
import net.esper.eql.core.EngineImportServiceImpl;
import net.esper.eql.core.EngineImportService;
import net.esper.eql.variable.VariableService;
import net.esper.eql.variable.VariableServiceImpl;
import net.esper.pattern.PatternObjectResolutionServiceImpl;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

public class SupportEQLTreeWalkerFactory
{
    public static EQLTreeWalker makeWalker(Tree tree, EngineImportService engineImportService, VariableService variableService)
    {
        return new EQLTreeWalker(new CommonTreeNodeStream(tree), engineImportService, variableService, System.currentTimeMillis());
    }

    public static EQLTreeWalker makeWalker(Tree tree)
    {
        return makeWalker(tree, new EngineImportServiceImpl(), new VariableServiceImpl(0, null, null));
    }
}
