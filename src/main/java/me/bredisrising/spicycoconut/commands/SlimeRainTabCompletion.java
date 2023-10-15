package me.bredisrising.spicycoconut.commands;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;

public class SlimeRainTabCompletion implements TabCompleter{
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args){
        if(args.length == 2) {
            List<String> entityNames = new ArrayList<String>();
            for(EntityType e : EntityType.values()) {
                entityNames.add(e.name());
            }
            return entityNames;
        }
        return null;
    }
}
