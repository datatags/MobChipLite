package me.gamercoder215.mobchip.abstraction.v26_3;

import com.google.common.collect.ImmutableBiMap;
import me.gamercoder215.mobchip.abstraction.ChipUtilFactory;
import me.gamercoder215.mobchip.abstraction.v26_2.*;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.CatLieOnBlockGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.TryFindLiquidGoal;
import org.bukkit.entity.Cushion;
import org.bukkit.entity.Entity;

public class ChipUtil26_3 extends ChipUtil26_2 {

    public static ChipUtil26_3 instance() {
        return (ChipUtil26_3) ChipUtilFactory.getChipUtil();
    }

    protected ChipUtil26_3(ImmutableBiMap.Builder<Class<? extends Entity>, Class<? extends net.minecraft.world.entity.Entity>> entityMap) {
        super(entityMap
                .put(Cushion.class, net.minecraft.world.entity.decoration.Cushion.class)
        );
    }

    public ChipUtil26_3() {
        this(ImmutableBiMap.builder());
    }

    // mojang finally fixed the casing
    @Override
    protected Class<? extends net.minecraft.world.entity.Entity> enderManClass() {
        return net.minecraft.world.entity.monster.Enderman.class;
    }

    // a bed is just a block now
    @Override
    protected Goal catOnBedGoal(net.minecraft.world.entity.animal.feline.Cat cat, double speed, int range) {
        return new CatLieOnBlockGoal(cat, speed, range);
    }

    // water is a liquid like any other, so the goal is now parameterized by a fluid tag
    @Override
    protected Goal findWaterGoal(PathfinderMob mob) {
        return new TryFindLiquidGoal(mob, FluidTags.WATER);
    }
}
