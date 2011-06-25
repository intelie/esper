package com.espertech.esper.core;

/**
 * Statement metadata factory.
 */
public interface StatementMetadataFactory
{
    public StatementMetadata create(StatementMetadataFactoryContext context);
}
