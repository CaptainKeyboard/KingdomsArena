package net.velion.kingdoms_arena.arena.condition.stage.end;

import net.velion.kingdoms_arena.arena.stage.Stage;

public class OrderAchieved extends StageEndCondition
{
    public OrderAchieved(Stage stage)
    {
        super(stage);
    }

    @Override
    public boolean check()
    {
        return false;
    }

    @Override
    public void setup()
    {

    }

    @Override
    public void reset()
    {
    }
}
