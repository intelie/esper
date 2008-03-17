/*
 * Created on Apr 23, 2006
 *
 */
package com.espertech.esper.example.transaction.sim;

import com.espertech.esper.example.transaction.TxnEventBase;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** Subclass to output events in your preferred format.
 * 
 * @author Hans Gilde, Thomas Bernhardt
 *
 */
public class PrinterOutputStream implements OutputStream {
    private PrintStream os;
    
    public PrinterOutputStream(PrintStream os) {
        this.os = os;
    }
    
    public void output(List<TxnEventBase> bucket) throws IOException {
        log.info(".output Start of bucket, " + bucket.size() + " items");
        for(TxnEventBase event:bucket) {
            os.println(event.toString());
        }
        log.info(".output End of bucket");
    }
    private static final Log log = LogFactory.getLog(PrinterOutputStream.class);
}
