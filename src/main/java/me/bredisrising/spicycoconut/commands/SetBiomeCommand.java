package me.bredisrising.spicycoconut.commands;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetBiomeCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("setbiome.use")&&sender instanceof Player) {
            Player p = (Player)sender;
            World world = Bukkit.getWorld("world");
            Chunk c = p.getLocation().getChunk();
            for(int x = 0; x < 16; x++) {
                for(int y = (int) (p.getLocation().getY()-40); y < (int)p.getLocation().getY()+40; y++) {
                    for(int z = 0; z < 16; z++) {
                        c.getBlock(x, y, z).setBiome(Biome.valueOf(args[0]));
                        Location loc = c.getBlock(x, y, z).getLocation();
                        Bukkit.broadcastMessage(loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getZ());
                    }
                }
            }
        }
        return true;
    }
}
