package E_Package;

public class Category {
    private String code;
    private String name;
    private String fileName;

    public Category(String code, String fileName) {
        this.code = code;
        this.name = name;
        this.fileName = fileName;
    }

    //getters
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    //setters
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String toString() {
        return code + ": " + fileName;
    }
}
