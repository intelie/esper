using System;
using System.Collections.Generic;

using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.view;
using net.esper.type;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.view
{

	[TestFixture]
    public class TestViewFactory
    {
        private SupportBeanClassView parentViewable = new SupportBeanClassView(typeof(SupportMarketDataBean));

        [Test]
        public virtual void testCreate()
        {
            IList<Object> parameters = new List<Object>();
            parameters.Add("price");
            ViewSpec spec = new ViewSpec(ViewEnum.UNIVARIATE_STATISTICS.Namespace, ViewEnum.UNIVARIATE_STATISTICS.Name, parameters);

            Viewable view = ViewFactory.Create(parentViewable, spec);
            if (view is ContextAwareView)
            {
                ContextAwareView contextAwareView = (ContextAwareView)view;
                contextAwareView.ViewServiceContext = SupportViewContextFactory.makeContext();
            }

            Assert.IsTrue(view != null);
            Assert.IsTrue(view.Views.Count == 0);
            Assert.IsTrue(view.EventType.GetPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.Name) != null);
        }

        [Test]
        public virtual void testInvalidViewName()
        {
            ViewSpec spec = new ViewSpec("dummy", "bumblebee", null);

            try
            {
                ViewFactory.Create(parentViewable, spec);
                Assert.IsFalse(true);
            }
            catch (ViewProcessingException ex)
            {
                log.Debug(".testInvalidViewName Expected exception caught, msg=" + ex.Message);
            }
        }

        [Test]
        public virtual void testInvalidViewParameters()
        {
            // Forget to populate view parameters, should error
            IList<Object> parameters = new List<Object>();
            ViewSpec spec = new ViewSpec("stat", "uni", parameters);

            try
            {
                ViewFactory.Create(parentViewable, spec);
                Assert.IsFalse(true);
            }
            catch (ViewProcessingException ex)
            {
                log.Debug(".testInvalidViewParameters Expected exception caught, msg=" + ex.Message);
            }
        }

        [Test]
        public virtual void testInvalidAttach()
        {
            // View doesn't allow non-numeric fields, the factory should check this
            IList<Object> parameters = new List<Object>();
            parameters.Add(new StringValue("symbol"));
            ViewSpec spec = new ViewSpec("stat", "uni", parameters);

            try
            {
                ViewFactory.Create(parentViewable, spec);
                Assert.IsFalse(true);
            }
            catch (ViewProcessingException ex)
            {
                log.Debug(".testInvalidViewName Expected exception caught, msg=" + ex.Message);
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
