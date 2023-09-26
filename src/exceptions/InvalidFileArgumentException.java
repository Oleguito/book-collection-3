package exceptions;

public class InvalidFileArgumentException extends  RuntimeException {
    public InvalidFileArgumentException(String message) {
        super(message);
    }
}
