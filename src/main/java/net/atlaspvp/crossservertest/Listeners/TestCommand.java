package net.atlaspvp.crossservertest.Listeners;

import net.atlaspvp.Objects.PlayerData;
import net.atlaspvp.crossservertest.CrossServerTest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String senderName = sender.getName();
        String senderUUID = Bukkit.getPlayer(senderName).getUniqueId().toString();

        try {
            byte[] SerialPlayer = CrossServerTest.getPrimary().getByte(senderUUID);
            PlayerData playerData = PlayerData.deserializeTest(SerialPlayer);

            sender.sendMessage(playerData.getName());
            sender.sendMessage(playerData.getUuid());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
