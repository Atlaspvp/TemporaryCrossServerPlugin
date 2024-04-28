package net.atlaspvp.crossservertest.Listeners;

import net.atlaspvp.Objects.PlayerData;
import net.atlaspvp.crossservertest.Configuration;
import net.atlaspvp.crossservertest.CrossServerTest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.UUID;

public class PlayerConnections implements Listener {

    private static final HashMap<UUID, PlayerData> localCache = new HashMap<>();

    @EventHandler
    public void onPreJoin(AsyncPlayerPreLoginEvent e) {
        //before the player is allowed to join the network we get the pd from the rsi server and cache it locally
        UUID uuid = e.getUniqueId();
        byte[] bytes = null;
        try {
            bytes = CrossServerTest.getPrimary().getPlayerDataAndLock(uuid.toString(), Configuration.getServerName());
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
        if(bytes==null) {
            //new player/no info found
            return;
        }
        PlayerData pd = PlayerData.fromByteArray(bytes);
        localCache.put(uuid, pd);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        //when the player actually joins we set the locally cached pd to the player
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        PlayerData pd = localCache.get(uuid);
        pd.applyData();
        localCache.remove(uuid);
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        //when the player disconnects we save the pd to the rsi server
        Player player = e.getPlayer();
        PlayerData pd = new PlayerData(player);
        byte[] serializedPD = pd.toByteArray();
        try {
            CrossServerTest.getPrimary().storePlayerDataAndUnlock(player.getUniqueId().toString(), serializedPD, Configuration.getServerName());
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }


    }



}
