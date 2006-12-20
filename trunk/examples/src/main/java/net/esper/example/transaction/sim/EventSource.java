/*
 * Created on Apr 22, 2006
 *
 */
package net.esper.example.transaction.sim;

import net.esper.example.transaction.TxnEventBase;

import java.util.Iterator;
import java.util.Map;

/** An Iterable source of events.
 * 
 * @author Hans Gilde
 *
 */
public abstract class EventSource implements Iterable<TxnEventBase> {

    /* (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<TxnEventBase> iterator() {
        return new InternalIterator();
    }
    
    protected abstract boolean hasNext();
    protected abstract TxnEventBase next();
        
    private class InternalIterator implements Iterator<TxnEventBase> {

        public boolean hasNext() {
            return EventSource.this.hasNext();
        }

        public TxnEventBase next() {
            return EventSource.this.next();
        }

        public void remove() {
            throw new UnsupportedOperationException("This iterator does not suppoer removal.");
        }
        
    }
    
}
