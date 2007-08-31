package net.esper.client.soda;

public class Patterns
{
    public static PatternExpr every(PatternExpr inner)
    {
        return new PatternEveryExpr(inner);
    }

    public static PatternExpr and()
    {
        return new PatternAndExpr();
    }

    public static PatternExpr and(PatternExpr first, PatternExpr second, PatternExpr ...more)
    {
        return new PatternAndExpr(first, second, more);
    }

    public static PatternExpr or(PatternExpr first, PatternExpr second, PatternExpr ...more)
    {
        return new PatternOrExpr(first, second, more);
    }

    public static PatternExpr or()
    {
        return new PatternOrExpr();
    }

    public static PatternExpr followedBy(PatternExpr first, PatternExpr second, PatternExpr ...more)
    {
        return new PatternFollowedByExpr(first, second, more);
    }

    public static PatternExpr followedBy()
    {
        return new PatternFollowedByExpr();
    }

    public static PatternExpr everyFilter(String alias)
    {
        PatternExpr filter = new PatternFilterExpr(Filter.create(alias));
        return new PatternEveryExpr(filter);
    }

    public static PatternExpr everyFilter(String alias, String tagName)
    {
        PatternExpr filter = new PatternFilterExpr(Filter.create(alias), tagName);
        return new PatternEveryExpr(filter);
    }

    public static PatternExpr everyFilter(Filter filter)
    {
        PatternExpr inner = new PatternFilterExpr(filter);
        return new PatternEveryExpr(inner);
    }

    public static PatternExpr everyFilter(Filter filter, String tagName)
    {
        PatternExpr inner = new PatternFilterExpr(filter, tagName);
        return new PatternEveryExpr(inner);
    }

    public static PatternExpr filter(String alias)
    {
        return new PatternFilterExpr(Filter.create(alias));
    }

    public static PatternExpr filter(String alias, String tagName)
    {
        return new PatternFilterExpr(Filter.create(alias), tagName);
    }

    public static PatternExpr filter(Filter filter)
    {
        return new PatternFilterExpr(filter);
    }

    public static PatternExpr filter(Filter filter, String tagName)
    {
        return new PatternFilterExpr(filter, tagName);
    }

    public static PatternExpr timerAt(Integer minutes, Integer hours, Integer daysOfMonth, Integer month, Integer daysOfWeek)
    {
        return new PatternTimerAt(minutes, hours, daysOfMonth, month, daysOfWeek);
    }
}
