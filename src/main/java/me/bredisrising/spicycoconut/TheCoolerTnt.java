package me.bredisrising.spicycoconut;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.util.Vector;

public class TheCoolerTnt implements Listener {
    private SpicyCoconut plugin;

    public TheCoolerTnt(SpicyCoconut _plugin){
        this.plugin = _plugin;
    }

    @EventHandler
    public void onEntityPrime(ExplosionPrimeEvent e){
        if(e.getEntity().getType() == EntityType.PRIMED_TNT){
            //multiplier for tnt
            e.setRadius(e.getRadius() * 2);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e){
        Vector ePos = e.getLocation().toVector();
        for(int i = 0; i < e.blockList().size(); i+=3){
            Block block = e.blockList().get(i);
            Material blockType = block.getBlockData().getMaterial();
            if(blockType != Material.GRAVEL && blockType != Material.TNT){
                FallingBlock fb = block.getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
                fb.setDropItem(false);

                Vector pos = fb.getLocation().toVector().subtract(ePos).normalize();
                pos.setY(Math.abs(pos.getY()));
                fb.setVelocity(pos);

                //fb.setGravity(false);
                //Vector pos = new Vector(0, 1, 0);
                //pos.setY(Math.abs(pos.getY()));
                //fb.setVelocity(pos);

                block.setType(Material.AIR);
            }
        }
    }

}
