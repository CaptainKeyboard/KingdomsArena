package net.velion.kingdoms_arena.arena.objective;

import net.velion.kingdoms_arena.arena.ArenaInvalidException;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.score.ScoreType;

import java.util.UUID;

public class KillObjective extends NonUniqueObjective
{
    protected Double kills;

    public KillObjective(UUID uuid, ArenaEntity operator, double kills)
    {
        super(uuid, operator);
        this.kills = kills;
    }

    @Override
    public void enable()
    {

    }

    @Override
    public void disable()
    {

    }

    @Override
    public void validate() throws ArenaInvalidException
    {
        super.validate();
    }

    @Override
    public boolean check()
    {
        return operator.getScoreEntry().getScore(ScoreType.KILLS) >= kills;
    }

    @Override
    public String toString()
    {
        return "Kills: " + operator.getScoreEntry().getScore(ScoreType.KILLS) + " / " + kills;
    }
}
