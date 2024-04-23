package net.atlaspvp.RemoteMethods;

import java.rmi.Remote;

public interface SecondaryMethods extends Remote { // Secondary referencing this plugin
    void receive(String context, Object content, String sender) throws java.rmi.RemoteException;
    void storeByte(String key, byte[] byteArray) throws java.rmi.RemoteException;
    byte[] getByte(String key) throws java.rmi.RemoteException;
    void syncChat(String player, String message) throws java.rmi.RemoteException;
}
