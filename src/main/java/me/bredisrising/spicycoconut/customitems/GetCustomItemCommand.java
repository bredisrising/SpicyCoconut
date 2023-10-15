package me.bredisrising.spicycoconut.customitems;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetCustomItemCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player))return true;
        if(!sender.hasPermission("getitem.use"))return false;

        Player p = (Player) sender;
        if(ItemManager.items.containsKey(args[0])) {
            p.getInventory().addItem(ItemManager.items.get(args[0]));
        }
        return false;
    }
}
