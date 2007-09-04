package net.esper.client.soda;

import net.esper.type.WildcardParameter;

import java.util.ArrayList;
import java.util.List;

public class Patterns
{
    public static PatternEveryExpr every(PatternExpr inner)
    {
        return new PatternEveryExpr(inner);
    }

    public static PatternAndExpr and()
    {
        return new PatternAndExpr();
    }

    public static PatternAndExpr and(PatternExpr first, PatternExpr second, PatternExpr ...more)
    {
        return new PatternAndExpr(first, second, more);
    }

    public static PatternOrExpr or(PatternExpr first, PatternExpr second, PatternExpr ...more)
    {
        return new PatternOrExpr(first, second, more);
    }

    public static PatternOrExpr or()
    {
        return new PatternOrExpr();
    }

    public static PatternFollowedByExpr followedBy(PatternExpr first, PatternExpr second, PatternExpr ...more)
    {
        return new PatternFollowedByExpr(first, second, more);
    }

    public static PatternFollowedByExpr followedBy()
    {
        return new PatternFollowedByExpr();
    }

    public static PatternEveryExpr everyFilter(String alias)
    {
        PatternExpr filter = new PatternFilterExpr(Filter.create(alias));
        return new PatternEveryExpr(filter);
    }

    public static PatternEveryExpr everyFilter(String alias, String tagName)
    {
        PatternExpr filter = new PatternFilterExpr(Filter.create(alias), tagName);
        return new PatternEveryExpr(filter);
    }

    public static PatternEveryExpr everyFilter(Filter filter)
    {
        PatternExpr inner = new PatternFilterExpr(filter);
        return new PatternEveryExpr(inner);
    }

    public static PatternEveryExpr everyFilter(Filter filter, String tagName)
    {
        PatternExpr inner = new PatternFilterExpr(filter, tagName);
        return new PatternEveryExpr(inner);
    }

    public static PatternFilterExpr filter(String alias)
    {
        return new PatternFilterExpr(Filter.create(alias));
    }

    public static PatternFilterExpr filter(String alias, String tagName)
    {
        return new PatternFilterExpr(Filter.create(alias), tagName);
    }

    public static PatternFilterExpr filter(Filter filter)
    {
        return new PatternFilterExpr(filter);
    }

    public static PatternFilterExpr filter(Filter filter, String tagName)
    {
        return new PatternFilterExpr(filter, tagName);
    }

    public static PatternGuardExpr guard(String namespace, String name, Object[] parameters, PatternExpr guarded)
    {
        return new PatternGuardExpr(namespace, name, parameters, guarded);
    }

    public static PatternObserverExpr observer(String namespace, String name, Object[] parameters)
    {
        return new PatternObserverExpr(namespace, name, parameters);
    }

    public static PatternGuardExpr timerWithin(double seconds, PatternExpr guarded)
    {
        return new PatternGuardExpr("timer", "within", new Object[] {seconds}, guarded);
    }

    public static PatternObserverExpr timerInterval(double seconds)
    {
        return new PatternObserverExpr("timer", "interval", new Object[] {seconds});
    }

    public static PatternNotExpr notFilter(String alias)
    {
        return new PatternNotExpr(new PatternFilterExpr(Filter.create(alias)));
    }

    public static PatternNotExpr notFilter(String alias, String tagName)
    {
        return new PatternNotExpr(new PatternFilterExpr(Filter.create(alias), tagName));
    }

    public static PatternNotExpr notFilter(Filter filter)
    {
        return new PatternNotExpr(new PatternFilterExpr(filter));
    }

    public static PatternNotExpr notFilter(Filter filter, String tagName)
    {
        return new PatternNotExpr(new PatternFilterExpr(filter, tagName));
    }

    public static PatternNotExpr not(PatternExpr subexpression)
    {
        return new PatternNotExpr(subexpression);
    }

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
