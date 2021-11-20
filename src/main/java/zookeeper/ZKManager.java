package zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ZKManager {
    public void create(String path, byte[] data)
            throws KeeperException, InterruptedException;
    public Object getZNodeData(String path, boolean watchFlag) throws KeeperException, UnsupportedEncodingException, InterruptedException;
    public void update(String path, byte[] data)
            throws KeeperException, InterruptedException;
    public void delete(String path)
            throws KeeperException, InterruptedException;
    public List<String> getChildren(String path, Watcher watcher) throws KeeperException, InterruptedException;
}