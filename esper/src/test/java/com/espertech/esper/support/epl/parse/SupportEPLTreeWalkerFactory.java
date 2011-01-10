package com.espertech.esper.support.epl.parse;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.epl.core.EngineImportService;
import com.espertech.esper.epl.core.EngineImportServiceImpl;
import com.espertech.esper.epl.parse.EPLTreeWalker;
import com.espertech.esper.epl.spec.SelectClauseStreamSelectorEnum;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.epl.variable.VariableServiceImpl;
import com.espertech.esper.pattern.PatternNodeFactoryImpl;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.schedule.SupportSchedulingServiceImpl;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;

public class SupportEPLTreeWalkerFactory
{
    public static EPLTreeWalker makeWalker(Tree tree, EngineImportService engineImportService, VariableService variableService)
    {
        return new EPLTreeWalker(new CommonTreeNodeStream(tree), engineImportService, variableService, new SupportSchedulingServiceImpl(), SelectClauseStreamSelectorEnum.ISTREAM_ONLY, "uri", new Configuration(), new PatternNodeFactoryImpl());
    }

    public static EPLTreeWalker makeWalker(Tree tree)
    {
        return makeWalker(tree, new EngineImportServiceImpl(true), new VariableServiceImpl(0, null, SupportEventAdapterService.getService(), null));
    }
}
