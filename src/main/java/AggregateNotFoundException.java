public class AggregateNotFoundException extends RuntimeException {

    public AggregateNotFoundException() {
        super("No aggregate found");
    }
}