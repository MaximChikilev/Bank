package entity.exception;

public class NoEnoughMoneyException extends Exception{
    public NoEnoughMoneyException() {
    }

    public NoEnoughMoneyException(String message) {
        super(message);
    }

    public NoEnoughMoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoEnoughMoneyException(Throwable cause) {
        super(cause);
    }
}
