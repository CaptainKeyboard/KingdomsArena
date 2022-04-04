package net.velion.kingdoms_arena.builder.zone;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.HookType;
import net.velion.kingdoms_arena.builder.zone.triggerfunction.CaptureFunctionBuilder;
import net.velion.kingdoms_arena.builder.zone.triggerfunction.PassiveFunctionBuilder;
import net.velion.kingdoms_arena.builder.zone.triggerfunction.TriggerFunctionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ZoneBuilder
{
    protected String name;
    protected List<TriggerFunctionBuilder> enterFunctionParsers;
    protected List<TriggerFunctionBuilder> leaveFunctionParsers;
    protected List<TriggerFunctionBuilder> insideFunctionParsers;
    protected List<PassiveFunctionBuilder> passiveFunctionBuilders;
    protected List<TriggerFunctionBuilder> ownerChangeFunctionParsers;
    protected CaptureFunctionBuilder captureFunctionBuilder;
    protected boolean disabled = false;

    public ZoneBuilder(String name)
    {
        this.name = name;
        enterFunctionParsers = new ArrayList<>();
        leaveFunctionParsers = new ArrayList<>();
        insideFunctionParsers = new ArrayList<>();
        passiveFunctionBuilders = new ArrayList<>();
        ownerChangeFunctionParsers = new ArrayList<>();
    }

    public abstract Zone build(Arena arena);

    public ZoneBuilder setEnterFunctions(TriggerFunctionBuilder... functionBuilders)
    {
        this.enterFunctionParsers = List.of(functionBuilders);
        return this;
    }

    public ZoneBuilder setFunctions(Map<HookType, Set<TriggerFunctionBuilder>> functions)
    {
        if (functions.containsKey(HookType.PASSIVE))
        {
            setPassiveFunctions(functions.get(HookType.PASSIVE).toArray(PassiveFunctionBuilder[]::new));
        }
        if (functions.containsKey(HookType.INSIDE))
        {
            setInsideFunctions(functions.get(HookType.INSIDE).toArray(TriggerFunctionBuilder[]::new));
        }
        if (functions.containsKey(HookType.LEAVE))
        {
            setLeaveFunctions(functions.get(HookType.LEAVE).toArray(TriggerFunctionBuilder[]::new));
        }
        if (functions.containsKey(HookType.ENTER))
        {
            setEnterFunctions(functions.get(HookType.ENTER).toArray(TriggerFunctionBuilder[]::new));
        }
        if (functions.containsKey(HookType.OWNER_CHANGE))
        {
            setOwnerChangeFunctions(functions.get(HookType.OWNER_CHANGE).toArray(TriggerFunctionBuilder[]::new));
        }

        return this;
    }

    public ZoneBuilder setLeaveFunctions(TriggerFunctionBuilder... functionBuilders)
    {
        this.leaveFunctionParsers = List.of(functionBuilders);
        return this;
    }

    public ZoneBuilder setInsideFunctions(TriggerFunctionBuilder... functionBuilders)
    {
        this.insideFunctionParsers = List.of(functionBuilders);
        return this;
    }

    public ZoneBuilder setPassiveFunctions(PassiveFunctionBuilder... functionBuilders)
    {
        this.passiveFunctionBuilders = List.of(functionBuilders);
        return this;
    }

    public ZoneBuilder setCaptureFunction(CaptureFunctionBuilder captureFunctionBuilder)
    {
        this.captureFunctionBuilder = captureFunctionBuilder;
        return this;
    }

    public ZoneBuilder setOwnerChangeFunctions(TriggerFunctionBuilder... functionBuilders)
    {
        this.ownerChangeFunctionParsers = List.of(functionBuilders);
        return this;
    }

    public ZoneBuilder setDisabled(boolean disabled)
    {
        this.disabled = disabled;
        return this;
    }
}
