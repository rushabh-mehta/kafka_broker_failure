package kafka;

public class PartitionId {
    private static int id = 0;

    public static int getId() {
        return id++;
    }
}
