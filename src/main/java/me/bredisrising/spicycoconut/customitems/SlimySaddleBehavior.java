package me.bredisrising.spicycoconut.customitems;

import me.bredisrising.spicycoconut.SpicyCoconut;
import me.bredisrising.spicycoconut.customentities.bosses.KingSlime;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.animal.EntityPig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.bredisrising.spicycoconut.customentities.SlimySaddleMountMob;

public class SlimySaddleBehavior implements Listener {
    SpicyCoconut plugin;
    Slime slimer;

    public SlimySaddleBehavior(SpicyCoconut plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Player p = e.getPlayer();
            World world = Bukkit.getWorld("world");
            if(e.getItem() != null){
                if(e.getItem().getItemMeta().equals(ItemManager.items.get("SlimySaddle").getItemMeta())){
                    Location loc = e.getClickedBlock().getLocation();
                    loc.setY(loc.getY()+2);
                    //SlimySaddleMountMob slime = new SlimySaddleMountMob(loc, p.getDisplayName(), 30, 5);

                    KingSlime slime = new KingSlime(loc);

                    WorldServer wrld = ((CraftWorld)p.getWorld()).getHandle();
                    wrld.e(slime);

                }else if(e.getItem().getItemMeta().equals(ItemManager.items.get("SlimySpeedsterSaddle").getItemMeta())){
                    Location loc = e.getClickedBlock().getLocation();
                    loc.setY(loc.getY()+2);
                    SlimySaddleMountMob slime = new SlimySaddleMountMob(loc, p.getDisplayName(),  100, 15);

                    WorldServer wrld = ((CraftWorld)p.getWorld()).getHandle();
                    wrld.e(slime);
                }
            }
        }
    }

    @EventHandler
    public void onRightClickSlime(PlayerInteractEntityEvent e) {
        /*if(e.getRightClicked() == slimer) {
            ArmorStand as = (ArmorStand)Bukkit.getWorld("world").spawnEntity(e.getRightClicked().getLocation(), EntityType.ARMOR_STAND);
            as.setSmall(true);
            slimer.addPassenger(as);
            as.setVisible(false);
            as.addPassenger(e.getPlayer());
        }*/
    }
}
