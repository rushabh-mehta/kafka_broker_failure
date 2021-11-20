package kafka;

import org.apache.zookeeper.KeeperException;
import zookeeper.ZKManager;

import java.util.ArrayList;
import java.util.List;

public class Broker {
    int id;
    Controller controller;
    List<Partition> partitions;
    ZKManager zkManager;

    public Broker(int id, ZKManager zkManager) {
        this.id = id;
        this.zkManager = zkManager;
        partitions = new ArrayList<>();
        controller = new Controller(zkManager);
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

    @Override
    public String toString() {
        return "Broker{" +
                "id=" + id +
                ", controller=" + controller +
                ", partitions=" + partitions +
                '}';
    }
}
