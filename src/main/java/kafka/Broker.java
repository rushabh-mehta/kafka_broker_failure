package kafka;

import java.util.ArrayList;
import java.util.List;

public class Broker {
    int id;
    Controller controller;
    List<Partition> partitions;

    public Broker(int id) {
        this.id = id;
        partitions = new ArrayList<>();
    }

    public void down(){
        // TODO add code to delete zookeeper znode
    }

//    public void up(){
//        // TODO add code to add zookeeper znode
//    }

    public void addPartition(Partition p){
        this.partitions.add(p);
    }

    @Override
    public String toString() {
        return "Broker{" +
                "id=" + id +
                ", controller=" + controller +
                ", partitions=" + partitions +
                '}';
    }
}
