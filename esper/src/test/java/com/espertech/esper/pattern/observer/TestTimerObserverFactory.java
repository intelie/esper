package com.espertech.esper.pattern.observer;

import com.espertech.esper.epl.core.StreamTypeServiceImpl;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.pattern.MatchedEventConvertorImpl;
import com.espertech.esper.support.pattern.SupportPatternContextFactory;
import com.espertech.esper.support.pattern.SupportMatchedEventConvertor;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.view.TestViewSupport;
import junit.framework.TestCase;

public class TestTimerObserverFactory extends TestCase
{
    private PatternContext patternContext;

    public void setUp()
    {
        patternContext = SupportPatternContextFactory.makeContext();
    }

    public void testIntervalWait() throws Exception
    {
        TimerIntervalObserverFactory factory = new TimerIntervalObserverFactory();
        factory.setObserverParameters(TestViewSupport.toExprListBean(new Object[] {1}), new SupportMatchedEventConvertor());
        EventObserver eventObserver = factory.makeObserver(patternContext, null, new SupportObserverEventEvaluator(patternContext), null, null);

        assertTrue(eventObserver instanceof TimerIntervalObserver);
    }

    private static class SupportObserverEventEvaluator implements ObserverEventEvaluator {
        private final PatternContext patternContext;

        private SupportObserverEventEvaluator(PatternContext patternContext) {
            this.patternContext = patternContext;
        }

        public void observerEvaluateTrue(MatchedEventMap matchEvent) {
        }

        public void observerEvaluateFalse() {
        }

        public PatternContext getContext() {
            return patternContext;
        }
    }
}
