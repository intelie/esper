package com.espertech.esper.epl.spec;

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
     * @param assignments is a list of assignments
     */
    public OnTriggerSetDesc(List<OnTriggerSetAssignment> assignments)
    {
        super(OnTriggerType.ON_SET);
        this.assignments = assignments;
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
