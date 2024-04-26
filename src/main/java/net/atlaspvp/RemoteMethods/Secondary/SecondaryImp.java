package net.atlaspvp.RemoteMethods.Secondary;

import net.atlaspvp.Objects.PlayerData;
import net.atlaspvp.RemoteMethods.SecondaryMethods;
import net.atlaspvp.crossservertest.CrossServerTest;
import net.atlaspvp.crossservertest.Events.MessageReceivedEvent;
import net.atlaspvp.crossservertest.Listeners.Chat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.bukkit.Bukkit;

import java.rmi.RemoteException;

public class SecondaryImp implements SecondaryMethods {

    @Override
    public void receive(String context, Object content, String sender) throws java.rmi.RemoteException {
        MessageReceivedEvent event = new MessageReceivedEvent(context, content, sender);
        event.callEvent();
    }

    @Override
    public boolean syncPlayer(String uuid, byte[] SerializedPD) throws java.rmi.RemoteException {
        PlayerData pd = PlayerData.fromByteArray(SerializedPD);
        if (pd != null) {
            Chat.onlinePlayer.put(uuid, pd);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void syncChat(String player, String message) throws RemoteException {
        Component DMessage = JSONComponentSerializer.json().deserialize(message);
        Component DPlayer = JSONComponentSerializer.json().deserialize(player);

        Bukkit.broadcast(DPlayer.append(DMessage));
    }

    @Override
    public void storeByte(String key, byte[] byteArray) throws RemoteException {
        CrossServerTest.getPrimary().storeByte(key, byteArray);
    }

    @Override
    public byte[] getByte(String key) throws RemoteException {
        return CrossServerTest.getPrimary().getByte(key);
    }

}
