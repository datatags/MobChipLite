package me.gamercoder215.mobchip.abstraction.v26_2;

import com.google.common.collect.ImmutableBiMap;
import me.gamercoder215.mobchip.abstraction.ChipUtilFactory;
import me.gamercoder215.mobchip.abstraction.v26_1.*;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class ChipUtil26_2 extends ChipUtil26_1 {

    // paper tryna outsmart me here
    private static final Class<? extends Entity> REAL_SLIME_CLASS;

    static {
        Class<?> clazz;
        try {
            clazz = Class.forName("org.bukkit.entity.Slime");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        REAL_SLIME_CLASS = (Class<? extends Entity>) clazz;
    }

    public static ChipUtil26_2 instance() {
        return (ChipUtil26_2) ChipUtilFactory.getChipUtil();
    }

    protected ChipUtil26_2(ImmutableBiMap.Builder<Class<? extends Entity>, Class<? extends net.minecraft.world.entity.Entity>> entityMap) {
        super(entityMap
                .put(AbstractCubeMob.class, net.minecraft.world.entity.monster.cubemob.AbstractCubeMob.class)
                .put(MagmaCube.class, net.minecraft.world.entity.monster.cubemob.MagmaCube.class)
                .put(REAL_SLIME_CLASS, net.minecraft.world.entity.monster.cubemob.Slime.class)
                .put(SulfurCube.class, net.minecraft.world.entity.monster.cubemob.SulfurCube.class)
        );
    }

    public ChipUtil26_2() {
        this(ImmutableBiMap.builder());
    }

    @Override
    protected GoalSelector getGoalSelector(net.minecraft.world.entity.Mob mob, boolean target) {
        return target ? mob.targetSelector : mob.getGoalSelector();
    }

    public ItemStack fromNMS(net.minecraft.world.item.ItemStack item) {
        // paper privated asBukkitCopy for some reason. Hopefully this is fine.
        return CraftItemStack.asCraftMirror(item);
    }
}