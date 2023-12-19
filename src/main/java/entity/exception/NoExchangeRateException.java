package entity.exception;

public class NoExchangeRateException extends Exception{
    public NoExchangeRateException() {
    }

    public NoExchangeRateException(String message) {
        super(message);
    }

    public NoExchangeRateException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoExchangeRateException(Throwable cause) {
        super(cause);
    }
}
