package me.bredisrising.spicycoconut.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Random;

public class CreateWorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
        if(sender.hasPermission("createworld.use")){
            WorldCreator wc = new WorldCreator("EmptyWorld");

            wc.environment(World.Environment.NORMAL);
            wc.type(WorldType.NORMAL);
            wc.seed(Bukkit.getWorld("world").getSeed());
            wc.createWorld();
            return true;
        }else{
            sender.sendMessage("fack u");
        }

        return false;
    }
}
