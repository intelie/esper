package com.espertech.esper.core;

import com.espertech.esper.epl.spec.OnTriggerType;
import com.espertech.esper.epl.spec.StatementSpecRaw;

/**
 * Statement metadata.
 */
public class StatementMetadataFactoryDefault implements StatementMetadataFactory
{
    public StatementMetadata create(StatementMetadataFactoryContext context) {
        return new StatementMetadata(getStatementType(context.getStatementSpec(), context.isPattern()));
    }

    public static StatementType getStatementType(StatementSpecRaw statementSpec, boolean pattern) {

        // determine statement type
        StatementType statementType = null;
        if (statementSpec.getCreateVariableDesc() != null) {
            statementType = StatementType.CREATE_VARIABLE;
        }
        else if (statementSpec.getCreateWindowDesc() != null) {
            statementType = StatementType.CREATE_WINDOW;
        }
        else if (statementSpec.getOnTriggerDesc() != null) {
            if (statementSpec.getOnTriggerDesc().getOnTriggerType() == OnTriggerType.ON_DELETE) {
                statementType = StatementType.ON_DELETE;
            }
            else if (statementSpec.getOnTriggerDesc().getOnTriggerType() == OnTriggerType.ON_UPDATE) {
                statementType = StatementType.ON_UPDATE;
            }
            else if (statementSpec.getOnTriggerDesc().getOnTriggerType() == OnTriggerType.ON_SELECT) {
                if (statementSpec.getInsertIntoDesc() != null) {
                    statementType = StatementType.ON_INSERT;
                }
                else {
                    statementType = StatementType.ON_SELECT;
                }
            }
            else if (statementSpec.getOnTriggerDesc().getOnTriggerType() == OnTriggerType.ON_SET) {
                statementType = StatementType.ON_SET;
            }
            else if (statementSpec.getOnTriggerDesc().getOnTriggerType() == OnTriggerType.ON_MERGE) {
                statementType = StatementType.ON_MERGE;
            }
            else if (statementSpec.getOnTriggerDesc().getOnTriggerType() == OnTriggerType.ON_SPLITSTREAM) {
                statementType = StatementType.ON_SPLITSTREAM;
            }
        }
        else if (statementSpec.getInsertIntoDesc() != null) {
            statementType = StatementType.INSERT_INTO;
        }
        else if (pattern) {
            statementType = StatementType.PATTERN;
        }
        else if (statementSpec.getUpdateDesc() != null) {
            statementType = StatementType.UPDATE;
        }
        else if (statementSpec.getCreateIndexDesc() != null) {
            statementType = StatementType.CREATE_INDEX;
        }
        else if (statementSpec.getCreateSchemaDesc() != null) {
            statementType = StatementType.CREATE_SCHEMA;
        }
        if (statementType == null) {
            statementType = StatementType.SELECT;
        }
        return statementType;
    }
}
