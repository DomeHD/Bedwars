package de.elwiron.bedwars.manager.scoreboard;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;


import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria.EnumScoreboardHealthDisplay;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class PacketScoreboard {

    private Player player;
    private HashMap<Integer, String> lines;
    private boolean displayed;

    public PacketScoreboard(Player p) {
        this.player = p;
        this.lines = new HashMap<Integer, String>();
        this.displayed = false;
    }

    public void sendSidebar(String displayedName) {
        if (displayed)
            return;

        PacketContainer scoreboard = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
        scoreboard.getStrings().write(0, "sideboard").write(1, displayedName);
        scoreboard.getIntegers().write(0, 0);
        scoreboard.getSpecificModifier(EnumScoreboardHealthDisplay.class).write(0, IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);

        PacketContainer playOut = new PacketContainer(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);
        playOut.getIntegers().write(0, 1);
        playOut.getStrings().write(0, "sideboard");

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, scoreboard);
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, playOut);
            displayed = true;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            displayed = false;
        }
    }

    public void remove() {

        PacketContainer unsend = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
        unsend.getStrings().write(0, "sideboard").write(1, "");
        unsend.getIntegers().write(0, 1);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, unsend);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setLine(Integer line, String text) {

        if (!displayed)
            return;

        if (text.length() > 40)
            text = text.substring(0, 40);

        PacketContainer score = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);
        score.getStrings().write(0, text).write(1, "sideboard");
        score.getIntegers().write(0, line);
        score.getSpecificModifier(PacketPlayOutScoreboardScore.EnumScoreboardAction.class).write(0, PacketPlayOutScoreboardScore.EnumScoreboardAction.CHANGE);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, score);
            lines.put(line, text);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void removeLine(Integer line) {
        if (!displayed)
            return;

        if (!lines.containsKey(line))
            return;

        String text = lines.get(line);

        PacketContainer score = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);
        score.getStrings().write(0, text).write(1, "sideboard");
        score.getIntegers().write(0, line);
        score.getSpecificModifier(PacketPlayOutScoreboardScore.EnumScoreboardAction.class).write(0, PacketPlayOutScoreboardScore.EnumScoreboardAction.REMOVE);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, score);
            lines.remove(line);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public void setName(String displayedName) {
        if (!displayed)
            return;

        PacketContainer scoreboard = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
        scoreboard.getStrings().write(0, "sideboard").write(1, displayedName);
        scoreboard.getIntegers().write(0, 2);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, scoreboard);
            displayed = true;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            displayed = false;
        }
    }

    public String getLine(Integer line) {
        return this.lines.get(line);
    }
}

