package etu1825.framework;

public class Mapping {
    String className;
    String Method;

    public Mapping(String className, String method) {
        this.setClassName(className);
        this.setMethod(method);
    }

    public Mapping() {
    }

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getMethod() {
        return Method;
    }
    public void setMethod(String method) {
        Method = method;
    }
}
