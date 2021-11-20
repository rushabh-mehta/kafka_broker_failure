package kafka;

import org.apache.zookeeper.KeeperException;
import zookeeper.ZKManager;

public class Controller extends Thread{
    boolean _forever = true;
    ZKManager zkManager;

    public Controller(ZKManager zkManager) {
        this.zkManager = zkManager;
    }

    @Override
    public void run() {
        System.out.println("Controller started");
        try {
            zkManager.getChildren("/brokers/ids",new BrokerWatcher(zkManager));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(_forever) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
