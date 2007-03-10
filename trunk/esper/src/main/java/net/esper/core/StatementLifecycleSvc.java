package net.esper.core;

import net.esper.client.EPStatement;
import net.esper.client.EPException;
import net.esper.client.UpdateListener;
import net.esper.eql.spec.StatementSpecRaw;
import net.esper.eql.spec.StatementSpecCompiled;

import java.util.Set;

public interface StatementLifecycleSvc
{
    public EPStatement createAndStart(StatementSpecCompiled statementSpec, String expression, boolean isPattern, String optStatementName);

    public void start(String statementId);
    public void stop(String statementId);
    public void destroy(String statementId);

    public EPStatement getStatementByName(String name);
    public String[] getStatementNames();
    public void startAllStatements() throws EPException;
    public void stopAllStatements() throws EPException;
    public void destroyAllStatements() throws EPException;

    public void updatedListeners(String statementId, Set<UpdateListener> listeners);
}
