package josh0766.exception;

public class ExternalCommmandExecutionException extends RuntimeException {
    public ExternalCommmandExecutionException () {
        super();
    }

    public ExternalCommmandExecutionException (String message) {
        super(message);
    }
}
