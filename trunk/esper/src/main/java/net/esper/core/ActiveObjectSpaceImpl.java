package net.esper.core;

public class ActiveObjectSpaceImpl implements ActiveObjectSpace
{
    private StatementLifecycleSvc statementSvc;

    public ActiveObjectSpaceImpl(StatementLifecycleSvc statementSvc)
    {
        this.statementSvc = statementSvc;
    }

    public void write(Object activeObject)
    {
        // TODO
        // (1) Minimal version: mapping to select to method, performance, base API
        // (2) Support parameterized annotations
        // (3) Support insert into
        // (4) Support start/stop
        // (5) Handle iterator
        // (6) Handle persistence

        // Interrogate all methods; get annotations
        // Compile and start statement; put Map<Object, List<EPStatement>>
        // Take: stops and removes statements

        
    }

    public void take(Object activeObject)
    {

    }
}
