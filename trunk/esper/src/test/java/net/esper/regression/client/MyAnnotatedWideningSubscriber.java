package net.esper.regression.client;

import net.esper.client.ann.EPSubscriberMethod;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.bean.SupportEnum;
import net.esper.support.bean.SupportBean;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class MyAnnotatedWideningSubscriber
{
    private ArrayList<Map> indicateMap = new ArrayList<Map>();
    private ArrayList<Object[]> indicateObjectArray = new ArrayList<Object[]>();
    private ArrayList<Object> indicateObject = new ArrayList<Object>();

    @EPSubscriberMethod(
        epl = "select bytePrimitive, intPrimitive, longPrimitive, floatPrimitive from SupportBean(string='E1')",
        name = "StmtA"
        )
    public void indicateWidening(int wideByte, long wideInt, double wideLong, double wideFloat)
    {
        indicateObjectArray.add(new Object[] {wideByte, wideInt, wideLong, wideFloat});
    }

    @EPSubscriberMethod(
        epl = "select * from SupportBean(string='E2')",
        name = "StmtC"
        )
    public void indicateWildcardBean(SupportBean bean)
    {
        indicateObjectArray.add(new Object[] {bean});
    }

    @EPSubscriberMethod(
        epl = "select * from SupportBean(string='E3').win:length_batch(2)",
        name = "StmtD"
        )
    public void indicateWildcardBeanBatch(SupportBean bean)
    {
        indicateObject.add(bean);
    }

    @EPSubscriberMethod(
        epl = "select nested, nested.nestedNested from SupportBeanComplexProps",
        name = "StmtB"
        )
    public void indicateNested(SupportBeanComplexProps.SupportBeanSpecialGetterNested n,
                               SupportBeanComplexProps.SupportBeanSpecialGetterNestedNested nn)
    {
        indicateObjectArray.add(new Object[] {n, nn});
    }

    @EPSubscriberMethod(
        epl = "select string, supportEnum from SupportBeanWithEnum",
        name = "StmtC"
        )
    public void indicateEnum(String string, SupportEnum supportEnum)
    {
        indicateObjectArray.add(new Object[] {string, supportEnum});
    }

    public List<Object[]> getAndResetIndicateObjectVarags()
    {
        List<Object[]> result = indicateObjectArray;
        indicateObjectArray = new ArrayList<Object[]>();
        return result;
    }

    public List<Object> getAndResetIndicateObject()
    {
        List<Object> result = indicateObject;
        indicateObject = new ArrayList<Object>();
        return result;
    }
}
