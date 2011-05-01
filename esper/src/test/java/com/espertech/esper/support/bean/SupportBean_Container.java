package com.espertech.esper.support.bean;

import java.util.ArrayList;
import java.util.List;

public class SupportBean_Container {

    private List<SupportBean> beans;

    public SupportBean_Container(List<SupportBean> beans) {
        this.beans = beans;
    }

    public List<SupportBean> getBeans() {
        return beans;
    }

    public void setBeans(List<SupportBean> beans) {
        this.beans = beans;
    }
}
