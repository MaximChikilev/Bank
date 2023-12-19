package entity.exception;

public class BadAccountNumberException extends Exception{
    public BadAccountNumberException() {
    }

    public BadAccountNumberException(String message) {
        super(message);
    }

    public BadAccountNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadAccountNumberException(Throwable cause) {
        super(cause);
    }
}
