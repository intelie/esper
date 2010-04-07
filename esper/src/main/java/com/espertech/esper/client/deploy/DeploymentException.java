package com.espertech.esper.client.deploy;

import java.util.List;
import java.io.StringWriter;

public class DeploymentException extends Exception {

    public static String newline = System.getProperty("line.separator");

    private List<DeploymentItemException> exceptions;

    public DeploymentException(String message, List<DeploymentItemException> exceptions) {
        super(message);
        this.exceptions = exceptions;
    }

    public List<DeploymentItemException> getExceptions() {
        return exceptions;
    }

    public String getDetail() {
        StringWriter detail = new StringWriter();
        int count = 0;
        String delimiter = "";
        for (DeploymentItemException item : exceptions) {
            detail.write(delimiter);
            detail.write("Exception #");
            detail.write(Integer.toString(count));
            detail.write(" : ");
            detail.write(item.getInner().getMessage());
            delimiter = newline;
            count++;
        }
        return detail.toString();
    }
}
