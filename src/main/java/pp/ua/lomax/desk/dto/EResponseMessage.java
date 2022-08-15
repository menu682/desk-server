package pp.ua.lomax.desk.dto;

public enum EResponseMessage {

    /**
     * Response message enum
     */

    REGISTER_SUCCESSFULLY("User registered successfully!"),
    CATEGORY_DELETED("Category deleted"),
    CATEGORY_IS_EMPTY("Category is empty"),
    COUNTRY_DELETED("Country deleted"),
    REGION_DELETED("Region deleted");

    private String message;

    EResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
