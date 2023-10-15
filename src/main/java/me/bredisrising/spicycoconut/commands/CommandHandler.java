package me.bredisrising.spicycoconut.commands;

import me.bredisrising.spicycoconut.SpicyCoconut;
import me.bredisrising.spicycoconut.customitems.GetCustomItemCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.bredisrising.spicycoconut.megaevents.MegaEvent;
import me.bredisrising.spicycoconut.megaevents.SlimeRain;

import me.bredisrising.spicycoconut.customitems.GetCustomItemTabCompletion;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CommandHandler implements CommandExecutor {
    private SpicyCoconut plugin;
    private HashMap<String, MegaEvent> runningEvents;

    public CommandHandler(SpicyCoconut plugin) {
        this.plugin = plugin;
        runningEvents = new HashMap<String, MegaEvent>();
        plugin.getCommand("nick").setExecutor(new NickCommand());
        plugin.getCommand("setskin").setExecutor(new SetSkinCommand());
        plugin.getCommand("ride").setExecutor(new RideCommand());

        plugin.getCommand("createworld").setExecutor(new CreateWorldCommand());

        plugin.getCommand("setbiome").setExecutor(new SetBiomeCommand());
        plugin.getCommand("setbiome").setTabCompleter(new SetBiomeTabCompletion());
        plugin.getCommand("regenchunk").setExecutor(new RegenChunkCommand(this.plugin));

        plugin.getCommand("invis").setExecutor(new InvisCommand(this.plugin));

        plugin.getCommand("startevent").setExecutor(this);
        plugin.getCommand("stopevent").setExecutor(this);
        plugin.getCommand("startevent").setTabCompleter(new SlimeRainTabCompletion());

        plugin.getCommand("getitem").setExecutor(new GetCustomItemCommand());
        plugin.getCommand("getitem").setTabCompleter(new GetCustomItemTabCompletion());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        World world = Bukkit.getWorld("world");

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players may execute this command!");
            return true;
        }
        Player cmdSender = (Player) sender;
        if(!cmdSender.hasPermission("startevent.use")) {
            cmdSender.sendMessage("fuck u");
        }
        if(cmdSender.hasPermission("startevent.use")&&cmd.getName().equalsIgnoreCase("startevent")) {
            if(args[0].equalsIgnoreCase("slimerain")) {
                if(!runningEvents.containsKey("slimerain")) {
                    SlimeRain slime = new SlimeRain(plugin);
                    slime.start();
                    runningEvents.put("slimerain", slime);
                }else {
                    sender.sendMessage("Event Already Running!");
                }
            }
        }
        if(cmdSender.hasPermission("stopevent.use") && cmd.getName().equalsIgnoreCase("stopevent")) {
            if(runningEvents.containsKey(args[0])) {
                int task = runningEvents.get(args[0]).getTaskID();
                Bukkit.getServer().getScheduler().cancelTask(task);
                ((SlimeRain)runningEvents.get(args[0])).stop();
                runningEvents.get(args[0]).isRunning = false;
                runningEvents.remove(args[0]);
            }else {
                sender.sendMessage("Event Not Running!");
            }
        }
        return false;
    }
}
