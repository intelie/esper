package com.espertech.esper.support.epl.parse;

import com.espertech.esper.epl.parse.EPLTreeWalker;
import com.espertech.esper.epl.core.EngineImportServiceImpl;
import com.espertech.esper.epl.core.EngineImportService;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.epl.variable.VariableServiceImpl;
import com.espertech.esper.epl.spec.SelectClauseStreamSelectorEnum;
import com.espertech.esper.schedule.SchedulingServiceImpl;
import com.espertech.esper.support.schedule.SupportSchedulingServiceImpl;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

public class SupportEPLTreeWalkerFactory
{
    public static EPLTreeWalker makeWalker(Tree tree, EngineImportService engineImportService, VariableService variableService)
    {
        return new EPLTreeWalker(new CommonTreeNodeStream(tree), engineImportService, variableService, new SupportSchedulingServiceImpl(), SelectClauseStreamSelectorEnum.ISTREAM_ONLY, "uri");
    }

    public static EPLTreeWalker makeWalker(Tree tree)
    {
        return makeWalker(tree, new EngineImportServiceImpl(), new VariableServiceImpl(0, null, null));
    }
}
