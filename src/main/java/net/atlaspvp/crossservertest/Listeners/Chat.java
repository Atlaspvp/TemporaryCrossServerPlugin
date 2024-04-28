package net.atlaspvp.crossservertest.Listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import it.unimi.dsi.fastutil.Pair;
import net.atlaspvp.Objects.PlayerData;
import net.atlaspvp.RemoteMethods.SecondaryMethods;
import net.atlaspvp.crossservertest.CrossServerTest;
import net.atlaspvp.crossservertest.Events.MessageReceivedEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.UUID;

public class Chat implements Listener {

    @EventHandler
    public void onMessageReceived(MessageReceivedEvent e) {
        String context = e.getContext();
        Object content = e.getContent();

        if (context.equals("chatmessage")) {
            if (content instanceof String)
                Bukkit.broadcast(ComponentSerializer.parse(content.toString()));
        }
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        String message = JSONComponentSerializer.json().serialize(e.message().color(NamedTextColor.GRAY).hoverEvent(HoverEvent.showText(Component.text("This Component Works Too"))));
        Component Cplayer = Component
                .text("┃ " + e.getPlayer().getName() + " ┃" + " ➣ ")
                .hoverEvent(HoverEvent.showText(Component.text("This Component Works")))
                .color(NamedTextColor.AQUA);
        String player = JSONComponentSerializer.json().serialize(Cplayer);
        try {
            CrossServerTest.getPrimary().syncChat(player, message);
        } catch (RemoteException ex) {
            ex.printStackTrace();
            System.out.println("You didnt successfully connect to the primary");
        }
    }

    public static HashMap<String, PlayerData> onlinePlayer = new HashMap<>();




}
