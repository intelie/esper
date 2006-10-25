package net.esper.support.command.stmt;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;

public class AddListenerCmd implements StmtLevelCommand
{
    private int statementNumber;

    public AddListenerCmd(int statementNumber)
    {
        this.statementNumber = statementNumber;
    }

    public void execute(EPServiceProvider engine)
    {
        EPStatement stmt = engine.getEPAdministrator().getStatements().get(statementNumber);
        stmt.addListener(new SupportUpdateListener());
    }

    public String toString()
    {
        return this.getClass().getSimpleName() + " statementNumber=" + statementNumber; 
    }
}
