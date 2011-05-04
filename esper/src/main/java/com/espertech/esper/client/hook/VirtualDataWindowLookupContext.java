package com.espertech.esper.client.hook;

import com.espertech.esper.epl.lookup.SubordPropPlan;

import java.util.Collections;
import java.util.List;

/**
 * Context passed to {@link VirtualDataWindow} upon obtaining a lookup strategy for use by an EPL statement
 * that queries the virtual data window.
 * <p>
 * Represents an analysis of correlation information provided in the where-clause of the querying EPL statement (join, subquery etc.).
 * Hash-fields are always operator-equals semantics. Btree fields require sorted access as the operator is always
 * a range or relational(>, <, >=, <=) operator.
 * <p>
 * For example, the query "select * from MyVirtualDataWindow, MyTrigger where prop = trigger and prop2 between trigger1 and trigger2"
 * indicates a single hash-field "prop" and a single btree field "prop2" with a range operator.
 */
public class VirtualDataWindowLookupContext {
    private String namedWindowName;
    private List<VirtualDataWindowLookupFieldDesc> hashFields;
    private List<VirtualDataWindowLookupFieldDesc> btreeFields;

    /**
     * Ctor.
     * @param hashFields operator-equals semantics fields
     * @param btreeFields sorted-access fields, check the {@link VirtualDataWindowLookupOp} operator for what range or relational-operator applies
     */
    public VirtualDataWindowLookupContext(String namedWindowName,
                                          List<VirtualDataWindowLookupFieldDesc> hashFields,
                                          List<VirtualDataWindowLookupFieldDesc> btreeFields) {
        this.namedWindowName = namedWindowName;
        this.hashFields = Collections.unmodifiableList(hashFields);
        this.btreeFields = Collections.unmodifiableList(btreeFields);
    }

    public String getNamedWindowName() {
        return namedWindowName;
    }

    /**
     * Returns the list of hash field descriptors.
     * @return hash fields
     */
    public List<VirtualDataWindowLookupFieldDesc> getHashFields() {
        return hashFields;
    }

    /**
     * Returns the list of btree field descriptors.
     * @return btree fields
     */
    public List<VirtualDataWindowLookupFieldDesc> getBtreeFields() {
        return btreeFields;
    }
}
