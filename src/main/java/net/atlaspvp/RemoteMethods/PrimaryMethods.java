package net.atlaspvp.RemoteMethods;

import java.rmi.Remote;

public interface PrimaryMethods extends Remote { // Primary means methods performed on the Connector
    /*boolean check() throws java.rmi.RemoteException;
    void storeByte(String key, byte[] saving) throws java.rmi.RemoteException;
    void send(String endpoint, String message) throws java.rmi.RemoteException;
    byte[] getByte(String table, String key) throws java.rmi.RemoteException;
    int getInt(String key) throws java.rmi.RemoteException;
    long getLong(String key) throws java.rmi.RemoteException;
    double getDouble(String key) throws java.rmi.RemoteException;
    String getString(String key) throws java.rmi.RemoteException;*/
    byte[] getByte(String key) throws java.rmi.RemoteException;
    byte[] loadPlayer(String uuid) throws java.rmi.RemoteException;
    void storeByte(String key, byte[] saving) throws java.rmi.RemoteException;
    void syncChat(String player, String message) throws java.rmi.RemoteException;
    void connectSecondary(String name,int port) throws java.rmi.RemoteException;
    void closeSecondary(int port) throws java.rmi.RemoteException;
    boolean sendPlayer(int port, String uuid, byte[] pd) throws java.rmi.RemoteException;
    boolean exists(String key) throws java.rmi.RemoteException;

    //new methods, using schema in dc chat

    void storePlayerDataAndUnlock(String uuid, byte[] pd, String server) throws java.rmi.RemoteException;
    byte[] getPlayerDataAndLock(String uuid, String server) throws java.rmi.RemoteException;


}
