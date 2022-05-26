package pp.ua.lomax.desk.exeptions;

public enum EExceptionMessage {
    /**
     * Exception messages enum
     */
    CATEGORY_NO_SUCH("Error: no such category"),
    CATEGORY_ALREADY_EXISTS("Error: this category already exists"),
    CATEGORY_IS_PARENT("Error: You cannot delete a category, it is a parent"),
    UNAUTHORIZED("Error: Unauthorized"),
    USER_NOT_FOUND("User Not Found with username: "),
    FIELDS_MUST_NOT_BE_EMPTY("Fields must not be empty"),
    NAME_IS_ALREADY_TAKEN("This name is already taken, please try another one."),
    EMAIL_IS_ALREADY_TAKEN("This email is already taken, please try another one."),
    NO_SUCH_ROLE("No such role");


    private String message;
    EExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
