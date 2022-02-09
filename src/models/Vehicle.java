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

    /**
     * Get the vehicle's customer ID
     *
     * @return An integer representing the vehicle's customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Set the vehicle's customer ID
     *
     * @param customerId An integer containing the vehicle's customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Get the vehicle's producer
     *
     * @return A string representing the vehicle's producer
     */
    public String getProducer() {
        return producer;
    }

    /**
     * Set the vehicle's producer
     *
     * @param producer A string containing the vehicle's producer
     */
    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * Get the vehicle's model
     *
     * @return A string representing the vehicle's model
     */
    public String getModel() {
        return model;
    }

    /**
     * Set the vehicle's model
     *
     * @param model A string containing the vehicle's model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Get the vehicle's horsepower
     *
     * @return An integer representing the vehicle's horsepower
     */
    public int getHorsepower() {
        return horsepower;
    }

    /**
     * Set the vehicle's horsepower
     *
     * @param horsepower An integer containing the vehicle's horsepower
     */
    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    /**
     * Get the vehicle's production year
     *
     * @return A string representing the vehicle's production year
     */
    public String getProductionYear() {
        return productionYear;
    }

    /**
     * Set the vehicle's production year
     *
     * @param productionYear A string containing the vehicle's production year
     */
    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    /**
     * Get the vehicle's number plates
     *
     * @return A string representing the vehicle's number plates
     */
    public String getNumberPlate() {
        return numberPlate;
    }

    /**
     * Set the vehicle's number plates
     *
     * @param numberPlate A string containing the vehicle's number plates
     */
    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    /* Constructors */

    /**
     * Create a vehicle with empty properties
     *
     */
    public Vehicle() {
        customerId = 0;
        producer = "undefined";
        model = "undefined";
        horsepower = 0;
        productionYear = null;
        numberPlate = "undefined";
    }

    /**
     * Create a vehicle with specified properties
     *
     * @param id vehicle ID
     * @param customerId customer ID
     * @param producer vehicle producer
     * @param model vehicle model
     * @param horsepower vehicle horsepower
     * @param productionYear vehicle production year
     * @param numberPlate vehicle number plates
     */
    public Vehicle(int id, int customerId, String producer, String model, int horsepower, String productionYear, String numberPlate) {
        setValues(id, customerId, producer, model, horsepower, productionYear, numberPlate);
    }

    /**
     * Create a vehicle with specified properties and custom value
     *
     * @param id vehicle ID
     * @param customerId customer ID
     * @param producer vehicle producer
     * @param model vehicle model
     * @param horsepower vehicle horsepower
     * @param productionYear vehicle production year
     * @param numberPlate vehicle number plates
     * @param customField vehicle custom field
     */
    public Vehicle(int id, int customerId, String producer, String model, int horsepower, String productionYear, String numberPlate, String customField) {
        setValues(id, customerId, producer, model, horsepower, productionYear, numberPlate, customField);
    }

    /* Methods */

    /**
     * Set properties and custom value for specified vehicle
     *
     * @param id vehicle ID
     * @param customerId customer ID
     * @param producer vehicle producer
     * @param model vehicle model
     * @param horsepower vehicle horsepower
     * @param productionYear vehicle production year
     * @param numberPlate vehicle number plates
     * @param customField vehicle custom field
     */
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

    /**
     * Set properties for specified vehicle
     *
     * @param id vehicle ID
     * @param customerId customer ID
     * @param producer vehicle producer
     * @param model vehicle model
     * @param horsepower vehicle horsepower
     * @param productionYear vehicle production year
     * @param numberPlate vehicle number plates
     */
    public void setValues(int id, int customerId, String producer, String model, int horsepower, String productionYear, String numberPlate) {
        setId(id);
        this.customerId = customerId;
        this.producer = producer;
        this.model = model;
        this.horsepower = horsepower;
        this.productionYear = productionYear;
        this.numberPlate = numberPlate;
    }

    /**
     * Update properties for vehicle
     *
     * @param customerId customer ID
     * @param producer vehicle producer
     * @param model vehicle model
     * @param horsepower vehicle horsepower
     * @param productionYear vehicle production year
     * @param numberPlate vehicle number plates
     */
    public void updateValues(int customerId, String producer, String model, int horsepower, String productionYear, String numberPlate) {
        this.customerId = customerId;
        this.producer = producer;
        this.model = model;
        this.horsepower = horsepower;
        this.productionYear = productionYear;
        this.numberPlate = numberPlate;
    }

    /* Override Methods */

    /**
     * Get text representation of vehicle
     *
     * @return A String containing information about vehicle
     */
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

    /**
     * Check if vehicle object is empty
     *
     * @return A Boolean value that indicates the existence of the vehicle
     */
    @Override
    public Boolean isEmpty() {
        return getId() == 0;
    }
}
