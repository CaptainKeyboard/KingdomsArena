package net.velion.kingdoms_arena.builder.zone.triggerfunction;

import net.velion.kingdoms_arena.arena.entity.score.ScoreType;
import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.AddScoreFunction;

public class AddScoreFunctionBuilder extends TriggerFunctionBuilder
{
    protected ScoreType scoreType;
    protected double value;

    public AddScoreFunctionBuilder(ScoreType scoreType, double value)
    {
        this.scoreType = scoreType;
        this.value = value;
    }

    @Override
    public AddScoreFunction build(Zone zone)
    {
        return new AddScoreFunction(zone, scoreType, value);
    }
}
