package me.gamercoder215.mobchip.abstraction.v26_2;

import com.google.common.collect.ImmutableBiMap;
import me.gamercoder215.mobchip.abstraction.ChipUtilFactory;
import me.gamercoder215.mobchip.abstraction.v26_1.*;
import org.bukkit.entity.*;

public class ChipUtil26_2 extends ChipUtil26_1 {

    public static ChipUtil26_2 instance() {
        return (ChipUtil26_2) ChipUtilFactory.getChipUtil();
    }

    protected ChipUtil26_2(ImmutableBiMap.Builder<Class<? extends Entity>, Class<? extends net.minecraft.world.entity.Entity>> entityMap) {
        super(entityMap
                .put(AbstractCubeMob.class, net.minecraft.world.entity.monster.cubemob.AbstractCubeMob.class)
                .put(MagmaCube.class, net.minecraft.world.entity.monster.cubemob.MagmaCube.class)
                .put(Slime.class, net.minecraft.world.entity.monster.cubemob.Slime.class)
                .put(SulfurCube.class, net.minecraft.world.entity.monster.cubemob.SulfurCube.class)
        );
    }

    public ChipUtil26_2() {
        this(ImmutableBiMap.builder());
    }
}