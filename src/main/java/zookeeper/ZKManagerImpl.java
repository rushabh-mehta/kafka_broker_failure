package zookeeper;

import org.apache.zookeeper.*;
import zookeeper.ZKConnection;
import zookeeper.ZKManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ZKManagerImpl implements ZKManager {
    private static ZooKeeper zkeeper;
    private static ZKConnection zkConnection;

    public ZKManagerImpl() throws IOException, InterruptedException {
        initialize();
    }

    private void initialize() throws IOException, InterruptedException {
        zkConnection = new ZKConnection();
        zkeeper = zkConnection.connect("localhost");
    }

    public void closeConnection() throws InterruptedException {
        zkConnection.close();
    }

    public void create(String path, byte[] data)
            throws KeeperException,
            InterruptedException {

        zkeeper.create(
                path,
                data,
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
    }

    public Object getZNodeData(String path, boolean watchFlag)
            throws KeeperException,
            InterruptedException, UnsupportedEncodingException {

        byte[] b = null;
        b = zkeeper.getData(path, null, null);
        return new String(b, "UTF-8");
    }

    public void update(String path, byte[] data) throws KeeperException,
            InterruptedException {
        int version = zkeeper.exists(path, true).getVersion();
        zkeeper.setData(path, data, version);
    }

    public void delete(String path) throws InterruptedException, KeeperException {
        int version = zkeeper.exists(path, true).getVersion();
        ZKUtil.deleteRecursive(zkeeper,path);
    }

    public List<String> getChildren(String path, Watcher watcher) throws InterruptedException, KeeperException {
        int version = zkeeper.exists(path, true).getVersion();
        return zkeeper.getChildren(path, watcher);
    }
}