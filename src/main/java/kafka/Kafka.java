package kafka;

import java.util.ArrayList;
import java.util.List;

public class Kafka {
    static List<Broker> brokers  = new ArrayList<>();

    public Kafka() {
    }

    public static void addBroker(Broker broker){
        brokers.add(broker);
    }
}
