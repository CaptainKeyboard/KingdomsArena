package net.velion.kingdoms_arena.builder.zone;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.zone.ArenaLocation;
import net.velion.kingdoms_arena.arena.zone.TriggerSphere;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.CaptureEnterFunction;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.CaptureFunction;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.CaptureLeaveFunction;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.HookType;
import net.velion.kingdoms_arena.builder.zone.triggerfunction.PassiveFunctionBuilder;
import net.velion.kingdoms_arena.builder.zone.triggerfunction.TriggerFunctionBuilder;
import org.bukkit.World;

public class TriggerSphereBuilder extends ZoneBuilder
{
    protected World world;
    protected ArenaLocationBuilder arenaLocationBuilder;
    protected double radius;
    protected double ticks;

    public TriggerSphereBuilder(String name, ArenaLocationBuilder arenaLocationBuilder, double radius)
    {
        super(name);
        this.arenaLocationBuilder = arenaLocationBuilder;
        this.radius = radius;
    }

    @Override
    public TriggerSphere build(Arena arena)
    {
        ArenaLocation arenaLocation = arenaLocationBuilder.build();

        TriggerSphere triggerSphere = new TriggerSphere(name, arena, arenaLocation, radius);

        for (TriggerFunctionBuilder functionParser : enterFunctionParsers)
        {
            triggerSphere.putFunction(HookType.ENTER, functionParser.build(triggerSphere));
        }

        for (TriggerFunctionBuilder functionParser : leaveFunctionParsers)
        {
            triggerSphere.putFunction(HookType.LEAVE, functionParser.build(triggerSphere));
        }

        for (TriggerFunctionBuilder functionBuilder : insideFunctionParsers)
        {
            triggerSphere.putFunction(HookType.INSIDE, functionBuilder.build(triggerSphere));
        }

        for (PassiveFunctionBuilder functionBuilder : passiveFunctionBuilders)
        {
            triggerSphere.putFunction(HookType.PASSIVE, functionBuilder.build(triggerSphere));
        }

        for (TriggerFunctionBuilder functionBuilder : ownerChangeFunctionParsers)
        {
            triggerSphere.putFunction(HookType.OWNER_CHANGE, functionBuilder.build(triggerSphere));
        }

        if (captureFunctionBuilder != null)
        {
            CaptureFunction captureFunction = captureFunctionBuilder.build(triggerSphere);
            triggerSphere.setCaptureFunction(captureFunction);
            triggerSphere.putFunction(HookType.ENTER, new CaptureEnterFunction(triggerSphere, captureFunction));
            triggerSphere.putFunction(HookType.LEAVE, new CaptureLeaveFunction(triggerSphere, captureFunction));
        }

        return triggerSphere;
    }
}
