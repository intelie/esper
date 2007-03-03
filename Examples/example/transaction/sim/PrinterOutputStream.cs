/*
 * Created on Apr 23, 2006
 *
 */

using System;
using System.Collections.Generic;
using System.IO;

using net.esper.example.transaction;

using org.apache.commons.logging;

namespace net.esper.example.transaction.sim
{
    /** Subclass to output events in your preferred format.
     * 
     * @author Hans Gilde, Thomas Bernhardt
     *
     */
    public class PrinterOutputStream : OutputStream
    {
        private TextWriter os;

        public PrinterOutputStream(Stream os)
        {
            this.os = new StreamWriter(os);
        }

        public PrinterOutputStream(TextWriter os)
        {
            this.os = os;
        }

        public void Output(IList<TxnEventBase> bucket)
        {
            log.Info(".output Start of bucket, " + bucket.Count + " items");
            foreach (TxnEventBase eventBean in bucket)
            {
                os.WriteLine(eventBean.ToString());
            }
            log.Info(".output End of bucket");
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}