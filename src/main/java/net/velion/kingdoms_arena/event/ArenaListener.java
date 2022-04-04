package net.velion.kingdoms_arena.event;

import net.velion.kingdoms_arena.arena.zone.triggerfunction.TriggerFunction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.sql.SQLException;

public class ArenaListener implements Listener
{
    @EventHandler
    public static void onEnterTrigger(EnterTriggerEvent event) throws SQLException
    {
        TriggerFunction function = event.triggerFunction;
        function.execute(event.getPlayers());
    }

    @EventHandler
    public static void onLeaveTrigger(LeaveTriggerEvent event) throws SQLException
    {
        TriggerFunction function = event.triggerFunction;
        function.execute(event.players);
    }

    @EventHandler
    public static void onInsideTrigger(InsideTriggerEvent event) throws SQLException
    {
        TriggerFunction function = event.triggerFunction;
        function.execute(event.players);
    }

    @EventHandler
    public static void onPassiveTrigger(PassiveTriggerEvent event) throws SQLException
    {
        TriggerFunction function = event.triggerFunction;
        function.execute(event.players);
    }

    @EventHandler
    public static void onOwnerChange(OwnerChangeEvent event) throws SQLException
    {
        TriggerFunction function = event.triggerFunction;
        function.execute(event.players);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onArenaRun(RunArenaEvent event) throws SQLException
    {
        event.getArena().startFirstRound();
    }
}
