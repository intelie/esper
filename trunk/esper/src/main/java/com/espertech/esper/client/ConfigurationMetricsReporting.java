package com.espertech.esper.client;

import com.espertech.esper.type.*;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationMetricsReporting implements Serializable
{
    private boolean enableMetricsReporting;
    private boolean isUseMetricsThreading;
    private long engineMetricsInterval;
    private long defaultStmtMetricsInterval;

    private Map<String, StmtGroupMetrics> statementGroups;

    public ConfigurationMetricsReporting()
    {
        enableMetricsReporting = false;
        isUseMetricsThreading = true;
        engineMetricsInterval = 10 * 1000; // 10 seconds
        defaultStmtMetricsInterval = 10 * 1000;
        statementGroups = new HashMap<String, StmtGroupMetrics>();
    }

    public void addStmtGroup(String name, StmtGroupMetrics config)
    {
        statementGroups.put(name, config);
    }

    public boolean isEnableMetricsReporting()
    {
        return enableMetricsReporting;
    }

    public void setEnableMetricsReporting(boolean enableMetricsReporting)
    {
        this.enableMetricsReporting = enableMetricsReporting;
    }

    public boolean isUseMetricsThreading()
    {
        return isUseMetricsThreading;
    }

    public void setUseMetricsThreading(boolean useMetricsThreading)
    {
        isUseMetricsThreading = useMetricsThreading;
    }

    public long getEngineMetricsInterval()
    {
        return engineMetricsInterval;
    }

    public void setEngineMetricsInterval(long engineMetricsInterval)
    {
        this.engineMetricsInterval = engineMetricsInterval;
    }

    public long getDefaultStmtMetricsInterval()
    {
        return defaultStmtMetricsInterval;
    }

    public void setDefaultStmtMetricsInterval(long defaultStmtMetricsInterval)
    {
        this.defaultStmtMetricsInterval = defaultStmtMetricsInterval;
    }

    public Map<String, StmtGroupMetrics> getStatementGroups()
    {
        return statementGroups;
    }

    public static class StmtGroupMetrics implements Serializable
    {
        private List<StringPatternSet> patterns;
        private int initialNumStmts;
        private long interval;

        public StmtGroupMetrics()
        {
            patterns = new ArrayList<StringPatternSet>();
            interval =  10000;
            initialNumStmts = 100;
        }

        public void addIncludeLike(String likeExpression)
        {
            patterns.add(new StringPatternSetIncludeLike(likeExpression));
        }

        public void addExcludeLike(String likeExpression)
        {
            patterns.add(new StringPatternSetExcludeLike(likeExpression));
        }

        public void addIncludeRegex(String regexExpression)
        {
            patterns.add(new StringPatternSetIncludeRegex(regexExpression));
        }

        public void addExcludeRegEx(String regexExpression)
        {
            patterns.add(new StringPatternSetExcludeRegex(regexExpression));
        }

        public long getInterval()
        {
            return interval;
        }

        public void setInterval(long interval)
        {
            this.interval = interval;
        }

        public List<StringPatternSet> getPatterns()
        {
            return patterns;
        }

        public int getInitialNumStmts()
        {
            return initialNumStmts;
        }

        public void setInitialNumStmts(int initialNumStmts)
        {
            this.initialNumStmts = initialNumStmts;
        }
    }

}
