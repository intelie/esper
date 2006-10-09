package net.esper.example.terminal.mdb;

import net.esper.client.Configuration;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.example.terminal.common.*;

public class EPServiceMDBAdapter
{
    private final EPServiceProvider epService;

    public EPServiceMDBAdapter(OutboundQueueSender outboundQueueSender)
    {
        Configuration config = new Configuration();
        config.addEventTypeAlias("Checkin", Checkin.class.getName());
        config.addEventTypeAlias("Cancelled", Cancelled.class.getName());
        config.addEventTypeAlias("Completed", Completed.class.getName());
        config.addEventTypeAlias("Status", Status.class.getName());
        config.addEventTypeAlias("LowPaper", LowPaper.class.getName());
        config.addEventTypeAlias("OutOfOrder", OutOfOrder.class.getName());
        config.addEventTypeAlias("BaseTerminalEvent", BaseTerminalEvent.class.getName());

        // Get engine instance - same engine instance for all MDB instances
        epService = EPServiceProviderManager.getDefaultProvider(config);
        System.out.println(TerminalMDB.class.getName() + "::instance this=" + this.toString() + " engine=" + epService.toString());

        EPStatement statement = null;
        String stmt = null;

        stmt = "select a.term.id as terminal from pattern [ every a=Checkin -> " +
                "      ( OutOfOrder(term.id=a.term.id) and not (Cancelled(term.id=a.term.id) or Completed(term.id=a.term.id)) )]";
        statement = epService.getEPAdministrator().createEQL(stmt);
        statement.addListener(new CheckinProblemListener(outboundQueueSender));

        stmt = "select * from BaseTerminalEvent where type = 'LowPaper' or type = 'OutOfOrder'";
        statement = epService.getEPAdministrator().createEQL(stmt);
        statement.addListener(new TerminalEventListener(outboundQueueSender));

        stmt = "select '1' as terminal, 'terminal is offline' as text from pattern [ every timer:interval(60 seconds) -> (timer:interval(65 seconds) and not Status(term.id = 'T1')) ] output first every 5 minutes";
        statement = epService.getEPAdministrator().createEQL(stmt);
        statement.addListener(new TerminalStatusListener(outboundQueueSender));

        stmt = "insert into CountPerType " +
                "select type, count(*) as countPerType " +
                "from BaseTerminalEvent.win:time(10 min) " +
                "group by type " +
                "output all every 1 minutes";
        statement = epService.getEPAdministrator().createEQL(stmt);
        statement.addListener(new CountPerTypeListener(outboundQueueSender));
    }

    public void sendEvent(Object event)
    {
        synchronized (epService)
        {
            epService.getEPRuntime().sendEvent(event);
        }
    }
}
