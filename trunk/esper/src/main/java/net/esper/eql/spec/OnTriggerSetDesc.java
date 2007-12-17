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

    /**
     * Adds a variable assignment.
     * @param assignment to add
     */
    public void addAssignment(OnTriggerSetAssignment assignment)
    {
        assignments.add(assignment);
    }

    /**
     * Returns a list of all variables assignment by the on-set
     * @return list of assignments
     */
    public List<OnTriggerSetAssignment> getAssignments()
    {
        return assignments;
    }
}
