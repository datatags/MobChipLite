package me.gamercoder215.mobchip.abstraction.v26_1;

import me.gamercoder215.mobchip.combat.CombatEntry;
import me.gamercoder215.mobchip.combat.EntityCombatTracker;
import me.gamercoder215.mobchip.util.StackTraceLogger;
import net.minecraft.world.damagesource.CombatTracker;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
final class EntityCombatTracker26_1 implements EntityCombatTracker {

    private final CombatTracker handle;
    private final Mob m;

    public EntityCombatTracker26_1(Mob m) {
        this.m = m;
        this.handle = ChipUtil26_1.toNMS(m).getCombatTracker();
    }

    @Override
    public @NotNull String getCurrentDeathMessage() {
        return handle.getDeathMessage().getString();
    }

    private List<net.minecraft.world.damagesource.CombatEntry> getEntriesNMS() {
        try {
            Field entriesF = CombatTracker.class.getDeclaredField("entries");
            entriesF.setAccessible(true);
            return (List<net.minecraft.world.damagesource.CombatEntry>) entriesF.get(handle);
        } catch (ReflectiveOperationException e) {
            StackTraceLogger.printStackTrace(e);
            return null;
        }
    }

    @Override
    public @Nullable CombatEntry getLatestEntry() {
        var entries = getEntriesNMS();
        return entries.isEmpty() ? null : ChipUtil26_1.fromNMS(m, entries.getLast());
    }

    @Override
    public @NotNull List<CombatEntry> getCombatEntries() {
        List<CombatEntry> entries = new ArrayList<>();
        getEntriesNMS().stream().map(en -> ChipUtil26_1.fromNMS(m, en)).forEach(entries::add);
        return entries;
    }

    @Override
    public void recordEntry(@NotNull CombatEntry entry) {
        if (entry == null) return;
        getEntriesNMS().add(ChipUtil26_1.toNMS(entry));
    }

    @Override
    public int getCombatDuration() {
        return handle.getCombatDuration();
    }

    @Override
    public boolean isTakingDamage() {
        try {
            Field takingDamageF = CombatTracker.class.getDeclaredField("takingDamage");
            takingDamageF.setAccessible(true);
            return takingDamageF.getBoolean(handle);
        } catch (ReflectiveOperationException e) {
            StackTraceLogger.printStackTrace(e);
            return false;
        }
    }

    @Override
    public boolean isInCombat() {
        try {
            Field inCombatF = CombatTracker.class.getDeclaredField("inCombat");
            inCombatF.setAccessible(true);
            return inCombatF.getBoolean(handle);
        } catch (ReflectiveOperationException e) {
            StackTraceLogger.printStackTrace(e);
            return false;
        }
    }

    @Override
    public boolean hasLastDamageCancelled() {
        return ChipUtil26_1.toNMS(m).lastDamageCancelled;
    }
}
