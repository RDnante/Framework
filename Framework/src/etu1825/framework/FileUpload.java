package etu1825.framework;

public class FileUpload {
    String name;
    String path;
    byte[] bytearray;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public byte[] getBytearray() {
        return bytearray;
    }
    public void setBytearray(byte[] bytearray) {
        this.bytearray = bytearray;
    }

    public FileUpload() {
    }
}
