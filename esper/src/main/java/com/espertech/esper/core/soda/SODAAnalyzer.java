/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.core.soda;

import com.espertech.esper.client.soda.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SODAAnalyzer
{
    public static List<Expression> analyzeModelExpressions(EPStatementObjectModel model) {
        final List<Expression> expressions = new ArrayList<Expression>();

        if (model.getExpressionDeclarations() != null) {
            for (ExpressionDeclaration decl : model.getExpressionDeclarations()) {
                expressions.add(decl.getExpression());
            }
        }

        if (model.getCreateVariable() != null) {
            Expression expr = model.getCreateVariable().getOptionalAssignment();
            if (expr != null) {
                expressions.add(expr);
            }
        }

        if (model.getCreateWindow() != null) {
            Expression expr = model.getCreateWindow().getInsertWhereClause();
            if (expr != null) {
                expressions.add(expr);
            }
            for (View view : model.getCreateWindow().getViews()) {
                expressions.addAll(view.getParameters());
            }
        }

        if (model.getUpdateClause() != null) {
            if (model.getUpdateClause().getOptionalWhereClause() != null) {
                expressions.add(model.getUpdateClause().getOptionalWhereClause());
            }
            if (model.getUpdateClause().getAssignments() != null) {
                for (AssignmentPair pair : model.getUpdateClause().getAssignments()) {
                    expressions.add(pair.getValue());
                } 
            }
        }
                            
        // on-expr
        if (model.getOnExpr() != null) {
            if (model.getOnExpr() instanceof OnInsertSplitStreamClause) {
                OnInsertSplitStreamClause onSplit = (OnInsertSplitStreamClause) model.getOnExpr();
                for (OnInsertSplitStreamItem item : onSplit.getItems()) {
                    if (item.getSelectClause() != null) {
                        for (SelectClauseElement selement : item.getSelectClause().getSelectList()) {
                            if (!(selement instanceof SelectClauseExpression)) {
                                continue;
                            }
                            SelectClauseExpression sexpr = (SelectClauseExpression) selement;
                            expressions.add(sexpr.getExpression());
                        }							
                    }
                    if (item.getWhereClause() != null) {
                        expressions.add(item.getWhereClause());
                    }
                }
            }
            if (model.getOnExpr() instanceof OnSetClause) {
                OnSetClause onSet = (OnSetClause) model.getOnExpr();
                if (onSet.getAssignments() != null) {
                    for (AssignmentPair aitem : onSet.getAssignments()) {
                        expressions.add(aitem.getValue());
                    }
                }
            }
            if (model.getOnExpr() instanceof OnUpdateClause) {
                OnUpdateClause onUpdate = (OnUpdateClause) model.getOnExpr();
                if (onUpdate.getAssignments() != null) {
                    for (AssignmentPair bitem : onUpdate.getAssignments()) {
                        expressions.add(bitem.getValue());
                    }
                }
            }
            if (model.getOnExpr() instanceof OnMergeClause) {
                OnMergeClause onMerge = (OnMergeClause) model.getOnExpr();
                for (OnMergeMatchItem item : onMerge.getMatchItems()) {
                    if (item.getOptionalCondition() != null) {
                        expressions.add(item.getOptionalCondition());
                    }
                    for (OnMergeMatchedAction action : item.getActions()) {
                        if (action instanceof OnMergeMatchedDeleteAction) {
                            OnMergeMatchedDeleteAction delete = (OnMergeMatchedDeleteAction) action;
                            if (delete.getWhereClause() != null) {
                                expressions.add(delete.getWhereClause());
                            }
                        }
                        else if (action instanceof OnMergeMatchedUpdateAction) {
                            OnMergeMatchedUpdateAction update = (OnMergeMatchedUpdateAction) action;
                            if (update.getWhereClause() != null) {
                                expressions.add(update.getWhereClause());
                            }
                            for (AssignmentPair assignment : update.getAssignments()) {
                                expressions.add(assignment.getValue());
                            }
                        }
                        else if (action instanceof OnMergeMatchedInsertAction) {
                            OnMergeMatchedInsertAction insert = (OnMergeMatchedInsertAction) action;
                            if (insert.getWhereClause() != null) {
                                expressions.add(insert.getWhereClause());
                            }
                            for (SelectClauseElement element : insert.getSelectList()) {
                                if (element instanceof SelectClauseExpression) {
                                    SelectClauseExpression expr = (SelectClauseExpression) element;
                                    expressions.add(expr.getExpression());
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // select clause
        if (model.getSelectClause() != null) {
            if (model.getSelectClause().getSelectList() != null) {
                for (SelectClauseElement selectItem : model.getSelectClause().getSelectList()) {
                    if (!(selectItem instanceof SelectClauseExpression)) {
                        continue;
                    }
                    SelectClauseExpression selectExpr = (SelectClauseExpression) selectItem;
                    expressions.add(selectExpr.getExpression());
                }
            }
        }

        // from clause
        if (model.getFromClause() != null) {
            for (Stream stream : model.getFromClause().getStreams()) {
                // filter stream
                if (stream instanceof FilterStream) {
                    FilterStream filterStream = (FilterStream) stream;
                    Filter filter = filterStream.getFilter();
                    if ((filter != null) && (filter.getFilter() != null)){
                        expressions.add(filterStream.getFilter().getFilter());
                    }
                    if ((filter != null) && (filter.getOptionalPropertySelects() != null)) {
                        for (ContainedEventSelect contained : filter.getOptionalPropertySelects()) {
                            for (SelectClauseElement selectItem : contained.getSelectClause().getSelectList()) {
                                if (!(selectItem instanceof SelectClauseExpression)) {
                                    continue;
                                }
                                SelectClauseExpression selectExpr = (SelectClauseExpression) selectItem;
                                expressions.add(selectExpr.getExpression());
                            }
                            if (contained.getWhereClause() != null) {
                                expressions.add(contained.getWhereClause());
                            }
                        }
                    }
                }
                // pattern stream
                if (stream instanceof PatternStream) {
                    PatternStream patternStream = (PatternStream) stream;
                    SODAAnalyzerPatternCollector collector = new SODAAnalyzerPatternCollector() {
                        public void visit(PatternExpr patternExpr)
                        {
                            if (patternExpr instanceof PatternFilterExpr) {
                                PatternFilterExpr filter = (PatternFilterExpr) patternExpr;
                                if (filter.getFilter().getFilter() != null) {
                                    expressions.add(filter.getFilter().getFilter());
                                }
                            }
                        }
                    };
                    traversePatternRecursive(patternStream.getExpression(), collector);
                }
                // method stream
                if (stream instanceof MethodInvocationStream) {
                    MethodInvocationStream methodStream = (MethodInvocationStream) stream;
                    if (methodStream.getParameterExpressions() != null) {
                        expressions.addAll(methodStream.getParameterExpressions());
                    }
                }
                if (stream instanceof ProjectedStream) {
                    ProjectedStream projectedStream = (ProjectedStream) stream;
                    if (projectedStream.getViews() != null) {
                        for (View view : projectedStream.getViews()) {
                            expressions.addAll(view.getParameters());
                        }
                    }
                }
            }

            if (model.getFromClause().getOuterJoinQualifiers() != null) {
                for (OuterJoinQualifier q : model.getFromClause().getOuterJoinQualifiers()) {
                    expressions.add(q.getLeft());
                    expressions.add(q.getRight());
                    for (PropertyValueExpressionPair pair : q.getAdditionalProperties()) {
                        expressions.add(pair.getLeft());
                        expressions.add(pair.getRight());
                    }
                }
            }
        }
        
        if (model.getWhereClause() != null) {
            expressions.add(model.getWhereClause());
        }
        
        if (model.getGroupByClause() != null) {
            for (Expression groupByExpr : model.getGroupByClause().getGroupByExpressions()) {
                expressions.add(groupByExpr);
            }
        }

        if (model.getHavingClause() != null) {
            expressions.add(model.getHavingClause());
        }

        if (model.getOutputLimitClause() != null) {
            if (model.getOutputLimitClause().getWhenExpression() != null) {
                expressions.add(model.getOutputLimitClause().getWhenExpression());
            }
            if (model.getOutputLimitClause().getThenAssignments() != null) {
                for (AssignmentPair thenAssign : model.getOutputLimitClause().getThenAssignments()) {
                    expressions.add(thenAssign.getValue());
                }					
            }
            if (model.getOutputLimitClause().getCrontabAtParameters() != null) {
                for (Expression expr : model.getOutputLimitClause().getCrontabAtParameters()) {
                    expressions.add(expr);
                }
            }
            if (model.getOutputLimitClause().getTimePeriodExpression() != null) {
                expressions.add(model.getOutputLimitClause().getTimePeriodExpression());
            }
            if (model.getOutputLimitClause().getAfterTimePeriodExpression() != null) {
                expressions.add(model.getOutputLimitClause().getAfterTimePeriodExpression());
            }
        }
        
        if (model.getOrderByClause() != null) {
            for (OrderByElement orderByElement : model.getOrderByClause().getOrderByExpressions()) {
                expressions.add(orderByElement.getExpression());
            }									
        }
        
        if (model.getMatchRecognizeClause() != null) {
            if (model.getMatchRecognizeClause().getPartitionExpressions() != null) {
                for (Expression partitionExpr : model.getMatchRecognizeClause().getPartitionExpressions()) {
                    expressions.add(partitionExpr);
                }
            }
            for (SelectClauseElement selectItemMR : model.getMatchRecognizeClause().getMeasures()) {
                if (!(selectItemMR instanceof SelectClauseExpression)) {
                    continue;
                }
                SelectClauseExpression selectExprMR = (SelectClauseExpression) selectItemMR;
                expressions.add(selectExprMR.getExpression());
            }
            for (MatchRecognizeDefine define : model.getMatchRecognizeClause().getDefines()) {
                expressions.add(define.getExpression());
            }
            if (model.getMatchRecognizeClause().getIntervalClause() != null) {
                if (model.getMatchRecognizeClause().getIntervalClause().getExpression() != null) {
                    expressions.add(model.getMatchRecognizeClause().getIntervalClause().getExpression());
                }
            }
        }

        if (model.getForClause() != null) {
            for (ForClauseItem item : model.getForClause().getItems()) {
                if (item.getExpressions() != null) {
                    expressions.addAll(item.getExpressions());
                }
            }
        }

        return expressions;
    }

    private static void traversePatternRecursive(PatternExpr patternExpr, SODAAnalyzerPatternCollector collectorFunction) {
        collectorFunction.visit(patternExpr);
        if (patternExpr == null){
            return;
        }
        if (patternExpr.getChildren() == null) {
            return;
        }
        for (PatternExpr child : patternExpr.getChildren()) {
            traversePatternRecursive(child, collectorFunction);
        }
    }

    public static List<MatchRecognizeRegEx> analyzeModelMatchRecogRegexs(EPStatementObjectModel model)
    {
        if (model.getMatchRecognizeClause() == null) {
            return Collections.EMPTY_LIST;
        }
        return Collections.singletonList(model.getMatchRecognizeClause().getPattern());
    }

    public static List<PatternExpr> analyzeModelPatterns(EPStatementObjectModel model)
    {
        List<PatternExpr> result = new ArrayList<PatternExpr>();
        if (model.getFromClause() != null) {
            for (Stream stream : model.getFromClause().getStreams()) {
                // pattern stream
                if (stream instanceof PatternStream) {
                    PatternStream patternStream = (PatternStream) stream;
                    if (patternStream.getExpression() != null) {
                        result.add(patternStream.getExpression());
                    }
                }
            }
        }
        return result;
    }
}
