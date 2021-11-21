package kafka;

import org.apache.zookeeper.KeeperException;
import zookeeper.ZKManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Broker {
    int id;
    Controller controller;
    List<Partition> partitions;
    ZKManager zkManager;

    public Broker(int id, ZKManager zkManager, List<Integer> brokerIds) {
        this.id = id;
        this.zkManager = zkManager;
        partitions = new ArrayList<>();
        controller = new Controller(zkManager,brokerIds);
    }

    public void down() throws InterruptedException, KeeperException {
        zkManager.delete("/brokers/ids/"+this.id);
    }

    public void up() throws InterruptedException, KeeperException {
        zkManager.create("/brokers/ids/"+this.id,null);
    }

    public void addPartition(Partition p){
        this.partitions.add(p);
    }

    public Controller getController() {
        return controller;
    }

    public List<Partition> getPartitions() {
        return partitions;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Broker{" +
                "id=" + id +
                ", controller=" + controller +
                ", partitions=" + partitions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Broker broker = (Broker) o;
        return id == broker.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
