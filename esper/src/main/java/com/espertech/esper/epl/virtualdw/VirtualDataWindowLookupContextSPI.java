package com.espertech.esper.epl.virtualdw;

import com.espertech.esper.client.EventType;
import com.espertech.esper.client.hook.VirtualDataWindowLookupContext;
import com.espertech.esper.client.hook.VirtualDataWindowLookupFieldDesc;
import com.espertech.esper.epl.lookup.SubordPropPlan;

import java.util.List;

public class VirtualDataWindowLookupContextSPI extends VirtualDataWindowLookupContext {
    private SubordPropPlan joinDesc;
    private boolean forceTableScan;
    private EventType[] outerTypePerStream;
    private String accessedByStatementName;
    private int accessedByStatementSequenceNum;

    public VirtualDataWindowLookupContextSPI(String namedWindowName, List<VirtualDataWindowLookupFieldDesc> hashFields, List<VirtualDataWindowLookupFieldDesc> btreeFields, SubordPropPlan joinDesc, boolean forceTableScan, EventType[] outerTypePerStream, String accessedByStatementName, int accessedByStatementSequenceNum) {
        super(namedWindowName, hashFields, btreeFields);
        this.joinDesc = joinDesc;
        this.forceTableScan = forceTableScan;
        this.outerTypePerStream = outerTypePerStream;
        this.accessedByStatementName = accessedByStatementName;
        this.accessedByStatementSequenceNum = accessedByStatementSequenceNum;
    }

    public SubordPropPlan getJoinDesc() {
        return joinDesc;
    }

    public boolean isForceTableScan() {
        return forceTableScan;
    }

    public EventType[] getOuterTypePerStream() {
        return outerTypePerStream;
    }

    public String getAccessedByStatementName() {
        return accessedByStatementName;
    }

    public int getAccessedByStatementSequenceNum() {
        return accessedByStatementSequenceNum;
    }
}
