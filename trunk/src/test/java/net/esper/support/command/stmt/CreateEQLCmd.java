package net.esper.support.command.stmt;

import net.esper.client.EPServiceProvider;

public class CreateEQLCmd implements StmtLevelCommand
{
    private String eql;

    public CreateEQLCmd(String eql)
    {
        this.eql = eql;
    }

    public void execute(EPServiceProvider engine)
    {
        engine.getEPAdministrator().createEQL(eql);
    }

    public String toString()
    {
        return this.getClass().getSimpleName() +
                " eql=" + eql;
    }    
}
