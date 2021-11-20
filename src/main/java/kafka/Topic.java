package kafka;

import java.util.List;

public class Topic {
    String name;
    List<Partition> partitions;

    public Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
