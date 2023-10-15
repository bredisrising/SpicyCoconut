package me.bredisrising.spicycoconut.customitems;
import java.util.ArrayList;
import java.util.List;

import me.bredisrising.spicycoconut.customitems.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;


public class GetCustomItemTabCompletion implements TabCompleter{
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args){
        if(args.length == 1) {
            List<String> itemNames = new ArrayList<String>();
            for(String e : ItemManager.items.keySet()) {
                itemNames.add(e);
            }
            return itemNames;
        }
        return null;
    }
}
