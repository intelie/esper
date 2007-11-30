package net.esper.eql.spec;

import java.util.List;
import java.util.ArrayList;

/**
 * Specification for the on-set statement.
 */
public class OnTriggerSetDesc extends OnTriggerDesc
{
    private List<OnTriggerSetAssignment> assignments;
    
    /**
     * Ctor.
     */
    public OnTriggerSetDesc()
    {
        super(OnTriggerType.ON_SET);
        assignments = new ArrayList<OnTriggerSetAssignment>();
    }

    public void addAssignment(OnTriggerSetAssignment assignment)
    {
        assignments.add(assignment);
    }

    public List<OnTriggerSetAssignment> getAssignments()
    {
        return assignments;
    }
}
