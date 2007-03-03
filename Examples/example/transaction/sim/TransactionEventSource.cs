/*
 * Created on Apr 22, 2006
 *
 */

using System;
using System.Collections;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.example.transaction;

using org.apache.commons.logging;

/** Generates events for a continuous stream of transactions.
 * Rules for generating events are coded in {@link #createNextTransaction()}.
 * 
 * @author Hans Gilde
 *
 */

namespace net.esper.example.transaction.sim
{
    public class TransactionEventSource : EventSource
    {
        protected String currentTransactionID;
        protected Random random = RandomUtil.GetNewInstance();
        protected List<TxnEventBase> transactionEvents;
        protected IEnumerable<TxnEventBase> transactionEnum;

        protected int maxTrans;
        protected int numTrans;

        protected FieldGenerator fieldGenerator = new FieldGenerator();

        /**
         * @param howManyTransactions How many transactions should events be generated for?
         */
        public TransactionEventSource(int howManyTransactions)
        {
            maxTrans = howManyTransactions;
        }

        protected List<TxnEventBase> CreateNextTransaction()
        {
            List<TxnEventBase> t = new List<TxnEventBase>();

            long beginningStamp = DateTimeHelper.CurrentTimeMillis;
            //skip event 1 with probability 1 in 5000
            if (random.Next(5000) < 4998)
            {
                TxnEventA txnEventA = new TxnEventA(null, beginningStamp, fieldGenerator.GetRandomCustomer());
                t.Add(txnEventA);
            }

            long e2Stamp = fieldGenerator.randomLatency(beginningStamp);
            //skip event 2 with probability 1 in 1000
            if (random.Next(1000) < 9998)
            {
                TxnEventB txnEventB = new TxnEventB(null, e2Stamp);
                t.Add(txnEventB);
            }

            long e3Stamp = fieldGenerator.randomLatency(e2Stamp);
            //skip event 3 with probability 1 in 10000
            if (random.Next(10000) < 9998)
            {
                TxnEventC txnEventC = new TxnEventC(null, e3Stamp, fieldGenerator.GetRandomSupplier());
                t.Add(txnEventC);
            }
            else
            {
                log.Debug(".createNextTransaction generated missing event");
            }

            return t;
        }

        /**
         * @return Returns the maxTrans.
         */
        public int MaxTrans
        {
            get { return maxTrans; }
        }

        public override IEnumerator<TxnEventBase> GetEnumerator()
        {
            while ((numTrans < maxTrans) || (transactionEnum.MoveNext()))
            {
                if (transactionEnum != null)
                {
                    TxnEventBase m = transactionEnum.Current;
                    m.TransactionId = currentTransactionID;
                    yield return m;
                }

                if (numTrans == maxTrans)
                {
                    throw new IllegalStateException("There is no next element.");
                }
                //create a new transaction, with ID.
                numTrans++;
                int id = random.Next();
                if (id < 0)
                {
                    id = -1 * id;
                }
                currentTransactionID = Convert.ToString(id);
                transactionEvents = CreateNextTransaction();
                transactionEnum = transactionEvents.GetEnumerator();
                yield return this.next();
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
