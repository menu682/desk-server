package pp.ua.lomax.desk.exeptions;

public enum EMessage {

    CATEGORY_NO_SUCH("Error: no such category"),
    CATEGORY_ALREADY_EXISTS("Error: this category already exists"),
    CATEGORY_IS_PARENT("Error: You cannot delete a category, it is a parent");


    private String message;
    EMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
