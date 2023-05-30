package dev.pizzaclient.gooberlobby.utils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static dev.pizzaclient.gooberlobby.core.Main.plugin;

public class Utils implements PluginMessageListener {

    public static int hubPlayerCount = 0;
    public static int tagPlayerCount = 0;


    public static String coloredString(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void sendServer(Player player, String server) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(plugin, "BungeeCord", byteArrayOutputStream.toByteArray());
        player.sendMessage(ChatColor.GREEN + "Connecting to server....");
    }

    public static void getPlayersInServer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);

        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("PlayerCount")) {
            String server = in.readUTF(); // Name of server, as given in the arguments
            int playercount = in.readInt();
//            player.sendMessage(ChatColor.GREEN + "There are " + playercount + " players on " + server);
            if(server.equalsIgnoreCase("lobby")) {
                hubPlayerCount = playercount;
            }
            if(server.equalsIgnoreCase("tag")) {
                tagPlayerCount = playercount;
            }
        }
    }


    public static HashMap<Player, BukkitTask> scoreboardName = new HashMap<>();

    public static ScoreboardFunctions sf = new ScoreboardFunctions();

    public static void giveScoreboard(Player player) {

        final String title = coloredString("&f&lG&a&looberMC");
//        scoreboard[0].setTitle(player, utils.coloredString("&2&lGooberTag"));
        final int split = 17;

        scoreboardName.put(player, new BukkitRunnable() {
            int counter = 1;
            @Override
            public void run() {
                getPlayersInServer(player, "lobby");
                getPlayersInServer(player, "tag");
                if (this.counter < split) {
                    ++this.counter;
//                    sf.animatedScoreboard(this.counter, scoreboard, player);
                    sf.showLobbyScoreboard(player, sf.animatedScoreboard(this.counter));
                }
                else {
                    this.counter = 1;
                    sf.showLobbyScoreboard(player, title);
                }
            }

        }.runTaskTimer(plugin, 0, 5L));

    }

    public static void removeScoreboard(Player p) {
        if(scoreboardName.containsKey(p)) {
            scoreboardName.get(p).cancel();
            scoreboardName.remove(p);
        }
    }


}
