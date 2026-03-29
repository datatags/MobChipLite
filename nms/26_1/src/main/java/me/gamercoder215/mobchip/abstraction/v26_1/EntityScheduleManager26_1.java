package me.gamercoder215.mobchip.abstraction.v26_1;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import me.gamercoder215.mobchip.ai.schedule.Activity;
import me.gamercoder215.mobchip.ai.schedule.EntityScheduleManager;
import me.gamercoder215.mobchip.ai.schedule.Schedule;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
final class EntityScheduleManager26_1 implements EntityScheduleManager {

    private final net.minecraft.world.entity.Mob nmsMob;

    public EntityScheduleManager26_1(Mob m) {
        this.nmsMob = ChipUtil26_1.toNMS(m);
    }


    @Override
    public @Nullable Schedule getCurrentSchedule() {
        return null;
    }

    @Override
    public void setSchedule(@NotNull Schedule s) {
    }

    @Override
    public @NotNull Set<Activity> getActiveActivities() {
        return nmsMob.getBrain().getActiveActivities().stream().map(ChipUtil26_1::fromNMS).collect(Collectors.toSet());
    }

    @Override
    public void setDefaultActivity(@NotNull Activity a) {
        nmsMob.getBrain().setDefaultActivity(ChipUtil26_1.toNMS(a));
    }

    @Override
    public void useDefaultActivity() {
        nmsMob.getBrain().useDefaultActivity();
    }

    @Override
    public void setRunningActivity(@NotNull Activity a) {
        nmsMob.getBrain().setActiveActivityIfPossible(ChipUtil26_1.toNMS(a));
    }

    @Override
    public @Nullable Activity getRunningActivity() {
        return nmsMob.getBrain().getActiveNonCoreActivity().isPresent() ? ChipUtil26_1.fromNMS(nmsMob.getBrain().getActiveNonCoreActivity().get()) : null;
    }

    @Override
    public boolean isRunning(@NotNull Activity a) {
        return nmsMob.getBrain().isActive(ChipUtil26_1.toNMS(a));
    }

    @Override
    public int size() {
        return nmsMob.getBrain().getRunningBehaviors().size();
    }

    @Override
    public boolean isEmpty() {
        return nmsMob.getBrain().getRunningBehaviors().isEmpty();
    }

    @Nullable
    @Override
    public Consumer<Mob> put(@NotNull Activity key, Consumer<Mob> value) {
        nmsMob.getBrain().addActivity(ChipUtil26_1.toNMS(key), ImmutableList.of(Pair.of(0, ChipUtil26_1.toNMS(value))), Collections.emptySet(), Collections.emptySet());
        return value;
    }

    @Override
    public void clear() {
        nmsMob.getBrain().removeAllBehaviors();
    }

}
