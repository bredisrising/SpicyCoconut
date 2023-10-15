package me.bredisrising.spicycoconut.commands;

import jdk.javadoc.internal.doclint.HtmlTag;
import me.bredisrising.spicycoconut.SpicyCoconut;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class RegenChunkCommand implements CommandExecutor {

    static SpicyCoconut plugin;

    public RegenChunkCommand(SpicyCoconut plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player && sender.hasPermission("regenchunk.use")){
            Player player = (Player) sender;

            doRegenChunk(player.getLocation().getChunk(), player);

            return true;
        }
        return false;
    }

    void doRegenChunk(Chunk c, Player p){
        World emptyWorld = Bukkit.getWorld("EmptyWorld");
        World newWorld = Bukkit.getWorld("world");

        p.sendMessage(String.valueOf(emptyWorld.getSeed()));
        p.sendMessage(String.valueOf(newWorld.getSeed()));

        new BukkitRunnable(){
            @Override
            public void run(){
                Chunk cc = newWorld.getChunkAt(c.getX()+0, c.getZ()+0);
                for(int x = 0; x < 16; x++){
                    for(int y = -64; y < 320; y++){
                        for(int z = 0; z < 16; z++){
                            Location loc = cc.getBlock(x, y, z).getLocation();
                            Material ogBlock = emptyWorld.getBlockAt(loc).getType();
                            newWorld.getBlockAt(loc).setType(ogBlock);
                        }
                    }
                }
                this.cancel();
            }
        }.runTask(plugin);
    }

}
