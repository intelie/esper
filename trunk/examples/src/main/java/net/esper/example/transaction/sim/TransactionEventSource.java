/*
 * Created on Apr 22, 2006
 *
 */
package net.esper.example.transaction.sim;

import net.esper.example.transaction.TxnEventA;
import net.esper.example.transaction.TxnEventB;
import net.esper.example.transaction.TxnEventC;
import net.esper.example.transaction.TxnEventBase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** Generates events for a continuous stream of transactions.
 * Rules for generating events are coded in {@link #createNextTransaction()}.
 * 
 * @author Hans Gilde
 *
 */
public class TransactionEventSource extends EventSource {
    protected String currentTransactionID;
    protected Random random = RandomUtil.getNewInstance();
    protected List<TxnEventBase> transactionEvents;
    protected Iterator<TxnEventBase> transactionIterator;
    
    protected int maxTrans;
    protected int numTrans;
    
    protected FieldGenerator fieldGenerator = new FieldGenerator();

    /**
     * @param howManyTransactions How many transactions should events be generated for?
     */
    public TransactionEventSource(int howManyTransactions) {
        maxTrans = howManyTransactions;
    }
    
    protected List<TxnEventBase> createNextTransaction() {
        List<TxnEventBase> t = new ArrayList<TxnEventBase>();
        
        long beginningStamp = System.currentTimeMillis();
        //skip event 1 with probability 1 in 5000
        if (random.nextInt(5000) < 4998) {
            TxnEventA txnEventA = new TxnEventA(null, beginningStamp, fieldGenerator.getRandomCustomer());
            t.add(txnEventA);
        }
        
        long e2Stamp = fieldGenerator.randomLatency(beginningStamp);
        //skip event 2 with probability 1 in 1000
        if (random.nextInt(1000) < 9998) {
            TxnEventB txnEventB = new TxnEventB(null, e2Stamp);
            t.add(txnEventB);
        }
        
        long e3Stamp = fieldGenerator.randomLatency(e2Stamp);
        //skip event 3 with probability 1 in 10000
        if (random.nextInt(10000) < 9998) {
            TxnEventC txnEventC = new TxnEventC(null, e3Stamp, fieldGenerator.getRandomSupplier());
            t.add(txnEventC);
        }
        else
        {
            log.debug(".createNextTransaction generated missing event");
        }

        return t;
    }

    /**
     * @return Returns the maxTrans.
     */
    public int getMaxTrans() {
        return maxTrans;
    }

    protected boolean hasNext()
    {
        if (numTrans < maxTrans) {
            return true;
        } else {
            return transactionIterator.hasNext();
        }
    }

    protected TxnEventBase next()
    {
        if (transactionIterator!=null && transactionIterator.hasNext()) {
            TxnEventBase m = transactionIterator.next();
            m.setTransactionId(currentTransactionID);
            return m;
        }

        if (numTrans == maxTrans) {
            throw new IllegalStateException("There is no next element.");
        }
        //create a new transaction, with ID.
        numTrans++;
        int id = random.nextInt();
        if (id < 0) {
            id = -1 * id;
        }
        currentTransactionID = new Integer(id).toString();
        transactionEvents = createNextTransaction();
        transactionIterator = transactionEvents.iterator();
        return this.next();
    }

    private static final Log log = LogFactory.getLog(TransactionEventSource.class);
}
