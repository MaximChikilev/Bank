package entity.exception;

public class NoUserException extends Exception{
    public NoUserException() {
    }

    public NoUserException(String message) {
        super(message);
    }

    public NoUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoUserException(Throwable cause) {
        super(cause);
    }
}
