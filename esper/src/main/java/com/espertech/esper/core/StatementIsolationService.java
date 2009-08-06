package com.espertech.esper.core;

import com.espertech.esper.client.EPServiceProviderIsolated;
import com.espertech.esper.client.EPStatement;

public interface StatementIsolationService
{
    public  EPServiceProviderIsolated getIsolationUnit(String name, Integer optionalUnitId);

    public void destroy();

    public String[] getIsolationUnitNames();

    public void beginIsolatingStatements(String name, int unitId, EPStatement[] stmt);
    public void commitIsolatingStatements(String name, int unitId, EPStatement[] stmt);
    public void rollbackIsolatingStatements(String name, int unitId, EPStatement[] stmt);

    public void beginUnisolatingStatements(String name, int unitId, EPStatement[] stmt);
    public void commitUnisolatingStatements(String name, int unitId, EPStatement[] stmt);
    public void rollbackUnisolatingStatements(String name, int unitId, EPStatement[] stmt);

    public void newStatement(String stmtId, String stmtName, EPIsolationUnitServices isolatedServices);
}
