package net.atlaspvp.crossservertest.Listeners;

import net.atlaspvp.Objects.PlayerData;
import net.atlaspvp.crossservertest.CrossServerTest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = Bukkit.getPlayer(sender.getName());

        if (args[0].equals("hub")) {



        } else if (args[0].equals("factions")) {



        }

        PlayerData pd = new PlayerData(player);
        sender.sendMessage("Created the PlayerData for " + player.getName());
        sender.sendMessage("Serializing...");
        byte[] spd = pd.toByteArray();
        sender.sendMessage("Serialized :)");
        sender.sendMessage("Attempting DeSerialize");
        PlayerData npd = PlayerData.fromByteArray(spd);
        sender.sendMessage("Deserialized:");
        sender.sendMessage(npd.getPlayerId().toString());
        player.sendMessage(npd.getPlayerName());
        sender.sendMessage("Clearing Inv");
        player.getInventory().clear();
        sender.sendMessage("Setting Inv");
        player.getInventory().setContents(npd.getInventoryContents());

        return true;
    }
}
