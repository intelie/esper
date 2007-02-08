using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.collection;
using net.esper.events;
using net.esper.dispatch;
using net.esper.view;
using net.esper.eql.expression;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.core
{
    /// <summary> Statement implementation for EQL statements.
    /// 
    /// The statement Starts on construction.
    /// When listeners are added and removed from the view a child dispatch view is added/removed to/from the parent view
    /// to support push mode.
    /// </summary>
    public class EPEQLStatementImpl : EPStatementSupport, EPStatement
    {
        virtual public String Text
        {
            get { return expressionText; }
        }

        virtual public EventType EventType
        {
            get { return parentView.EventType; }
        }

        private readonly String expressionText;
        private readonly UpdateDispatchView dispatchChildView;
        private readonly EPEQLStmtStartMethod startMethod;

        private Viewable parentView;
        private EPStatementStopMethod stopMethod;

        /// <summary> Ctor.</summary>
        /// <param name="expressionText">expression
        /// </param>
        /// <param name="dispatchService">for dispatching
        /// </param>
        /// <param name="StartMethod">to Start the view
        /// </param>

        public EPEQLStatementImpl(String expressionText, DispatchService dispatchService, EPEQLStmtStartMethod startMethod)
        {
            this.expressionText = expressionText;
            this.dispatchChildView = new UpdateDispatchView(this.Listeners, dispatchService);
            this.startMethod = startMethod;

            Start();
        }

        public virtual void Stop()
        {
            if (stopMethod == null)
            {
                throw new SystemException("View statement already Stopped");
            }

            if (this.Listeners.Count > 0)
            {
                parentView.RemoveView(dispatchChildView);
            }

            stopMethod();
            stopMethod = null;
            parentView = null;
        }

        public virtual void Start()
        {
            if (stopMethod != null)
            {
                throw new SystemException("View statement already Started");
            }

            Pair<Viewable, EPStatementStopMethod> pair;

            try
            {
                pair = startMethod.Start();
            }
            catch (ExprValidationException ex)
            {
                log.Debug(".Start Error Starting view", ex);
                throw new EPStatementException("Error Starting view: " + ex.Message, expressionText);
            }
            catch (ViewProcessingException ex)
            {
                log.Debug(".Start Error Starting view", ex);
                throw new EPStatementException("Error Starting view: " + ex.Message, expressionText);
            }

            parentView = pair.First;
            stopMethod = pair.Second;

            if (this.Listeners.Count > 0)
            {
                parentView.AddView(dispatchChildView);
            }
        }

        public virtual IEnumerator<EventBean> GetEnumerator()
        {
            // Return null if not Started
            if (parentView == null)
            {
                return null;
            }

            return parentView.GetEnumerator();
        }

        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return this.GetEnumerator();
        }

        #endregion

        public override void listenerStop()
        {
            if (parentView != null)
            {
                parentView.RemoveView(dispatchChildView);
            }
        }

        public override void listenerStart()
        {
            if (parentView != null)
            {
                parentView.AddView(dispatchChildView);
            }
        }

        private static Log log = LogFactory.GetLog(typeof(EPEQLStatementImpl));
    }
}