using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.regression.support
{
    public class EventExpressionCase
    {
        private String expressionText;
        private LinkedDictionary<String, IList<EventDescriptor>> expectedResults;

        public EventExpressionCase(String expressionText)
        {
            this.expressionText = expressionText;
            this.expectedResults = new LinkedDictionary<String, IList<EventDescriptor>>();
        }

        public String ExpressionText
        {
            get { return expressionText; }
        }

        public LinkedDictionary<String, IList<EventDescriptor>> ExpectedResults
        {
            get { return expectedResults; }
        }

        public void Add(String expectedOnEventId)
        {
            AddDesc(expectedOnEventId);
        }

        public void Add(String expectedOnEventId, String tag, Object bean)
        {
            EventDescriptor desc = AddDesc(expectedOnEventId);
            desc.Put(tag, bean);
        }

        public void Add(String expectedOnEventId, String tagOne, Object beanOne,
                        String tagTwo, Object beanTwo)
        {
            EventDescriptor desc = AddDesc(expectedOnEventId);
            desc.Put(tagOne, beanOne);
            desc.Put(tagTwo, beanTwo);
        }

        public void Add(String expectedOnEventId, String tagOne, Object beanOne,
                        String tagTwo, Object beanTwo,
                        String tagThree, Object beanThree)
        {
            EventDescriptor desc = AddDesc(expectedOnEventId);
            desc.Put(tagOne, beanOne);
            desc.Put(tagTwo, beanTwo);
            desc.Put(tagThree, beanThree);
        }

        public void Add(String expectedOnEventId, String tagOne, Object beanOne,
                        String tagTwo, Object beanTwo,
                        String tagThree, Object beanThree,
                        String tagFour, Object beanFour)
        {
            EventDescriptor desc = AddDesc(expectedOnEventId);
            desc.Put(tagOne, beanOne);
            desc.Put(tagTwo, beanTwo);
            desc.Put(tagThree, beanThree);
            desc.Put(tagFour, beanFour);
        }

        private EventDescriptor AddDesc(String expectedOnEventId)
        {
			IList<EventDescriptor> resultList = expectedResults.Fetch( expectedOnEventId );

            if (resultList == null)
            {
                resultList = new List<EventDescriptor>();
                expectedResults.Put(expectedOnEventId, resultList);
            }

            EventDescriptor eventDesc = new EventDescriptor();
            resultList.Add(eventDesc);
            return eventDesc;
        }
    }
}
