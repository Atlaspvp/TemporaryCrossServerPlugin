package net.atlaspvp.crossservertest.Listeners;

import net.atlaspvp.Objects.PlayerData;
import net.atlaspvp.crossservertest.CrossServerTest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.ajg0702.queue.api.AjQueueAPI;
import us.ajg0702.queue.api.spigot.AjQueueSpigotAPI;

import java.rmi.RemoteException;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = Bukkit.getPlayer(sender.getName());

        if (args[0].equals("hub")) {
            try {
                if (CrossServerTest.getPrimary().sendPlayer(27010, player.getUniqueId().toString(), new PlayerData(player).toByteArray())) {
                    AjQueueSpigotAPI.getInstance().addToQueue(player.getUniqueId(), "hub");
                } else {
                    System.out.println("A Problem Occured Whilst Syncing Player Data To Hub");
                }
            } catch (RemoteException e){
                e.printStackTrace();
            }
        } else if (args[0].equals("factions")) {
            try {
                if (CrossServerTest.getPrimary().sendPlayer(27003, player.getUniqueId().toString(), new PlayerData(player).toByteArray())) {
                    AjQueueSpigotAPI.getInstance().addToQueue(player.getUniqueId(), "factions");
                } else {
                    System.out.println("A Problem Occured Whilst Syncing Player Data To Factions");
                }
            } catch (RemoteException e){
                e.printStackTrace();
            }
        }
        return true;
    }
}
