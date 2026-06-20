package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import me.gamercoder215.mobchip.abstraction.ChipUtilFactory;
import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.behavior.EntityBehavior;
import me.gamercoder215.mobchip.ai.memories.Memory;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

class BukkitEntityBehavior implements EntityBehavior {
    protected static final ChipUtil wrapper = ChipUtilFactory.getChipUtil();

    private final Mob m;

    protected BukkitEntityBehavior(Mob m) {
        this.m = m;
    }

    protected static void notNull(Object o, String message) {
        if (o == null) throw new IllegalArgumentException(message);
    }

    protected BehaviorResult run(String behaviorName, Object... args) {
        return wrapper.runBehavior(m, behaviorName, args);
    }

    @Override
    public @NotNull BehaviorResult backupIfClose(int min, float speedMod) {
        return run("BehaviorRetreat", min, speedMod);
    }

    @Override
    public @NotNull BehaviorResult passiveIf(@NotNull Memory<?> memory, int durationTicks) throws IllegalArgumentException {
        notNull(memory, "Memory cannot be null");
        return run("BehaviorPacify", memory, durationTicks);
    }

    @Override
    public @NotNull BehaviorResult eraseIf(@NotNull Predicate<Mob> function, @NotNull Memory<?> memory) throws IllegalArgumentException {
        notNull(function, "Function cannot be null");
        notNull(memory, "Memory cannot be null");
        return run("BehaviorRemoveMemory", function, memory);
    }

    @Override
    public @NotNull BehaviorResult moveToWantedItem(int minDist, float speedMod, boolean requireTarget) {
        return run("BehaviorFindAdmirableItem", speedMod, requireTarget, minDist);
    }

    @Override
    public @NotNull BehaviorResult jumpOnBed(float speedMod) {
        return run("BehaviorBedJump", speedMod);
    }

    @Override
    public @NotNull BehaviorResult meleeAttack(int cooldown) {
        return run("BehaviorAttack", cooldown);
    }

    @Override
    public @NotNull BehaviorResult wakeUp() {
        return run("BehaviorWake");
    }

    @Override
    public @NotNull BehaviorResult ringBell() {
        return run("BehaviorBellRing");
    }

    @Override
    public @NotNull BehaviorResult reactToBell() {
        return run("BehaviorBellAlert");
    }

    @Override
    public @NotNull BehaviorResult interactWithDoor() {
        return run("BehaviorInteractDoor");
    }

    @Override
    public @NotNull BehaviorResult sleep() {
        return run("BehaviorSleep");
    }

    @Override
    public @NotNull BehaviorResult socializeAtBell() {
        return run("BehaviorBell");
    }
}
