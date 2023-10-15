package me.bredisrising.spicycoconut.megaevents;

import me.bredisrising.spicycoconut.SpicyCoconut;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;

public class SlimeRain extends MegaEvent implements Listener {
    private double delaySeconds = .1f;
    private SpicyCoconut plugin;
    private ArrayList<Player> playerList;
    private Scoreboard board;

    public SlimeRain(SpicyCoconut plugin){
        this.plugin = plugin;
    }

    public void start(){
        //playerList = new ArrayList<Player>(Bukkit.getOnlinePlayers());
        playerList = new ArrayList<Player>();
        playerList.add(Bukkit.getPlayer("BredIsRising"));
        World world = Bukkit.getWorld("world");
        createScoreBoard();
        startRain(1, world);
    }

    void startRain(double delay, World world){
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        taskID = scheduler.scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                for(Player p : playerList){
                    Location playerLoc = p.getLocation();
                    playerLoc.setY(playerLoc.getY() + 100);
                    playerLoc.setX(playerLoc.getX()+(int)(Math.random() * 150 + 1)-75);
                    playerLoc.setZ(playerLoc.getZ()+(int)(Math.random() * 150 + 1)-75);
                    Slime curSlime = (Slime) world.spawnEntity(playerLoc, EntityType.SLIME);
                    curSlime.setCustomName(ChatColor.GREEN+"Slime Goon");

                }
            }
        }, 0L, (long) (delay * 20));
    }

    void createScoreBoard(){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("KingSlime", "dummy", ChatColor.GOLD+"King Slime!");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = obj.getScore(ChatColor.GREEN+"");
        score.setScore(playerList.size()+1);
        for(int i = 0; i < playerList.size(); i++){
            playerList.get(i).setMetadata("EventScore", new FixedMetadataValue(this.plugin, 0));
            String playerName = playerList.get(i).getDisplayName();
            int kills = playerList.get(i).getMetadata("EventScore").get(0).asInt();
            Score score1 = obj.getScore(playerName + " " + kills);

            score1.setScore(i);
        }

        for(Player p : playerList){
            p.setScoreboard(board);
        }

    }

    public void stop(){
        //remove scoreboard
        //just stop the event correctly
        for(Player p : Bukkit.getOnlinePlayers()){
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    @EventHandler
    public void onKillUpdateScore(EntityDeathEvent e){
        if(e.getEntity().getCustomName()==null)return;
        if(e.getEntity().getCustomName().contains(ChatColor.GREEN+"Slime Goon")){
            //Bukkit.getConsoleSender().sendMessage(e.getEntity().getKiller().getName());
            if(e.getEntity().getKiller() != null){
                Player killer = e.getEntity().getKiller();

                ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

                int oldScore = killer.getMetadata("EventScore").get(0).asInt();
                oldScore++;

                FixedMetadataValue newScore = new FixedMetadataValue(this.plugin, oldScore);
                killer.setMetadata("EventScore", newScore);

                stop();

                Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

                Objective obj = scoreboard.registerNewObjective("KingSlime", "dummy", ChatColor.GOLD+"King Slime!");

                obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                Score score = obj.getScore(ChatColor.GREEN+"");
                score.setScore(players.size()+1);


                for(int i = 0; i < players.size(); i++){
                    String playerName = players.get(i).getDisplayName();
                    int kills = players.get(i).getMetadata("EventScore").get(0).asInt();
                    Score score1 = obj.getScore(playerName + " " + kills);

                    score1.setScore(i);
                }

                for(Player p : players){
                    p.setScoreboard(scoreboard);
                }

            }
        }
    }

    @EventHandler
    public void noFall(EntityDamageEvent e){
        if(e.getEntityType().equals(EntityType.SLIME) && e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            e.setCancelled(true);
        }
    }
}
