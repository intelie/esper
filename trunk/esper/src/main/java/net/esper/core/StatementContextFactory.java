package net.esper.core;

import net.esper.eql.spec.OnDeleteDesc;
import net.esper.eql.spec.CreateWindowDesc;

import java.util.Map;

/**
 * Interface for a factory class that makes statement context specific to a statement.
 */
public interface StatementContextFactory
{
    /**
     * Create a new statement context consisting of statement-level services.
     * @param statementId is the statement is
     * @param statementName is the statement name
     * @param expression is the statement expression
     * @param engineServices is engine services
     * @param optAdditionalContext addtional context to pass to the statement
     * @param optOnDeleteDesc the on-delete statement descriptor for named window context creation
     * @param optCreateWindowDesc the create-window statement descriptor for named window context creation
     * @return statement context
     */
    public StatementContext makeContext(String statementId, 
                                        String statementName,
                                        String expression,
                                        EPServicesContext engineServices,
                                        Map<String, Object> optAdditionalContext,
                                        OnDeleteDesc optOnDeleteDesc,
                                        CreateWindowDesc optCreateWindowDesc);
}
