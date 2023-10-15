package me.bredisrising.spicycoconut.commands;

import me.bredisrising.spicycoconut.SpicyCoconut;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class InvisCommand implements CommandExecutor {
    static SpicyCoconut plugin;
    public InvisCommand(SpicyCoconut plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
        if(sender instanceof Player && sender.hasPermission("invis.use")){
            Player player = (Player) sender;
            Bukkit.getPlayer(args[0]).hidePlayer(plugin, player);
            return true;
        }
        return false;
    }
}
