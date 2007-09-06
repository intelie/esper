/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client.soda;

import net.esper.type.WildcardParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Convenience factory for creating {@link PatternExpr} instances, which represent pattern expression trees.
 * <p>
 * Provides quick-access method to create all possible pattern expressions and provides typical parameter lists to each.
 * <p>
 * Note that only the typical parameter lists are provided and pattern expressions can allow adding
 * additional parameters.
 * <p>
 * Many expressions, for example logical AND and OR (conjunction and disjunction), allow
 * adding an unlimited number of additional sub-expressions to a pattern expression. For those pattern expressions
 * there are additional add methods.
 */
public class Patterns
{
    private static final long serialVersionUID = 0L;

    /**
     * Pattern-every expression control the lifecycle of the pattern sub-expression.
     * @param inner sub-expression to the every-keyword
     * @return pattern expression
     */
    public static PatternEveryExpr every(PatternExpr inner)
    {
        return new PatternEveryExpr(inner);
    }

    /**
     * Pattern-AND expression, allows adding sub-expressions that are connected by a logical AND.
     * @return pattern expression representing the AND relationship
     */
    public static PatternAndExpr and()
    {
        return new PatternAndExpr();
    }

    /**
     * Pattern-AND expression, allows adding sub-expressions that are connected by a logical AND.
     * @param first is the first pattern sub-expression to add to the AND
     * @param second is a second pattern sub-expression to add to the AND
     * @param more is optional additional pattern sub-expression to add to the AND
     * @return pattern expression representing the AND relationship
     */
    public static PatternAndExpr and(PatternExpr first, PatternExpr second, PatternExpr ...more)
    {
        return new PatternAndExpr(first, second, more);
    }

    /**
     * Pattern-OR expression, allows adding sub-expressions that are connected by a logical OR.
     * @param first is the first pattern sub-expression to add to the OR
     * @param second is a second pattern sub-expression to add to the OR
     * @param more is optional additional pattern sub-expression to add to the OR
     * @return pattern expression representing the OR relationship
     */
    public static PatternOrExpr or(PatternExpr first, PatternExpr second, PatternExpr ...more)
    {
        return new PatternOrExpr(first, second, more);
    }

    /**
     * Pattern-OR expression, allows adding sub-expressions that are connected by a logical OR.
     * @return pattern expression representing the OR relationship
     */
    public static PatternOrExpr or()
    {
        return new PatternOrExpr();
    }

    /**
     * Pattern followed-by expression, allows adding sub-expressions that are connected by a followed-by.
     * @param first is the first pattern sub-expression to add to the followed-by
     * @param second is a second pattern sub-expression to add to the followed-by
     * @param more is optional additional pattern sub-expression to add to the followed-by
     * @return pattern expression representing the followed-by relationship
     */
    public static PatternFollowedByExpr followedBy(PatternExpr first, PatternExpr second, PatternExpr ...more)
    {
        return new PatternFollowedByExpr(first, second, more);
    }

    /**
     * Pattern followed-by expression, allows adding sub-expressions that are connected by a followed-by.
     * @return pattern expression representing the followed-by relationship
     */
    public static PatternFollowedByExpr followedBy()
    {
        return new PatternFollowedByExpr();
    }

    /**
     * Pattern every-operator and filter in combination, equivalent to the "every MyEvent" syntax. 
     * @param alias is the event type alias name to filter for
     * @return pattern expression
     */
    public static PatternEveryExpr everyFilter(String alias)
    {
        PatternExpr filter = new PatternFilterExpr(Filter.create(alias));
        return new PatternEveryExpr(filter);
    }

    /**
     * Pattern every-operator and filter in combination, equivalent to the "every tag=MyEvent" syntax.
     * @param alias is the event type alias name to filter for
     * @param tagName is the tag name to assign to matching events
     * @return pattern expression
     */
    public static PatternEveryExpr everyFilter(String alias, String tagName)
    {
        PatternExpr filter = new PatternFilterExpr(Filter.create(alias), tagName);
        return new PatternEveryExpr(filter);
    }

    /**
     * Pattern every-operator and filter in combination, equivalent to the "every MyEvent(vol > 100)" syntax.
     * @param filter specifies the event type name and filter expression to filter for
     * @return pattern expression
     */
    public static PatternEveryExpr everyFilter(Filter filter)
    {
        PatternExpr inner = new PatternFilterExpr(filter);
        return new PatternEveryExpr(inner);
    }

    /**
     * Pattern every-operator and filter in combination, equivalent to the "every tag=MyEvent(vol > 100)" syntax. 
     * @param filter specifies the event type name and filter expression to filter for
     * @param tagName is the tag name to assign to matching events
     * @return pattern expression
     */
    public static PatternEveryExpr everyFilter(Filter filter, String tagName)
    {
        PatternExpr inner = new PatternFilterExpr(filter, tagName);
        return new PatternEveryExpr(inner);
    }

    /**
     * Filter expression for use in patterns, equivalent to the simple "MyEvent" syntax.
     * @param alias is the event type alias name of the events to filter for
     * @return pattern expression
     */
    public static PatternFilterExpr filter(String alias)
    {
        return new PatternFilterExpr(Filter.create(alias));
    }

    /**
     * Filter expression for use in patterns, equivalent to the simple "tag=MyEvent" syntax.
     * @param alias is the event type alias name of the events to filter for
     * @param tagName is the tag name to assign to matching events
     * @return pattern expression
     */
    public static PatternFilterExpr filter(String alias, String tagName)
    {
        return new PatternFilterExpr(Filter.create(alias), tagName);
    }

