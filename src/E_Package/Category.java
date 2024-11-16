package E_Package;

public class Category {
    private String code;
    private String fileName;

    public Category(String code, String fileName) {
        this.code = code;
        this.fileName = fileName;
    }

    //getters
    public String getCode() {
        return code;
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
