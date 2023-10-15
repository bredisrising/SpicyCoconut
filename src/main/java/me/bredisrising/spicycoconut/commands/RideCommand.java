package me.bredisrising.spicycoconut.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;

public class RideCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player cmdSender = (Player)sender;
            Player toRide = Bukkit.getPlayer(args[0]);

            toRide.addPassenger(cmdSender);

            //piggy.addPassenger(cmdSender);
        }
        return true;
    }
}
