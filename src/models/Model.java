package models;

/**
 * An abstract class for database models.
 *
 */
public abstract class Model {
    /* Fields */

    private int id;
    private String customField;

    /* Getters & Setters */

    /**
     * Get the id of the database model
     *
     * @return An integer representing the id of the database model
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of the database model
     *
     * @param id An integer containing the id of the database model
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the custom field of the database model
     *
     * @return A String representing the id custom field of the database model
     */
    public String getCustomField() {
        return customField;
    }

    /**
     * Set the custom field of the database model
     *
     * @param customField A String containing the custom field of the database model
     */
    public void setCustomField(String customField) {
        this.customField = customField;
    }

    /* Override Methods */

    /**
     * Get text representation of model
     *
     * @return A String containing information about model
     */
    public abstract String toString();

    /**
     * Check if model object is empty
     *
     * @return A Boolean value that indicates the existence of the model
     */
    public abstract Boolean isEmpty();

    /** Mandatory methods to be overwritten */

    public void setValues() { throw new RuntimeException("Method not overwritten"); };
    public void updateValues() { throw new RuntimeException("Method not overwritten"); };
}
