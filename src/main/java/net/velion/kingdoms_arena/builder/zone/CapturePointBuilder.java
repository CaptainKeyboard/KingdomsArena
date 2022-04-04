package net.velion.kingdoms_arena.builder.zone;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.zone.ArenaLocation;
import net.velion.kingdoms_arena.arena.zone.TriggerSphere;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.CaptureEnterFunction;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.CaptureFunction;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.CaptureLeaveFunction;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.HookType;

import java.util.UUID;

public class CapturePointBuilder extends TriggerSphereBuilder
{
    private double ticks;

    public CapturePointBuilder(UUID uuid, String name, ArenaLocationBuilder arenaLocationBuilder, double radius,
                               double ticks)
    {
        super(name, arenaLocationBuilder, radius);
        this.ticks = ticks;
    }

    @Override
    public TriggerSphere build(Arena arena)
    {
        ArenaLocation arenaLocation = arenaLocationBuilder.build();

        TriggerSphere triggerSphere = new TriggerSphere(name, arena, arenaLocation, radius);

        CaptureFunction captureFunction = new CaptureFunction(triggerSphere, radius, ticks);
        CaptureEnterFunction captureEnterFunction = new CaptureEnterFunction(triggerSphere, captureFunction);
        CaptureLeaveFunction captureLeaveFunction = new CaptureLeaveFunction(triggerSphere, captureFunction);

        triggerSphere.setCaptureFunction(captureFunction);
        triggerSphere.putFunction(HookType.ENTER, captureEnterFunction);
        triggerSphere.putFunction(HookType.LEAVE, captureLeaveFunction);

        return triggerSphere;
    }
}