    /**
     * Filter expression for use in patterns, equivalent to the "MyEvent(vol > 100)" syntax.
     * @param filter specifies the event type name and filter expression to filter for
     * @return pattern expression
     */
    public static PatternFilterExpr filter(Filter filter)
    {
        return new PatternFilterExpr(filter);
    }

    /**
     * Filter expression for use in patterns, equivalent to the "tag=MyEvent(vol > 100)" syntax.
     * @param filter specifies the event type name and filter expression to filter for
     * @param tagName is the tag name to assign to matching events
     * @return pattern expression
     */
    public static PatternFilterExpr filter(Filter filter, String tagName)
    {
        return new PatternFilterExpr(filter, tagName);
    }

    /**
     * Guard pattern expression guards a sub-expression, equivalent to the "every MyEvent where timer:within(1 sec)" syntax
     * @param namespace is the guard objects namespace, i.e. "timer"
     * @param name is the guard objects name, i.e. ""within"
     * @param parameters is the guard objects optional parameters, i.e. integer 1 for 1 second
     * @param guarded is the pattern sub-expression to be guarded
     * @return pattern guard expression
     */
    public static PatternGuardExpr guard(String namespace, String name, Object[] parameters, PatternExpr guarded)
    {
        return new PatternGuardExpr(namespace, name, parameters, guarded);
    }

    /**
     * Observer pattern expression, equivalent to the "every timer:interval(1 sec)" syntax
     * @param namespace is the observer objects namespace, i.e. "timer"
     * @param name is the observer objects name, i.e. ""within"
     * @param parameters is the observer objects optional parameters, i.e. integer 1 for 1 second
     * @return pattern observer expression
     */
    public static PatternObserverExpr observer(String namespace, String name, Object[] parameters)
    {
        return new PatternObserverExpr(namespace, name, parameters);
    }

    /**
     * Timer-within guard expression.
     * @param seconds is the number of seconds for the guard
     * @param guarded is the sub-expression to guard
     * @return pattern guard
     */
    public static PatternGuardExpr timerWithin(double seconds, PatternExpr guarded)
    {
        return new PatternGuardExpr("timer", "within", new Object[] {seconds}, guarded);
    }

    /**
     * Timer-interval observer expression.
     * @param seconds is the number of seconds in the interval
     * @return pattern observer
     */
    public static PatternObserverExpr timerInterval(double seconds)
    {
        return new PatternObserverExpr("timer", "interval", new Object[] {seconds});
    }

    /**
     * Pattern not-operator and filter in combination, equivalent to the "not MyEvent" syntax. 
     * @param alias is the event type alias name to filter for
     * @return pattern expression
     */
    public static PatternNotExpr notFilter(String alias)
    {
        return new PatternNotExpr(new PatternFilterExpr(Filter.create(alias)));
    }

    /**
     * Pattern not-operator and filter in combination, equivalent to the "not tag=MyEvent" syntax. 
     * @param alias is the event type alias name to filter for
     * @param tagName is the tag name to assign to matching events
     * @return pattern expression
     */
    public static PatternNotExpr notFilter(String alias, String tagName)
    {
        return new PatternNotExpr(new PatternFilterExpr(Filter.create(alias), tagName));
    }

    /**
     * Pattern not-operator and filter in combination, equivalent to the "not MyEvent(vol > 100)" syntax.
     * @param filter specifies the event type name and filter expression to filter for
     * @return pattern expression
     */
    public static PatternNotExpr notFilter(Filter filter)
    {
        return new PatternNotExpr(new PatternFilterExpr(filter));
    }

    /**
     * Pattern not-operator and filter in combination, equivalent to the "not tag=MyEvent(vol > 100)" syntax.
     * @param filter specifies the event type name and filter expression to filter for
     * @param tagName is the tag name to assign to matching events
     * @return pattern expression
     */
    public static PatternNotExpr notFilter(Filter filter, String tagName)
    {
        return new PatternNotExpr(new PatternFilterExpr(filter, tagName));
    }

    /**
     * Not-keyword pattern expression flips the truth-value of the pattern sub-expression.
     * @param subexpression is the expression whose truth value to flip
     * @return pattern expression
     */
    public static PatternNotExpr not(PatternExpr subexpression)
    {
        return new PatternNotExpr(subexpression);
    }

    /**
     * Timer-at observer
     * @param minutes a single integer value supplying the minute to fire the timer, or null for any (wildcard) minute
     * @param hours a single integer value supplying the hour to fire the timer, or null for any (wildcard) hour
     * @param daysOfMonth a single integer value supplying the day of the month to fire the timer, or null for any (wildcard) day of the month
     * @param month a single integer value supplying the month to fire the timer, or null for any (wildcard) month
     * @param daysOfWeek a single integer value supplying the days of the week to fire the timer, or null for any (wildcard) day of the week
     * @param seconds a single integer value supplying the second to fire the timer, or null for any (wildcard) second
     * @return timer-at observer
     */
    public static PatternObserverExpr timerAt(Integer minutes, Integer hours, Integer daysOfMonth, Integer month, Integer daysOfWeek, Integer seconds)
    {
        List<Object> params = new ArrayList<Object>();
        params.add(minutes == null ? new WildcardParameter() : minutes);
        params.add(hours == null ? new WildcardParameter() : hours);
        params.add(daysOfMonth == null ? new WildcardParameter() : daysOfMonth);
        params.add(month == null ? new WildcardParameter() : month);
        params.add(daysOfWeek == null ? new WildcardParameter() : daysOfWeek);
        params.add(seconds == null ? new WildcardParameter() : seconds);
        return new PatternObserverExpr("timer", "at", params);
    }
}
