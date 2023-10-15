package me.bredisrising.spicycoconut.commands;// ^ remove before submitting!

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.bredisrising.spicycoconut.SpicyCoconut;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.network.protocol.game.PacketPlayOutRespawn;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.level.EnumGamemode;
import org.bukkit.Bukkit;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class SetSkinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("setskin.use")){
            Player playerToSet = (Player) Bukkit.getPlayer(args[0]);
            GameProfile profile = ((CraftPlayer)playerToSet).getHandle().fp();
            PlayerConnection connection = ((CraftPlayer)playerToSet).getHandle().b;

            connection.a(new PacketPlayOutPlayerInfo(
                    PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e,
                    ((CraftPlayer)playerToSet).getHandle()
            ));

            profile.getProperties().removeAll("textures");
            profile.getProperties().put("textures",getSkin());

            connection.a(new PacketPlayOutPlayerInfo(
                    PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a,
                    ((CraftPlayer)playerToSet).getHandle()
            ));

            for(Player p : Bukkit.getOnlinePlayers()){
                PlayerConnection conn = ((CraftPlayer)p).getHandle().b;
                //conn.a(new PacketPlayOutEntityDestroy(playerToSet.getEntityId()));
                //conn.a(new PacketPlayOutNamedEntitySpawn(((CraftPlayer)playerToSet).getHandle()));
                Bukkit.getServer().getScheduler().runTask(SpicyCoconut.mainPlugin, () -> p.hidePlayer(playerToSet));
                Bukkit.getServer().getScheduler().runTaskLater(SpicyCoconut.mainPlugin, () -> p.showPlayer(playerToSet), 5);
            }
        }
        return false;
    }

    private static Property getSkin(){
        return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYyMjQwNzU1MjE4OSwKICAicHJvZmlsZUlkIiA6ICJmMGIzYmRkMjEwNDg0Y2VlYjZhNTQyYmZiOGEyNTdiMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBbm9uaW1ZVFQiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmMyMDJkZmY0ZmFjYzZhNTBhZDdjM2I4ZjI3NmMzZDM4ZmJmMjY2ZDdlYmJjZjdlMDhkYjE2YmEwNjYyNjExNyIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9", "QPWj154ZdjYctj9dHSEjEQweX27ro5woetNH1T0uws5kw0CXXvPXx0384udZpMSXS/3nSTZIBZF/+FF0T+di0EKTGasQlKTxDfP7wP8/RB8fwEqgnpJNfxYv+WlXjgkau/N7G3JCNJLgWQHKMNc4rhunJ0wHTcluY+7jQg+j9XwjcQJIe0ZecVXW5Ndny0rgg99dhsuo0FEJpNHBPM0Z/ulLs3A9ps8BCZyYQf64QYh5HuCv58lEho3hYCOg3VQKnX1/dWsMETJ/Q5fD0Oj2rm0OpsD1+bf1GU2cLqnrL/34e9RXRrk73s0B3Hqb9d/71hJauTd4w8gRFn0INPVD1dINXKfIDly+zQWkKVTa4yUBrW8/+7J9B/Lp7TTSP/rqbke9kcAxIl7EKe4V6apzvBGVrbLzxB/9QH9l4omMp/5rxUGKX/RX7A73Bir/3VRBsrAAU4Fdg7injUZvcyY2a4EJ/unbyjDyAEkgoLolI4prEghDS8RxE+1i7p2suRdwx3iJchgl+lMpY3uwmRba4e5QM515y5SeOlhKO69evh5qNq6ZbUhjgNRxDg7LkU/Z3Zr7FhrjXCcaP3i9cLGL1llsNViVkByjoZ6voZ4OGOFP2vsJc0/ScIL6g3X1InIi8J6y86XD+OuRA9K2EvNWzo+o9/ZzpuyfU9gP8b5zJaQ=");
    }

}
