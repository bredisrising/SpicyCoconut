package me.bredisrising.spicycoconut.customentities;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelPipeline;
import me.bredisrising.spicycoconut.SpicyCoconut;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntitySlime;
import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.util.Vector;

public class SlimySaddleMountMob extends EntitySlime implements Listener {

    static SpicyCoconut plugin;
    private double maxSpeed;
    private double speedAcceleration;

    public SlimySaddleMountMob(SpicyCoconut plugin){
        super(EntityTypes.aD, ((CraftWorld) Bukkit.getWorld("world")).getHandle());
        this.plugin = plugin;
    }

    public SlimySaddleMountMob(Location loc, String playerName, double maxSpeed, double speedAcceleration) {
        super(EntityTypes.aD, ((CraftWorld) loc.getWorld()).getHandle());

        this.maxSpeed = maxSpeed;
        this.speedAcceleration = speedAcceleration;

        this.b(loc.getX(), loc.getY(), loc.getZ()); // set location
        this.u(false); // is aggressive?
        this.n(true); // Custom Name Visible
        this.a(new ChatComponentText(ChatColor.GREEN + playerName + "'s SlimeMount")); // Custom Name

        this.a(3, false);

        Slime e = (Slime) this.getBukkitEntity();
        e.setAware(false);
    }

    @EventHandler
    public void onRideSlime(PlayerInteractEntityEvent e){
        if(e.getRightClicked().getCustomName()==null)return;
        if(e.getRightClicked().getCustomName().contains("SlimeMount")){

            e.getRightClicked().addPassenger(e.getPlayer());
        }
    }

    @EventHandler
    public void onDamager(EntityDamageByEntityEvent e){
        if(e.getDamager().getCustomName()==null)return;
        if(e.getDamager().getCustomName().contains("SlimeMount")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFall(EntityDamageEvent e){
        if(e.getEntity().getCustomName()==null)return;
        if(e.getEntity().getCustomName().contains("SlimeMount")){
            if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerRideFall(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            if(e.getEntity().getVehicle()==null) return;
            if(e.getEntity().getVehicle().getCustomName()==null) return;
            if(e.getEntity().getVehicle().getCustomName().contains("SlimeMount")){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSplit(SlimeSplitEvent e){
        if(e.getEntity().getCustomName()==null)return;
        if(e.getEntity().getCustomName().contains("SlimeMount")){
            e.setCancelled(true);
        }
    }

    @Override
    public void n(double x, double y, double z){
        if(this.cF().size()!=0){
            super.n(x,y,z);
            EntityLiving passenger = (EntityLiving) this.cF().get(0);
            Slime slime = (Slime) this.getBukkitEntity();
            this.o(passenger.dm());

            passenger.da().d();

            double magnitude = Math.sqrt(passenger.da().b * passenger.da().b + passenger.da().d * passenger.da().d);

            //Bukkit.broadcastMessage(String.valueOf(Double.valueOf(magnitude)));
            //passenger.da().d();
            if(magnitude > .010){

                Vector v = new Vector();
                v.setX(slime.getVelocity().getX()+passenger.da().b*speedAcceleration);
                v.setY(slime.getVelocity().getY());
                v.setZ(slime.getVelocity().getZ()+passenger.da().d*speedAcceleration);

                v.setX(Math.max(-maxSpeed, Math.min(maxSpeed, v.getX())));
                v.setZ(Math.max(-maxSpeed, Math.min(maxSpeed, v.getZ())));

                slime.setVelocity(v);

                if(this.z){
                    slime.setVelocity(new Vector(slime.getVelocity().getX(), 3, slime.getVelocity().getZ()));
                }else{
                    if(slime.getVelocity().getY()<0){
                        double bruh = slime.getVelocity().getY();
                        bruh -= .05;
                        slime.setVelocity(new Vector(slime.getVelocity().getX(), bruh, slime.getVelocity().getZ()));
                    }

                }
            }



            //super.n(passenger.da().b * multiplier, y, passenger.da().d * multiplier);
        }else{
            super.n(x, y ,z);
        }
    }

}
