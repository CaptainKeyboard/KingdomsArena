package net.velion.kingdoms_arena.builder.zone.triggerfunction;

import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.CaptureFunction;

public class CaptureFunctionBuilder
{
    protected double radius;
    protected double ticks;

    public CaptureFunctionBuilder(double radius, double ticks)
    {
        this.radius = radius;
        this.ticks = ticks;
    }

    public CaptureFunction build(Zone zone)
    {
        CaptureFunction captureFunction = new CaptureFunction(zone, radius, ticks);

        return captureFunction;
    }
}
