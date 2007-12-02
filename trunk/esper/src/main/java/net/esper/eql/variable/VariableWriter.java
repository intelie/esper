package net.esper.eql.variable;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.io.StringWriter;

public class VariableWriter
{
    private final Class type;
    private final int variableNumber;
    private final VariableService variableService;

    public VariableWriter(int variableNumber, Class type, VariableService variableService)
    {
        this.variableNumber = variableNumber;
        this.variableService = variableService;
        this.type = type;
    }

    public void write(Object newValue)
    {
        variableService.write(variableNumber, newValue);
    }

    public Class getType()
    {
        return type;
    }
}
