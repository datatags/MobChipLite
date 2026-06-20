package me.gamercoder215.mobchip.bukkit;

import me.gamercoder215.mobchip.abstraction.ChipUtil;
import me.gamercoder215.mobchip.abstraction.ChipUtilFactory;
import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import me.gamercoder215.mobchip.ai.behavior.DragonBehavior;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BukkitDragonBehavior extends BukkitEntityBehavior implements DragonBehavior {
    protected static final ChipUtil wrapper = ChipUtilFactory.getChipUtil();

    protected final EnderDragon m;

    public BukkitDragonBehavior(EnderDragon m) {
        super((Mob) m);
        this.m = m;
    }

    @Override
    public @NotNull BehaviorResult naturalKnockback(@Nullable List<Entity> entities) {
        if (entities != null) {
            wrapper.knockback(m, entities);
        }
        return BehaviorResult.STOPPED;
    }
}
