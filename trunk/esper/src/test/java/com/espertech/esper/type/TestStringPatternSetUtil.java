package com.espertech.esper.type;

import junit.framework.TestCase;

import java.util.List;
import java.util.LinkedList;

public class TestStringPatternSetUtil extends TestCase
{
    private List<StringPatternSet> patterns = new LinkedList<StringPatternSet>();

    public void testEmpty()
    {
        assertTrue(StringPatternSetUtil.evaluate(true, patterns, "abc"));
        assertFalse(StringPatternSetUtil.evaluate(false, patterns, "abc"));
    }

    public void testCombinationLike()
    {
        patterns.add(new StringPatternSetIncludeLike("%123%"));
        patterns.add(new StringPatternSetExcludeLike("%abc%"));
        patterns.add(new StringPatternSetIncludeLike("%def%"));
        patterns.add(new StringPatternSetExcludeLike("%xyz%"));

        runAssertion();
    }

    public void testCombinationRegex()
    {
        patterns.add(new StringPatternSetIncludeRegex("(.)*123(.)*"));
        patterns.add(new StringPatternSetExcludeRegex("(.)*abc(.)*"));
        patterns.add(new StringPatternSetIncludeRegex("(.)*def(.)*"));
        patterns.add(new StringPatternSetExcludeRegex("(.)*xyz(.)*"));

        runAssertion();
    }

    private void runAssertion()
    {
        assertTrue(StringPatternSetUtil.evaluate(false, patterns, "123"));
        assertFalse(StringPatternSetUtil.evaluate(false, patterns, "123abc"));
        assertTrue(StringPatternSetUtil.evaluate(false, patterns, "123abcdef"));
        assertFalse(StringPatternSetUtil.evaluate(false, patterns, "123abcdefxyz"));
        assertFalse(StringPatternSetUtil.evaluate(false, patterns, "456"));
        assertTrue(StringPatternSetUtil.evaluate(true, patterns, "456"));
    }
}
