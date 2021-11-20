package kafka;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import zookeeper.ZKManager;

import java.util.List;

public class BrokerWatcher implements Watcher {

    ZKManager zkManager;
    public BrokerWatcher(ZKManager zkManager) {
        this.zkManager = zkManager;
    }

    public void process(WatchedEvent event) {
        System.out.println("Event heard");
        try {
            List<String> testList = zkManager.getChildren(event.getPath(), new BrokerWatcher(zkManager));
            
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
