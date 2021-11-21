package kafka;

import org.apache.zookeeper.KeeperException;
import zookeeper.ZKManager;

import java.util.List;

public class Controller extends Thread{
    boolean _forever = true;
    ZKManager zkManager;
    List<Integer> brokerIds;

    public Controller(ZKManager zkManager, List<Integer> brokerIds) {
        this.zkManager = zkManager;
        this.brokerIds = brokerIds;
    }

    @Override
    public void run() {
        System.out.println("Controller started");
        try {
            zkManager.getChildren("/brokers/ids", new BrokerWatcher(zkManager,brokerIds));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(_forever) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
