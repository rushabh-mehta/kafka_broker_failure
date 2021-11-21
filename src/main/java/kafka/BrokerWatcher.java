package kafka;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.json.JSONObject;
import zookeeper.ZKManager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BrokerWatcher implements Watcher {

    ZKManager zkManager;
    List<Integer> brokerIds;

    public BrokerWatcher(ZKManager zkManager, List<Integer> brokerIds) {
        this.zkManager = zkManager;
        this.brokerIds = brokerIds;
    }

    public void process(WatchedEvent event) {
        try {
            List<String> aliveBrokers = zkManager.getChildren(event.getPath(), new BrokerWatcher(zkManager, brokerIds));
            List<Integer> deadBrokerIds = new ArrayList();
            for(Integer i : brokerIds){
                if(!aliveBrokers.contains(i.toString())){
                    deadBrokerIds.add(i);
                }
            }
            for(int i=0;i<deadBrokerIds.size();i++){
                for(int j=0;j<Kafka.brokers.size();j++){
                    if(deadBrokerIds.get(i)==Kafka.brokers.get(j).getId()){
                        Broker b = Kafka.brokers.get(j);
                        List<Partition> allPartitions = b.getPartitions();
                        List<Partition> leaderPartitions = new ArrayList<>();
                        for(Partition partition : allPartitions){
                            if(partition.leader){
                                leaderPartitions.add(partition);
                            }
                        }
                        for(Partition partition : leaderPartitions){
                            String partitionData = (String) zkManager.getZNodeData("/brokers/topics/"+partition.getTopic().getName()+"/partitions/"+partition.getId()+"/state",false);
                            JSONObject jsonObj = new JSONObject(partitionData);
                            String leader = (String) jsonObj.get("leader");
                            String isr = (String) jsonObj.get("isr");
                            isr = isr.replaceAll(leader+",","");
                            jsonObj.put("isr",isr);
                            String leader2 = isr.replaceAll("\\[","").replaceAll("\\]","");
                            jsonObj.put("leader",leader2);
                            zkManager.update("/brokers/topics/"+partition.getTopic().getName()+"/partitions/"+partition.getId()+"/state", jsonObj.toString().getBytes());
                        }
                    }
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
