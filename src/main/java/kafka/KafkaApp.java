package kafka;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZKUtil;
import org.json.JSONObject;
import zookeeper.ZKConnection;
import zookeeper.ZKManager;
import zookeeper.ZKManagerImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KafkaApp {
    public static void main(String[] args) throws InterruptedException, KeeperException, IOException {
        ZKManager zkManager = new ZKManagerImpl();
        zkManager.delete("/brokers");
        zkManager.create("/brokers",null);
        zkManager.create("/brokers/ids",null);
        zkManager.create("/brokers/topics",null);

        Broker b1 = new Broker(0, zkManager);
        zkManager.create("/brokers/ids/0",null);
        Broker b2 = new Broker(1, zkManager);
        zkManager.create("/brokers/ids/1",null);
        Topic t1 = new Topic("topic1");
        zkManager.create("/brokers/topics/topic1",null);
        Topic t2 = new Topic("topic2");
        zkManager.create("/brokers/topics/topic2",null);

        int id;
        zkManager.create("/brokers/topics/topic1/partitions",null);
        id = PartitionId.getId();
        Partition t1l1 = new Partition(id,t1,true);
        zkManager.create("/brokers/topics/topic1/partitions/"+id,null);
//        zkManager.create("/brokers/topics/topic1/partitions/"+id+"/state",null);
        id = PartitionId.getId();
        Partition t1l2 = new Partition(id,t1,true);
        zkManager.create("/brokers/topics/topic1/partitions/"+id,null);
//        zkManager.create("/brokers/topics/topic1/partitions/"+id+"/state",null);
        id = PartitionId.getId();
        Partition t1l3 = new Partition(id,t1,true);
        zkManager.create("/brokers/topics/topic1/partitions/"+id,null);
//        zkManager.create("/brokers/topics/topic1/partitions/"+id+"/state",null);

        zkManager.create("/brokers/topics/topic2/partitions",null);
        id = PartitionId.getId();
        Partition t2l1 = new Partition(id,t2,true);
        zkManager.create("/brokers/topics/topic2/partitions/"+id,null);
//        zkManager.create("/brokers/topics/topic2/partitions/"+id+"/state",null);
        id = PartitionId.getId();
        Partition t2l2 = new Partition(id,t2,true);
        zkManager.create("/brokers/topics/topic2/partitions/"+id,null);
//        zkManager.create("/brokers/topics/topic2/partitions/"+id+"/state",null);
        id = PartitionId.getId();
        Partition t2l3 = new Partition(id,t2,true);
        zkManager.create("/brokers/topics/topic2/partitions/"+id,null);
//        zkManager.create("/brokers/topics/topic2/partitions/"+id+"/state",null);

        b1.addPartition(t1l1);
        b1.addPartition(t1l2);
        b1.addPartition(t1l3);
        b2.addPartition(t2l1);
        b2.addPartition(t2l2);
        b2.addPartition(t2l3);

        List<Broker> brokerList = new ArrayList<>();

        brokerList.add(b1);
        t2l1.createReplica(1, brokerList);
        t2l2.createReplica(1, brokerList);
        t2l3.createReplica(1, brokerList);
        brokerList.clear();

        brokerList.add(b2);
        t1l1.createReplica(1, brokerList);
        t1l2.createReplica(1, brokerList);
        t1l3.createReplica(1, brokerList);
        brokerList.clear();
        String data = new JSONObject().put("isr","[0,6]").put("leader","0").toString();
        zkManager.create("/brokers/topics/topic1/partitions/0/state", data.getBytes());
        data = new JSONObject().put("isr","[1,7]").put("leader","1").toString();
        zkManager.create("/brokers/topics/topic1/partitions/1/state",data.getBytes());
        data = new JSONObject().put("isr","[2,8]").put("leader","2").toString();
        zkManager.create("/brokers/topics/topic1/partitions/2/state",data.getBytes());
        data = new JSONObject().put("isr","[3,9]").put("leader","3").toString();
        zkManager.create("/brokers/topics/topic2/partitions/3/state",data.getBytes());
        data = new JSONObject().put("isr","[4,10]").put("leader","4").toString();
        zkManager.create("/brokers/topics/topic2/partitions/4/state",data.getBytes());
        data = new JSONObject().put("isr","[5,11]").put("leader","5").toString();
        zkManager.create("/brokers/topics/topic2/partitions/5/state",data.getBytes());
        data = new JSONObject().put("isr","[0,6]").put("leader","0").toString();
        zkManager.create("/brokers/topics/topic2/partitions/6/state",data.getBytes());
        data = new JSONObject().put("isr","[1,7]").put("leader","1").toString();
        zkManager.create("/brokers/topics/topic2/partitions/7/state",data.getBytes());
        data = new JSONObject().put("isr","[2,8]").put("leader","2").toString();
        zkManager.create("/brokers/topics/topic2/partitions/8/state",data.getBytes());
        data = new JSONObject().put("isr","[3,9]").put("leader","3").toString();
        zkManager.create("/brokers/topics/topic1/partitions/9/state",data.getBytes());
        data = new JSONObject().put("isr","[4,10]").put("leader","4").toString();
        zkManager.create("/brokers/topics/topic1/partitions/10/state",data.getBytes());
        data = new JSONObject().put("isr","[5,11]").put("leader","5").toString();
        zkManager.create("/brokers/topics/topic1/partitions/11/state",data.getBytes());
        System.out.println(b1);
        System.out.println(b2);
        b1.getController().start();
        b2.down();
        b1.down();
    }
}
