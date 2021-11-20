package zookeeper;

import org.apache.zookeeper.KeeperException;

import java.io.UnsupportedEncodingException;

public interface ZKManager {
    public void create(String path, byte[] data)
            throws KeeperException, InterruptedException;
    public Object getZNodeData(String path, boolean watchFlag) throws KeeperException, UnsupportedEncodingException, InterruptedException;
    public void update(String path, byte[] data)
            throws KeeperException, InterruptedException;
    public void delete(String path)
            throws KeeperException, InterruptedException;
}