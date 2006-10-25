package net.esper.regression.persist;

import junit.framework.TestCase;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.command.*;
import net.esper.support.command.engine.*;
import net.esper.support.command.stmt.*;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestAggregationReplay extends TestCase
{
    // 2 statements all aggregations, group-all and ungrouped, short test
    private LinkedHashMap<Integer, StmtLevelCommand> commandSetOne;

    // 1 statement simple aggregation many event test
    private LinkedHashMap<Integer, StmtLevelCommand> commandSetTwo;

    public void setUp()
    {
        setUpCommandsOne();
        setUpCommandsTwo();
    }

    public void testSingleEngineOne()
    {
        CommandPlayer.playStatements(commandSetOne);
        tryReplay(25, commandSetOne);
        tryReplay(35, commandSetOne);
        tryReplay(45, commandSetOne);
        tryReplay(55, commandSetOne);
    }

    public void testSingleEngineTwo()
    {
        CommandPlayer.playStatements(commandSetTwo);
        tryReplay(15, commandSetTwo);
        tryReplay(25, commandSetTwo);
        tryReplay(35, commandSetTwo);
        tryReplay(45, commandSetTwo);
        tryReplay(55, commandSetTwo);
    }

    private void tryReplay(int breakPointEngine, LinkedHashMap<Integer, StmtLevelCommand> commandSet)
    {
        // Play into first engine instance
        List<EngineLevelCommand> txnCommands = new LinkedList<EngineLevelCommand>();
        txnCommands.add(new CreateEngineCmd("engine1"));
        txnCommands.add(new CreateLogEntryContainerCmd("log1"));
        txnCommands.add(new TransactionCmd("engine1", 1, breakPointEngine, "log1"));

        // Replay log entries to second engine instance
        txnCommands.add(new CreateEngineCmd("engine2"));
        txnCommands.add(new LogReplayCmd("log1", "engine2"));
        txnCommands.add(new TransactionCmd("engine2", breakPointEngine, 100, "log1"));
        CommandPlayer.playEngine(commandSet, txnCommands);
    }

    private void setUpCommandsTwo()
    {
        commandSetTwo = new LinkedHashMap<Integer, StmtLevelCommand>();
        String statementText = "select sum(intPrimitive) as mysum from " + SupportBean.class.getName();
        commandSetTwo.put(10, new CreateEQLCmd(statementText));
        commandSetTwo.put(11, new AddListenerCmd(0));

        commandSetTwo.put(20, new SendEventCmd(makeSupportBeanEvent(10)));
        commandSetTwo.put(21, new AssertReceivedCmd(0, 0,
                    new Object[][] { {"mysum", 10}}, // new data
                    new Object[][] { {"mysum", null}} // old data
                ));

        commandSetTwo.put(30, new SendEventCmd(makeSupportBeanEvent(50)));
        commandSetTwo.put(31, new AssertReceivedCmd(0, 0,
                    new Object[][] { {"mysum", 60}}, // new data
                    new Object[][] { {"mysum", 10}} // old data
                ));

        commandSetTwo.put(40, new SendEventCmd(makeSupportBeanEvent(30)));
        commandSetTwo.put(41, new AssertReceivedCmd(0, 0,
                    new Object[][] { {"mysum", 90}}, // new data
                    new Object[][] { {"mysum", 60}} // old data
                ));

        commandSetTwo.put(50, new SendEventCmd(makeSupportBeanEvent(1)));
        commandSetTwo.put(51, new AssertReceivedCmd(0, 0,
                    new Object[][] { {"mysum", 91}}, // new data
                    new Object[][] { {"mysum", 90}} // old data
                ));

        commandSetTwo.put(60, new SendEventCmd(makeSupportBeanEvent(-10)));
        commandSetTwo.put(61, new AssertReceivedCmd(0, 0,
                    new Object[][] { {"mysum", 81}}, // new data
                    new Object[][] { {"mysum", 91}} // old data
                ));
    }

    private void setUpCommandsOne()
    {
        commandSetOne = new LinkedHashMap<Integer, StmtLevelCommand>();

        String statementTextOne = "select " +
                "avg(price) as avgPrice," +
                "sum(price) as sumPrice," +
                "min(price) as minPrice," +
                "max(price) as maxPrice," +
                "median(price) as medianPrice," +
                "stddev(price) as stddevPrice," +
                "avedev(price) as avedevPrice," +
                "count(*) as datacount," +
                "count(distinct price) as countDistinctPrice " +
                "from " + SupportMarketDataBean.class.getName();
        commandSetOne.put(10, new CreateEQLCmd(statementTextOne));
        commandSetOne.put(11, new AddListenerCmd(0));

        String statementTextTwo = "select string, avg(intPrimitive) as avgInt from " +
                SupportBean.class.getName() + " group by string";
        commandSetOne.put(30, new CreateEQLCmd(statementTextTwo));
        commandSetOne.put(31, new AddListenerCmd(1));

        Object[] events = new Object[] {
                makeMarketDataEvent(100),
                makeSupportBeanEvent(1, "DELL"),
                makeMarketDataEvent(200),
                makeSupportBeanEvent(2, "IBM")
        };
        commandSetOne.put(40, new SendEventCmd(events));

        commandSetOne.put(50, new SendEventCmd(makeMarketDataEvent(150)));
        commandSetOne.put(51, new AssertReceivedCmd(0, 0,
                    new Object[][] {
                            {"avgPrice", (150 + 100 + 200) / 3.0},
                            {"sumPrice", 150 + 100 + 200d},
                            {"minPrice", 100d},
                            {"maxPrice", 200d},
                            {"medianPrice", 150d},
                            {"stddevPrice", 50d},
                            {"avedevPrice", 33 + 1/3d},
                            {"datacount", 3L},
                            {"countDistinctPrice", 3L},
                            }, // new data
                    new Object[][] {
                            {"avgPrice", (100 + 200) / 2.0},
                            {"sumPrice", 100 + 200d},
                            {"minPrice", 100d},
                            {"maxPrice", 200d},
                            {"medianPrice", 150d},
                            {"stddevPrice", 70.71067811865476},
                            {"avedevPrice", 50d},
                            {"datacount", 2L},
                            {"countDistinctPrice", 2L},
                            } // old data
                ));

        commandSetOne.put(60, new SendEventCmd(makeSupportBeanEvent(10, "IBM")));
        commandSetOne.put(61, new AssertReceivedCmd(1, 0,
                    new Object[][] {
                            {"string", "IBM"},
                            {"avgInt", (2 + 10) / 2.0},
                            }, // new data
                    new Object[][] {
                            {"string", "IBM"},
                            {"avgInt", 2.0},
                            } // old data
                ));
    }

    private SupportMarketDataBean makeMarketDataEvent(double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean("DELL", price, 0l, null);
        return bean;
    }

    private SupportBean makeSupportBeanEvent(int intPrimitive)
    {
        return makeSupportBeanEvent(intPrimitive, "");
    }

    private SupportBean makeSupportBeanEvent(int intPrimitive, String string)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        bean.setString(string);
        return bean;
    }

    private static Log log = LogFactory.getLog(TestAggregationReplay.class);
}
