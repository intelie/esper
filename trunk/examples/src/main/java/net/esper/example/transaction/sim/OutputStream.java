/*
 * Created on Apr 23, 2006
 *
 */
package net.esper.example.transaction.sim;

import net.esper.example.transaction.TxnEventBase;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

/** Interface to output events in your preferred format.
 * 
 *
 */
public interface OutputStream {
    public void output(List<TxnEventBase> bucket) throws IOException;
}
