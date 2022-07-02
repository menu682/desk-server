package pp.ua.lomax.desk.exeptions;

public enum EExceptionMessage {
    /**
     * Exception messages enum
     */
    CATEGORY_NO_SUCH("Error: no such category"),
    CATEGORY_ALREADY_EXISTS("Error: this category already exists"),
    CATEGORY_IS_PARENT("Error: You cannot delete a category, it is a parent"),
    CATEGORY_IS_NOT_EMPTY("Error: Category is not empty. Can not be deleted!"),
    UNAUTHORIZED("Error: Unauthorized"),
    USER_NOT_FOUND("User Not Found with username: "),
    FIELDS_MUST_NOT_BE_EMPTY("Fields must not be empty"),
    NAME_IS_ALREADY_TAKEN("This name is already taken, please try another one."),
    EMAIL_IS_ALREADY_TAKEN("This email is already taken, please try another one."),
    NO_SUCH_ROLE("No such role"),
    POST_NOT_FOUND("Post not found"),
    POST_PUT_ACCESS_IS_DENIED("Access denied, only the user who created the ad can change it!"),
    UNSUPORTED_FILE_FORMAT("Unsupported file format"),
    FILE_NOT_FOUND("File not found"),
    INVALID_FILE_NAME("Assert: File name invalid or empty!"),
    CONFIG_NOT_CHANGE("Configuration has not changed!");


    private String message;
    EExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
