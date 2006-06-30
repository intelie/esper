/*
 * Created on Apr 22, 2006
 *
 */
package net.esper.example.transaction.sim;

import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPServiceProvider;
import net.esper.client.Configuration;
import net.esper.client.time.TimerControlEvent;
import net.esper.example.transaction.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;


/** Runs the generator.
 * 
 * @author Hans Gilde
 *
 */
public class TxnGenMain {

    private static Map<String,Integer> BUCKET_SIZES = new LinkedHashMap<String,Integer>();

    static {
        BUCKET_SIZES.put("tiniest", 20);
        BUCKET_SIZES.put("tiny", 499);
        BUCKET_SIZES.put("small", 4999);
        BUCKET_SIZES.put("medium", 14983);
        BUCKET_SIZES.put("large", 49999);
        BUCKET_SIZES.put("larger", 1999993);
        BUCKET_SIZES.put("largerer", 9999991);
    }

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Arguments are: bucket_size num_transactions");
            System.exit(-1);
        }
        
        int bucketSize;
        try {
            bucketSize = BUCKET_SIZES.get(args[0]);
        } catch (NullPointerException e) {
            System.out.println("Invalid bucket size:");
            for(String key:BUCKET_SIZES.keySet()) {
                System.out.println("\t"+key+" -> "+BUCKET_SIZES.get(key));
            }
            
            System.exit(-2);
            return;
        }

        int numTransactions;
        try {
            numTransactions = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid num transactions");
            System.exit(-2);
            return;
        }

        // Run the sample
        System.out.println("Using bucket size of " + bucketSize + " with " + numTransactions + " transactions");
        TxnGenMain txnGenMain = new TxnGenMain(bucketSize, numTransactions);
        txnGenMain.run();
    }

    private int bucketSize;
    private int numTransactions;

    public TxnGenMain(int bucketSize, int numTransactions)
    {
        this.bucketSize = bucketSize;
        this.numTransactions = numTransactions;
    }

    public void run() throws IOException
    {
        // Configure engine with event names to make the statements more readable.
        // This could also be done in a configuration file.
        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias("EventA", TxnEventA.class.getName());
        configuration.addEventTypeAlias("EventB", TxnEventB.class.getName());
        configuration.addEventTypeAlias("EventC", TxnEventC.class.getName());

        // Get engine instance
        EPServiceProvider epService = EPServiceProviderManager.getProvider("TransactionExample", configuration);

        // We will be supplying timer events externally.
        // We will assume that each bucket arrives within a defined period of time.
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        // Set up statement for listening to combined events
        CombinedEventStmt combinedEventStmt = new CombinedEventStmt(epService.getEPAdministrator());
        combinedEventStmt.addListener(new CombinedEventListener());

        // Set up statements for realtime summary latency data - overall totals and totals per customer and per supplier
        RealtimeSummaryStmt realtimeSummaryStmt = new RealtimeSummaryStmt(epService.getEPAdministrator());
        realtimeSummaryStmt.addTotalsListener(new RealtimeSummaryTotalsListener());
        realtimeSummaryStmt.addByCustomerListener(new RealtimeSummaryGroupListener("customerId"));
        realtimeSummaryStmt.addBySupplierListener(new RealtimeSummaryGroupListener("supplierId"));

        // Set up statement for finding missing events
        FindMissingEventStmt findMissingEventStmt = new FindMissingEventStmt(epService.getEPAdministrator());
        findMissingEventStmt.addListener(new FindMissingEventListener());

        // The feeder to feed the engine
        FeederOutputStream feeder = new FeederOutputStream(epService.getEPRuntime());

        // Generate transactions
        TransactionEventSource source = new TransactionEventSource(numTransactions);
        PrinterOutputStream printer = new PrinterOutputStream(System.out);  // Use this to print stream without feeding
        ShuffledBucketOutput output = new ShuffledBucketOutput(source, feeder, bucketSize);

        // Feed events
        output.output();
    }
}
