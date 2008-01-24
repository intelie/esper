package net.esper.regression.client;

import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.bean.SupportEnum;

import java.util.ArrayList;
import java.util.List;

public class MySubscriberRowByRowObjectArr
{
    private ArrayList<Object[]> indicate = new ArrayList<Object[]>();

    public void update(Object... row)
    {
        indicate.add(row);
    }

    public List<Object[]> getAndResetIndicate()
    {
        List<Object[]> result = indicate;
        indicate = new ArrayList<Object[]>();
        return result;
    }
}
