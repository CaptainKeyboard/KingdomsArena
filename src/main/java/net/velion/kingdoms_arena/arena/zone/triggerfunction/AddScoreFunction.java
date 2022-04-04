package net.velion.kingdoms_arena.arena.zone.triggerfunction;

import net.velion.core.NoNullArrayList;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.entity.Team;
import net.velion.kingdoms_arena.arena.entity.score.ScoreType;
import net.velion.kingdoms_arena.arena.zone.Zone;

import java.util.List;
import java.util.Set;

public class AddScoreFunction extends TriggerFunction
{
    protected ScoreType scoreType;
    protected double value;

    public AddScoreFunction(Zone zone, ScoreType scoreType, double value)
    {
        super(zone);
        this.scoreType = scoreType;
        this.value = value;
    }

    @Override
    protected void _execute(Set<Player> players)
    {
        List<Player> playerList = new NoNullArrayList<>(players);

        switch (executeOnceFor)
        {
            case ALL:
                // TODO For all players? For each team? WHAT?
                break;
            case TEAM:
                for (int i = 0; i < playerList.size(); i++)
                {
                    Player player = playerList.get(i);
                    Team team = player.getTeam();
                    if (team != null && !isRegistered(team))
                    {
                        attachEntity(team);
                        __execute(team, i <= playerList.size());
                    }
                }
                break;
            case PLAYER:
                for (int i = 0; i < playerList.size(); i++)
                {
                    Player player = playerList.get(i);
                    if (!isRegistered(player))
                    {
                        attachEntity(player);
                        __execute(player, i <= playerList.size());
                    }
                }
                break;
            default:
                for (int i = 0; i < playerList.size(); i++)
                {
                    Player player = playerList.get(i);
                    __execute(player, i <= playerList.size());
                }
        }
    }

    private void __execute(ArenaEntity entity, boolean isLastEntity)
    {
        entity.getScoreEntry().addScore(scoreType, value);
        entity.getScoreEntry().updateScoreboard(isLastEntity);
    }
}
