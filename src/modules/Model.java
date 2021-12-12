package modules;

public abstract class Model {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String toString();
    public abstract Boolean isEmpty();
}
