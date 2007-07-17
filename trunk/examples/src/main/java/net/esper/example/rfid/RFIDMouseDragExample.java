package net.esper.example.rfid;

import net.esper.client.Configuration;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPServiceProvider;
import net.esper.client.UpdateListener;
import net.esper.event.EventBean;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class RFIDMouseDragExample extends JFrame
{
    private final static int WIDTH = 600;
    private final static int HEIGHT = 400;

    private DisplayCanvas canvas;

    public RFIDMouseDragExample() {
        super();

        // Setup engine
        Configuration config = new Configuration();
        config.addEventTypeAlias("LocationReport", LocationReport.class);

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);

        LRMovingZoneStmt.createStmt(epService, 10, new UpdateListener()
        {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                for (int i = 0; i < newEvents.length; i++)
                {
                    System.out.println("ALERT: Asset group not moving together, zone " +
                            newEvents[i].get("Part.zone"));
                }
            }
        });

        // Setup window
        Container container = getContentPane();
        canvas = new DisplayCanvas(epService, WIDTH, HEIGHT);
        container.add(canvas);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(WIDTH, HEIGHT);
        setVisible(true);
    }

    public static void main(String arg[]) {
        new RFIDMouseDragExample();
    }
}
