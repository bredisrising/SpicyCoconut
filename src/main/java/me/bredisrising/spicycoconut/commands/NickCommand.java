package me.bredisrising.spicycoconut.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player playerToNick = Bukkit.getPlayer(args[0]);
            playerToNick.setGravity(true);
            playerToNick.setDisplayName(args[1]);
        }
        return true;
    }
}
