package me.bredisrising.spicycoconut.commands;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class SetBiomeTabCompletion implements TabCompleter{
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args){
        if(args.length == 1) {
            List<String> biomeNames = new ArrayList<String>();
            for(Biome e : Biome.values()) {
                biomeNames.add(e.name());
            }
            return biomeNames;
        }
        return null;
    }
}
