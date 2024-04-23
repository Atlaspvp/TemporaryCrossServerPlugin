package net.atlaspvp.RemoteMethods;

import java.rmi.Remote;

public interface PrimaryMethods extends Remote { // Primary means methods performed on the Connector
    byte[] getByte(String key) throws java.rmi.RemoteException;
    void storeByte(String key, byte[] saving) throws java.rmi.RemoteException;
    void syncChat(String player, String message) throws java.rmi.RemoteException;
    void connectSecondary(String name,int port) throws java.rmi.RemoteException;
    void closeSecondary(int port) throws java.rmi.RemoteException;
}
