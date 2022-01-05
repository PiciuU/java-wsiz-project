package models;

public class Vehicle extends Model {
    /* Fields */

    private int customerId;
    private String producer;
    private String model;
    private int horsepower;
    private String productionYear;
    private String numberPlate;

    /* Getters & Setters */

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    /* Constructors */

    public Vehicle() {
        customerId = 0;
        producer = "undefined";
        model = "undefined";
        horsepower = 0;
        productionYear = null;
        numberPlate = "undefined";
    }

    public Vehicle(int id, int customerId, String producer, String model, int horsepower, String productionYear, String numberPlate) {
        setValues(id, customerId, producer, model, horsepower, productionYear, numberPlate);
    }

    public Vehicle(int id, int customerId, String producer, String model, int horsepower, String productionYear, String numberPlate, String customField) {
        setValues(id, customerId, producer, model, horsepower, productionYear, numberPlate, customField);
    }

    /* Methods */

    public void setValues(int id, int customerId, String producer, String model, int horsepower, String productionYear, String numberPlate, String customField) {
        setId(id);
        this.customerId = customerId;
        this.producer = producer;
        this.model = model;
        this.horsepower = horsepower;
        this.productionYear = productionYear;
        this.numberPlate = numberPlate;
        setCustomField(customField);
    }

    public void setValues(int id, int customerId, String producer, String model, int horsepower, String productionYear, String numberPlate) {
        setId(id);
        this.customerId = customerId;
        this.producer = producer;
        this.model = model;
        this.horsepower = horsepower;
        this.productionYear = productionYear;
        this.numberPlate = numberPlate;
    }

    public void updateValues(int customerId, String producer, String model, int horsepower, String productionYear, String numberPlate) {
        this.customerId = customerId;
        this.producer = producer;
        this.model = model;
        this.horsepower = horsepower;
        this.productionYear = productionYear;
        this.numberPlate = numberPlate;
    }

    /* Override Methods */

    @Override
    public String toString() {
        return "Vehicle ID: " + getId() +
                "\nCustomer ID: " + customerId +
                "\nProducer: " + producer +
                "\nModel: " + model +
                "\nHorsepower: " + horsepower +
                "\nProduction year: " + productionYear +
                "\nNumber plate: " + numberPlate;
    }

    @Override
    public Boolean isEmpty() {
        return getId() == 0;
    }
}
