package net.atlaspvp.RemoteMethods.Secondary;

import dev.rosewood.rosechat.api.RoseChatAPI;
import net.atlaspvp.RemoteMethods.SecondaryMethods;
import net.atlaspvp.crossservertest.CrossServerTest;
import net.atlaspvp.crossservertest.Events.MessageReceivedEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.rmi.RemoteException;

public class SecondaryImp implements SecondaryMethods {

    public void receive(String context, Object content, String sender) throws java.rmi.RemoteException {
        MessageReceivedEvent event = new MessageReceivedEvent(context, content, sender);
        event.callEvent();
    }
    public void syncPlayer(String endpoint, byte[] SerializedPD) throws java.rmi.RemoteException {
        //OnlinePlayers.syncPlayer(PlayerData.deserializePlayer(SerializedPD));
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
