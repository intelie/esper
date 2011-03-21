package com.espertech.esper.regression.enummethod;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.lrreport.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Collection;

public class TestEnumDocSamples extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("Item", Item.class);
        config.addEventType("LocationReport", LocationReport.class);
        config.addEventType("Zone", Zone.class);
        config.addPlugInSingleRowFunction("inrect", LRUtil.class.getName(), "inrect");
        config.addPlugInSingleRowFunction("distance", LRUtil.class.getName(), "distance");
        config.addPlugInSingleRowFunction("getZoneNames", Zone.class.getName(), "getZoneNames");

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testHowToUse() {
        String eplFragment = "select items.where(i => i.location.x = 0 and i.location.y = 0) as zeroloc from LocationReport";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);

        epService.getEPRuntime().sendEvent(LocationReportFactory.makeSmall());

        Item[] items = toArrayItems((Collection<Item>)listener.assertOneGetNewAndReset().get("zeroloc"));
        assertEquals(1, items.length);
        assertEquals("P00020", items[0].getAssetId());

        stmtFragment.destroy();
        eplFragment = "select items.where(i => i.location.x = 0).where(i => i.location.y = 0) as zeroloc from LocationReport";
        stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);

        epService.getEPRuntime().sendEvent(LocationReportFactory.makeSmall());

        items = toArrayItems((Collection<Item>) listener.assertOneGetNewAndReset().get("zeroloc"));
        assertEquals(1, items.length);
        assertEquals("P00020", items[0].getAssetId());
    }

    public void testSubquery() {

        String eplFragment = "select assetId," +
                "  (select * from Zone.win:keepall()).where(z => inrect(z.rectangle, location)) as zones " +
                "from Item";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);

        epService.getEPRuntime().sendEvent(new Zone("Z1", new Rectangle(0, 0, 20, 20)));
        epService.getEPRuntime().sendEvent(new Zone("Z2", new Rectangle(21, 21, 40, 40)));
        epService.getEPRuntime().sendEvent(new Item("A1", new Location(10, 10)));

        Zone[] zones = toArrayZones((Collection<Zone>) listener.assertOneGetNewAndReset().get("zones"));
        assertEquals(1, zones.length);
        assertEquals("Z1", zones[0].getName());
    }

    public void testNamedWindow() {
        epService.getEPAdministrator().createEPL("create window ZoneWindow.win:keepall() as Zone");
        epService.getEPAdministrator().createEPL("insert into ZoneWindow select * from Zone");

        String epl = "select ZoneWindow.where(z => inrect(z.rectangle, location)) as zones from Item";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new Zone("Z1", new Rectangle(0, 0, 20, 20)));
        epService.getEPRuntime().sendEvent(new Zone("Z2", new Rectangle(21, 21, 40, 40)));
        epService.getEPRuntime().sendEvent(new Item("A1", new Location(10, 10)));

        Zone[] zones = toArrayZones((Collection<Zone>) listener.assertOneGetNewAndReset().get("zones"));
        assertEquals(1, zones.length);
        assertEquals("Z1", zones[0].getName());
        stmt.destroy();

        epl = "select ZoneWindow(name in ('Z4', 'Z5', 'Z3')).where(z => inrect(z.rectangle, location)) as zones from Item";
        stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new Zone("Z3", new Rectangle(0, 0, 20, 20)));
        epService.getEPRuntime().sendEvent(new Item("A1", new Location(10, 10)));

        zones = toArrayZones((Collection<Zone>) listener.assertOneGetNewAndReset().get("zones"));
        assertEquals(1, zones.length);
        assertEquals("Z3", zones[0].getName());
    }

    public void testAccessAggWindow() {
        String epl = "select window(*).where(p => distance(0, 0, p.location.x, p.location.y) < 20) as centeritems " +
                "from Item(type='P').win:time(10) group by assetId";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new Item("P0001", new Location(10, 10), "P", null));
        Item[] items = toArrayItems((Collection<Item>) listener.assertOneGetNewAndReset().get("centeritems"));
        assertEquals(1, items.length);
        assertEquals("P0001", items[0].getAssetId());

        epService.getEPRuntime().sendEvent(new Item("P0002", new Location(10, 1000), "P", null));
        items = toArrayItems((Collection<Item>) listener.assertOneGetNewAndReset().get("centeritems"));
        assertEquals(0, items.length);
    }

    public void testPrevWindow() {
        String epl = "select prevwindow(items).where(p => distance(0, 0, p.location.x, p.location.y) < 20) as centeritems " +
                "from Item(type='P').win:time(10) as items";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new Item("P0001", new Location(10, 10), "P", null));
        Item[] items = toArrayItems((Collection<Item>) listener.assertOneGetNewAndReset().get("centeritems"));
        assertEquals(1, items.length);
        assertEquals("P0001", items[0].getAssetId());

        epService.getEPRuntime().sendEvent(new Item("P0002", new Location(10, 1000), "P", null));
        items = toArrayItems((Collection<Item>) listener.assertOneGetNewAndReset().get("centeritems"));
        assertEquals(1, items.length);
        assertEquals("P0001", items[0].getAssetId());
    }

    public void testProperties() {
        String epl = "select items.where(p => distance(0, 0, p.location.x, p.location.y) < 20) as centeritems " +
                "from LocationReport";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(LocationReportFactory.makeSmall());
        Item[] items = toArrayItems((Collection<Item>) listener.assertOneGetNewAndReset().get("centeritems"));
        assertEquals(1, items.length);
        assertEquals("P00020", items[0].getAssetId());
    }

    public void testUDFSingleRow() {
        epService.getEPAdministrator().getConfiguration().addImport(ZoneFactory.class);
        
        String epl = "select ZoneFactory.getZones().where(z => inrect(z.rectangle, item.location)) as zones\n" +
                "from Item as item";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new Item("A1", new Location(5, 5)));
        Zone[] zones = toArrayZones((Collection<Zone>) listener.assertOneGetNewAndReset().get("zones"));
        assertEquals(1, zones.length);
        assertEquals("Z1", zones[0].getName());
    }

    public void testDeclared() {
        String epl = "expression passengers {\n" +
                "  lr => lr.items.where(l => l.type='P')\n" +
                "}\n" +
                "select passengers(lr) as p," +
                "passengers(lr).where(x => assetId = 'P01') as p2 from LocationReport lr";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(LocationReportFactory.makeSmall());
        Item[] items = toArrayItems((Collection<Item>) listener.assertOneGetNewAndReset().get("p"));
        assertEquals(2, items.length);
        assertEquals("P00002", items[0].getAssetId());
        assertEquals("P00020", items[1].getAssetId());
    }

    public void testExpressions() {
        assertStmt("select items.firstof().assetId as firstcenter from LocationReport");
        assertStmt("select items.where(p => p.type = \"P\") from LocationReport");
        assertStmt("select items.where((p, ind) => p.type = \"P\" and ind > 2) from LocationReport");
        assertStmt("select items.aggregate(\"\", (result, item) => result || (case when result = \"\" then \"\" else \",\" end) || item.assetId) as assets from LocationReport");
        assertStmt("select items.allof(i => distance(i.location.x, i.location.y, 0, 0) < 1000) as assets from LocationReport");
        assertStmt("select items.average(i => distance(i.location.x, i.location.y, 0, 0)) as avgdistance from LocationReport");
        assertStmt("select items.countof(i => distance(i.location.x, i.location.y, 0, 0) < 20) as cntcenter from LocationReport");
        assertStmt("select items.firstof(i => distance(i.location.x, i.location.y, 0, 0) < 20) as firstcenter from LocationReport");
        assertStmt("select items.lastof().assetId as firstcenter from LocationReport");
        assertStmt("select items.lastof(i => distance(i.location.x, i.location.y, 0, 0) < 20) as lastcenter from LocationReport");
        assertStmt("select items.where(i => i.type = \"L\").groupby(i => assetIdPassenger) as luggagePerPerson from LocationReport");
        assertStmt("select items.where((p, ind) => p.type = \"P\" and ind > 2) from LocationReport");
        assertStmt("select items.groupby(k => assetId, v => distance(v.location.x, v.location.y, 0, 0)) as distancePerItem from LocationReport");
        assertStmt("select items.min(i => distance(i.location.x, i.location.y, 0, 0)) as mincenter from LocationReport");
        assertStmt("select items.max(i => distance(i.location.x, i.location.y, 0, 0)) as maxcenter from LocationReport");
        assertStmt("select items.minBy(i => distance(i.location.x, i.location.y, 0, 0)) as minItemCenter from LocationReport");
        assertStmt("select items.minBy(i => distance(i.location.x, i.location.y, 0, 0)).assetId as minItemCenter from LocationReport");
        assertStmt("select items.orderBy(i => distance(i.location.x, i.location.y, 0, 0)) as itemsOrderedByDist from LocationReport");
        assertStmt("select items.selectFrom(i => assetId) as itemAssetIds from LocationReport");
        assertStmt("select items.take(5) as first5Items, items.takeLast(5) as last5Items from LocationReport");
        assertStmt("select items.toMap(k => k.assetId, v => distance(v.location.x, v.location.y, 0, 0)) as assetDistance from LocationReport");
        assertStmt("select items.where(i => i.assetId = \"L001\").union(items.where(i => i.type = \"P\")) as itemsUnion from LocationReport");
        assertStmt("select ((select name from Zone.std:unique(name))).orderBy() as orderedZones from pattern [every timer:interval(30)]");
        assertStmt("create schema MyEvent as (seqone String[], seqtwo String[])");
        assertStmt("select seqone.sequenceEquals(seqtwo) from MyEvent");
        assertStmt("select (window(assetId)).orderBy() as orderedAssetIds from Item.win:time(10) group by assetId");
        assertStmt("select (prevwindow(assetId)).orderBy() as orderedAssetIds from Item.win:time(10) as items");
        assertStmt("select getZoneNames().where(z => z != \"Z1\") from pattern [every timer:interval(30)]");

        String epl = "expression myquery {itm => " +
                "((select * from Zone.win:keepall())).where(z => inrect(z.rectangle, itm.location))" +
                "} " +
                "select assetId, myquery(item) as subq, (myquery(item)).where(z => z.name = \"Z01\") as assetItem " +
                "from Item as item";
        assertStmt(epl);
    }

    private void assertStmt(String epl) {
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.destroy();

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        assertEquals(epl, model.toEPL());

        stmt = epService.getEPAdministrator().create(model);
        assertEquals(epl, stmt.getText());
    }

    private Zone[] toArrayZones(Collection<Zone> it) {
        return it.toArray(new Zone[it.size()]);
    }

    private Item[] toArrayItems(Collection<Item> it) {
        return it.toArray(new Item[it.size()]);
    }
}
