package josh0766.exception;

public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException () {
        super();
    }

    public ContentNotFoundException (String message) {
        super(message);
    }
}
