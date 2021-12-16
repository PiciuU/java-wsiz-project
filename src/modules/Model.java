package modules;

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

    // DEPRECATED
//    public String getType(Object obj) {
//        if (obj instanceof String) return "String";
//        else if (obj instanceof Integer) return "Int";
//        else return "Object";
//    }

    public abstract String toString();
    public abstract Boolean isEmpty();
}
