package me.gamercoder215.mobchip.abstraction.v26_3;

import me.gamercoder215.mobchip.ai.gossip.GossipType;
import me.gamercoder215.mobchip.util.OptimizedSmallEnumSet;
import net.minecraft.DetectedVersion;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import org.bukkit.Difficulty;
import org.bukkit.entity.Cushion;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

public class TestChipUtil26_3 {

    @BeforeAll
    public static void init() {
        SharedConstants.setVersion(DetectedVersion.BUILT_IN);

        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("Test Bukkit-NMS Conversion")
    public void testNMSConversion() {
        ChipUtil26_3 wrapper = new ChipUtil26_3();
        // Entities
        for (EntityType t : EntityType.values()) {
            if (t.getEntityClass() == null) {
                continue;
            }
            Assertions.assertNotNull(wrapper.toNMS(t.getEntityClass()));
        }

        // Other
        for (Difficulty d : Difficulty.values()) Assertions.assertNotNull(wrapper.toNMS(d));
        for (GossipType t : GossipType.values()) Assertions.assertNotNull(wrapper.toNMS(t));

        Assertions.assertNotNull(wrapper.toNMS(m -> m.damage(2)));
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Test NMS-Bukkit Conversion")
    public void testBukkitConversion() {
        ChipUtil26_3 wrapper = new ChipUtil26_3();
        // For some reason if we read the registry directly, the type arguments are not available, but if we iterate
        // over the fields directly, they are.
        for (Field field : net.minecraft.world.entity.EntityType.class.getDeclaredFields()) {
            if (field.getType() != net.minecraft.world.entity.EntityType.class) {
                continue;
            }
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Assertions.assertNotNull(wrapper.fromNMS((Class<? extends Entity>)type.getActualTypeArguments()[0], org.bukkit.entity.Entity.class));
        }

        // Other
        for (net.minecraft.world.Difficulty d : net.minecraft.world.Difficulty.values()) Assertions.assertNotNull(wrapper.fromNMS(d));
        for (net.minecraft.world.entity.ai.gossip.GossipType t : net.minecraft.world.entity.ai.gossip.GossipType.values()) Assertions.assertNotNull(wrapper.fromNMS(t));
    }

    @Test
    @DisplayName("Test Renamed And Added Entities")
    public void testRenamedAndAddedEntities() {
        ChipUtil26_3 wrapper = new ChipUtil26_3();

        // net.minecraft.world.entity.monster.EnderMan was renamed to Enderman
        Assertions.assertEquals(
                net.minecraft.world.entity.monster.Enderman.class,
                wrapper.toNMS(Enderman.class)
        );
        Assertions.assertEquals(
                Enderman.class,
                wrapper.fromNMS(net.minecraft.world.entity.monster.Enderman.class, org.bukkit.entity.Entity.class)
        );

        // Cushion was added in 26.3
        Assertions.assertEquals(
                net.minecraft.world.entity.decoration.Cushion.class,
                wrapper.toNMS(Cushion.class)
        );
        Assertions.assertEquals(
                Cushion.class,
                wrapper.fromNMS(net.minecraft.world.entity.decoration.Cushion.class, org.bukkit.entity.Entity.class)
        );
    }

    @Test
    @DisplayName("Test Renamed Goals Are Wired Up")
    public void testRenamedGoals() throws Exception {
        // CatLieOnBedGoal was renamed to CatLieOnBlockGoal, and TryFindWaterGoal was replaced by the
        // fluid-tag-parameterized TryFindLiquidGoal. Both are only reachable through the hooks that
        // ChipUtil26_1 delegates to, so verify those hooks are overridden here.
        Assertions.assertDoesNotThrow(() -> ChipUtil26_3.class.getDeclaredMethod(
                "catOnBedGoal", net.minecraft.world.entity.animal.feline.Cat.class, double.class, int.class
        ));
        Assertions.assertDoesNotThrow(() -> ChipUtil26_3.class.getDeclaredMethod(
                "findWaterGoal", net.minecraft.world.entity.PathfinderMob.class
        ));

        // the goals the hooks build must exist, and the 26.1/26.2 names must not linger
        Assertions.assertNotNull(Class.forName("net.minecraft.world.entity.ai.goal.CatLieOnBlockGoal"));
        Assertions.assertNotNull(Class.forName("net.minecraft.world.entity.ai.goal.TryFindLiquidGoal"));
        Assertions.assertThrows(ClassNotFoundException.class, () -> Class.forName("net.minecraft.world.entity.ai.goal.CatLieOnBedGoal"));
        Assertions.assertThrows(ClassNotFoundException.class, () -> Class.forName("net.minecraft.world.entity.ai.goal.TryFindWaterGoal"));
    }

    @Test
    @DisplayName("Test ChipUtil26_1#getFlags")
    public void testGetFlags() {
        OptimizedSmallEnumSet<Goal.Flag> set = new OptimizedSmallEnumSet<>(Goal.Flag.class);
        set.addUnchecked(Goal.Flag.MOVE);
        set.addUnchecked(Goal.Flag.LOOK);

        Assertions.assertTrue(set.hasElement(Goal.Flag.MOVE));
        Assertions.assertTrue(set.hasElement(Goal.Flag.LOOK));

        Set<Goal.Flag> flags = new ChipUtil26_3().getFlags(set.getBackingSet());
        Assertions.assertTrue(flags.contains(Goal.Flag.MOVE));
        Assertions.assertTrue(flags.contains(Goal.Flag.LOOK));
    }

}
