package kafka;


import org.apache.zookeeper.KeeperException;
import zookeeper.ZKManager;
import zookeeper.ZKManagerImpl;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Partition {
    int id;
    Topic topic;
    State state;
    boolean leader;
    Set<Partition> isr;

    public Partition(int id, Topic topic, boolean leader) {
        this.id = id;
        this.topic = topic;
        this.leader = leader;
        if(leader){
            this.isr = new HashSet<>();
        }
    }

    public void createReplica(int replicationCount, List<Broker> brokers) throws InterruptedException, KeeperException, IOException {
        ZKManager zkManager = new ZKManagerImpl();
        for(int i=0;i<replicationCount;i++){
            int id = PartitionId.getId();
            Partition p = new Partition(id, this.topic, false);
            brokers.get(i).addPartition(p);
            this.isr.add(p);
            zkManager.create("/brokers/topics/"+this.topic.getName()+"/partitions/"+id,null);
            zkManager.create("/brokers/topics/"+this.topic.getName()+"/partitions/"+id+"/state",null);
        }
    }

    @Override
    public String toString() {
        return "Partition{" +
                "id=" + id +
                ", topic=" + topic +
                ", state=" + state +
                ", leader=" + leader +
                ", isr=" + isr +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partition partition = (Partition) o;
        return id == partition.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
