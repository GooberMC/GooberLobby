package dev.pizzaclient.gooberlobby.utils;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import static dev.pizzaclient.gooberlobby.utils.Utils.*;
import static org.bukkit.Bukkit.getServer;

public class ScoreboardFunctions {

    public String animatedScoreboard(final int counter) {
        switch(counter) {
            case 1:
                return coloredString("&f&lG&a&looberMC");
            case 2:
                return coloredString("&2&lG&f&lo&a&loberMC");
            case 3:
                return coloredString("&2&lGo&f&lo&a&lberMC");
            case 4:
                return coloredString("&2&lGoo&f&lb&a&lerMC");
            case 5:
                return coloredString("&2&lGoob&f&le&a&lrMC");
            case 6:
                return coloredString("&2&lGoobe&f&lr&a&lMC");
            case 7:
                return coloredString("&2&lGoober&f&lM&a&lC");
            case 8:
                return coloredString("&2&lGooberM&f&lC");
            case 9:
                return coloredString("&2&lGooberMC");
            case 10:
                return coloredString("&f&lG&2&looberMC");
            case 11:
                return coloredString("&a&lG&f&lo&2&loberMC");
            case 12:
                return coloredString("&a&lGo&f&lo&2&lberMC");
            case 13:
                return coloredString("&a&lGoo&f&lb&2&lerMC");
            case 14:
                return coloredString("&a&lGoob&f&le&2&lrMC");
            case 15:
                return coloredString("&a&lGoobe&f&lr&2&lMC");
            case 16:
                return coloredString("&a&lGoober&f&lM&2&lC");
            case 17:
                return coloredString("&a&lGooberM&f&lC");

        }
        return "";
    }


    public static String getEntryFromScore(Objective o, int score) {
        if(o == null) return null;
        if(!hasScoreTaken(o, score)) return null;
        for (String s : o.getScoreboard().getEntries()) {
            if(o.getScore(s).getScore() == score) return o.getScore(s).getEntry();
        }
        return null;
    }

    public static boolean hasScoreTaken(Objective o, int score) {
        for (String s : o.getScoreboard().getEntries()) {
            if(o.getScore(s).getScore() == score) return true;
        }
        return false;
    }

    public static void replaceScore(Objective o, int score, String name) {
        if(hasScoreTaken(o, score)) {
            if(getEntryFromScore(o, score).equalsIgnoreCase(name)) return;
            if(!(getEntryFromScore(o, score).equalsIgnoreCase(name))) o.getScoreboard().resetScores(getEntryFromScore(o, score));
        }
        o.getScore(name).setScore(score);
    }


    public void showLobbyScoreboard(Player p, String title) {
//        if(initialized.contains(p.getUniqueId())) return; //Remove this line if you want, I had it in place because the server I made this for had issues creating player objects quickly onPlayerJoin, caused a fat NPE to happen, just delayed this method by a second in onPlayerJoin
        if(p.getScoreboard().equals(getServer().getScoreboardManager().getMainScoreboard())) p.setScoreboard(getServer().getScoreboardManager().getNewScoreboard()); //Per-player scoreboard, not necessary if all the same data, but we're personalizing the displayname and all
        Scoreboard score = p.getScoreboard(); //Personalized scoreboard
        Objective objective = score.getObjective(p.getName()) == null ? score.registerNewObjective(p.getName(), "dummy") : score.getObjective(p.getName()); //Per-player objectives, even though it doesn't matter what it's called since we're using per-player scoreboards.
        String displayName = title;
        objective.setDisplayName(displayName);
        replaceScore(objective, 14, "");
        replaceScore(objective, 13, "");
        replaceScore(objective, 12, "");
        replaceScore(objective, 11, "");
        replaceScore(objective, 10, "");
        replaceScore(objective, 9, "");
        replaceScore(objective, 8, "");
        replaceScore(objective, 7, coloredString("&2&lOnline Players:"));
        replaceScore(objective, 6, coloredString("&a&lHub: &f" + hubPlayerCount + "/100"));
        replaceScore(objective, 5, coloredString("&a&lTag: &f" + tagPlayerCount + "/100"));
        replaceScore(objective, 4, coloredString("&2&l  &f&l"));
        replaceScore(objective, 3, coloredString("&2&lPing: &f" + p.getPing() + "ms"));
        replaceScore(objective, 2, coloredString("&2&l   &f&l"));
        replaceScore(objective, 1, coloredString("&7Store.GooberMC.ml"));
        if(objective.getDisplaySlot() != DisplaySlot.SIDEBAR) objective.setDisplaySlot(DisplaySlot.SIDEBAR); //Vital functionality
        p.setScoreboard(score); //Vital functionality
    }

}
