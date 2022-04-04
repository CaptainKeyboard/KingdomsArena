package net.velion.kingdoms_arena.arena;

import net.velion.kingdoms_arena.arena.entity.ArenaColor;
import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.entity.Team;

import java.sql.SQLException;
import java.util.UUID;

import static net.velion.kingdoms_arena.Kingdoms_arena.ARENA_PLAYERS;

public class TeamArena extends Arena
{
    public TeamArena(UUID uuid, String name, int minPlayers)
    {
        super(uuid, name, minPlayers);
    }

    @Override
    public void setup()
    {
        super.setup();
        for (Team team : teams)
        {
            team.setup();
        }
    }

    @Override
    public void join(org.bukkit.entity.Player bukkitPlayer) throws SQLException
    {
        if (!ARENA_PLAYERS.containsKey(bukkitPlayer))
        {
            Player player = new Player(bukkitPlayer, this, ArenaColor.DEFAULT());

            players.add(player);
            ARENA_PLAYERS.put(bukkitPlayer, this);

            if (status == Status.RUNNING)
            {
                Team team = getSmallestTeam();
                team.addPlayer(player);
                player.setArenaColor(team.getArenaColor());
            }
        }
    }
}
