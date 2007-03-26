/*
 * Created on Apr 22, 2006
 *
 */

using System;
using System.Collections;
using System.Collections.Generic;

using net.esper.client;
using net.esper.client.time;
using net.esper.compat;
using net.esper.example.transaction;

namespace net.esper.example.transaction.sim
{
    /// <summary>Runs the generator.</summary>
    /// <author>Hans Gilde</author>

    public class TxnGenMain
    {
        private static EDictionary<String, int?> BUCKET_SIZES = new LinkedDictionary<String, int?>();

        static TxnGenMain()
        {
            BUCKET_SIZES.Put("tiniest", 20);
            BUCKET_SIZES.Put("tiny", 499);
            BUCKET_SIZES.Put("small", 4999);
            BUCKET_SIZES.Put("medium", 14983);
            BUCKET_SIZES.Put("large", 49999);
            BUCKET_SIZES.Put("larger", 1999993);
            BUCKET_SIZES.Put("largerer", 9999991);
        }
        
        /// <summary>
        /// Application entry point
        /// </summary>
        /// <param name="args"></param>

        public static void Main(String[] args)
        {
            if (args.Length < 2)
            {
                Console.Out.WriteLine("Arguments are: <bucket_size> <num_transactions>");
                Environment.Exit(-1);
            }

            int? bucketSize;
            try
            {
                bucketSize = BUCKET_SIZES.Fetch(args[0]);
            }
            catch (NullReferenceException e)
            {
                Console.Out.WriteLine("Invalid bucket size:");
                foreach (String key in BUCKET_SIZES.Keys)
                {
                    Console.Out.WriteLine("\t" + key + " -> " + BUCKET_SIZES.Fetch(key));
                }

                Environment.Exit(-2);
                return;
            }

            int numTransactions;
            try
            {
                numTransactions = Int32.Parse(args[1]);
            }
            catch (FormatException e)
            {
                Console.Out.WriteLine("Invalid num transactions");
                Environment.Exit(-2);
                return;
            }

            // Run the sample
            Console.Out.WriteLine("Using bucket size of " + bucketSize + " with " + numTransactions + " transactions");
            TxnGenMain txnGenMain = new TxnGenMain(bucketSize.Value, numTransactions);
            txnGenMain.Run();
        }

        private int bucketSize;
        private int numTransactions;

        /// <summary>
        /// Initializes a new instance of the <see cref="TxnGenMain"/> class.
        /// </summary>
        /// <param name="bucketSize">Size of the bucket.</param>
        /// <param name="numTransactions">The num transactions.</param>
        public TxnGenMain(int bucketSize, int numTransactions)
        {
            this.bucketSize = bucketSize;
            this.numTransactions = numTransactions;
        }

        /// <summary>
        /// Runs this instance.
        /// </summary>
        public void Run()
        {
            // Configure engine with event names to make the statements more readable.
            // This could also be done in a configuration file.
            Configuration configuration = new Configuration();
            configuration.AddEventTypeAlias("TxnEventA", typeof(TxnEventA).FullName);
            configuration.AddEventTypeAlias("TxnEventB", typeof(TxnEventB).FullName);
            configuration.AddEventTypeAlias("TxnEventC", typeof(TxnEventC).FullName);

            // Get engine instance
            EPServiceProvider epService = EPServiceProviderManager.GetProvider("TransactionExample", configuration);

            // We will be supplying timer events externally.
            // We will assume that each bucket arrives within a defined period of time.
            epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));

            // Set up statement for listening to combined events
            CombinedEventStmt combinedEventStmt = new CombinedEventStmt(epService.EPAdministrator);
            combinedEventStmt.AddListener(new CombinedEventListener().Update);

            // Set up statements for realtime summary latency data - overall totals and totals per customer and per supplier
            RealtimeSummaryStmt realtimeSummaryStmt = new RealtimeSummaryStmt(epService.EPAdministrator);
            realtimeSummaryStmt.AddTotalsListener(new RealtimeSummaryTotalsListener().Update);
            realtimeSummaryStmt.AddByCustomerListener(new RealtimeSummaryGroupListener("customerId").Update);
            realtimeSummaryStmt.AddBySupplierListener(new RealtimeSummaryGroupListener("supplierId").Update);

            // Set up statement for finding missing events
            FindMissingEventStmt findMissingEventStmt = new FindMissingEventStmt(epService.EPAdministrator);
            findMissingEventStmt.AddListener(new FindMissingEventListener().Update);

            // The feeder to feed the engine
            FeederOutputStream feeder = new FeederOutputStream(epService.EPRuntime);

            // Generate transactions
            TransactionEventSource source = new TransactionEventSource(numTransactions);
            PrinterOutputStream printer = new PrinterOutputStream(Console.Out);  // Use this to print stream without feeding
            ShuffledBucketOutput output = new ShuffledBucketOutput(source, feeder, bucketSize);

            // Feed events
            output.Output();
        }
    }
}