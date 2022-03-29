package pp.ua.lomax.desk.exeptions;

public class NoFindRuntimeExeption extends RuntimeException{
    private String message;

    public NoFindRuntimeExeption(String message) {
        super(message);
    }
}
