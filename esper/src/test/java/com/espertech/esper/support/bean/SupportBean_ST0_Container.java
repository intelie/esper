package com.espertech.esper.support.bean;

import java.util.ArrayList;
import java.util.List;

public class SupportBean_ST0_Container {

    private static String[] samples;

    public static void setSamples(String[] samples) {
        SupportBean_ST0_Container.samples = samples;
    }

    private List<SupportBean_ST0> contained;

    public SupportBean_ST0_Container(List<SupportBean_ST0> contained) {
        this.contained = contained;
    }

    public static List<SupportBean_ST0> makeSampleList() {
        if (samples == null) {
            return null;
        }
        return make2Value(samples).getContained();
    }

    public static SupportBean_ST0[] makeSampleArray() {
        if (samples == null) {
            return null;
        }
        List<SupportBean_ST0> items = make2Value(samples).getContained();
        return items.toArray(new SupportBean_ST0[items.size()]);
    }

    public static SupportBean_ST0_Container make3Value(String ... values) {
        List<SupportBean_ST0> contained = new ArrayList<SupportBean_ST0>();
        for (int i = 0; i < values.length; i++) {
            String[] triplet = values[i].split(",");
            contained.add(new SupportBean_ST0(triplet[0], triplet[1], Integer.parseInt(triplet[2])));
        }
        return new SupportBean_ST0_Container(contained);
    }

    public static SupportBean_ST0_Container make2Value(String ... values) {
        if (values == null) {
            return new SupportBean_ST0_Container(null);
        }
        List<SupportBean_ST0> contained = new ArrayList<SupportBean_ST0>();
        for (int i = 0; i < values.length; i++) {
            String[] pair = values[i].split(",");
            contained.add(new SupportBean_ST0(pair[0], Integer.parseInt(pair[1])));
        }
        return new SupportBean_ST0_Container(contained);
    }

    public List<SupportBean_ST0> getContained() {
        return contained;
    }

    public static SupportBean_ST0 makeTest(String value) {
        return make2Value(value).getContained().get(0);
    }
}
