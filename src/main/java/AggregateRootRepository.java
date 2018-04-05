import java.util.UUID;

public interface AggregateRootRepository<AggregateRoot> {

    void add(AggregateRoot aggregate, int expectedVersion);

    AggregateRoot withId(UUID gameId);
}
