package net.velion.kingdoms_arena.arena.condition.stage.end;

import net.velion.kingdoms_arena.arena.condition.Condition;
import net.velion.kingdoms_arena.arena.stage.Stage;

public abstract class StageEndCondition extends Condition
{
    protected Stage stage;

    public Stage getStage()
    {
        return stage;
    }

    public StageEndCondition(Stage stage)
    {
        this.stage = stage;
    }

    public abstract boolean check();
}
