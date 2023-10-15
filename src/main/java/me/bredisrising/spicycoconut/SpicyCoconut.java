package me.bredisrising.spicycoconut;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;
import io.netty.channel.*;
import me.bredisrising.spicycoconut.commands.CommandHandler;
import me.bredisrising.spicycoconut.customentities.SlimySaddleMountMob;
import me.bredisrising.spicycoconut.customentities.bosses.KingSlime;
import me.bredisrising.spicycoconut.customitems.ItemManager;
import me.bredisrising.spicycoconut.customitems.SlimySaddleBehavior;
import me.bredisrising.spicycoconut.megaevents.SlimeRain;
import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class SpicyCoconut extends JavaPlugin implements Listener, CommandExecutor {

    public static SpicyCoconut mainPlugin;

    @Override
    public void onEnable() {
        mainPlugin = this;
        getServer().getPluginManager().registerEvents(new TheCoolerTnt(this), this);
        getServer().getPluginManager().registerEvents(new SlimeRain(this), this);
        getServer().getPluginManager().registerEvents(new SlimySaddleBehavior(this), this);
        getServer().getPluginManager().registerEvents(new SlimySaddleMountMob(this), this);
        getServer().getPluginManager().registerEvents(new KingSlime(this), this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getWorlds().add((getServer().createWorld(new WorldCreator("EmptyWorld"))));

        ItemManager.init();
        new CommandHandler(this);
    }

    @EventHandler
    public void sussy(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && (e.getClickedBlock().getType() == Material.OAK_STAIRS)){
            World world = Bukkit.getWorld("world");
            Location loc = e.getClickedBlock().getLocation();

            loc.setX(loc.getX()+.5);
            loc.setY(loc.getY());
            loc.setZ(loc.getZ()+.5);

            Entity arrow = world.spawnEntity(loc, EntityType.ARROW);
            ((CraftEntity) arrow).getHandle().c(true);

            arrow.setPassenger(e.getPlayer());

        }

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && (e.getClickedBlock().getType() == Material.JUNGLE_LOG)){
            World world = Bukkit.getWorld("world");
            Location loc = e.getClickedBlock().getLocation();

            loc.setX(loc.getX()+.5);
            loc.setY(loc.getY()+.5);
            loc.setZ(loc.getZ()+.5);

            Entity arrow = world.spawnEntity(loc, EntityType.ARROW);
            ((CraftEntity) arrow).getHandle().c(true);

            arrow.setPassenger(e.getPlayer());

        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        //injectPlayer(e.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        //removePlayer(e.getPlayer());
    }

    @EventHandler
    public void diePhantom(EntitySpawnEvent e){
        if(e.getEntityType().equals(EntityType.PHANTOM)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void dontKillBred(EntityDamageByEntityEvent e){
        if(e.getEntity().getCustomName().equalsIgnoreCase("bred")){
            ((Damageable)e.getDamager()).damage(10000);
            ((Player)e.getDamager()).sendMessage(ChatColor.RED+"DONT FUCKING HIT HIM!");
            e.setCancelled(true);
        }
    }

    private void removePlayer(Player player){
        Channel channel = ((CraftPlayer) player).getHandle().b.a.k;
        channel.eventLoop().submit(()->{
            channel.pipeline().remove(player.getName());
            return null;
        });
    }

    private void injectPlayer(Player player){
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler(){
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception{
                super.channelRead(channelHandlerContext, packet);
            }

            @Override
            public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception{
                super.write(channelHandlerContext, packet, channelPromise);
            }
        };

        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().b.a.k.pipeline();
        pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);

    }

    public static boolean setSkin(GameProfile profile, UUID uuid) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                String reply = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                String skin = reply.split("\"value\":\"")[1].split("\"")[0];
                String signature = reply.split("\"signature\":\"")[1].split("\"")[0];
                profile.getProperties().put("textures", new Property("textures", skin, signature));
                return true;
            } else {
                System.out.println("Connection could not be opened (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    //BOUNCY BOW GONE FOR NOW REMAKE AS ITEM THAT DROPS FROM SKELTON
	/*@EventHandler
	public void onArrowHit(ProjectileHitEvent e) {
		World world = Bukkit.getWorld("World");
		Arrow arrowShot = (Arrow) e.getEntity();

		Entity shooter = (Entity )arrowShot.getShooter();

		if(shooter instanceof Player) {
			Player player = (Player)shooter;
			if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("dietz nuts")) {
				arrowShot.setDamage(0);
				for(Entity p : world.getNearbyEntities(arrowShot.getLocation(), 10, 10, 10)) {

					Location loc = p.getLocation();
					Vector v = loc.subtract(arrowShot.getLocation()).toVector().normalize().multiply(5);
					v.setY(5);
					p.setVelocity(v);
					p.setMetadata("doFall", new FixedMetadataValue(this, false));
					if(p.getType().equals(EntityType.BOAT)) {
						p.setGravity(false);
					}
				}
			}
		}

	}*/

}
