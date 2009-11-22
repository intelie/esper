package com.espertech.esper.client.soda;

import com.espertech.esper.collection.Pair;

import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;

public class AnnotationPart {

    private String name;

    // Map of identifier name and value can be any of the following:
    //      <"value"|attribute name, constant|array of value (Object[])| AnnotationPart
    private List<Pair<String, Object>> attributes = new ArrayList<Pair<String, Object>>();

    public AnnotationPart(String name) {
        this.name = name;
    }

    /**
     * Ctor.
     * @param name name of annotation
     * @param attributes are the attribute values
     */
    public AnnotationPart(String name, List<Pair<String, Object>> attributes)
    {
        this.name = name;
        this.attributes = attributes;
    }

    /**
     * Returns annotation interface class name.
     * @return name of class, can be fully qualified
     */
    public String getName()
    {
        return name;
    }

    public void addValue(Object value) {
        attributes.add(new Pair<String, Object>("value", value));
    }

    public void addValue(String name, Object value) {
        attributes.add(new Pair<String, Object>(name, value));
    }

    /**
     * Returns annotation attributes.
     * @return the attribute values
     */
    public List<Pair<String, Object>> getAttributes()
    {
        return attributes;
    }

    public static void toEPL(StringWriter writer, List<AnnotationPart> annotations) {
        if ((annotations == null) || (annotations.isEmpty())) {
            return;
        }

        String delimiter = "";
        for (AnnotationPart part : annotations) {
            writer.append(delimiter);
            part.toEPL(writer);
            delimiter = " ";
        }
        writer.append(" ");
    }

    public void toEPL(StringWriter writer) {
        writer.append("@");
        writer.append(name);

        if (attributes.isEmpty()) {
            return;
        }

        if (attributes.size() == 1) {
            if (attributes.get(0).getFirst() == null || attributes.get(0).getFirst().equals("value")) {
                writer.append("(");
                toEPL(writer, attributes.get(0).getSecond());
                writer.append(")");
                return;
            }
        }

        String delimiter = "";
        writer.append("(");
        for (Pair<String, Object> pair : attributes) {
            if (pair.getSecond() == null) {
                return;
            }
            writer.append(delimiter);
            writer.append(pair.getFirst());
            writer.append("=");
            toEPL(writer, pair.getSecond());
            delimiter = ",";
        }
        writer.append(")");
    }

    private void toEPL(StringWriter writer, Object second) {
        if (second instanceof String) {
            writer.append("'");
            writer.append(second.toString());
            writer.append("'");
        }
        else if (second instanceof AnnotationPart) {
            ((AnnotationPart) second).toEPL(writer);
        }
        else if (second.getClass().isEnum()) {
            writer.append(second.getClass().getName());
            writer.append(".");
            writer.append(second.toString());
        }
        else if (second.getClass().isArray()) {
            String delimiter = "";
            writer.append("{");
            for (int i = 0; i < Array.getLength(second); i++)
            {
                writer.append(delimiter);
                toEPL(writer, Array.get(second, i));
                delimiter = ",";
            }
            writer.append("}");
        }
        else {
            writer.append(second.toString());
        }
    }
}
