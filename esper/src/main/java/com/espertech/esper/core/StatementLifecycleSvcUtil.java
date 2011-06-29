package com.espertech.esper.core;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprNodeSubselectVisitor;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.spec.*;
import com.espertech.esper.pattern.*;

public class StatementLifecycleSvcUtil {

    public static ExprNodeSubselectVisitor walkSubselectAndDeclaredDotExpr(StatementSpecRaw spec) throws ExprValidationException {

        // Look for expressions with sub-selects in select expression list and filter expression
        // Recursively compile the statement within the statement.
        ExprNodeSubselectVisitor visitor = new ExprNodeSubselectVisitor();
        for (SelectClauseElementRaw raw : spec.getSelectClauseSpec().getSelectExprList())
        {
            if (raw instanceof SelectClauseExprRawSpec)
            {
                SelectClauseExprRawSpec rawExpr = (SelectClauseExprRawSpec) raw;
                rawExpr.getSelectExpression().accept(visitor);
            }
            else
            {
                continue;
            }
        }
        if (spec.getFilterRootNode() != null)
        {
            spec.getFilterRootNode().accept(visitor);
        }
        if (spec.getUpdateDesc() != null)
        {
            if (spec.getUpdateDesc().getOptionalWhereClause() != null)
            {
                spec.getUpdateDesc().getOptionalWhereClause().accept(visitor);
            }
            for (OnTriggerSetAssignment assignment : spec.getUpdateDesc().getAssignments())
            {
                assignment.getExpression().accept(visitor);
            }
        }
        if (spec.getOnTriggerDesc() != null) {
            visitSubselectOnTrigger(spec.getOnTriggerDesc(), visitor);
        }
        // Determine pattern-filter subqueries
        for (StreamSpecRaw streamSpecRaw : spec.getStreamSpecs()) {
            if (streamSpecRaw instanceof PatternStreamSpecRaw) {
                PatternStreamSpecRaw patternStreamSpecRaw = (PatternStreamSpecRaw) streamSpecRaw;
                EvalNodeAnalysisResult analysisResult = EvalNodeUtil.recursiveAnalyzeChildNodes(patternStreamSpecRaw.getEvalNode());
                for (EvalNode evalNode : analysisResult.getActiveNodes()) {
                    if (evalNode instanceof EvalFilterNode) {
                        EvalFilterNode filterNode = (EvalFilterNode) evalNode;
                        for (ExprNode filterExpr : filterNode.getRawFilterSpec().getFilterExpressions()) {
                            filterExpr.accept(visitor);
                        }
                    }
                    else if (evalNode instanceof EvalObserverNode) {
                        int beforeCount = visitor.getSubselects().size();
                        EvalObserverNode observerNode = (EvalObserverNode) evalNode;
                        for (ExprNode param : observerNode.getPatternObserverSpec().getObjectParameters()) {
                            param.accept(visitor);
                        }
                        if (visitor.getSubselects().size() != beforeCount) {
                            throw new ExprValidationException("Subselects are not allowed within pattern observer parameters, please consider using a variable instead");
                        }
                    }
                }
            }
        }
        // Determine filter streams
        for (StreamSpecRaw rawSpec : spec.getStreamSpecs())
        {
            if (rawSpec instanceof FilterStreamSpecRaw) {
                FilterStreamSpecRaw raw = (FilterStreamSpecRaw) rawSpec;
                for (ExprNode filterExpr : raw.getRawFilterSpec().getFilterExpressions()) {
                    filterExpr.accept(visitor);
                }
            }
        }

        return visitor;
    }

    private static void visitSubselectOnTrigger(OnTriggerDesc onTriggerDesc, ExprNodeSubselectVisitor visitor) {
        if (onTriggerDesc instanceof OnTriggerWindowUpdateDesc) {
            OnTriggerWindowUpdateDesc updates = (OnTriggerWindowUpdateDesc) onTriggerDesc;
            for (OnTriggerSetAssignment assignment : updates.getAssignments())
            {
                assignment.getExpression().accept(visitor);
            }
        }
        else if (onTriggerDesc instanceof OnTriggerSetDesc) {
            OnTriggerSetDesc sets = (OnTriggerSetDesc) onTriggerDesc;
            for (OnTriggerSetAssignment assignment : sets.getAssignments())
            {
                assignment.getExpression().accept(visitor);
            }
        }
        else if (onTriggerDesc instanceof OnTriggerSplitStreamDesc) {
            OnTriggerSplitStreamDesc splits = (OnTriggerSplitStreamDesc) onTriggerDesc;
            for (OnTriggerSplitStream split : splits.getSplitStreams())
            {
                if (split.getWhereClause() != null) {
                    split.getWhereClause().accept(visitor);
                }
                if (split.getSelectClause().getSelectExprList() != null) {
                    for (SelectClauseElementRaw element : split.getSelectClause().getSelectExprList()) {
                        if (element instanceof SelectClauseExprRawSpec) {
                            SelectClauseExprRawSpec selectExpr = (SelectClauseExprRawSpec) element;
                            selectExpr.getSelectExpression().accept(visitor);
                        }
                    }
                }
            }
        }
        else if (onTriggerDesc instanceof OnTriggerMergeDesc) {
            OnTriggerMergeDesc merge = (OnTriggerMergeDesc) onTriggerDesc;
            for (OnTriggerMergeMatched matched : merge.getItems()) {
                if (matched.getOptionalMatchCond() != null) {
                    matched.getOptionalMatchCond().accept(visitor);
                }
                for (OnTriggerMergeAction action : matched.getActions())
                {
                    if (action.getOptionalWhereClause() != null) {
                        action.getOptionalWhereClause().accept(visitor);
                    }

                    if (action instanceof OnTriggerMergeActionUpdate) {
                        OnTriggerMergeActionUpdate update = (OnTriggerMergeActionUpdate) action;
                        for (OnTriggerSetAssignment assignment : update.getAssignments())
                        {
                            assignment.getExpression().accept(visitor);
                        }
                    }
                    if (action instanceof OnTriggerMergeActionInsert) {
                        OnTriggerMergeActionInsert insert = (OnTriggerMergeActionInsert) action;
                        for (SelectClauseElementRaw element : insert.getSelectClause()) {
                            if (element instanceof SelectClauseExprRawSpec) {
                                SelectClauseExprRawSpec selectExpr = (SelectClauseExprRawSpec) element;
                                selectExpr.getSelectExpression().accept(visitor);
                            }
                        }
                    }
                }
            }
        }
    }
}
