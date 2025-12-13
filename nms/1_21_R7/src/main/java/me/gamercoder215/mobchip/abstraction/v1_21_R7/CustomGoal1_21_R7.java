package me.gamercoder215.mobchip.abstraction.v1_21_R7;

import me.gamercoder215.mobchip.ai.goal.CustomPathfinder;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.Arrays;
import java.util.EnumSet;

final class CustomGoal1_21_R7 extends Goal {

    private final CustomPathfinder p;

    public CustomGoal1_21_R7(CustomPathfinder p) {
        this.p = p;
        EnumSet<Flag> set = EnumSet.noneOf(Goal.Flag.class);
        Arrays.stream(p.getFlags()).map(ChipUtil1_21_R7::toNMS).forEach(set::add);
        setFlags(set);
    }

    @Override
    public boolean canUse() {
        return p.canStart();
    }
    @Override
    public boolean canContinueToUse() {
        return p.canContinueToUse();
    }
    @Override
    public boolean isInterruptable() {
        return p.canInterrupt();
    }

    @Override
    public void start() {
        p.start();
    }

    @Override
    public void tick() {
        p.tick();
    }

    @Override
    public void stop() {
        p.stop();
    }

    public CustomPathfinder getPathfinder() {
        return p;
    }
}
