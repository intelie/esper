using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.pattern;
using net.esper.pattern.observer;

namespace net.esper.support.guard
{
    public class SupportObserverEvaluator : ObserverEventEvaluator
    {
        private IList<MatchedEventMap> matchEvents = new List<MatchedEventMap>();
        private int evaluateFalseCounter;


        public void ObserverEvaluateTrue(MatchedEventMap matchEvent)
        {
            matchEvents.Add(matchEvent);
        }

        public void ObserverEvaluateFalse()
        {
            evaluateFalseCounter++;
        }

        public IList<MatchedEventMap> getAndClearMatchEvents()
        {
            IList<MatchedEventMap> original = matchEvents;
            matchEvents = new List<MatchedEventMap>();
            return original;
        }

        public IList<MatchedEventMap> getMatchEvents()
        {
            return matchEvents;
        }

        public int getAndResetEvaluateFalseCounter()
        {
            int value = evaluateFalseCounter;
            evaluateFalseCounter = 0;
            return value;
        }
    }
}
