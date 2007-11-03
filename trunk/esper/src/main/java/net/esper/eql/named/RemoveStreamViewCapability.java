package net.esper.eql.named;

import net.esper.view.ViewCapability;
import net.esper.view.ViewFactory;
import net.esper.core.StatementContext;

import java.util.List;

public class RemoveStreamViewCapability implements ViewCapability
{
    public boolean inspect(int streamNumber, List<ViewFactory> viewFactories, StatementContext statementContext)
    {
        return true;
    }
}
