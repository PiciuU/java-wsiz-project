package models;

public abstract class Model {
    private int id;
    private String customField;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomField() {
        return customField;
    }

    public void setCustomField(String customField) {
        this.customField = customField;
    }

    public abstract String toString();
    public abstract Boolean isEmpty();

    /** Mandatory methods to be overwritten */

    public void setValues() { throw new RuntimeException("Method not overwritten"); };
    public void updateValues() { throw new RuntimeException("Method not overwritten"); };
}
