package me.bredisrising.spicycoconut.customentities.bosses;

import me.bredisrising.spicycoconut.SpicyCoconut;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntitySlime;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SlimeSplitEvent;

public class KingSlime extends EntitySlime implements Listener {
    static SpicyCoconut plugin;

    public KingSlime(SpicyCoconut plugin){
        super(EntityTypes.aD, ((CraftWorld) Bukkit.getWorld("world")).getHandle());
        this.plugin = plugin;
    }

    public KingSlime(Location loc) {
        super(EntityTypes.aD, ((CraftWorld) loc.getWorld()).getHandle());
        this.b(loc.getX(), loc.getY(), loc.getZ()); // set location
        this.u(true); // is aggressive?
        this.n(true); // Custom Name Visible
        this.a(new ChatComponentText(ChatColor.GOLD + "King Slime")); // Custom Name

        this.a(30, false);

        LivingEntity entity = (LivingEntity) this.getBukkitEntity();

        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(0);
        entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(250);

        Slime e = (Slime) this.getBukkitEntity();

    }

    @EventHandler
    public void onSplit(SlimeSplitEvent e){
        if(e.getEntity().getCustomName()==null)return;
        if(e.getEntity().getCustomName().contains("King Slime")){
            e.setCancelled(true);
        }
    }


}
